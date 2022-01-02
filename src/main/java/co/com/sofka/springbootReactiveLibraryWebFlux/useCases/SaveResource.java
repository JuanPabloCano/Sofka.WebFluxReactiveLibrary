package co.com.sofka.springbootReactiveLibraryWebFlux.useCases;

import co.com.sofka.springbootReactiveLibraryWebFlux.dto.ResourceDto;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface SaveResource {
    Mono<ResourceDto> saveResource(ResourceDto resourceDto);
}