package com.michael.Personal.Finance.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccessTokenService {


    private final AccessTokenRepository repository;


    public String generateAndSaveActivationToken(AppUser newUser) {
        String otp = generateOtp(6);
        AccessToken token = new AccessToken();
        token.setUser(newUser);
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiresAt(LocalDateTime.now().plusSeconds(30));
        token.setToken(otp);
        repository.save(token);
        return  otp;
    }

    private String generateOtp(int optLength) {
        String characters = "0123456789";

        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for(int i=0; i<optLength; i++){
            Integer nextCharacter = secureRandom.nextInt(characters.length());

            codeBuilder.append(characters.charAt(nextCharacter));
        }

        return codeBuilder.toString();


    }


}
