package co.com.sofka.springbootReactiveLibraryWebFlux.useCases;

import co.com.sofka.springbootReactiveLibraryWebFlux.mappers.ResourceMapper;
import co.com.sofka.springbootReactiveLibraryWebFlux.repository.ResourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@AllArgsConstructor
@Service
@Validated
public class UseCaseReturnResource implements Function<String, Mono<String>> {

    private final UseCaseUpdateResource useCaseUpdateResource;
    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;

    @Override
    public Mono<String> apply(String id) {
        Objects.requireNonNull(id, "el id no puede ser nullo");
        return resourceRepository.findById(id).flatMap(
                recurso -> {
                    if (recurso.isAvailable()) {
                        recurso.setAvailable(false);
                        return useCaseUpdateResource.saveResource(resourceMapper
                                        .mapResourceToDTO().apply(recurso))
                                .thenReturn("El recurso fue entregado con éxito");
                    }
                    return Mono.just("El Recurso no ha sido prestado");
                }
        );
    }
}
