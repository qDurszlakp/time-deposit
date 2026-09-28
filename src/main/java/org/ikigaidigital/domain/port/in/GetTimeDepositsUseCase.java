package org.ikigaidigital.domain.port.in;

import org.ikigaidigital.domain.model.TimeDepositWithWithdrawals;

import java.util.List;

public interface GetTimeDepositsUseCase {
    List<TimeDepositWithWithdrawals> getAllTimeDeposits();
}
