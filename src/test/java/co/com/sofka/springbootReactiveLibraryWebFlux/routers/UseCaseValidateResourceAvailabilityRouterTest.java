package co.com.sofka.springbootReactiveLibraryWebFlux.routers;

import co.com.sofka.springbootReactiveLibraryWebFlux.enums.ResourceCategory;
import co.com.sofka.springbootReactiveLibraryWebFlux.enums.ResourceType;
import co.com.sofka.springbootReactiveLibraryWebFlux.mappers.ResourceMapper;
import co.com.sofka.springbootReactiveLibraryWebFlux.model.Resource;
import co.com.sofka.springbootReactiveLibraryWebFlux.repository.ResourceRepository;
import co.com.sofka.springbootReactiveLibraryWebFlux.useCases.UseCaseValidateResourceAvailability;
import co.com.sofka.springbootReactiveLibraryWebFlux.utils.Notification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Date;

import static org.mockito.Mockito.when;


@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UseCaseValidateResourceAvailabilityRouter.class,
        UseCaseValidateResourceAvailability.class,
        ResourceMapper.class})
class UseCaseValidateResourceAvailabilityRouterTest {

    @MockBean
    private ResourceRepository resourceRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void resourceAvailability(){

        var resource = new Resource(
                "xxxxxxxx",
                "Gabriel Garcia Marquez",
                "El coronel no tiene quien le escriba",
                new Date(),
                ResourceCategory.FICTION,
                ResourceType.BOOK,
                true );

        Mono<Resource> resourceMono = Mono.just(resource);

        when(resourceRepository.findById("xxxxxxxx")).thenReturn(resourceMono);

        webTestClient.get()
                .uri("/biblioteca/validar/xxxxxxxx")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Notification.class)
                .value(userResponse-> Assertions.assertThat(userResponse.getMessage())
                        .isEqualTo("El material esta disponible"));

        Mockito.verify(resourceRepository,Mockito.times(1)).findById("xxxxxxxx");

    }


}