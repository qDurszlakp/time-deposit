package org.ikigaidigital.domain;

import org.ikigaidigital.domain.strategy.BasicInterestStrategy;
import org.ikigaidigital.domain.strategy.InterestCalculationStrategy;
import org.ikigaidigital.domain.strategy.PremiumInterestStrategy;
import org.ikigaidigital.domain.strategy.StudentInterestStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class InterestCalculationStrategyTest {

    private final InterestCalculationStrategy basic = new BasicInterestStrategy();
    private final InterestCalculationStrategy student = new StudentInterestStrategy();
    private final InterestCalculationStrategy premium = new PremiumInterestStrategy();


    @Test
    @DisplayName("BasicStrategy: 0% <= 30 days, 1% annual > 30 days")
    void basicStrategy() {
        assertThat(basic.calculateMonthlyInterest(20, BigDecimal.valueOf(1000.00)))
                .isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(basic.calculateMonthlyInterest(45, BigDecimal.valueOf(1234567.00)))
                .isEqualByComparingTo(new BigDecimal("1028.81"));
    }

    @Test
    @DisplayName("StudentStrategy: 0% <= 30 days, 3% between 31 and 365 days, 0% > 365 days")
    void studentStrategy() {
        assertThat(student.calculateMonthlyInterest(25, BigDecimal.valueOf(2000.00)))
                .isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(student.calculateMonthlyInterest(90, BigDecimal.valueOf(5000.00)))
                .isEqualByComparingTo(new BigDecimal("12.50"));
        assertThat(student.calculateMonthlyInterest(400, BigDecimal.valueOf(3000.00)))
                .isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("PremiumStrategy: 0% <= 45 days, 5% annual > 45 days")
    void premiumStrategy() {
        assertThat(premium.calculateMonthlyInterest(35, BigDecimal.valueOf(10000.00)))
                .isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(premium.calculateMonthlyInterest(60, BigDecimal.valueOf(50000.00)))
                .isEqualByComparingTo(new BigDecimal("208.33"));
    }
}
