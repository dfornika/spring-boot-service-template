package ca.bccdcphl.service.dev;

import ca.bccdcphl.service.dto.AggregateDTO;
import ca.bccdcphl.service.model.aggregates.Aggregate;
import ca.bccdcphl.service.service.AggregateService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JsonAggregateLoader {
    static final Logger log = LoggerFactory.getLogger(JsonAggregateLoader.class);
    private final ResourceLoader resourceLoader;
    private final AggregateService service;
    private final ObjectMapper objectMapper;

    @Autowired
    public JsonAggregateLoader(ResourceLoader resourceLoader, AggregateService service, ObjectMapper objectMapper) {
        this.resourceLoader = resourceLoader;
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public Iterable<Aggregate> buildAggregatesFromJson() throws IOException {
        log.info("STARTING DEV JSON AGGREGATES LOADER");
        List<Aggregate> savedAggregates = new ArrayList<>();
        try {
            File resource = resourceLoader.getResource("classpath:aggregates.json").getFile();
            JsonNode tree = objectMapper.readTree(resource);
            for (JsonNode jsonAggregate : tree) {
                AggregateDTO aggregateDTO = objectMapper.treeToValue(jsonAggregate, AggregateDTO.class);
                Aggregate savedAggregate = service.createAggregate(aggregateDTO);
                savedAggregates.add(savedAggregate);
            }
        } catch (FileNotFoundException ignored) {

        }
        return savedAggregates;
    }
}
