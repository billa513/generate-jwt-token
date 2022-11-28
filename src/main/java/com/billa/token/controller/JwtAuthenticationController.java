package com.billa.token.controller;

import com.billa.token.model.JwtRequest;
import com.billa.token.service.JwtTokenService;
import com.billa.token.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private final JwtTokenService jwtTokenService;

    private final UserDetailsService userDetailsService;

    @PostMapping("/getToken")
    public ResponseEntity<?> generateJwtToken(@RequestBody JwtRequest jwtRequest) {
        if (userDetailsService.verifyUserCredentials(jwtRequest.getUsername(), jwtRequest.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("jwtToken", jwtTokenService.generateToken(jwtRequest.getUsername()));
            return ResponseEntity.ok(response);
        } else return ResponseEntity.badRequest().body("Invalid Credentials");
    }


}
