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
        // given
        BigDecimal balance = BigDecimal.valueOf(1000.00);
        BigDecimal largeBalance = BigDecimal.valueOf(1234567.00);

        // when
        BigDecimal interestWithin30Days = basic.calculateMonthlyInterest(20, balance);
        BigDecimal interestOver30Days = basic.calculateMonthlyInterest(45, largeBalance);

        // then
        assertThat(interestWithin30Days).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(interestOver30Days).isEqualByComparingTo(new BigDecimal("1028.81"));
    }

    @Test
    @DisplayName("StudentStrategy: 0% <= 30 days, 3% between 31 and 365 days, 0% > 365 days")
    void studentStrategy() {
        // given
        BigDecimal balance = BigDecimal.valueOf(2000.00);
        BigDecimal midBalance = BigDecimal.valueOf(5000.00);
        BigDecimal matureBalance = BigDecimal.valueOf(3000.00);

        // when
        BigDecimal interestWithin30Days = student.calculateMonthlyInterest(25, balance);
        BigDecimal interestWithinYear = student.calculateMonthlyInterest(90, midBalance);
        BigDecimal interestAfterYear = student.calculateMonthlyInterest(400, matureBalance);

        // then
        assertThat(interestWithin30Days).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(interestWithinYear).isEqualByComparingTo(new BigDecimal("12.50"));
        assertThat(interestAfterYear).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("PremiumStrategy: 0% <= 45 days, 5% annual > 45 days")
    void premiumStrategy() {
        // given
        BigDecimal balance = BigDecimal.valueOf(10000.00);
        BigDecimal largeBalance = BigDecimal.valueOf(50000.00);

        // when
        BigDecimal interestWithin45Days = premium.calculateMonthlyInterest(35, balance);
        BigDecimal interestOver45Days = premium.calculateMonthlyInterest(60, largeBalance);

        // then
        assertThat(interestWithin45Days).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(interestOver45Days).isEqualByComparingTo(new BigDecimal("208.33"));
    }
}
