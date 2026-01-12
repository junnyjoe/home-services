package com.homeservices.exception;

/**
 * Exception pour les ressources non trouvées
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " non trouvé(e) avec l'ID: " + id);
    }
}
