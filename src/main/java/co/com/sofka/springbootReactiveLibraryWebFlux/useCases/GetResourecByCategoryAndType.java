package co.com.sofka.springbootReactiveLibraryWebFlux.useCases;

import co.com.sofka.springbootReactiveLibraryWebFlux.dto.ResourceDto;
import reactor.core.publisher.Flux;

public interface GetResourecByCategoryAndType {

    Flux<ResourceDto> apply(String category, String type);
}
