package com.michael.Personal.Finance.users;

import com.michael.Personal.Finance.account.Account;
import com.michael.Personal.Finance.expense.Expense;
import com.michael.Personal.Finance.finance.FinancialGoal;
import com.michael.Personal.Finance.income.Income;
import com.michael.Personal.Finance.role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class AppUser  implements UserDetails , Principal {


    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  Long id;

    private  String username;

    private String firstName;

    private String lastName;

    private String password;

    private String phoneNumber;

    private String email;

    @OneToOne(fetch = FetchType.EAGER)
    private Address address;

    private boolean isEnabled;

    private boolean isAccountLocked;

    private LocalDate dateOfBirth;

    @ManyToMany
    Set<Role> roles = new HashSet<>();
//    @OneToMany(mappedBy = "user")
//    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Income> incomes = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Expense> expenses = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<FinancialGoal> financialGoals = new HashSet<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    @Override
    public String getName() {
        return email;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.
                stream()
                .map(role ->new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }


    public String getFullName() {
        return firstName + " " + lastName;
    }
}
