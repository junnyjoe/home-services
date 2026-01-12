/**
 * HOME SERVICES - Internationalization Module
 * Gère les traductions FR/EN
 */

const I18n = {
    // Langue courante
    currentLang: localStorage.getItem('lang') || 'fr',

    // Traductions
    translations: {
        fr: {
            // Navigation
            nav: {
                home: 'Accueil',
                services: 'Services',
                about: 'À propos',
                contact: 'Contact',
                login: 'Connexion',
                register: 'Inscription',
                logout: 'Déconnexion',
                dashboard: 'Tableau de bord',
                profile: 'Profil'
            },

            // Auth
            auth: {
                loginTitle: 'Connexion',
                loginSubtitle: 'Accédez à votre compte',
                registerTitle: 'Inscription',
                registerSubtitle: 'Créez votre compte',
                email: 'Email',
                password: 'Mot de passe',
                confirmPassword: 'Confirmer le mot de passe',
                firstName: 'Prénom',
                lastName: 'Nom',
                phone: 'Téléphone',
                address: 'Adresse',
                loginButton: 'Se connecter',
                registerButton: "S'inscrire",
                noAccount: "Pas encore de compte ?",
                haveAccount: 'Déjà un compte ?',
                forgotPassword: 'Mot de passe oublié ?',
                selectRole: 'Je suis...',
                client: 'Client',
                provider: 'Prestataire',
                admin: 'Admin'
            },

            // Roles
            roles: {
                CLIENT: 'Client',
                PRESTATAIRE: 'Prestataire',
                ADMINISTRATEUR: 'Administrateur'
            },

            // Services
            services: {
                title: 'Nos Services',
                subtitle: 'Des professionnels qualifiés à votre service',
                menuiserie: 'Menuiserie',
                plomberie: 'Plomberie',
                electricite: 'Électricité',
                menuiserieDesc: 'Portes, fenêtres, meubles sur mesure',
                plomberieDesc: 'Installation, réparation, débouchage',
                electriciteDesc: 'Installation, dépannage, mise aux normes',
                seeProviders: 'Voir les prestataires',
                bookNow: 'Réserver maintenant',
                priceFrom: 'À partir de'
            },

            // Reservation
            reservation: {
                title: 'Réserver une prestation',
                selectDate: 'Date souhaitée',
                selectTime: 'Heure',
                notes: 'Notes additionnelles',
                address: 'Adresse de la prestation',
                confirm: 'Confirmer la réservation',
                success: 'Réservation effectuée avec succès !',
                myReservations: 'Mes réservations'
            },

            // Status
            status: {
                EN_ATTENTE: 'En attente',
                CONFIRMEE: 'Confirmée',
                EN_COURS: 'En cours',
                TERMINEE: 'Terminée',
                ANNULEE: 'Annulée'
            },

            // Payment
            payment: {
                title: 'Paiement',
                amount: 'Montant',
                method: 'Méthode de paiement',
                card: 'Carte bancaire',
                cardNumber: 'Numéro de carte',
                expiry: 'Date d\'expiration',
                cvc: 'CVC',
                pay: 'Payer',
                success: 'Paiement effectué avec succès !',
                processing: 'Traitement en cours...'
            },

            // Dashboard
            dashboard: {
                welcome: 'Bienvenue',
                overview: 'Vue d\'ensemble',
                myOrders: 'Mes commandes',
                myServices: 'Mes services',
                earnings: 'Mes gains',
                balance: 'Solde',
                pending: 'En attente',
                completed: 'Terminées',
                total: 'Total'
            },

            // Admin
            admin: {
                title: 'Administration',
                users: 'Utilisateurs',
                reservations: 'Réservations',
                transactions: 'Transactions',
                stats: 'Statistiques',
                totalUsers: 'Utilisateurs totaux',
                totalClients: 'Clients',
                totalProviders: 'Prestataires',
                totalRevenue: 'Revenu total',
                todayReservations: "Réservations aujourd'hui"
            },

            // Common
            common: {
                loading: 'Chargement...',
                error: 'Erreur',
                success: 'Succès',
                save: 'Enregistrer',
                cancel: 'Annuler',
                delete: 'Supprimer',
                edit: 'Modifier',
                view: 'Voir',
                search: 'Rechercher',
                filter: 'Filtrer',
                noResults: 'Aucun résultat',
                confirm: 'Confirmer',
                back: 'Retour',
                next: 'Suivant',
                previous: 'Précédent',
                close: 'Fermer',
                or: 'ou',
                currency: 'FCFA',
                perHour: '/heure'
            },

            // Hero
            hero: {
                title: 'Services à domicile de qualité',
                subtitle: 'Trouvez les meilleurs artisans pour vos travaux de menuiserie, plomberie et électricité. Réservez en quelques clics.',
                cta: 'Trouver un prestataire',
                ctaSecondary: 'En savoir plus'
            },

            // Errors
            errors: {
                required: 'Ce champ est obligatoire',
                email: 'Email invalide',
                minLength: 'Minimum {0} caractères',
                passwordMatch: 'Les mots de passe ne correspondent pas',
                generic: 'Une erreur est survenue'
            }
        },

        en: {
            // Navigation
            nav: {
                home: 'Home',
                services: 'Services',
                about: 'About',
                contact: 'Contact',
                login: 'Login',
                register: 'Register',
                logout: 'Logout',
                dashboard: 'Dashboard',
                profile: 'Profile'
            },

            // Auth
            auth: {
                loginTitle: 'Login',
                loginSubtitle: 'Access your account',
                registerTitle: 'Register',
                registerSubtitle: 'Create your account',
                email: 'Email',
                password: 'Password',
                confirmPassword: 'Confirm password',
                firstName: 'First name',
                lastName: 'Last name',
                phone: 'Phone',
                address: 'Address',
                loginButton: 'Sign in',
                registerButton: 'Sign up',
                noAccount: "Don't have an account?",
                haveAccount: 'Already have an account?',
                forgotPassword: 'Forgot password?',
                selectRole: 'I am a...',
                client: 'Client',
                provider: 'Provider',
                admin: 'Admin'
            },

            // Roles
            roles: {
                CLIENT: 'Client',
                PRESTATAIRE: 'Provider',
                ADMINISTRATEUR: 'Administrator'
            },

            // Services
            services: {
                title: 'Our Services',
                subtitle: 'Qualified professionals at your service',
                menuiserie: 'Carpentry',
                plomberie: 'Plumbing',
                electricite: 'Electricity',
                menuiserieDesc: 'Doors, windows, custom furniture',
                plomberieDesc: 'Installation, repair, unclogging',
                electriciteDesc: 'Installation, troubleshooting, compliance',
                seeProviders: 'See providers',
                bookNow: 'Book now',
                priceFrom: 'Starting from'
            },

            // Reservation
            reservation: {
                title: 'Book a service',
                selectDate: 'Preferred date',
                selectTime: 'Time',
                notes: 'Additional notes',
                address: 'Service address',
                confirm: 'Confirm booking',
                success: 'Booking confirmed successfully!',
                myReservations: 'My bookings'
            },

            // Status
            status: {
                EN_ATTENTE: 'Pending',
                CONFIRMEE: 'Confirmed',
                EN_COURS: 'In progress',
                TERMINEE: 'Completed',
                ANNULEE: 'Cancelled'
            },

            // Payment
            payment: {
                title: 'Payment',
                amount: 'Amount',
                method: 'Payment method',
                card: 'Credit card',
                cardNumber: 'Card number',
                expiry: 'Expiry date',
                cvc: 'CVC',
                pay: 'Pay',
                success: 'Payment successful!',
                processing: 'Processing...'
            },

            // Dashboard
            dashboard: {
                welcome: 'Welcome',
                overview: 'Overview',
                myOrders: 'My orders',
                myServices: 'My services',
                earnings: 'My earnings',
                balance: 'Balance',
                pending: 'Pending',
                completed: 'Completed',
                total: 'Total'
            },

            // Admin
            admin: {
                title: 'Administration',
                users: 'Users',
                reservations: 'Reservations',
                transactions: 'Transactions',
                stats: 'Statistics',
                totalUsers: 'Total users',
                totalClients: 'Clients',
                totalProviders: 'Providers',
                totalRevenue: 'Total revenue',
                todayReservations: "Today's reservations"
            },

            // Common
            common: {
                loading: 'Loading...',
                error: 'Error',
                success: 'Success',
                save: 'Save',
                cancel: 'Cancel',
                delete: 'Delete',
                edit: 'Edit',
                view: 'View',
                search: 'Search',
                filter: 'Filter',
                noResults: 'No results',
                confirm: 'Confirm',
                back: 'Back',
                next: 'Next',
                previous: 'Previous',
                close: 'Close',
                or: 'or',
                currency: 'FCFA',
                perHour: '/hour'
            },

            // Hero
            hero: {
                title: 'Quality home services',
                subtitle: 'Find the best craftsmen for your carpentry, plumbing and electrical work. Book in a few clicks.',
                cta: 'Find a provider',
                ctaSecondary: 'Learn more'
            },

            // Errors
            errors: {
                required: 'This field is required',
                email: 'Invalid email',
                minLength: 'Minimum {0} characters',
                passwordMatch: 'Passwords do not match',
                generic: 'An error occurred'
            }
        }
    },

    /**
     * Initialise le module i18n
     */
    init() {
        this.updateDOM();
        this.setupLanguageSwitcher();
    },

    /**
     * Change la langue
     */
    setLanguage(lang) {
        this.currentLang = lang;
        localStorage.setItem('lang', lang);
        this.updateDOM();
    },

    /**
     * Récupère une traduction par clé
     * @param {string} key - Clé de traduction (ex: 'nav.home')
     * @param {object} params - Paramètres de remplacement
     */
    t(key, params = {}) {
        const keys = key.split('.');
        let value = this.translations[this.currentLang];

        for (const k of keys) {
            if (value && value[k] !== undefined) {
                value = value[k];
            } else {
                return key; // Retourner la clé si traduction non trouvée
            }
        }

        // Remplacer les paramètres
        if (typeof value === 'string' && Object.keys(params).length > 0) {
            Object.entries(params).forEach(([paramKey, paramValue]) => {
                value = value.replace(`{${paramKey}}`, paramValue);
            });
        }

        return value;
    },

    /**
     * Met à jour tous les éléments avec data-i18n
     */
    updateDOM() {
        document.querySelectorAll('[data-i18n]').forEach(el => {
            const key = el.getAttribute('data-i18n');
            const translation = this.t(key);

            if (el.hasAttribute('data-i18n-attr')) {
                const attr = el.getAttribute('data-i18n-attr');
                el.setAttribute(attr, translation);
            } else {
                el.textContent = translation;
            }
        });

        // Mettre à jour les placeholders
        document.querySelectorAll('[data-i18n-placeholder]').forEach(el => {
            const key = el.getAttribute('data-i18n-placeholder');
            el.placeholder = this.t(key);
        });

        // Mettre à jour les boutons de langue
        document.querySelectorAll('.lang-btn').forEach(btn => {
            btn.classList.toggle('active', btn.dataset.lang === this.currentLang);
        });
    },

    /**
     * Configure les sélecteurs de langue
     */
    setupLanguageSwitcher() {
        document.querySelectorAll('.lang-btn').forEach(btn => {
            btn.addEventListener('click', () => {
                this.setLanguage(btn.dataset.lang);
            });
        });
    }
};

// Export
window.I18n = I18n;

// Initialiser au chargement
document.addEventListener('DOMContentLoaded', () => I18n.init());
