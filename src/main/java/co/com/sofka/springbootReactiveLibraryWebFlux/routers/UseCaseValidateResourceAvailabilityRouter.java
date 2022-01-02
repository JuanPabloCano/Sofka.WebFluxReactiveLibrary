package co.com.sofka.springbootReactiveLibraryWebFlux.routers;

import co.com.sofka.springbootReactiveLibraryWebFlux.useCases.UseCaseValidateResourceAvailability;
import co.com.sofka.springbootReactiveLibraryWebFlux.utils.Notification;
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
public class UseCaseValidateResourceAvailabilityRouter {

    @Bean
    public RouterFunction<ServerResponse> getResourceAvailability(UseCaseValidateResourceAvailability useCaseValidateResourceAvailability) {
        return route(GET("/biblioteca/validar/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCaseValidateResourceAvailability.apply(
                                request.pathVariable("id")), Notification.class))
                        .onErrorResume((Error) -> ServerResponse.badRequest().build())
        );
    }
}
