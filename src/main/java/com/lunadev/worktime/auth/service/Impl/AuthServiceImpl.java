package com.lunadev.worktime.auth.service.Impl;

import com.lunadev.worktime.Enum.RoleType;
import com.lunadev.worktime.auth.dto.RegisterRequestDto;
import com.lunadev.worktime.auth.service.AuthService;
import com.lunadev.worktime.company.dto.CreateAdminRequestDto;
import com.lunadev.worktime.company.dto.CreateRequestDto;
import com.lunadev.worktime.company.entity.Company;
import com.lunadev.worktime.company.repository.CompanyRepository;
import com.lunadev.worktime.utils.JwtUtil;
import com.lunadev.worktime.member.repository.MemberRepository;
import com.lunadev.worktime.member.dto.CustomUserInfoDto;
import com.lunadev.worktime.auth.dto.LoginRequestDto;
import com.lunadev.worktime.auth.dto.LoginResponseDto; // LoginResponseDto import 추가
import com.lunadev.worktime.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final BCryptPasswordEncoder encoder;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public LoginResponseDto login(LoginRequestDto dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();
        Member member = memberRepository.findMemberByEmail(email);

        if (member == null) {
            // 사용자 찾기 실패 시 예외 발생
            return createErrorResponse(HttpStatus.UNAUTHORIZED, "이메일이 존재하지 않습니다.");
        }

        // 비밀번호 검증
        if (!encoder.matches(password, member.getPassword())) {
            return createErrorResponse(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        CustomUserInfoDto info = modelMapper.map(member, CustomUserInfoDto.class);

        String token = jwtUtil.createAccessToken(info);
        return new LoginResponseDto(token, HttpStatus.OK, "로그인 성공");
    }

    @Override
    @Transactional
    public LoginResponseDto register(RegisterRequestDto dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();
        Member member = memberRepository.findMemberByEmail(email);

        // 이미 이메일이 존재할 시 오류 응답 반환
        if (member != null) {
            return createErrorResponse(HttpStatus.BAD_REQUEST, "이미 사용 중인 이메일입니다.");
        }

        // 회사 인증코드(회사값)
        String verifyCode = dto.getVerifyCode();
        Company company;

        if (verifyCode != null) {
            company = companyRepository.findCompanyByVerifyCode(verifyCode);
        } else {
            return createErrorResponse(HttpStatus.BAD_REQUEST, "존재하지 않는 회사코드 입니다.");
        }

        Member newMember = modelMapper.map(dto, Member.class);
        newMember.setPassword(encoder.encode(password));
        newMember.setCompany(company);
        memberRepository.save(newMember);

        return new LoginResponseDto(null, HttpStatus.CREATED, "회원 가입 성공");
    }

    @Override
    @Transactional
    public boolean adminCreate(CreateAdminRequestDto dto) {
        String EncodePassword = encoder.encode(dto.getPassword());

        Member member = Member.builder()
                        .role(RoleType.MANAGER)
                        .name(dto.getName())
                        .email(dto.getEmail())
                        .password(EncodePassword)
                        .company(dto.getCompany())
                        .build();


        memberRepository.save(member);

        return true;
    }

    // 오류 응답 생성 메소드
    private LoginResponseDto createErrorResponse(HttpStatus status, String message) {
        return new LoginResponseDto(null, status, message);
    }
}