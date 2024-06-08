package com.michael.Personal.Finance.expense;

import com.michael.Personal.Finance.common.BaseEntity;
import com.michael.Personal.Finance.users.AppUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Expense extends BaseEntity {
    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;
}
