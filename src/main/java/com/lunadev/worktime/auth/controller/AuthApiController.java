package com.lunadev.worktime.auth.controller;

import com.lunadev.worktime.auth.dto.LoginRequestDto;
import com.lunadev.worktime.auth.dto.LoginResponseDto; // LoginResponseDto import 추가
import com.lunadev.worktime.auth.dto.RegisterRequestDto;
import com.lunadev.worktime.auth.service.AuthService;
import com.lunadev.worktime.member.dto.CustomUserInfoDto;
import com.lunadev.worktime.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequestDto request
    ) {
        String token = authService.login(request);

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PostMapping("register")
    public ResponseEntity<LoginResponseDto> register(
            @RequestBody RegisterRequestDto request
    ) {
        LoginResponseDto response = authService.register(request);

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("getUserInfo")
    public CustomUserInfoDto getUserInfo() {
        return authService.getUserInfo();
    }
}