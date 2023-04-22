package ca.bccdcphl.service.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Relation(collectionRelation = "entities", itemRelation = "entity")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EntityDTO extends RepresentationModel<EntityDTO> {
    private String id;
    private String attribute;
}
