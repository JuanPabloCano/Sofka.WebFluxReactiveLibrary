package co.com.sofka.springbootReactiveLibraryWebFlux.routers;

import co.com.sofka.springbootReactiveLibraryWebFlux.mappers.ResourceMapper;
import co.com.sofka.springbootReactiveLibraryWebFlux.collections.Resource;
import co.com.sofka.springbootReactiveLibraryWebFlux.repository.ResourceRepository;
import co.com.sofka.springbootReactiveLibraryWebFlux.useCases.UseCaseDeleteResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UseCaseDeleteResourceRouter.class,
        UseCaseDeleteResource.class,
        ResourceMapper.class})
class UseCaseDeleteResourceRouterTest {

    @MockBean
    private ResourceRepository resourceRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void deleteResource(){

        Mono<Void> voidMono = Mono.empty();

        when(resourceRepository.deleteById("1")).thenReturn(voidMono);

        webTestClient.delete()
                .uri("/borrar/1")
                .exchange()
                .expectStatus()
                .isAccepted();
    }
}