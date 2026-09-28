package org.ikigaidigital.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Time deposit plan details")
public record TimeDepositResponse(
        @Schema(description = "Time deposit ID", example = "1")
        Integer id,

        @Schema(description = "Plan type (e.g. basic, student, premium)", example = "basic")
        String planType,

        @Schema(description = "Current account balance", example = "1000.00")
        Double balance,

        @Schema(description = "Number of active deposit days", example = "45")
        Integer days,

        @Schema(description = "List of historical withdrawals")
        List<WithdrawalResponse> withdrawals
) {}
