package co.com.sofka.springbootReactiveLibraryWebFlux.useCases;

import co.com.sofka.springbootReactiveLibraryWebFlux.utils.Notification;
import reactor.core.publisher.Mono;

public interface GetNotificationById {

    Mono<Notification> apply(String id);
}
