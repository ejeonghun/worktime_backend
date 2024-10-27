package com.lunadev.worktime.company.service.Impl;

import com.lunadev.worktime.Enum.ApiResponseCode;
import com.lunadev.worktime.company.dto.DeptDto;
import com.lunadev.worktime.company.dto.DeptMemberListDto;
import com.lunadev.worktime.company.entity.Company;
import com.lunadev.worktime.company.entity.Department;
import com.lunadev.worktime.company.repository.CompanyRepository;
import com.lunadev.worktime.company.repository.DepartmentRepository;
import com.lunadev.worktime.company.service.DepartmentService;
import com.lunadev.worktime.member.repository.MemberRepository;
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

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public ResultDTO<Object> create(DeptDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            Long companyId = userDetails.getMember().getCompany().getCompanyId();

            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 회사가 존재하지 않음"));

            Department dept = modelMapper.map(dto, Department.class);
            System.out.println(dept.getDeptName());
            dept.setCompany(company);

            try {
                dept = departmentRepository.save(dept);
                DeptDto res = modelMapper.map(dept, DeptDto.class);
                return ResultDTO.of(true, ApiResponseCode.CREATED.getCode(),ApiResponseCode.CREATED.getMessage(), res);
            } catch (Exception e) {
                return ResultDTO.of(false, ApiResponseCode.FAILED.getCode(), ApiResponseCode.FAILED.getMessage(), null);
            }
        }
        return ResultDTO.of(false, ApiResponseCode.UNAUTHORIZED.getCode(), ApiResponseCode.UNAUTHORIZED.getMessage(), null);
    }


    @Override
    public ResultDTO update(DeptDto dto) {
        return null;
    }

    @Override
    public ResultDTO delete(Long id) {
        return null;
    }

    @Override
    public DeptMemberListDto findAll() {
        return null;
    }
}
