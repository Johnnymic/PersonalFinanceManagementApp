package com.michael.Personal.Finance.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser,Long>, JpaSpecificationExecutor<AppUser> {
    Optional<AppUser> findByEmail(String username);
}
