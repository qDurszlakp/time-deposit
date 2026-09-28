package org.ikigaidigital.application;

import org.ikigaidigital.TimeDeposit;
import org.ikigaidigital.TimeDepositCalculator;
import org.ikigaidigital.domain.model.TimeDepositWithWithdrawals;
import org.ikigaidigital.domain.model.Withdrawal;
import org.ikigaidigital.domain.port.out.TimeDepositRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeDepositServiceTest {

    @Mock
    private TimeDepositRepositoryPort repositoryPort;

    @Mock
    private TimeDepositCalculator calculator;

    @InjectMocks
    private TimeDepositService timeDepositService;

    @Test
    @DisplayName("Should return all time deposits with withdrawals")
    void getAllTimeDeposits_delegatesToRepository() {

        Withdrawal withdrawal = new Withdrawal(1, 2, BigDecimal.valueOf(500.00), Instant.parse("2026-01-15T10:30:00Z"));
        TimeDepositWithWithdrawals view = new TimeDepositWithWithdrawals(2, "basic", 1234567.00, 45, List.of(withdrawal));
        when(repositoryPort.findAllWithWithdrawals()).thenReturn(List.of(view));

        List<TimeDepositWithWithdrawals> result = timeDepositService.getAllTimeDeposits();

        assertThat(result).containsExactly(view);
        verify(repositoryPort).findAllWithWithdrawals();
    }

    @Test
    @DisplayName("Should calculate and save updated balances")
    void updateBalances_loadsCalculatesAndSaves() {
        
        TimeDeposit deposit = new TimeDeposit(2, "basic", 1234567.00, 45);
        List<TimeDeposit> deposits = List.of(deposit);
        when(repositoryPort.findAll()).thenReturn(deposits);

        timeDepositService.updateBalances();

        verify(repositoryPort).findAll();
        verify(calculator).updateBalance(deposits);
        verify(repositoryPort).saveAll(deposits);
    }
}
