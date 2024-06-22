package com.michael.Personal.Finance.auth;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/user")
    public ResponseEntity<AuthenticationResponse> registerCustomer(@RequestBody @Valid AuthenticationRequest authenticationRequest) throws MessagingException {
        return ResponseEntity.ok(authenticationService.registerUser(authenticationRequest));
    }

    @PostMapping("/verify_otp")
    public ResponseEntity<Boolean> verifyOtp(@RequestBody @Valid AuthenticationResponse response){

        return ResponseEntity.ok(authenticationService.verifyOtp(response));
    }


}
