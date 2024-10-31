package com.lunadev.worktime.company.controller;

import com.lunadev.worktime.company.dto.CreateRequestDto;
import com.lunadev.worktime.company.entity.Company;
import com.lunadev.worktime.company.service.CompanyService;
import com.lunadev.worktime.utils.ResponseDto;
import com.lunadev.worktime.utils.ResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/company")
public class CompanyApiController {

    private final CompanyService companyService;

    @PostMapping("create")
    public ResponseDto createCompany(@RequestBody CreateRequestDto dto) {
        return companyService.create(dto);
    }

//    @GetMapping("info")
//    public ResultDTO<Object> getCompanyInfo(Long companyId) {
//
//    }
}
