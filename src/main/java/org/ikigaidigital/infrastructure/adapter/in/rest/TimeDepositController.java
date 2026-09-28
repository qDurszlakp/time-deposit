package org.ikigaidigital.infrastructure.adapter.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ikigaidigital.domain.port.in.GetTimeDepositsUseCase;
import org.ikigaidigital.domain.port.in.UpdateBalancesUseCase;
import org.ikigaidigital.infrastructure.adapter.in.rest.dto.TimeDepositResponse;
import org.ikigaidigital.infrastructure.adapter.in.rest.mapper.TimeDepositRestMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/time-deposits")
@Tag(name = "Time Deposits", description = "API for managing bank time deposits and monthly interest calculations")
public class TimeDepositController {

    private final GetTimeDepositsUseCase getTimeDepositsUseCase;
    private final UpdateBalancesUseCase updateBalancesUseCase;
    private final TimeDepositRestMapper mapper;

    public TimeDepositController(GetTimeDepositsUseCase getTimeDepositsUseCase,
                                 UpdateBalancesUseCase updateBalancesUseCase,
                                 TimeDepositRestMapper mapper) {
        this.getTimeDepositsUseCase = getTimeDepositsUseCase;
        this.updateBalancesUseCase = updateBalancesUseCase;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(summary = "Get all time deposits", description = "Retrieves all time deposit plans with their associated historical withdrawals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved time deposits")
    })
    public List<TimeDepositResponse> getAllTimeDeposits() {
        return getTimeDepositsUseCase.getAllTimeDeposits().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @PostMapping("/update-balances")
    @Operation(summary = "Update balances", description = "Performs monthly interest calculation and updates account balances for all time deposits")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balances successfully updated")
    })
    public ResponseEntity<String> updateBalances() {
        updateBalancesUseCase.updateBalances();
        return ResponseEntity.ok("Balances updated");
    }
}
