package org.ikigaidigital.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public record Withdrawal(
        Integer id,
        Integer timeDepositId,
        BigDecimal amount,
        Instant date
) {}
