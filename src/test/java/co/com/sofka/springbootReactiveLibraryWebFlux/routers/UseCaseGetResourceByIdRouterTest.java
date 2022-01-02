package co.com.sofka.springbootReactiveLibraryWebFlux.routers;

import co.com.sofka.springbootReactiveLibraryWebFlux.enums.ResourceCategory;
import co.com.sofka.springbootReactiveLibraryWebFlux.enums.ResourceType;
import co.com.sofka.springbootReactiveLibraryWebFlux.mappers.ResourceMapper;
import co.com.sofka.springbootReactiveLibraryWebFlux.model.Resource;
import co.com.sofka.springbootReactiveLibraryWebFlux.repository.ResourceRepository;
import co.com.sofka.springbootReactiveLibraryWebFlux.useCases.UseCaseGetResourceById;
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
import static reactor.core.publisher.Mono.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UseCaseGetResourceByIdRouter.class, UseCaseGetResourceById.class,
        ResourceMapper.class})
class UseCaseGetResourceByIdRouterTest {

    @MockBean
    private ResourceRepository resourceRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getResourceByID(){

        var resource = new Resource(
                "xxxxxxxx",
                "Gabriel Garcia Marquez",
                "El coronel no tiene quien le escriba",
                new Date(),
                ResourceCategory.FICTION,
                ResourceType.BOOK,
                true );

        Mono<Resource> resourceMono = Mono.just(resource);
        when(resourceRepository.findById(resource.getId())).thenReturn(resourceMono);

        webTestClient.get()
                .uri("/biblioteca/xxxxxxxx")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Resource.class)
                .value(response -> Assertions.assertThat(response.getId()).isEqualTo(resource.getId()));
        Mockito.verify(resourceRepository, Mockito.times(1)).findById("xxxxxxxx");
    }
}