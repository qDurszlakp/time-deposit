package org.ikigaidigital.domain.strategy;

import org.ikigaidigital.domain.model.PlanType;

import java.math.BigDecimal;

public interface InterestCalculationStrategy {
    BigDecimal calculateMonthlyInterest(int days, BigDecimal balance);
    PlanType supportedPlan();
}
