package org.ikigaidigital.application;

import org.ikigaidigital.TimeDeposit;
import org.ikigaidigital.TimeDepositCalculator;
import org.ikigaidigital.domain.model.TimeDepositWithWithdrawals;
import org.ikigaidigital.domain.port.in.GetTimeDepositsUseCase;
import org.ikigaidigital.domain.port.in.UpdateBalancesUseCase;
import org.ikigaidigital.domain.port.out.TimeDepositRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TimeDepositService implements GetTimeDepositsUseCase, UpdateBalancesUseCase {

    private final TimeDepositRepositoryPort repositoryPort;
    private final TimeDepositCalculator calculator;

    public TimeDepositService(TimeDepositRepositoryPort repositoryPort, TimeDepositCalculator calculator) {
        this.repositoryPort = repositoryPort;
        this.calculator = calculator;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeDepositWithWithdrawals> getAllTimeDeposits() {
        return repositoryPort.findAllWithWithdrawals();
    }

    @Override
    @Transactional
    public void updateBalances() {
        List<TimeDeposit> deposits = repositoryPort.findAll();
        calculator.updateBalance(deposits);
        repositoryPort.saveAll(deposits);
    }
}
