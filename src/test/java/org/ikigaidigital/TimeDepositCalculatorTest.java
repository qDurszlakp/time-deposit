package org.ikigaidigital;

import org.ikigaidigital.domain.strategy.BasicInterestStrategy;
import org.ikigaidigital.domain.strategy.PremiumInterestStrategy;
import org.ikigaidigital.domain.strategy.StudentInterestStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeDepositCalculatorTest {

    private TimeDepositCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new TimeDepositCalculator(List.of(
                new BasicInterestStrategy(),
                new StudentInterestStrategy(),
                new PremiumInterestStrategy()
        ));
    }

    @Test
    @DisplayName("Basic plan <= 30 days yields 0% interest")
    void basicPlan_within30Days_noInterest() {
        TimeDeposit deposit = new TimeDeposit(1, "basic", 1000.00, 20);
        calculator.updateBalance(List.of(deposit));
        assertThat(deposit.getBalance()).isEqualTo(1000.00);
    }

    @Test
    @DisplayName("Basic plan > 30 days yields 1% annual interest divided by 12")
    void basicPlan_over30Days_onePercentInterest() {
        TimeDeposit deposit = new TimeDeposit(2, "basic", 1234567.00, 45);
        calculator.updateBalance(List.of(deposit));
        // 1234567.00 * 0.01 / 12 = 1028.8058... -> 1028.81
        assertThat(deposit.getBalance()).isEqualTo(1235595.81);
    }

    @Test
    @DisplayName("Student plan <= 30 days yields 0% interest")
    void studentPlan_within30Days_noInterest() {
        TimeDeposit deposit = new TimeDeposit(3, "student", 2000.00, 25);
        calculator.updateBalance(List.of(deposit));
        assertThat(deposit.getBalance()).isEqualTo(2000.00);
    }

    @Test
    @DisplayName("Student plan between 31 and 365 days yields 3% annual interest")
    void studentPlan_between31And365Days_threePercentInterest() {
        TimeDeposit deposit = new TimeDeposit(4, "student", 5000.00, 90);
        calculator.updateBalance(List.of(deposit));
        // 5000.00 * 0.03 / 12 = 12.50
        assertThat(deposit.getBalance()).isEqualTo(5012.50);
    }

    @Test
    @DisplayName("Student plan > 365 days yields 0% interest (after 1 year)")
    void studentPlan_over365Days_noInterest() {
        TimeDeposit deposit = new TimeDeposit(5, "student", 3000.00, 400);
        calculator.updateBalance(List.of(deposit));
        assertThat(deposit.getBalance()).isEqualTo(3000.00);
    }

    @Test
    @DisplayName("Premium plan <= 45 days yields 0% interest")
    void premiumPlan_within45Days_noInterest() {
        TimeDeposit deposit = new TimeDeposit(6, "premium", 10000.00, 35);
        calculator.updateBalance(List.of(deposit));
        assertThat(deposit.getBalance()).isEqualTo(10000.00);
    }

    @Test
    @DisplayName("Premium plan > 45 days yields 5% annual interest")
    void premiumPlan_over45Days_fivePercentInterest() {
        TimeDeposit deposit = new TimeDeposit(7, "premium", 50000.00, 60);
        calculator.updateBalance(List.of(deposit));
        // 50000.00 * 0.05 / 12 = 208.3333... -> 208.33
        assertThat(deposit.getBalance()).isEqualTo(50208.33);
    }
}

