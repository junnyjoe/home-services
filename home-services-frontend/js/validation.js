/**
 * HOME SERVICES - Form Validation Module
 * Validation sécurisée des formulaires
 */

const Validation = {
    // Règles de validation
    rules: {
        required: (value) => ({
            valid: value !== null && value !== undefined && String(value).trim() !== '',
            message: 'Ce champ est requis'
        }),

        email: (value) => ({
            valid: Security.isValidEmail(value),
            message: 'Email invalide'
        }),

        password: (value) => ({
            valid: Security.isValidPassword(value),
            message: 'Minimum 8 caractères avec majuscule, minuscule et chiffre'
        }),

        passwordMatch: (value, fields) => ({
            valid: value === fields.password,
            message: 'Les mots de passe ne correspondent pas'
        }),

        phone: (value) => ({
            valid: !value || Security.isValidPhone(value),
            message: 'Numéro de téléphone invalide'
        }),

        minLength: (min) => (value) => ({
            valid: String(value).length >= min,
            message: `Minimum ${min} caractères`
        }),

        maxLength: (max) => (value) => ({
            valid: String(value).length <= max,
            message: `Maximum ${max} caractères`
        }),

        min: (minVal) => (value) => ({
            valid: parseFloat(value) >= minVal,
            message: `Minimum ${minVal}`
        }),

        max: (maxVal) => (value) => ({
            valid: parseFloat(value) <= maxVal,
            message: `Maximum ${maxVal}`
        }),

        pattern: (regex, message) => (value) => ({
            valid: regex.test(value),
            message: message || 'Format invalide'
        }),

        noScript: (value) => ({
            valid: !/<script|javascript:|on\w+=/i.test(value),
            message: 'Contenu non autorisé détecté'
        })
    },

    /**
     * Valide un champ unique
     */
    validateField(value, validations, allFields = {}) {
        const errors = [];

        for (const validation of validations) {
            let result;

            if (typeof validation === 'string') {
                // Règle prédéfinie
                if (this.rules[validation]) {
                    result = this.rules[validation](value, allFields);
                }
            } else if (typeof validation === 'function') {
                // Règle personnalisée
                result = validation(value, allFields);
            } else if (typeof validation === 'object') {
                // Règle avec paramètres
                const { rule, params, message } = validation;
                if (this.rules[rule]) {
                    const ruleFunc = typeof this.rules[rule] === 'function'
                        ? this.rules[rule]
                        : this.rules[rule](params);
                    result = ruleFunc(value, allFields);
                    if (message && !result.valid) {
                        result.message = message;
                    }
                }
            }

            if (result && !result.valid) {
                errors.push(result.message);
            }
        }

        return {
            valid: errors.length === 0,
            errors
        };
    },

    /**
     * Valide un formulaire complet
     */
    validateForm(fields, schema) {
        const errors = {};
        let isValid = true;

        for (const [fieldName, validations] of Object.entries(schema)) {
            const value = fields[fieldName];
            const result = this.validateField(value, validations, fields);

            if (!result.valid) {
                isValid = false;
                errors[fieldName] = result.errors;
            }
        }

        return { valid: isValid, errors };
    },

    /**
     * Affiche les erreurs sur un formulaire
     */
    showFormErrors(form, errors) {
        // Nettoyer les erreurs précédentes
        form.querySelectorAll('.form-error').forEach(el => el.remove());
        form.querySelectorAll('.form-control.error').forEach(el => el.classList.remove('error'));

        for (const [fieldName, fieldErrors] of Object.entries(errors)) {
            const field = form.querySelector(`[name="${fieldName}"], #${fieldName}`);
            if (field) {
                field.classList.add('error');

                const errorEl = document.createElement('span');
                errorEl.className = 'form-error';
                errorEl.textContent = fieldErrors[0]; // Afficher la première erreur
                errorEl.style.cssText = 'color: #ea4335; font-size: 0.75rem; display: block; margin-top: 4px;';

                field.parentNode.appendChild(errorEl);
            }
        }
    },

    /**
     * Nettoie les erreurs d'un formulaire
     */
    clearFormErrors(form) {
        form.querySelectorAll('.form-error').forEach(el => el.remove());
        form.querySelectorAll('.form-control.error').forEach(el => el.classList.remove('error'));
    },

    /**
     * Schéma de validation pour l'inscription
     */
    registerSchema: {
        firstName: ['required', { rule: 'minLength', params: 2 }, { rule: 'maxLength', params: 50 }, 'noScript'],
        lastName: ['required', { rule: 'minLength', params: 2 }, { rule: 'maxLength', params: 50 }, 'noScript'],
        email: ['required', 'email'],
        password: ['required', 'password'],
        confirmPassword: ['required', 'passwordMatch'],
        phone: ['phone'],
        role: ['required']
    },

    /**
     * Schéma de validation pour la connexion
     */
    loginSchema: {
        email: ['required', 'email'],
        password: ['required']
    },

    /**
     * Schéma de validation pour le profil
     */
    profileSchema: {
        firstName: ['required', { rule: 'minLength', params: 2 }, 'noScript'],
        lastName: ['required', { rule: 'minLength', params: 2 }, 'noScript'],
        email: ['required', 'email'],
        phone: ['phone']
    },

    /**
     * Schéma de validation pour un service
     */
    serviceSchema: {
        name: ['required', { rule: 'minLength', params: 3 }, { rule: 'maxLength', params: 100 }, 'noScript'],
        description: ['required', { rule: 'minLength', params: 10 }, 'noScript'],
        category: ['required'],
        hourlyRate: ['required', { rule: 'min', params: 1000 }, { rule: 'max', params: 500000 }]
    },

    /**
     * Schéma de validation pour une réservation
     */
    reservationSchema: {
        serviceId: ['required'],
        scheduledDate: ['required'],
        hours: ['required', { rule: 'min', params: 1 }, { rule: 'max', params: 24 }]
    }
};

// Export
window.Validation = Validation;
