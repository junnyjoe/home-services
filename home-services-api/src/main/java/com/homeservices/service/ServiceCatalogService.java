package com.homeservices.service;

import com.homeservices.dto.ProviderServiceDTO;
import com.homeservices.dto.ServiceCatalogDTO;
import com.homeservices.entity.ProviderService;
import com.homeservices.entity.ServiceCatalog;
import com.homeservices.entity.User;
import com.homeservices.enums.ServiceType;
import com.homeservices.repository.ProviderServiceRepository;
import com.homeservices.repository.ServiceCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service pour la gestion du catalogue de services
 */
@Service
public class ServiceCatalogService {

    @Autowired
    private ServiceCatalogRepository serviceCatalogRepository;

    @Autowired
    private ProviderServiceRepository providerServiceRepository;

    @Autowired
    private UserService userService;

    /**
     * Récupère tous les types de services
     */
    public List<ServiceCatalogDTO> getAllServices() {
        return serviceCatalogRepository.findAll()
                .stream()
                .map(ServiceCatalogDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un service par ID
     */
    public ServiceCatalogDTO getServiceById(Long id) {
        ServiceCatalog service = serviceCatalogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service non trouvé"));
        return new ServiceCatalogDTO(service);
    }

    /**
     * Récupère les prestataires pour un type de service
     */
    public List<ProviderServiceDTO> getProvidersByServiceType(String serviceType) {
        ServiceType type = ServiceType.valueOf(serviceType.toUpperCase());
        return providerServiceRepository.findByServiceTypeAndAvailable(type)
                .stream()
                .map(ProviderServiceDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Récupère tous les services disponibles avec leurs prestataires
     */
    public List<ProviderServiceDTO> getAllAvailableProviderServices() {
        return providerServiceRepository.findByAvailableTrue()
                .stream()
                .map(ProviderServiceDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les services du prestataire connecté
     */
    public List<ProviderServiceDTO> getMyServices() {
        User provider = userService.getCurrentUser();
        return providerServiceRepository.findByProviderId(provider.getId())
                .stream()
                .map(ProviderServiceDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Ajoute un nouveau service pour le prestataire
     */
    @Transactional
    public ProviderServiceDTO addService(Long serviceId, BigDecimal price, String description,
            Integer experienceYears) {
        User provider = userService.getCurrentUser();

        // Vérifier si le prestataire a déjà ce service
        if (providerServiceRepository.existsByProviderIdAndServiceCatalogId(provider.getId(), serviceId)) {
            throw new RuntimeException("Vous proposez déjà ce service");
        }

        ServiceCatalog catalog = serviceCatalogRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service non trouvé"));

        ProviderService ps = new ProviderService();
        ps.setProvider(provider);
        ps.setServiceCatalog(catalog);
        ps.setPrice(price);
        ps.setDescription(description);
        ps.setExperienceYears(experienceYears);
        ps.setAvailable(true);

        ps = providerServiceRepository.save(ps);
        return new ProviderServiceDTO(ps);
    }

    /**
     * Met à jour un service du prestataire
     */
    @Transactional
    public ProviderServiceDTO updateService(Long providerServiceId, BigDecimal price, String description,
            Boolean available) {
        User provider = userService.getCurrentUser();

        ProviderService ps = providerServiceRepository.findById(providerServiceId)
                .orElseThrow(() -> new RuntimeException("Service non trouvé"));

        // Vérifier que le service appartient au prestataire
        if (!ps.getProvider().getId().equals(provider.getId())) {
            throw new RuntimeException("Non autorisé");
        }

        if (price != null)
            ps.setPrice(price);
        if (description != null)
            ps.setDescription(description);
        if (available != null)
            ps.setAvailable(available);

        ps = providerServiceRepository.save(ps);
        return new ProviderServiceDTO(ps);
    }

    /**
     * Récupère un service prestataire par ID
     */
    public ProviderServiceDTO getProviderServiceById(Long id) {
        ProviderService ps = providerServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service non trouvé"));
        return new ProviderServiceDTO(ps);
    }
}
