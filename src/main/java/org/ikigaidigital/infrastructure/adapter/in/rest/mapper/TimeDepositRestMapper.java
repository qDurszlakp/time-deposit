package org.ikigaidigital.infrastructure.adapter.in.rest.mapper;

import org.ikigaidigital.domain.model.TimeDepositWithWithdrawals;
import org.ikigaidigital.domain.model.Withdrawal;
import org.ikigaidigital.infrastructure.adapter.in.rest.dto.TimeDepositResponse;
import org.ikigaidigital.infrastructure.adapter.in.rest.dto.WithdrawalResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimeDepositRestMapper {

    public TimeDepositResponse toResponse(TimeDepositWithWithdrawals deposit) {
        if (deposit == null) {
            return null;
        }

        List<WithdrawalResponse> withdrawals = deposit.withdrawals() != null
                ? deposit.withdrawals().stream().map(this::toWithdrawalResponse).toList()
                : List.of();

        return new TimeDepositResponse(
                deposit.id(),
                deposit.planType(),
                deposit.balance(),
                deposit.days(),
                withdrawals
        );
    }

    public WithdrawalResponse toWithdrawalResponse(Withdrawal withdrawal) {
        if (withdrawal == null) {
            return null;
        }

        return new WithdrawalResponse(
                withdrawal.id(),
                withdrawal.timeDepositId(),
                withdrawal.amount(),
                withdrawal.date()
        );
    }
}
