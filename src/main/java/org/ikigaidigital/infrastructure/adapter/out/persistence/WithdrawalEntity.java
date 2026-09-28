package org.ikigaidigital.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "withdrawals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "time_deposit_id", nullable = false)
    private Integer timeDepositId;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "date", nullable = false)
    private Instant date;
}
