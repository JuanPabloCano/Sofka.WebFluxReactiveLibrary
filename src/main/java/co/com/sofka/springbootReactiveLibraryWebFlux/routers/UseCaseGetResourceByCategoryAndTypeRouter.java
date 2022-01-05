package co.com.sofka.springbootReactiveLibraryWebFlux.routers;

import co.com.sofka.springbootReactiveLibraryWebFlux.dto.ResourceDto;
import co.com.sofka.springbootReactiveLibraryWebFlux.useCases.UseCaseGetResourceByCategoryAndType;
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
public class UseCaseGetResourceByCategoryAndTypeRouter {

    @Bean
    public RouterFunction<ServerResponse> findByCategoryAndType(UseCaseGetResourceByCategoryAndType useCaseGetResourceByCategoryAndType) {
        return route(
                GET("/recursos/filtrar/{category}/{type}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCaseGetResourceByCategoryAndType
                                .apply(request.pathVariable(
                                "category"), request.pathVariable(
                                "type")), ResourceDto.class))
        );
    }
}
