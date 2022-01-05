package co.com.sofka.springbootReactiveLibraryWebFlux.routers;

import co.com.sofka.springbootReactiveLibraryWebFlux.collections.Resource;
import co.com.sofka.springbootReactiveLibraryWebFlux.dto.ResourceDto;
import co.com.sofka.springbootReactiveLibraryWebFlux.enums.ResourceCategory;
import co.com.sofka.springbootReactiveLibraryWebFlux.enums.ResourceType;
import co.com.sofka.springbootReactiveLibraryWebFlux.mappers.ResourceMapper;
import co.com.sofka.springbootReactiveLibraryWebFlux.repository.ResourceRepository;
import co.com.sofka.springbootReactiveLibraryWebFlux.useCases.UseCaseGetResourceByCategoryAndType;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UseCaseGetResourceByCategoryAndTypeRouter.class,
        UseCaseGetResourceByCategoryAndType.class, ResourceMapper.class})
class UseCaseGetResourceByCategoryAndTypeRouterTest {

    @MockBean
    ResourceRepository resourceRepository;

    @Autowired
    WebTestClient webTestClient;

    @Test
    void useCaseGetResourceByCategoryAndTypeRouterTest() {

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

        Mono<Resource> resourceMono = Mono.just(resource1);

        when(resourceRepository.findByResourceCategoryAndResourceType(resource1.getResourceCategory().toString(),
                resource1.getResourceType().toString()))
                .thenReturn(Flux.just(resource1, resource2));

        webTestClient.get()
                .uri("/recursos/filtrar/FICTION/BOOK")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ResourceDto.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse.get(0).getResourceCategory()).isEqualTo(resource1.getResourceCategory());
                            Assertions.assertThat(userResponse.get(0).getResourceType()).isEqualTo(resource1.getResourceType());
                            Assertions.assertThat(userResponse.get(1).getResourceCategory()).isEqualTo(resource2.getResourceCategory());
                            Assertions.assertThat(userResponse.get(1).getResourceType()).isEqualTo(resource2.getResourceType());
                        }
                );

        Mockito.verify(resourceRepository, Mockito.times(1))
                .findByResourceCategoryAndResourceType("FICTION", "BOOK");
    }
}