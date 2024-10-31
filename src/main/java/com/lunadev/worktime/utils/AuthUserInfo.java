package com.lunadev.worktime.utils;

import com.lunadev.worktime.company.entity.Company;
import com.lunadev.worktime.company.repository.CompanyRepository;
import com.lunadev.worktime.member.entity.Member;
import com.lunadev.worktime.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


@Component // 이 클래스를 스프링 빈으로 등록
@RequiredArgsConstructor
public class AuthUserInfo {

    private final CompanyRepository companyRepository;
    private final MemberRepository memberRepository;

    /**
     * 헤더에 Bearer Token 정보로 해당 유저의 소속 회사 반환 메소드
     */
    public Company getAuthenticatedCompany() { // 접근 제한자 변경
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            Long companyId = userDetails.getMember().getCompany().getCompanyId();

            return companyRepository.findById(companyId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 회사가 존재하지 않음"));
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "인증 정보가 존재하지 않습니다.");
        }


    /**
     * 헤더에 Bearer Token 정보로 해당 유저의 Id 반환 메소드
     */
    public Member getAuthenticatedMember() { // 접근 제한자 변경 및 메소드 이름 변경
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            Long memberId = userDetails.getMember().getMemberId();

            return memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 회원 정보가 존재하지 않음"));
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "인증 정보가 존재하지 않습니다.");
    }
}