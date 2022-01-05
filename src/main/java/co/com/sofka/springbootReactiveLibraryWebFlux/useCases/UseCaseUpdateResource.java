package co.com.sofka.springbootReactiveLibraryWebFlux.useCases;

import co.com.sofka.springbootReactiveLibraryWebFlux.collections.Resource;
import co.com.sofka.springbootReactiveLibraryWebFlux.dto.ResourceDto;
import co.com.sofka.springbootReactiveLibraryWebFlux.mappers.ResourceMapper;
import co.com.sofka.springbootReactiveLibraryWebFlux.repository.ResourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@AllArgsConstructor
@Service
@Validated
public class UseCaseUpdateResource implements SaveResource{

    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;

    @Override
    public Mono<String> saveResource(ResourceDto resourceDto) {
        Objects.requireNonNull(resourceDto.getId(), "El ID del recurso es requerido");
        return resourceRepository.save(resourceMapper.mapDTOToResource(resourceDto.getId())
                .apply(resourceDto))
                .map(Resource::getId);
    }
}
