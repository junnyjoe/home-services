package com.homeservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application principale - Services à Domicile
 * Point d'entrée de l'application Spring Boot
 * 
 * @author HomeServices Team
 * @version 1.0.0
 */
@SpringBootApplication
public class HomeServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeServicesApplication.class, args);
        System.out.println("===========================================");
        System.out.println("   HOME SERVICES API - Démarré avec succès");
        System.out.println("   Port: 8080");
        System.out.println("   H2 Console: http://localhost:8080/h2-console");
        System.out.println("===========================================");
    }
}
