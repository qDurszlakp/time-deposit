package org.ikigaidigital.infrastructure.configuration;

import org.ikigaidigital.TimeDepositCalculator;
import org.ikigaidigital.domain.strategy.BasicInterestStrategy;
import org.ikigaidigital.domain.strategy.InterestCalculationStrategy;
import org.ikigaidigital.domain.strategy.PremiumInterestStrategy;
import org.ikigaidigital.domain.strategy.StudentInterestStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DomainConfiguration {

    @Bean
    public BasicInterestStrategy basicInterestStrategy() {
        return new BasicInterestStrategy();
    }

    @Bean
    public StudentInterestStrategy studentInterestStrategy() {
        return new StudentInterestStrategy();
    }

    @Bean
    public PremiumInterestStrategy premiumInterestStrategy() {
        return new PremiumInterestStrategy();
    }

    @Bean
    public TimeDepositCalculator timeDepositCalculator(List<InterestCalculationStrategy> strategies) {
        return new TimeDepositCalculator(strategies);
    }
}
