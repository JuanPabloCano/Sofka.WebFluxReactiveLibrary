package co.com.sofka.springbootReactiveLibraryWebFlux.useCases;

import co.com.sofka.springbootReactiveLibraryWebFlux.dto.ResourceDto;
import co.com.sofka.springbootReactiveLibraryWebFlux.mappers.ResourceMapper;
import co.com.sofka.springbootReactiveLibraryWebFlux.repository.ResourceRepository;
import co.com.sofka.springbootReactiveLibraryWebFlux.utils.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RequiredArgsConstructor
@Service
@Validated
public class UseCaseValidateResourceAvailability implements GetNotificationById{

    @Autowired
    private final ResourceRepository resourceRepository;
    @Autowired
    private final ResourceMapper resourceMapper;


    @Override
    public Mono<Notification> apply(String id) {
        Objects.requireNonNull(id, "El Id es requerido");
        return resourceRepository.findById(id)
                .map(resourceMapper.mapResourceToDTO())
                .map(resource -> isAvailableMessage(resource, resource.isAvailable()))
                .switchIfEmpty(Mono.just(new Notification("Recurso no encontrado", false)));
    }

    private Notification isAvailableMessage(ResourceDto resourceDto, boolean available) {
        var notification = new Notification();
        notification.setState(available);
        if (available){
            notification.setMessage("El recurso está disponible");
        }
        notification.setMessage("El recurso fue prestado en la fecha: " + resourceDto.getReturnDate());
        return notification;
    }
}
