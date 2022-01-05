package co.com.sofka.springbootReactiveLibraryWebFlux.routers;

import co.com.sofka.springbootReactiveLibraryWebFlux.useCases.UseCaseReturnResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UseCaseReturnResourceRouter {

    @Bean
    public RouterFunction<ServerResponse> returnResource(UseCaseReturnResource useCaseReturnResource) {
        return route(
                GET("/recursos/devolver/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters
                                .fromPublisher(useCaseReturnResource
                                        .apply(request.pathVariable("id")), String.class))
        );
    }
}
