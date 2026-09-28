package org.ikigaidigital.infrastructure.adapter.out.persistence;

import org.ikigaidigital.TimeDeposit;
import org.ikigaidigital.domain.model.TimeDepositWithWithdrawals;
import org.ikigaidigital.domain.model.Withdrawal;
import org.ikigaidigital.domain.port.out.TimeDepositRepositoryPort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TimeDepositPersistenceAdapter implements TimeDepositRepositoryPort {

    private final TimeDepositJpaRepository repository;

    public TimeDepositPersistenceAdapter(TimeDepositJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TimeDepositWithWithdrawals> findAllWithWithdrawals() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(TimeDepositEntity::getId))
                .map(this::toView)
                .toList();
    }

    @Override
    public List<TimeDeposit> findAll() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(TimeDepositEntity::getId))
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void saveAll(List<TimeDeposit> timeDeposits) {
        if (timeDeposits == null || timeDeposits.isEmpty()) {
            return;
        }

        Map<Integer, TimeDeposit> depositsById = timeDeposits.stream()
                .collect(Collectors.toMap(TimeDeposit::getId, Function.identity()));

        List<TimeDepositEntity> entities = repository.findAllById(depositsById.keySet());
        for (TimeDepositEntity entity : entities) {
            TimeDeposit domain = depositsById.get(entity.getId());
            if (domain != null && domain.getBalance() != null) {
                entity.setBalance(BigDecimal.valueOf(domain.getBalance()));
            }
        }

        repository.saveAll(entities);
    }

    private TimeDepositWithWithdrawals toView(TimeDepositEntity entity) {
        List<Withdrawal> withdrawals = entity.getWithdrawals() != null
                ? entity.getWithdrawals().stream()
                .sorted(Comparator.comparing(WithdrawalEntity::getId))
                .map(w -> new Withdrawal(w.getId(), w.getTimeDepositId(), w.getAmount(), w.getDate()))
                .toList()
                : List.of();

        return new TimeDepositWithWithdrawals(
                entity.getId(),
                entity.getPlanType(),
                entity.getBalance() != null ? entity.getBalance().doubleValue() : null,
                entity.getDays(),
                withdrawals
        );
    }

    private TimeDeposit toDomain(TimeDepositEntity entity) {
        return new TimeDeposit(
                entity.getId(),
                entity.getPlanType(),
                entity.getBalance() != null ? entity.getBalance().doubleValue() : null,
                entity.getDays()
        );
    }
}
