package co.com.sofka.springbootReactiveLibraryWebFlux.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    private String message;
    private boolean state;

}
