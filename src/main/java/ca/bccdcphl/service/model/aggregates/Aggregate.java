package ca.bccdcphl.service.model.aggregates;

import ca.bccdcphl.service.model.SomeEntity;

import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="aggregate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aggregate extends AbstractAggregateRoot<Aggregate> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String aggregateId;
    @OneToMany(mappedBy="aggregate", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    private List<SomeEntity> entities;
}
