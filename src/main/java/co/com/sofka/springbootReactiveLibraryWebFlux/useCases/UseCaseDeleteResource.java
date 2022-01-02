package co.com.sofka.springbootReactiveLibraryWebFlux.useCases;

import co.com.sofka.springbootReactiveLibraryWebFlux.repository.ResourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;

@AllArgsConstructor
@Service
@Validated
public class UseCaseDeleteResource implements Function<String, Mono<Void>> {

    private final ResourceRepository resourceRepository;

    @Override
    public Mono<Void> apply(String id) {
        Objects.requireNonNull(id, "El ID es requerido");
        return resourceRepository.existsById(id)
                .flatMap(deleteId -> {
                    if (deleteId){
                        return resourceRepository.deleteById(id);
                    }
                    throw new NoSuchElementException("No se pudo eliminar el recurso porque el ID no existe");
                });
    }
}
