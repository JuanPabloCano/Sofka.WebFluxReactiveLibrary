package co.com.sofka.springbootReactiveLibraryWebFlux.routers;

import co.com.sofka.springbootReactiveLibraryWebFlux.dto.ResourceDto;
import co.com.sofka.springbootReactiveLibraryWebFlux.enums.ResourceCategory;
import co.com.sofka.springbootReactiveLibraryWebFlux.enums.ResourceType;
import co.com.sofka.springbootReactiveLibraryWebFlux.mappers.ResourceMapper;
import co.com.sofka.springbootReactiveLibraryWebFlux.collections.Resource;
import co.com.sofka.springbootReactiveLibraryWebFlux.repository.ResourceRepository;
import co.com.sofka.springbootReactiveLibraryWebFlux.useCases.UseCaseGetAllResources;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.Date;

import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UseCaseGetAllResourcesRouter.class,
        UseCaseGetAllResources.class,
        ResourceMapper.class})
class UseCaseGetAllResourcesRouterTest {

    @MockBean
    private ResourceRepository resourceRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getAllResources() {

        var resource1 = new Resource();
        resource1.setId("xxxx");
        resource1.setAuthor("Gabrel Garcia Marquez");
        resource1.setName("El coronel no tiene quien le escriba");
        resource1.setReturnDate(new Date());
        resource1.setResourceCategory(ResourceCategory.FICTION);
        resource1.setResourceType(ResourceType.BOOK);
        resource1.setAvailable(true);

        var resource2 = new Resource();
        resource2.setId("aaaa");
        resource2.setAuthor("Gabrel Garcia Marquez");
        resource2.setName("Mil años de soledad");
        resource2.setReturnDate(new Date());
        resource2.setResourceCategory(ResourceCategory.FICTION);
        resource2.setResourceType(ResourceType.BOOK);
        resource2.setAvailable(true);

        when(resourceRepository.findAll()).thenReturn(Flux.just(resource1, resource2));

        webTestClient.get()
                .uri("/consultar")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ResourceDto.class)
                .value(resourceResponse -> {
                    Assertions.assertThat(resourceResponse.get(0).getAuthor()).isEqualTo(resource1.getAuthor());
                    Assertions.assertThat(resourceResponse.get(1).getAuthor()).isEqualTo(resource2.getAuthor());
                });
    }
}