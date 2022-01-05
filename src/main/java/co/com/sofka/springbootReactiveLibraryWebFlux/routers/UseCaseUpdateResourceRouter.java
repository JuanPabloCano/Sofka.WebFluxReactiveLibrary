package co.com.sofka.springbootReactiveLibraryWebFlux.routers;

import co.com.sofka.springbootReactiveLibraryWebFlux.dto.ResourceDto;
import co.com.sofka.springbootReactiveLibraryWebFlux.useCases.UseCaseUpdateResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UseCaseUpdateResourceRouter {

    @Bean
    public RouterFunction<ServerResponse> updateResource(UseCaseUpdateResource useCaseUpdateResource) {
        return route(PUT("/recursos/modificar").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ResourceDto.class)
                        .flatMap(questionDTO -> useCaseUpdateResource.saveResource(questionDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.TEXT_PLAIN)
                                        .bodyValue(result))
                        )
        );
    }
}
