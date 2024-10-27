package com.lunadev.worktime.company.controller;

import com.lunadev.worktime.company.dto.DeptDto;
import com.lunadev.worktime.company.service.DepartmentService;
import com.lunadev.worktime.utils.ResponseDto;
import com.lunadev.worktime.utils.ResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dept")
public class DepartmentApiController {

    private final DepartmentService departmentService;

    @PostMapping("create")
    public ResultDTO<Object> createDepartment(@RequestBody DeptDto dto) {
        return departmentService.create(dto);
    }
}
