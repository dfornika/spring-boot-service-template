package ca.bccdcphl.service.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Relation(collectionRelation = "aggregates", itemRelation = "aggregate")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AggregateDTO extends RepresentationModel<AggregateDTO> {
    private String id;

}
