package com.lunadev.worktime.auth.service;

import com.lunadev.worktime.auth.dto.LoginRequestDto;
import com.lunadev.worktime.auth.dto.LoginResponseDto;
import com.lunadev.worktime.auth.dto.RegisterRequestDto;
import com.lunadev.worktime.company.dto.CreateAdminRequestDto;
import com.lunadev.worktime.company.dto.CreateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface AuthService {
    LoginResponseDto login(LoginRequestDto dto);

    LoginResponseDto register(RegisterRequestDto dto);

    boolean adminCreate(CreateAdminRequestDto dto);
}
