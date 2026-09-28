package org.ikigaidigital.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "time_deposits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeDepositEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "plan_type", nullable = false, length = 50)
    private String planType;

    @Column(name = "days", nullable = false)
    private Integer days;

    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_deposit_id")
    private List<WithdrawalEntity> withdrawals = new ArrayList<>();
}
