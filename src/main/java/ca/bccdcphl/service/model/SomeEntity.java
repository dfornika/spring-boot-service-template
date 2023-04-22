package ca.bccdcphl.service.model;

import ca.bccdcphl.service.model.aggregates.Aggregate;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Entity
@Table(name="entity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SomeEntity extends AbstractPersistable<Long> {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String entityId;
    private String attribute;
    @ManyToOne
    @JoinColumn(name="aggregate_id", referencedColumnName="id")
    private Aggregate aggregate;
}
