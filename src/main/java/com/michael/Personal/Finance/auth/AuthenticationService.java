package com.michael.Personal.Finance.auth;

import com.michael.Personal.Finance.email.EmailService;
import com.michael.Personal.Finance.email.EmailServiceTemplate;
import com.michael.Personal.Finance.users.AccessToken;
import com.michael.Personal.Finance.users.AccessTokenRepository;
import com.michael.Personal.Finance.users.AppUser;
import com.michael.Personal.Finance.users.AppUserRepository;
import com.michael.Personal.Finance.utils.DefaultApiResponse;
import com.michael.Personal.Finance.utils.ResponseMessage;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AppUserRepository userRepository;

    private  final AccessTokenRepository accessTokenRepository;

    private  final PasswordEncoder passwordEncoder;

    private  final EmailService emailService;



    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;


    @Transactional
    public DefaultApiResponse<AuthenticationResponse> registerUser(AuthenticationRequest authenticationRequest) throws MessagingException {
        DefaultApiResponse response = new DefaultApiResponse();
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        //check for role and it should be user
         boolean existEmail = userRepository.findByEmail(authenticationRequest.getEmail()).isPresent();

         if(existEmail){
             // return a default api request
              response.setStatus(ResponseMessage.ACCOUNT_ALREADY_EXITS.getResponseMessage());
              response.setMessage(ResponseMessage.ACCOUNT_ALREADY_EXITS.getResponseCode());

              return response;
         }
         AppUser newUser = new AppUser();
         newUser.setEmail(authenticationRequest.getEmail());
         newUser.setPassword(passwordEncoder.encode(authenticationRequest.getPassword()));
         newUser.setEnabled(false);
         userRepository.save(newUser);


         String regOTP=  sendValidationEmail(newUser);
         authenticationResponse.setToken(regOTP);

         response.setStatus(ResponseMessage.SUCCESS.getResponseMessage());
         response.setMessage(ResponseMessage.SUCCESS.getResponseCode());
         response.setData(authenticationResponse);
         return  response;
    }

    private String sendValidationEmail(AppUser newUser) throws MessagingException {

        String  newToken = generateAndSaveActivationToken(newUser);
        emailService.sendEmail(newUser.getEmail(),
                newUser.getFullName(), activationUrl,
                newToken,
                EmailServiceTemplate.ACTIVATE_ACCOUNT,
                "activate account");
        return newToken;

    }

    private String generateAndSaveActivationToken(AppUser newUser) {
        String otp = generateOtp(6);
        AccessToken token = new AccessToken();
        token.setUser(newUser);
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiresAt(LocalDateTime.now().plusMinutes(20));
        token.setAccessToken(otp);
        accessTokenRepository.save(token);
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

    @Transactional
    public Boolean verifyOtp(AuthenticationResponse response) {

        Optional<AccessToken> accessToken = accessTokenRepository.findByAccessToken(response.getToken());
        if (accessToken.isPresent()){
            AccessToken token = accessToken.get();
            if(token.getExpiresAt().isBefore(LocalDateTime.now())){
                   throw  new  AccessTokenException("token has expired" + token.getAccessToken().toString());
            }
            if(StringUtils.isNotBlank(token.getAccessToken()) ||
                    StringUtils.isNotBlank(token.getAccessToken())){
                return  accessToken.isPresent();
            }

        }

        return !accessToken.isPresent();

    }
}
