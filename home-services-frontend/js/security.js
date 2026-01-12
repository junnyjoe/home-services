/**
 * HOME SERVICES - Security Module
 * Protection XSS, CSRF, validation des entrées, et rate limiting
 */

const Security = {
    // Configuration
    config: {
        maxLoginAttempts: 5,
        lockoutDuration: 15 * 60 * 1000, // 15 minutes
        tokenRefreshInterval: 4 * 60 * 1000, // 4 minutes (refresh avant expiration 5min)
        sessionTimeout: 30 * 60 * 1000, // 30 minutes d'inactivité
        csrfTokenName: 'X-CSRF-Token'
    },

    // État
    state: {
        loginAttempts: {},
        lastActivity: Date.now(),
        csrfToken: null,
        activityTimer: null
    },

    /**
     * Initialisation du module sécurité
     */
    init() {
        this.generateCsrfToken();
        this.startActivityMonitor();
        this.initTokenRefresh();
        this.preventXSSInStorage();
        console.log('🔒 Security module initialized');
    },

    // ==================== XSS PROTECTION ====================

    /**
     * Nettoie une chaîne pour prévenir XSS
     */
    sanitizeHTML(str) {
        if (!str) return '';
        const div = document.createElement('div');
        div.textContent = str;
        return div.innerHTML;
    },

    /**
     * Nettoie un objet complet
     */
    sanitizeObject(obj) {
        if (typeof obj !== 'object' || obj === null) {
            return typeof obj === 'string' ? this.sanitizeHTML(obj) : obj;
        }

        if (Array.isArray(obj)) {
            return obj.map(item => this.sanitizeObject(item));
        }

        const sanitized = {};
        for (const [key, value] of Object.entries(obj)) {
            sanitized[this.sanitizeHTML(key)] = this.sanitizeObject(value);
        }
        return sanitized;
    },

    /**
     * Crée un élément texte sécurisé
     */
    createSafeElement(tag, text, attributes = {}) {
        const el = document.createElement(tag);
        el.textContent = text; // textContent est sécurisé contre XSS

        for (const [key, value] of Object.entries(attributes)) {
            if (key.startsWith('on')) continue; // Bloquer les event handlers
            el.setAttribute(this.sanitizeHTML(key), this.sanitizeHTML(value));
        }

        return el;
    },

    /**
     * Empêche les attaques via localStorage
     */
    preventXSSInStorage() {
        const originalSetItem = Storage.prototype.setItem;
        const self = this;

        Storage.prototype.setItem = function (key, value) {
            // Ne pas sanitizer le token JWT ou les données JSON
            if (key === 'token' || key === 'refreshToken') {
                return originalSetItem.call(this, key, value);
            }

            try {
                // Si c'est du JSON, parser et sanitizer
                const parsed = JSON.parse(value);
                const sanitized = self.sanitizeObject(parsed);
                return originalSetItem.call(this, key, JSON.stringify(sanitized));
            } catch {
                // Sinon, sanitizer directement
                return originalSetItem.call(this, key, self.sanitizeHTML(value));
            }
        };
    },

    // ==================== INPUT VALIDATION ====================

    /**
     * Valide un email
     */
    isValidEmail(email) {
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        return emailRegex.test(email) && email.length <= 254;
    },

    /**
     * Valide un mot de passe
     */
    isValidPassword(password) {
        return password.length >= 8 &&
            /[A-Z]/.test(password) &&  // Au moins une majuscule
            /[a-z]/.test(password) &&  // Au moins une minuscule
            /[0-9]/.test(password);    // Au moins un chiffre
    },

    /**
     * Valide un numéro de téléphone
     */
    isValidPhone(phone) {
        const phoneRegex = /^[\d\s\-\+\(\)]{8,20}$/;
        return phoneRegex.test(phone);
    },

    /**
     * Valide un montant
     */
    isValidAmount(amount) {
        const num = parseFloat(amount);
        return !isNaN(num) && num > 0 && num <= 10000000;
    },

    /**
     * Nettoie les entrées dangereuses
     */
    sanitizeInput(input, type = 'text') {
        if (!input) return '';

        let sanitized = String(input).trim();

        // Supprimer les caractères de contrôle
        sanitized = sanitized.replace(/[\x00-\x1F\x7F]/g, '');

        switch (type) {
            case 'email':
                sanitized = sanitized.toLowerCase();
                break;
            case 'phone':
                sanitized = sanitized.replace(/[^\d\s\-\+\(\)]/g, '');
                break;
            case 'number':
                sanitized = sanitized.replace(/[^\d.,\-]/g, '');
                break;
            case 'alphanumeric':
                sanitized = sanitized.replace(/[^a-zA-Z0-9\s]/g, '');
                break;
            case 'text':
            default:
                // Supprimer les tags HTML potentiels
                sanitized = this.sanitizeHTML(sanitized);
        }

        return sanitized;
    },

    // ==================== CSRF PROTECTION ====================

    /**
     * Génère un token CSRF
     */
    generateCsrfToken() {
        const array = new Uint8Array(32);
        crypto.getRandomValues(array);
        this.state.csrfToken = Array.from(array, b => b.toString(16).padStart(2, '0')).join('');
        sessionStorage.setItem('csrfToken', this.state.csrfToken);
        return this.state.csrfToken;
    },

    /**
     * Récupère le token CSRF
     */
    getCsrfToken() {
        if (!this.state.csrfToken) {
            this.state.csrfToken = sessionStorage.getItem('csrfToken') || this.generateCsrfToken();
        }
        return this.state.csrfToken;
    },

    /**
     * Ajoute le token CSRF aux headers
     */
    addCsrfHeader(headers = {}) {
        headers[this.config.csrfTokenName] = this.getCsrfToken();
        return headers;
    },

    // ==================== RATE LIMITING ====================

    /**
     * Vérifie si l'utilisateur est bloqué
     */
    isRateLimited(identifier) {
        const attempts = this.state.loginAttempts[identifier];
        if (!attempts) return false;

        if (attempts.count >= this.config.maxLoginAttempts) {
            const timeElapsed = Date.now() - attempts.lastAttempt;
            if (timeElapsed < this.config.lockoutDuration) {
                return true;
            }
            // Reset après le lockout
            delete this.state.loginAttempts[identifier];
        }

        return false;
    },

    /**
     * Enregistre une tentative échouée
     */
    recordFailedAttempt(identifier) {
        if (!this.state.loginAttempts[identifier]) {
            this.state.loginAttempts[identifier] = { count: 0, lastAttempt: 0 };
        }

        this.state.loginAttempts[identifier].count++;
        this.state.loginAttempts[identifier].lastAttempt = Date.now();

        return this.config.maxLoginAttempts - this.state.loginAttempts[identifier].count;
    },

    /**
     * Reset les tentatives après connexion réussie
     */
    resetAttempts(identifier) {
        delete this.state.loginAttempts[identifier];
    },

    /**
     * Temps restant avant déblocage
     */
    getRemainingLockoutTime(identifier) {
        const attempts = this.state.loginAttempts[identifier];
        if (!attempts) return 0;

        const timeElapsed = Date.now() - attempts.lastAttempt;
        return Math.max(0, this.config.lockoutDuration - timeElapsed);
    },

    // ==================== SESSION MANAGEMENT ====================

    /**
     * Démarre le monitoring d'activité
     */
    startActivityMonitor() {
        const events = ['mousedown', 'keydown', 'scroll', 'touchstart'];

        const resetTimer = () => {
            this.state.lastActivity = Date.now();
        };

        events.forEach(event => {
            document.addEventListener(event, resetTimer, { passive: true });
        });

        // Vérifier l'inactivité périodiquement
        this.state.activityTimer = setInterval(() => {
            if (API.isAuthenticated()) {
                const inactiveTime = Date.now() - this.state.lastActivity;
                if (inactiveTime >= this.config.sessionTimeout) {
                    this.handleSessionTimeout();
                }
            }
        }, 60000); // Vérifier chaque minute
    },

    /**
     * Gère le timeout de session
     */
    handleSessionTimeout() {
        API.removeToken();
        sessionStorage.clear();
        alert('Votre session a expiré pour inactivité. Veuillez vous reconnecter.');
        window.location.href = '/pages/login.html';
    },

    /**
     * Initialise le refresh automatique du token
     */
    initTokenRefresh() {
        setInterval(async () => {
            if (API.isAuthenticated()) {
                try {
                    await API.auth.refresh();
                    console.log('🔄 Token refreshed');
                } catch (error) {
                    console.error('Token refresh failed:', error);
                }
            }
        }, this.config.tokenRefreshInterval);
    },

    // ==================== PASSWORD SECURITY ====================

    /**
     * Calcule la force du mot de passe
     */
    getPasswordStrength(password) {
        let score = 0;

        if (password.length >= 8) score += 1;
        if (password.length >= 12) score += 1;
        if (/[A-Z]/.test(password)) score += 1;
        if (/[a-z]/.test(password)) score += 1;
        if (/[0-9]/.test(password)) score += 1;
        if (/[^A-Za-z0-9]/.test(password)) score += 1;

        if (score <= 2) return { level: 'weak', label: 'Faible', color: '#ea4335' };
        if (score <= 4) return { level: 'medium', label: 'Moyen', color: '#fbbc04' };
        return { level: 'strong', label: 'Fort', color: '#34a853' };
    },

    /**
     * Vérifie si le mot de passe est dans une liste de mots de passe faibles
     */
    isCommonPassword(password) {
        const commonPasswords = [
            'password', '123456', '12345678', 'qwerty', 'abc123',
            'password1', '111111', 'iloveyou', 'admin', 'letmein',
            'welcome', 'monkey', '123123', 'football', 'master'
        ];
        return commonPasswords.includes(password.toLowerCase());
    },

    // ==================== SECURE STORAGE ====================

    /**
     * Stockage sécurisé avec expiration
     */
    secureStore(key, value, expirationMs = null) {
        const item = {
            value: value,
            timestamp: Date.now(),
            expiration: expirationMs ? Date.now() + expirationMs : null
        };

        try {
            sessionStorage.setItem(key, JSON.stringify(item));
            return true;
        } catch (e) {
            console.error('Secure storage error:', e);
            return false;
        }
    },

    /**
     * Récupération sécurisée
     */
    secureRetrieve(key) {
        try {
            const itemStr = sessionStorage.getItem(key);
            if (!itemStr) return null;

            const item = JSON.parse(itemStr);

            // Vérifier l'expiration
            if (item.expiration && Date.now() > item.expiration) {
                sessionStorage.removeItem(key);
                return null;
            }

            return item.value;
        } catch (e) {
            console.error('Secure retrieve error:', e);
            return null;
        }
    },

    // ==================== URL SECURITY ====================

    /**
     * Valide une URL
     */
    isValidUrl(url) {
        try {
            const parsed = new URL(url);
            return ['http:', 'https:'].includes(parsed.protocol);
        } catch {
            return false;
        }
    },

    /**
     * Vérifie si une URL est interne
     */
    isInternalUrl(url) {
        try {
            const parsed = new URL(url, window.location.origin);
            return parsed.origin === window.location.origin;
        } catch {
            return false;
        }
    },

    /**
     * Redirection sécurisée
     */
    safeRedirect(url) {
        if (this.isInternalUrl(url)) {
            window.location.href = url;
        } else {
            console.warn('Blocked external redirect:', url);
            window.location.href = '/index.html';
        }
    }
};

// Export
window.Security = Security;

// Initialiser au chargement
document.addEventListener('DOMContentLoaded', () => Security.init());
