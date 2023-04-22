package ca.bccdcphl.service.config.api;

import ca.bccdcphl.service.dto.*;
import com.toedter.spring.hateoas.jsonapi.JsonApiConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonApiConfig {
    @Bean
    JsonApiConfiguration jsonApiConfiguration() {
        JsonApiConfiguration config = new JsonApiConfiguration()
                .withPluralizedTypeRendered(false)
                .withLowerCasedTypeRendered(true)
                .withTypeForClass(AggregateDTO.class, "aggregate");

        return config;
    }
}
