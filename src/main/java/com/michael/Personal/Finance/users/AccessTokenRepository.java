package com.michael.Personal.Finance.users;

import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccessTokenRepository extends JpaRepository<AccessToken,Long> , JpaSpecificationExecutor<AccessToken> {

    @Query(value = "SELECT token FROM  AccessToken token WHERE token.accessToken =: token", nativeQuery = true)
    Optional<AccessToken> findByAccessToken(String token);
}
