package co.com.sofka.springbootReactiveLibraryWebFlux.useCases;

import co.com.sofka.springbootReactiveLibraryWebFlux.dto.ResourceDto;
import co.com.sofka.springbootReactiveLibraryWebFlux.mappers.ResourceMapper;
import co.com.sofka.springbootReactiveLibraryWebFlux.repository.ResourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@AllArgsConstructor
@Service
@Validated
public class UseCaseGetAllResources implements Supplier<Flux<ResourceDto>> {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;


    @Override
    public Flux<ResourceDto> get() {
        return resourceRepository.findAll()
                .map(resourceMapper.mapResourceToDTO());
    }
}
