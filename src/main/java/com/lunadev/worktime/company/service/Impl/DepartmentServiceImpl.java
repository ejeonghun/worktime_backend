package com.lunadev.worktime.company.service.Impl;

import com.lunadev.worktime.Enum.ApiResponseCode;
import com.lunadev.worktime.auth.service.AuthService;
import com.lunadev.worktime.company.dto.DeptDto;
import com.lunadev.worktime.company.dto.DeptJoinDto;
import com.lunadev.worktime.company.dto.DeptJoinResponse;
import com.lunadev.worktime.company.dto.DeptMemberListDto;
import com.lunadev.worktime.company.entity.Company;
import com.lunadev.worktime.company.entity.Department;
import com.lunadev.worktime.company.repository.CompanyRepository;
import com.lunadev.worktime.company.repository.DepartmentRepository;
import com.lunadev.worktime.company.service.DepartmentService;
import com.lunadev.worktime.member.entity.Member;
import com.lunadev.worktime.member.repository.MemberRepository;
import com.lunadev.worktime.utils.AuthUserInfo;
import com.lunadev.worktime.utils.CustomUserDetails;
import com.lunadev.worktime.utils.ResponseDto;
import com.lunadev.worktime.utils.ResultDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;

    private final AuthUserInfo authUserInfo;

    @Override
    @Transactional
    public ResultDTO<Object> create(DeptDto dto) {
        Company company = authUserInfo.getAuthenticatedCompany(); // 시큐리티 인증정보로 회사 정보를 가져옴

        Department dept = modelMapper.map(dto, Department.class);
        dept.setCompany(company);

        try {
            if (departmentRepository.findByCompanyAndDeptName(company, dto.getDeptName()) != null) // 해당 회사의 부서 중복확인
                return ResultDTO.of(false,ApiResponseCode.DUPLICATE.getCode(), ApiResponseCode.DUPLICATE.getMessage(), null);
            dept = departmentRepository.save(dept);
            DeptDto res = modelMapper.map(dept, DeptDto.class);
            return ResultDTO.of(true, ApiResponseCode.CREATED.getCode(), ApiResponseCode.CREATED.getMessage(), res);
        } catch (Exception e) {
            return ResultDTO.of(false, ApiResponseCode.FAILED.getCode(), ApiResponseCode.FAILED.getMessage(), null);
        }
    }


    @Override
    public ResultDTO<Object> update(DeptDto dto) {
        Company company = authUserInfo.getAuthenticatedCompany(); // 시큐리티 인증정보로 회사 정보를 가져옴

        Department dept = modelMapper.map(dto, Department.class);
        dept.setCompany(company);

        try {
            if (departmentRepository.findByCompanyAndDeptName(company, dto.getDeptName()) == null) // 해당 회사의 수정할 부서 정보가 없음
                return ResultDTO.of(false,ApiResponseCode.NOT_FOUND.getCode(), ApiResponseCode.NOT_FOUND.getMessage(), null);
            dept = departmentRepository.save(dept);
            DeptDto res = modelMapper.map(dept, DeptDto.class);
            return ResultDTO.of(true, ApiResponseCode.CREATED.getCode(), ApiResponseCode.CREATED.getMessage(), res);
        } catch (Exception e) {
            return ResultDTO.of(false, ApiResponseCode.FAILED.getCode(), ApiResponseCode.FAILED.getMessage(), null);
        }
    }

    @Override
    public ResultDTO<Object> delete(Long id) {
        Company company = authUserInfo.getAuthenticatedCompany(); // 시큐리티 인증정보로 회사 정보를 가져옴
        Optional<Department> dept = departmentRepository.findById(id);

        if (dept.isPresent()) {
            // Department의 Company와 인증된 사용자의 Company가 같은지 비교
            if (!dept.get().getCompany().equals(company)) {
                return ResultDTO.of(false, ApiResponseCode.FORBIDDEN.getCode(), "해당 부서를 삭제할 권한이 없습니다.", null);
            }

            try {
                departmentRepository.delete(dept.get());
                return ResultDTO.of(true, ApiResponseCode.SUCCESS.getCode(), "부서 삭제 성공", null);
            } catch (Exception e) {
                return ResultDTO.of(false, ApiResponseCode.FAILED.getCode(), "부서 삭제 실패: " + e.getMessage(), null);
            }
        } else {
            return ResultDTO.of(false, ApiResponseCode.NOT_FOUND.getCode(), ApiResponseCode.NOT_FOUND.getMessage(), null);
        }
    }


    @Override
    public ResultDTO<Object> findAll() {
        Company company = authUserInfo.getAuthenticatedCompany();
        List<Department> depts = departmentRepository.findAllByCompany(company);

        List<DeptDto> deptDtos = new ArrayList<>();
        for (Department dept : depts) {
            DeptDto deptDto = modelMapper.map(dept, DeptDto.class);
            deptDtos.add(deptDto);
        }

        return ResultDTO.of(true, ApiResponseCode.SUCCESS.getCode(), ApiResponseCode.SUCCESS.getMessage(), deptDtos);
    }

    @Override
    public ResultDTO<Object> join(DeptJoinDto dto) {
        Company company = authUserInfo.getAuthenticatedCompany();
        Optional<Department> dept = departmentRepository.findById(dto.getDeptId());
        if (dept.isPresent()) {
            if (!dept.get().getCompany().equals(company)) {
                return ResultDTO.of(false, ApiResponseCode.FORBIDDEN.getCode(), "해당 부서에 대한 권한이 없습니다.", null);
            }

            try {
                Member member = authUserInfo.getAuthenticatedMember();
                member.setDepartment(dept.get());
                member.setPosition(dto.getPosition());

                Member saved = memberRepository.save(member);

                DeptJoinResponse result = DeptJoinResponse.builder()
                        .deptId(dept.get().getDeptId())
                        .deptName(dept.get().getDeptName())
                        .position(dto.getPosition())
                        .build();

                return ResultDTO.of(true, ApiResponseCode.SUCCESS.getCode(), ApiResponseCode.SUCCESS.getMessage(), result);
            } catch (Exception e) {
                return ResultDTO.of(false, ApiResponseCode.FAILED.getCode(), "부서 참가 실패 : " + e.getMessage(), null);
            }
        } else {
            return ResultDTO.of(false, ApiResponseCode.NOT_FOUND.getCode(), ApiResponseCode.NOT_FOUND.getMessage(), null);
        }
    }



}
