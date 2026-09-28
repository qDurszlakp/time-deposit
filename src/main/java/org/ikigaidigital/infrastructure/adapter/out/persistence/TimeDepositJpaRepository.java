package org.ikigaidigital.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeDepositJpaRepository extends JpaRepository<TimeDepositEntity, Integer> {

    @Override
    @EntityGraph(attributePaths = "withdrawals")
    List<TimeDepositEntity> findAll();
}
