package com.lunadev.worktime.company.service.Impl;

import com.lunadev.worktime.auth.service.AuthService;
import com.lunadev.worktime.company.dto.CreateAdminRequestDto;
import com.lunadev.worktime.company.dto.CreateRequestDto;
import com.lunadev.worktime.company.entity.Company;
import com.lunadev.worktime.company.repository.CompanyRepository;
import com.lunadev.worktime.company.service.CompanyService;
import com.lunadev.worktime.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final BCryptPasswordEncoder encoder;
    private final ModelMapper modelMapper;

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 8;
    private static final Random RANDOM = new Random();
    private final AuthService authService;

    /** 8자리 난문자 생성기 */
    public static String generateRandomString() {
        StringBuilder result = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(index));
        }
        return result.toString();
    }

    @Override
    @Transactional
    public ResponseDto create(CreateRequestDto dto) {
        String generatedCode;
        Company company;

        do {
            generatedCode = generateRandomString();
            company = companyRepository.findCompanyByVerifyCode(generatedCode);
        } while (company != null); // 회사가 null이 아닐 경우, 즉 코드가 중복된 경우 반복

        // 새로운 인증 코드를 사용하여 회사 객체 생성
        Company newCompany = Company.builder()
                            .companyName(dto.getCompanyName())
                            .latitude(dto.getLatitude())
                            .longitude(dto.getLongitude())
                            .verifyCode(generatedCode)
                            .build();

        // 회사 저장
        Company CreatedCompany = companyRepository.save(newCompany);

        CreateAdminRequestDto adminDto = modelMapper.map(dto, CreateAdminRequestDto.class);
        adminDto.setCompany(CreatedCompany);

        if (authService.adminCreate(adminDto))
            // ResponseDto 반환
            return new ResponseDto(HttpStatus.OK,"성공적으로 생성되었습니다.", CreatedCompany, LocalDateTime.now());

        return new ResponseDto(HttpStatus.BAD_REQUEST, "실패했습니다.", null ,LocalDateTime.now());
    }
}
