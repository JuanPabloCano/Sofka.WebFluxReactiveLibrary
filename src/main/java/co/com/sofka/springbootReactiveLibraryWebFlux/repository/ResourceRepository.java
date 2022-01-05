package co.com.sofka.springbootReactiveLibraryWebFlux.repository;

import co.com.sofka.springbootReactiveLibraryWebFlux.enums.ResourceCategory;
import co.com.sofka.springbootReactiveLibraryWebFlux.enums.ResourceType;
import co.com.sofka.springbootReactiveLibraryWebFlux.collections.Resource;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ResourceRepository extends ReactiveMongoRepository<Resource, String> {

    Mono<Resource> findByName(String bookName);

    Flux<Resource> findByResourceCategoryAndResourceType(String resourceCategory,
                                                         String resourceType);
}
