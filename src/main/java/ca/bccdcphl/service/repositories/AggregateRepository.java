package ca.bccdcphl.service.repositories;

import ca.bccdcphl.service.model.aggregates.Aggregate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AggregateRepository extends JpaRepository<Aggregate, Long> {
    @Override
    @NonNull
    public <S extends Aggregate> S save(@NonNull S aggregate);

    @Override
    @NonNull
    public <S extends Aggregate> List<S> saveAll(@NonNull Iterable<S> aggregates);

    @Override
    @NonNull
    public Optional<Aggregate> findById(@NonNull Long id);

    public Optional<Aggregate> findByAggregateId(@NonNull String aggregateId);

    @Override
    public boolean existsById(@NonNull Long id);

    @Override
    @NonNull
    public List<Aggregate> findAll();

    @Override
    @NonNull
    public List<Aggregate> findAllById(@NonNull Iterable<Long> ids);

    @Override
    public long count();

    @Override
    public void deleteById(@NonNull Long id);

    public void deleteByAggregateId(@NonNull String aggregateId);

    @Override
    public void delete(@NonNull Aggregate aggregate);

    @Override
    public void deleteAllById(Iterable<? extends Long> ids);

    @Override
    public void deleteAll(@NonNull Iterable<? extends Aggregate> aggregates);

    @Override
    public void deleteAll();
}
