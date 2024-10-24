package com.lunadev.worktime.auth.controller;

import com.lunadev.worktime.auth.dto.LoginRequestDto;
import com.lunadev.worktime.auth.dto.LoginResponseDto; // LoginResponseDto import 추가
import com.lunadev.worktime.auth.dto.RegisterRequestDto;
import com.lunadev.worktime.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto request
    ) {
        LoginResponseDto response = authService.login(request);

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("register")
    public ResponseEntity<LoginResponseDto> register(
            @RequestBody RegisterRequestDto request
    ) {
        LoginResponseDto response = authService.register(request);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}