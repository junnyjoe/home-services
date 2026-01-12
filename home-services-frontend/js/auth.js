/**
 * HOME SERVICES - Auth Module
 * Gère l'authentification et la protection des routes
 */

const Auth = {
    /**
     * Vérifie si l'utilisateur est connecté
     */
    isAuthenticated() {
        return API.isAuthenticated();
    },

    /**
     * Récupère l'utilisateur courant
     */
    getUser() {
        return API.getUser();
    },

    /**
     * Récupère le rôle de l'utilisateur
     */
    getRole() {
        const user = this.getUser();
        return user ? user.role : null;
    },

    /**
     * Vérifie si l'utilisateur a un rôle spécifique
     */
    hasRole(role) {
        return this.getRole() === role;
    },

    /**
     * Vérifie si l'utilisateur est client
     */
    isClient() {
        return this.hasRole('CLIENT');
    },

    /**
     * Vérifie si l'utilisateur est prestataire
     */
    isProvider() {
        return this.hasRole('PRESTATAIRE');
    },

    /**
     * Vérifie si l'utilisateur est admin
     */
    isAdmin() {
        return this.hasRole('ADMINISTRATEUR');
    },

    /**
     * Connexion
     */
    async login(email, password) {
        try {
            const response = await API.auth.login(email, password);
            this.onLoginSuccess(response);
            return response;
        } catch (error) {
            throw error;
        }
    },

    /**
     * Inscription
     */
    async register(userData) {
        try {
            const response = await API.auth.register(userData);
            this.onLoginSuccess(response);
            return response;
        } catch (error) {
            throw error;
        }
    },

    /**
     * Déconnexion
     */
    logout() {
        API.auth.logout();
    },

    /**
     * Actions après connexion réussie
     */
    onLoginSuccess(response) {
        // Redirection selon le rôle
        const role = response.user.role;

        switch (role) {
            case 'CLIENT':
                window.location.href = '/pages/client/home.html';
                break;
            case 'PRESTATAIRE':
                window.location.href = '/pages/provider/dashboard.html';
                break;
            case 'ADMINISTRATEUR':
                window.location.href = '/pages/admin/dashboard.html';
                break;
            default:
                window.location.href = '/index.html';
        }
    },

    /**
     * Protège une page selon le rôle
     * @param {string|string[]} allowedRoles - Rôle(s) autorisé(s)
     */
    requireAuth(allowedRoles = null) {
        if (!this.isAuthenticated()) {
            window.location.href = '/pages/login.html';
            return false;
        }

        if (allowedRoles) {
            const roles = Array.isArray(allowedRoles) ? allowedRoles : [allowedRoles];
            if (!roles.includes(this.getRole())) {
                this.redirectToDashboard();
                return false;
            }
        }

        return true;
    },

    /**
     * Redirige vers le dashboard approprié
     */
    redirectToDashboard() {
        const role = this.getRole();

        switch (role) {
            case 'CLIENT':
                window.location.href = '/pages/client/home.html';
                break;
            case 'PRESTATAIRE':
                window.location.href = '/pages/provider/dashboard.html';
                break;
            case 'ADMINISTRATEUR':
                window.location.href = '/pages/admin/dashboard.html';
                break;
            default:
                window.location.href = '/index.html';
        }
    },

    /**
     * Empêche l'accès aux pages auth si déjà connecté
     */
    redirectIfAuthenticated() {
        if (this.isAuthenticated()) {
            this.redirectToDashboard();
            return true;
        }
        return false;
    },

    /**
     * Met à jour l'UI selon l'état de connexion
     */
    updateUI() {
        const user = this.getUser();
        const isAuth = this.isAuthenticated();

        // Éléments à afficher si connecté
        document.querySelectorAll('.auth-only').forEach(el => {
            el.style.display = isAuth ? '' : 'none';
        });

        // Éléments à afficher si non connecté
        document.querySelectorAll('.guest-only').forEach(el => {
            el.style.display = isAuth ? 'none' : '';
        });

        // Éléments spécifiques au rôle
        ['CLIENT', 'PRESTATAIRE', 'ADMINISTRATEUR'].forEach(role => {
            document.querySelectorAll(`.role-${role.toLowerCase()}`).forEach(el => {
                el.style.display = this.hasRole(role) ? '' : 'none';
            });
        });

        // Afficher le nom de l'utilisateur
        if (user) {
            document.querySelectorAll('.user-name').forEach(el => {
                el.textContent = `${user.firstName} ${user.lastName}`;
            });
            document.querySelectorAll('.user-email').forEach(el => {
                el.textContent = user.email;
            });
            document.querySelectorAll('.user-role').forEach(el => {
                el.textContent = I18n.t(`roles.${user.role}`);
            });
        }
    }
};

// Export
window.Auth = Auth;

// Initialiser au chargement
document.addEventListener('DOMContentLoaded', () => Auth.updateUI());
