package de.workshops.bookshelf.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties("bookshelf")
@Getter
@Setter
@Validated
public class BookshelfProperties {

    /**
     * Some number
     * Default value is 10.
     * The allowed values are in the range 0-20.
     */
    @Min(0)
    @Max(20)
    private Integer someNumber = 10;

    /**
     * Some text
     */
    private String someText;

    /**
     * A list
     */
    private List<String> someList;
}
