package ca.bccdcphl.service.service;

import ca.bccdcphl.service.dto.AggregateDTO;
import ca.bccdcphl.service.model.aggregates.Aggregate;
import ca.bccdcphl.service.repositories.AggregateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AggregateService {
    @Autowired
    private AggregateRepository repo;

    public Aggregate createNullAggregate(String aggregateId) {
        Aggregate aggregate = Aggregate.builder()
                .aggregateId(aggregateId)
                .build();

        return repo.save(aggregate);
    }

    public Aggregate createAggregate(AggregateDTO aggregateDTO) {
        Aggregate aggregate = Aggregate.builder()
                .aggregateId(aggregateDTO.getId())
                .build();
        return repo.save(aggregate);
    }

    public Iterable<Aggregate> getAggregates() {
        return repo.findAll();
    }

    public Optional<Aggregate> getAggregateById(final String aggregateId) {
        return repo.findByAggregateId(aggregateId);
    }

}
