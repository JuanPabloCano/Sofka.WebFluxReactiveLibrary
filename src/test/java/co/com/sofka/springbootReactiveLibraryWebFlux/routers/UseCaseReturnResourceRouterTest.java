package co.com.sofka.springbootReactiveLibraryWebFlux.routers;

import co.com.sofka.springbootReactiveLibraryWebFlux.collections.Resource;
import co.com.sofka.springbootReactiveLibraryWebFlux.dto.ResourceDto;
import co.com.sofka.springbootReactiveLibraryWebFlux.enums.ResourceCategory;
import co.com.sofka.springbootReactiveLibraryWebFlux.enums.ResourceType;
import co.com.sofka.springbootReactiveLibraryWebFlux.mappers.ResourceMapper;
import co.com.sofka.springbootReactiveLibraryWebFlux.repository.ResourceRepository;
import co.com.sofka.springbootReactiveLibraryWebFlux.useCases.UseCaseReturnResource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import java.util.Date;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UseCaseReturnResourceRouter.class,
        UseCaseReturnResource.class, ResourceMapper.class})
class UseCaseReturnResourceRouterTest {

    @MockBean
    private ResourceRepository resourceRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void returnResourceTest() {

        var resource = new Resource(
                "xxxxxxxx",
                "Gabriel Garcia Marquez",
                "El coronel no tiene quien le escriba",
                new Date(),
                ResourceCategory.FICTION,
                ResourceType.BOOK,
                true);

        var resourceDto = new ResourceDto(
                resource.getId(),
                resource.getAuthor(),
                resource.getName(),
                resource.getReturnDate(),
                resource.getResourceCategory(),
                resource.getResourceType(),
                resource.isAvailable()
        );

        Mono<Resource> resourceMono = Mono.just(resource);
        when(resourceRepository.findById(resource.getId())).thenReturn(resourceMono);
        when(resourceRepository.save(any())).thenReturn(resourceMono);

        webTestClient.get()
                .uri("/recursos/devolver/{id}", "xxxxxxxx")
                .exchange()
                .expectStatus().isAccepted()
                .expectBody(String.class)
                .value(userResponse -> Assertions.assertThat(userResponse)
                        .isEqualTo("El recurso fue entregado con éxito"));
    }
}
