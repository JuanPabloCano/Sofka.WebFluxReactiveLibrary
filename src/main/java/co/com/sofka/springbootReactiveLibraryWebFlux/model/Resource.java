package co.com.sofka.springbootReactiveLibraryWebFlux.model;

import co.com.sofka.springbootReactiveLibraryWebFlux.enums.ResourceCategory;
import co.com.sofka.springbootReactiveLibraryWebFlux.enums.ResourceType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "demo")
public class Resource {

    @Id
    private String Id;
    private String author;
    private String name;
    private Date returnDate = null;
    private ResourceCategory resourceCategory;
    private ResourceType resourceType;
    private boolean isAvailable = true;

}
