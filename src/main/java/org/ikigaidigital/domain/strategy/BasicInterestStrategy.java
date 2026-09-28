package org.ikigaidigital.domain.strategy;

import org.ikigaidigital.domain.model.PlanType;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BasicInterestStrategy implements InterestCalculationStrategy {

    private static final BigDecimal ANNUAL_RATE = BigDecimal.valueOf(0.01);
    private static final BigDecimal MONTHS_IN_YEAR = BigDecimal.valueOf(12);

    @Override
    public BigDecimal calculateMonthlyInterest(int days, BigDecimal balance) {
        if (days <= 30 || balance == null || balance.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        return balance.multiply(ANNUAL_RATE)
                .divide(MONTHS_IN_YEAR, 10, RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public PlanType supportedPlan() {
        return PlanType.BASIC;
    }
}
