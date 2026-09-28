package org.ikigaidigital.domain.model;

import java.util.List;

public record TimeDepositWithWithdrawals(
        Integer id,
        String planType,
        Double balance,
        Integer days,
        List<Withdrawal> withdrawals
) {
    public TimeDepositWithWithdrawals {
        withdrawals = withdrawals != null ? List.copyOf(withdrawals) : List.of();
    }
}
