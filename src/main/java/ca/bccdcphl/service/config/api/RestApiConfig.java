package ca.bccdcphl.service.config.api;

import ca.bccdcphl.service.model.aggregates.Aggregate;
import ca.bccdcphl.service.repositories.AggregateRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestApiConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        // This disables the default Spring Data REST endpoints, allowing
        // us to control how our endpoints are configured.
        config.disableDefaultExposure();
        config.withEntityLookup()
                .forRepository(AggregateRepository.class)
                .withIdMapping(Aggregate::getAggregateId)
                .withLookup(AggregateRepository::findByAggregateId);
    }


}
