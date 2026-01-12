package com.homeservices.enums;

/**
 * Types de services disponibles
 * - MENUISERIE: Travaux de menuiserie
 * - PLOMBERIE: Travaux de plomberie
 * - ELECTRICITE: Travaux d'électricité
 */
public enum ServiceType {
    MENUISERIE("Menuiserie", "🪚"),
    PLOMBERIE("Plomberie", "🚿"),
    ELECTRICITE("Électricité", "⚡");

    private final String label;
    private final String icon;

    ServiceType(String label, String icon) {
        this.label = label;
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public String getIcon() {
        return icon;
    }
}
