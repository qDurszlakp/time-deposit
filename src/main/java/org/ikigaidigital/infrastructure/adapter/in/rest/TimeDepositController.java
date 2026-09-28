package org.ikigaidigital.infrastructure.adapter.in.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/time-deposits")
public class TimeDepositController {

    @GetMapping
    public String getAllTimeDeposits() {
        return "Time deposits";
    }

    @PostMapping("/update-balances")
    public String updateBalances() {
        return "Balances updated";
    }
}
