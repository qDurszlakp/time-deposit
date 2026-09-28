package org.ikigaidigital;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class TimeDepositApplicationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17-alpine");

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Retrieve deposits, update balances, and verify interest calculation")
    void fullTimeDepositFlow() throws Exception {
        // given
        mockMvc.perform(get("/api/v1/time-deposits")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$[?(@.id == 1)].planType").value(hasItem("basic")))
                .andExpect(jsonPath("$[?(@.id == 1)].balance").value(hasItem(1000.00)))
                .andExpect(jsonPath("$[?(@.id == 2)].planType").value(hasItem("basic")))
                .andExpect(jsonPath("$[?(@.id == 2)].balance").value(hasItem(1234567.00)))
                .andExpect(jsonPath("$[?(@.id == 2)].withdrawals[*].amount").value(containsInAnyOrder(500.00, 1000.00)))
                .andExpect(jsonPath("$[?(@.id == 2)].withdrawals[*].date").value(containsInAnyOrder("2026-01-15T10:30:00Z", "2026-02-10T14:00:00Z")));

        // when
        mockMvc.perform(post("/api/v1/time-deposits/update-balances"))
                .andExpect(status().isOk())
                .andExpect(content().string("Balances updated"));

        // then
        mockMvc.perform(get("/api/v1/time-deposits")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)))
                // Basic <= 30 days: 0% interest
                .andExpect(jsonPath("$[?(@.id == 1)].balance").value(hasItem(1000.00)))
                // Basic > 30 days: +1% annual interest (+1028.81)
                .andExpect(jsonPath("$[?(@.id == 2)].balance").value(hasItem(1235595.81)))
                // Student <= 30 days: 0% interest
                .andExpect(jsonPath("$[?(@.id == 3)].balance").value(hasItem(2000.00)))
                // Student 31-365 days: +3% annual interest (+12.50)
                .andExpect(jsonPath("$[?(@.id == 4)].balance").value(hasItem(5012.50)))
                // Student > 365 days: 0% interest
                .andExpect(jsonPath("$[?(@.id == 5)].balance").value(hasItem(3000.00)))
                // Premium <= 45 days: 0% interest
                .andExpect(jsonPath("$[?(@.id == 6)].balance").value(hasItem(10000.00)))
                // Premium > 45 days: +5% annual interest (+208.33)
                .andExpect(jsonPath("$[?(@.id == 7)].balance").value(hasItem(50208.33)));
    }
}
