package com.homeservices.config;

import com.homeservices.entity.ServiceCatalog;
import com.homeservices.entity.User;
import com.homeservices.enums.ServiceType;
import com.homeservices.enums.UserRole;
import com.homeservices.repository.ServiceCatalogRepository;
import com.homeservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Initialiseur de données
 * Crée les données de base au démarrage de l'application
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ServiceCatalogRepository serviceCatalogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialiser le catalogue de services
        initServiceCatalog();

        // Créer un admin par défaut
        initAdminUser();

        System.out.println("✅ Données initiales créées avec succès!");
    }

    private void initServiceCatalog() {
        if (serviceCatalogRepository.count() == 0) {
            // Menuiserie
            ServiceCatalog menuiserie = new ServiceCatalog(
                    "Menuiserie",
                    ServiceType.MENUISERIE,
                    "Travaux de menuiserie : portes, fenêtres, meubles sur mesure, réparations bois",
                    "🪚",
                    50.0);
            serviceCatalogRepository.save(menuiserie);

            // Plomberie
            ServiceCatalog plomberie = new ServiceCatalog(
                    "Plomberie",
                    ServiceType.PLOMBERIE,
                    "Services de plomberie : fuites, installation sanitaire, débouchage, chauffe-eau",
                    "🚿",
                    60.0);
            serviceCatalogRepository.save(plomberie);

            // Électricité
            ServiceCatalog electricite = new ServiceCatalog(
                    "Électricité",
                    ServiceType.ELECTRICITE,
                    "Travaux électriques : installation, dépannage, mise aux normes, éclairage",
                    "⚡",
                    70.0);
            serviceCatalogRepository.save(electricite);

            System.out.println("📋 Catalogue de services initialisé");
        }
    }

    private void initAdminUser() {
        String adminEmail = "admin@homeservices.com";

        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = new User();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFirstName("Admin");
            admin.setLastName("System");
            admin.setRole(UserRole.ADMINISTRATEUR);
            admin.setPhone("+33 1 00 00 00 00");

            userRepository.save(admin);
            System.out.println("👤 Administrateur créé: " + adminEmail + " / admin123");
        }
    }
}
