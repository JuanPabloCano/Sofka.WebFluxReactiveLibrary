package co.com.sofka.springbootReactiveLibraryWebFlux.useCases;

import co.com.sofka.springbootReactiveLibraryWebFlux.dto.ResourceDto;
import co.com.sofka.springbootReactiveLibraryWebFlux.mappers.ResourceMapper;
import co.com.sofka.springbootReactiveLibraryWebFlux.repository.ResourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@Service
@Validated
public class UseCaseGetResourceByCategoryAndType implements GetResourecByCategoryAndType{

    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;

    @Override
    public Flux<ResourceDto> apply(String category, String type) {
        return resourceRepository.findByResourceCategoryAndResourceType(category, type)
                .map(resourceMapper.mapResourceToDTO());
    }
}
