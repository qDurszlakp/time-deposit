package org.ikigaidigital.domain.strategy;

import org.ikigaidigital.domain.model.PlanType;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PremiumInterestStrategy implements InterestCalculationStrategy {

    private static final BigDecimal ANNUAL_RATE = BigDecimal.valueOf(0.05);
    private static final BigDecimal MONTHS_IN_YEAR = BigDecimal.valueOf(12);

    @Override
    public BigDecimal calculateMonthlyInterest(int days, BigDecimal balance) {
        if (days <= 45 || balance == null || balance.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        return balance.multiply(ANNUAL_RATE)
                .divide(MONTHS_IN_YEAR, 10, RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public PlanType supportedPlan() {
        return PlanType.PREMIUM;
    }
}
