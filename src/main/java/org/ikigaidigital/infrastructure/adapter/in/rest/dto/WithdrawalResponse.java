package org.ikigaidigital.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;

@Schema(description = "Withdrawal details")
public record WithdrawalResponse(
        @Schema(description = "Withdrawal ID", example = "1")
        Integer id,

        @Schema(description = "Associated time deposit ID", example = "2")
        Integer timeDepositId,

        @Schema(description = "Withdrawn amount", example = "500.00")
        BigDecimal amount,

        @Schema(description = "Date and time of withdrawal in ISO-8601 UTC format", example = "2026-01-15T10:30:00Z")
        Instant date
) {}
