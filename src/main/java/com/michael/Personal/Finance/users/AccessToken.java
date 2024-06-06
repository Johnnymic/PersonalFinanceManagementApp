package com.michael.Personal.Finance.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccessToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accessToken;

    private LocalDateTime createdAt;

    private LocalDateTime validatedAt;

    private LocalDateTime  expiresAt;

    @ManyToOne
    @JoinColumn(name = "userId")
    private AppUser user;
}
