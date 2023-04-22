package ca.bccdcphl.service.assembler;

import ca.bccdcphl.service.controller.AggregateController;
import ca.bccdcphl.service.dto.AggregateDTO;
import ca.bccdcphl.service.model.aggregates.Aggregate;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AggregateAssembler implements RepresentationModelAssembler<Aggregate, AggregateDTO> {
    @Override
    public @NonNull AggregateDTO toModel(@NonNull Aggregate aggregate) {

        AggregateDTO aggregateDTO = AggregateDTO.builder()
                .id(aggregate.getAggregateId())
                .build();


        aggregateDTO.add(linkTo(methodOn(AggregateController.class).getAggregateById(aggregate.getAggregateId())).withSelfRel());

        return aggregateDTO;

    }
}
