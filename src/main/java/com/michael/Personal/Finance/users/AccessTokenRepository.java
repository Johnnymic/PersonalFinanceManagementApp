package com.michael.Personal.Finance.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccessTokenRepository extends JpaRepository<AccessToken,Long> , JpaSpecificationExecutor<AccessToken> {
}
