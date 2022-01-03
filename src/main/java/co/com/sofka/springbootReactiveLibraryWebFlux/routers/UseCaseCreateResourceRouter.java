package co.com.sofka.springbootReactiveLibraryWebFlux.routers;

import co.com.sofka.springbootReactiveLibraryWebFlux.dto.ResourceDto;
import co.com.sofka.springbootReactiveLibraryWebFlux.useCases.UseCaseCreateResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UseCaseCreateResourceRouter {

    @Bean
    public RouterFunction<ServerResponse> createResource(UseCaseCreateResource useCaseCreateResource) {
        return route(POST("/biblioteca/crear").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ResourceDto.class)
                        .flatMap(questionDTO -> useCaseCreateResource.saveResource(questionDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.TEXT_PLAIN)
                                        .bodyValue(result))
                        )
        );
    }
}
