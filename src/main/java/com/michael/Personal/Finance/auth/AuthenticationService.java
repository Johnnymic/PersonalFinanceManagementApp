package com.michael.Personal.Finance.auth;

import com.michael.Personal.Finance.email.EmailService;
import com.michael.Personal.Finance.email.EmailServiceTemplate;
import com.michael.Personal.Finance.users.AccessTokenRepository;
import com.michael.Personal.Finance.users.AppUser;
import com.michael.Personal.Finance.users.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AppUserRepository userRepository;

    private  final AccessTokenRepository accessTokenRepository;

    private  final PasswordEncoder passwordEncoder;

    private  final EmailService emailService;


    private String activationUrl;


    public AuthenticationResponse registerUser(AuthenticationRequest authenticationRequest) {
        AuthenticationResponse response = new AuthenticationResponse();
         boolean existEmail = userRepository.findByEmail(authenticationRequest.getEmail()).isPresent();
         if(existEmail){
             // return a default api request
         }
         AppUser newUser = new AppUser();
         newUser.setEmail(authenticationRequest.getEmail());
         newUser.setPassword(passwordEncoder.encode(authenticationRequest.getPassword()));
         newUser.setEnabled(false);

          userRepository.save(newUser);


          sendValidationEmail(newUser);

            return null;
    }

    private void sendValidationEmail(AppUser newUser) {

        String  newToken = generateAndSaveActivationToken(newUser);
        emailService.sendEmail(newUser.getEmail(), newUser.getFullName(), activationUrl, newToken, EmailServiceTemplate.ACTIVATE_ACCOUNT,"activation");

    }

    private String generateAndSaveActivationToken(AppUser newUser) {
        String otp = generateOtp(6);
    }

    private String generateOtp(int i) {
    }


}
