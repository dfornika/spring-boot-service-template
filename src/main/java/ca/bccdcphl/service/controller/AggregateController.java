package ca.bccdcphl.service.controller;

import ca.bccdcphl.service.assembler.AggregateAssembler;
import ca.bccdcphl.service.dto.*;
import ca.bccdcphl.service.model.aggregates.Aggregate;
import ca.bccdcphl.service.service.AggregateService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@RestController()
public class AggregateController {

    private static final Logger log = LoggerFactory.getLogger(AggregateController.class);
    private final AggregateService aggregateService;
    private final AggregateAssembler aggregateAssembler;

    @Autowired
    public AggregateController(
            AggregateService aggregateService,
            AggregateAssembler aggregateAssembler
    ) {
        this.aggregateService = aggregateService;
        this.aggregateAssembler = aggregateAssembler;
    }


    @GetMapping(value="/aggregates", consumes = MediaType.ALL_VALUE, produces = {"application/json", "application/vnd.api+json"})
    public CollectionModel<AggregateDTO> getAggregates() {
        List<AggregateDTO> aggregateDTOList = new ArrayList<>();

        Iterable<Aggregate> aggregates = aggregateService.getAggregates();
        for (Aggregate aggregate : aggregates) {
            AggregateDTO aggregateModel = aggregateAssembler.toModel(aggregate);
            aggregateDTOList.add(aggregateModel);
        }

        return CollectionModel.of(aggregateDTOList);
    }


    @GetMapping(value="/aggregates/{aggregateId}", consumes = MediaType.ALL_VALUE, produces = {"application/json", "application/vnd.api+json"})
    public EntityModel<AggregateDTO> getAggregateById(@PathVariable final String aggregateId) {

        Optional<Aggregate> maybeAggregate = aggregateService.getAggregateById(aggregateId);

        if (maybeAggregate.isPresent()) {
            Aggregate aggregate = maybeAggregate.get();
            return EntityModel.of(aggregateAssembler.toModel(aggregate));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "not found"
            );
        }
    }
}
