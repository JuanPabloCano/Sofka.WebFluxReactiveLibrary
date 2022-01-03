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
import java.util.function.Function;

@RequiredArgsConstructor
@Service
@Validated
public class UseCaseValidateResourceAvailability implements Function<String, Mono<String>> {

    @Autowired
    private final ResourceRepository resourceRepository;
    @Autowired
    private final ResourceMapper resourceMapper;

    @Override
    public Mono<String> apply(String id) {
        Objects.requireNonNull(id, "El id no puede ser nulo");
        return resourceRepository.findById(id)
                .map(recurso ->
                        recurso.isAvailable()
                                ? "El recurso está disponible"
                                : "El recurso no está disponible, se prestó el día: "
                                + recurso.getReturnDate());

    }
}

