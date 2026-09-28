package org.ikigaidigital.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "time_deposits")
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

    public TimeDepositEntity() {
    }

    public TimeDepositEntity(Integer id, String planType, Integer days, BigDecimal balance, List<WithdrawalEntity> withdrawals) {
        this.id = id;
        this.planType = planType;
        this.days = days;
        this.balance = balance;
        this.withdrawals = withdrawals != null ? withdrawals : new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<WithdrawalEntity> getWithdrawals() {
        return withdrawals;
    }

    public void setWithdrawals(List<WithdrawalEntity> withdrawals) {
        this.withdrawals = withdrawals;
    }
}
