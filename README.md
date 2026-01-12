# Home Services Platform 🏠⚒️

Une plateforme complète de mise en relation entre prestataires de services à domicile et clients. Ce projet intègre une API robuste développée avec Spring Boot et une interface utilisateur moderne en HTML/CSS/JS.

## 🚀 Fonctionnalités Principales

### 👤 Gestion des Utilisateurs
- **Multi-Rôles** : Support complet pour les **Clients**, les **Prestataires** (Providers) et les **Administrateurs**.
- **Sécurisation par JWT** : Authentification sécurisée avec Jetons Web JSON pour protéger les données.
- **Profils Personnalisés** : Chaque utilisateur peut gérer ses informations personnelles et son historique.

### 💼 Pour les Prestataires
- **Gestion des Services** : Création, modification et suppression des services proposés.
- **Tableau de Bord** : Vue d'ensemble des revenus, des commandes en cours et des statistiques.
- **Gestion des Commandes** : Suivi du statut des réservations (En attente, Confirmée, Terminée).

### 🛒 Pour les Clients
- **Catalogue de Services** : Exploration des services disponibles par catégories.
- **Système de Réservation** : Processus fluide pour réserver une prestation à une date précise.
- **Suivi des Réservations** : Historique complet des prestations passées et à venir.
- **Paiements** : Simulation et historique des transactions.

### 🛡️ Administration
- **Dashboard Global** : Statistiques sur le nombre d'utilisateurs, transactions et réservations.
- **Modération** : Gestion des utilisateurs et des services à l'échelle de la plateforme.
- **Suivi Financier** : Visualisation de l'ensemble des flux financiers de la plateforme.

## 🛠️ Stack Technique

### Backend (`home-services-api`)
- **Framework** : Spring Boot 3.x (Java)
- **Sécurité** : Spring Security & JWT
- **Persistance** : Spring Data JPA
- **Internationalisation** : Support multilingue (FR/EN)

### Frontend (`home-services-frontend`)
- **Structure** : HTML5 sémantique
- **Style** : Vanilla CSS 3 (Flexbox/Grid, design moderne et responsive)
- **Logique** : JavaScript (Fetch API pour la communication avec le backend)
- **UI/UX** : Dashboards interactifs et validations de formulaires en temps réel.

## 📦 Installation et Lancement

### Prérequis
- Java 17+
- Maven
- Un navigateur moderne

### Lancement du Backend
```bash
cd home-services-api
mvn spring-boot:run
```

### Lancement du Frontend
Ouvrez simplement le fichier `home-services-frontend/index.html` dans votre navigateur ou utilisez un serveur local (Live Server par exemple).

## 🌍 Internationalisation
Le projet supporte nativement le Français et l'Anglais, tant au niveau des messages d'erreur de l'API que de l'interface utilisateur.
