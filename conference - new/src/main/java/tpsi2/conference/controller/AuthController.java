package tpsi2.conference.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tpsi2.conference.config.JwtIssuer;
import tpsi2.conference.model.LoginRequest;
import tpsi2.conference.model.LoginResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtIssuer jwtIssuer;

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request){
        var token = jwtIssuer.issue(1L,request.getUsername(), List.of("USER"));
        return LoginResponse.builder().accessToken(token).build();
    }//le builder joue le role du constructeur




}
