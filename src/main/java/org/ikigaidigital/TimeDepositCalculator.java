package org.ikigaidigital;

import org.ikigaidigital.domain.model.PlanType;
import org.ikigaidigital.domain.strategy.InterestCalculationStrategy;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class TimeDepositCalculator {

    private final List<InterestCalculationStrategy> strategies;

    public TimeDepositCalculator(List<InterestCalculationStrategy> strategies) {
        this.strategies = strategies;
    }

    public void updateBalance(List<TimeDeposit> timeDeposits) {
        if (timeDeposits.isEmpty()) {
            return;
        }

        for (TimeDeposit deposit : timeDeposits) {
            if (deposit == null || deposit.getBalance() == null) {
                continue;
            }

            PlanType planType = PlanType.fromCode(deposit.getPlanType());
            
            InterestCalculationStrategy strategy = strategies.stream()
                    .filter(s -> s.supportedPlan() == planType)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No strategy found for plan: " + planType));

            BigDecimal balance = BigDecimal.valueOf(deposit.getBalance());
            BigDecimal interest = strategy.calculateMonthlyInterest(deposit.getDays(), balance);

            BigDecimal newBalance = balance.add(interest).setScale(2, RoundingMode.HALF_UP);
            deposit.setBalance(newBalance.doubleValue());
        }
    }
}

