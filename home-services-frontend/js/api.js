/**
 * HOME SERVICES - API Module
 * Gère toutes les requêtes HTTP vers le backend
 * Inclut la gestion automatique du token JWT
 */

const API = {
    // Configuration
    BASE_URL: 'http://localhost:8080/api',
    
    /**
     * Récupère le token JWT du localStorage
     */
    getToken() {
        return localStorage.getItem('token');
    },

    /**
     * Stocke le token JWT
     */
    setToken(token) {
        localStorage.setItem('token', token);
    },

    /**
     * Supprime le token JWT
     */
    removeToken() {
        localStorage.removeItem('token');
        localStorage.removeItem('refreshToken');
        localStorage.removeItem('user');
    },

    /**
     * Vérifie si l'utilisateur est connecté
     */
    isAuthenticated() {
        return !!this.getToken();
    },

    /**
     * Récupère les infos utilisateur stockées
     */
    getUser() {
        const user = localStorage.getItem('user');
        return user ? JSON.parse(user) : null;
    },

    /**
     * Stocke les infos utilisateur
     */
    setUser(user) {
        localStorage.setItem('user', JSON.stringify(user));
    },

    /**
     * Headers par défaut pour les requêtes
     */
    getHeaders(includeAuth = true) {
        const headers = {
            'Content-Type': 'application/json',
            'Accept-Language': localStorage.getItem('lang') || 'fr'
        };
        
        if (includeAuth && this.getToken()) {
            headers['Authorization'] = `Bearer ${this.getToken()}`;
        }
        
        return headers;
    },

    /**
     * Requête HTTP générique avec gestion d'erreurs
     */
    async request(endpoint, options = {}) {
        const url = `${this.BASE_URL}${endpoint}`;
        
        const config = {
            ...options,
            headers: this.getHeaders(options.auth !== false)
        };

        try {
            const response = await fetch(url, config);
            
            // Si non autorisé, rediriger vers login
            if (response.status === 401) {
                this.removeToken();
                window.location.href = '/pages/login.html';
                throw new Error('Session expirée');
            }

            // Si forbidden, afficher erreur
            if (response.status === 403) {
                throw new Error('Accès non autorisé');
            }

            const data = await response.json();
            
            if (!response.ok) {
                throw new Error(data.message || 'Une erreur est survenue');
            }
            
            return data;
        } catch (error) {
            console.error('API Error:', error);
            throw error;
        }
    },

    /**
     * GET Request
     */
    get(endpoint, options = {}) {
        return this.request(endpoint, { method: 'GET', ...options });
    },

    /**
     * POST Request
     */
    post(endpoint, data, options = {}) {
        return this.request(endpoint, {
            method: 'POST',
            body: JSON.stringify(data),
            ...options
        });
    },

    /**
     * PUT Request
     */
    put(endpoint, data, options = {}) {
        return this.request(endpoint, {
            method: 'PUT',
            body: JSON.stringify(data),
            ...options
        });
    },

    /**
     * DELETE Request
     */
    delete(endpoint, options = {}) {
        return this.request(endpoint, { method: 'DELETE', ...options });
    },

    // ==================== AUTH ENDPOINTS ====================

    auth: {
        /**
         * Inscription
         */
        async register(userData) {
            const response = await API.post('/auth/register', userData, { auth: false });
            API.setToken(response.token);
            localStorage.setItem('refreshToken', response.refreshToken);
            API.setUser(response.user);
            return response;
        },

        /**
         * Connexion
         */
        async login(email, password) {
            const response = await API.post('/auth/login', { email, password }, { auth: false });
            API.setToken(response.token);
            localStorage.setItem('refreshToken', response.refreshToken);
            API.setUser(response.user);
            return response;
        },

        /**
         * Déconnexion
         */
        logout() {
            API.removeToken();
            window.location.href = '/pages/login.html';
        },

        /**
         * Rafraîchir le token
         */
        async refresh() {
            const refreshToken = localStorage.getItem('refreshToken');
            if (!refreshToken) throw new Error('No refresh token');
            
            const response = await API.post('/auth/refresh', { refreshToken }, { auth: false });
            API.setToken(response.token);
            localStorage.setItem('refreshToken', response.refreshToken);
            return response;
        },

        /**
         * Récupérer l'utilisateur courant
         */
        async me() {
            return API.get('/auth/me');
        }
    },

    // ==================== USER ENDPOINTS ====================

    users: {
        /**
         * Profil utilisateur
         */
        async getProfile() {
            return API.get('/users/me');
        },

        /**
         * Mettre à jour le profil
         */
        async updateProfile(data) {
            return API.put('/users/me', data);
        },

        /**
         * Liste des prestataires
         */
        async getProviders() {
            return API.get('/users/providers');
        },

        /**
         * Liste tous les utilisateurs (admin)
         */
        async getAll() {
            return API.get('/users');
        },

        /**
         * Récupérer un utilisateur par ID
         */
        async getById(id) {
            return API.get(`/users/${id}`);
        }
    },

    // ==================== SERVICES ENDPOINTS ====================

    services: {
        /**
         * Liste tous les types de services
         */
        async getAll() {
            return API.get('/services', { auth: false });
        },

        /**
         * Récupérer un service par ID
         */
        async getById(id) {
            return API.get(`/services/${id}`, { auth: false });
        },

        /**
         * Liste les prestataires pour un type de service
         */
        async getProvidersByType(type) {
            return API.get(`/services/${type}/providers`, { auth: false });
        },

        /**
         * Liste tous les services disponibles
         */
        async getAvailable() {
            return API.get('/services/available', { auth: false });
        },

        /**
         * Récupérer les services du prestataire
         */
        async getMyServices() {
            return API.get('/provider-services');
        },

        /**
         * Ajouter un service (prestataire)
         */
        async addService(data) {
            return API.post('/provider-services', data);
        },

        /**
         * Mettre à jour un service (prestataire)
         */
        async updateService(id, data) {
            return API.put(`/provider-services/${id}`, data);
        },

        /**
         * Récupérer un service prestataire par ID
         */
        async getProviderService(id) {
            return API.get(`/provider-services/${id}`, { auth: false });
        }
    },

    // ==================== RESERVATIONS ENDPOINTS ====================

    reservations: {
        /**
         * Créer une réservation
         */
        async create(data) {
            return API.post('/reservations', data);
        },

        /**
         * Liste mes réservations
         */
        async getMy() {
            return API.get('/reservations/my');
        },

        /**
         * Récupérer une réservation par ID
         */
        async getById(id) {
            return API.get(`/reservations/${id}`);
        },

        /**
         * Mettre à jour le statut (prestataire)
         */
        async updateStatus(id, status) {
            return API.put(`/reservations/${id}/status`, { status });
        },

        /**
         * Annuler une réservation (client)
         */
        async cancel(id) {
            return API.put(`/reservations/${id}/cancel`);
        },

        /**
         * Liste les réservations en attente (prestataire)
         */
        async getPending() {
            return API.get('/reservations/pending');
        },

        /**
         * Liste toutes les réservations (admin)
         */
        async getAll() {
            return API.get('/reservations');
        }
    },

    // ==================== PAYMENTS ENDPOINTS ====================

    payments: {
        /**
         * Traiter un paiement
         */
        async process(data) {
            return API.post('/payments', data);
        },

        /**
         * Liste mes transactions
         */
        async getMy() {
            return API.get('/payments/my');
        },

        /**
         * Solde du prestataire
         */
        async getBalance() {
            return API.get('/payments/provider/balance');
        },

        /**
         * Liste toutes les transactions (admin)
         */
        async getAll() {
            return API.get('/payments');
        }
    },

    // ==================== ADMIN ENDPOINTS ====================

    admin: {
        /**
         * Statistiques dashboard
         */
        async getStats() {
            return API.get('/admin/stats');
        },

        /**
         * Activité récente
         */
        async getActivity() {
            return API.get('/admin/activity');
        },

        /**
         * Liste tous les utilisateurs
         */
        async getUsers() {
            return API.get('/admin/users');
        },

        /**
         * Liste toutes les réservations
         */
        async getReservations() {
            return API.get('/admin/reservations');
        },

        /**
         * Liste toutes les transactions
         */
        async getTransactions() {
            return API.get('/admin/transactions');
        }
    }
};

// Export pour utilisation dans d'autres modules
window.API = API;
