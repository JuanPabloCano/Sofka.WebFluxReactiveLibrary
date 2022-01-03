package co.com.sofka.springbootReactiveLibraryWebFlux.mappers;

import co.com.sofka.springbootReactiveLibraryWebFlux.dto.ResourceDto;
import co.com.sofka.springbootReactiveLibraryWebFlux.collections.Resource;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ResourceMapper {

    public Function<ResourceDto, Resource> mapDTOToResource(String id){
        return resourceMap -> {
            var resource = new Resource();
            resource.setId(id);
            resource.setAuthor(resourceMap.getAuthor());
            resource.setName(resourceMap.getName());
            resource.setReturnDate(resourceMap.getReturnDate());
            resource.setResourceCategory(resourceMap.getResourceCategory());
            resource.setResourceType(resourceMap.getResourceType());
            resource.setAvailable(resourceMap.isAvailable());
            return resource;
        };
    }

    public Function<Resource, ResourceDto> mapResourceToDTO(){
        return resource -> new ResourceDto(resource.getId(),
                resource.getAuthor(),
                resource.getName(),
                resource.getReturnDate(),
                resource.getResourceCategory(),
                resource.getResourceType(),
                resource.isAvailable());
    }
}
