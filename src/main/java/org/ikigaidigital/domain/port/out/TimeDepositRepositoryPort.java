package org.ikigaidigital.domain.port.out;

import org.ikigaidigital.TimeDeposit;
import org.ikigaidigital.domain.model.TimeDepositWithWithdrawals;

import java.util.List;

public interface TimeDepositRepositoryPort {
    List<TimeDepositWithWithdrawals> findAllWithWithdrawals();
    List<TimeDeposit> findAll();
    void saveAll(List<TimeDeposit> timeDeposits);
}
