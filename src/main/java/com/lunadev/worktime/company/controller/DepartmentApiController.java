package com.lunadev.worktime.company.controller;

import com.lunadev.worktime.company.dto.DeptDto;
import com.lunadev.worktime.company.dto.DeptJoinDto;
import com.lunadev.worktime.company.service.DepartmentService;
import com.lunadev.worktime.utils.ResponseDto;
import com.lunadev.worktime.utils.ResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dept")
public class DepartmentApiController {

    private final DepartmentService departmentService;

    @PostMapping("create")
    public ResultDTO<Object> createDepartment(@RequestBody DeptDto dto) {
        return departmentService.create(dto);
    }

    @PostMapping("update")
    public ResultDTO<Object> updateDepartment(@RequestBody DeptDto dto) {
        return departmentService.update(dto);
    }

    @PostMapping("delete")
    public ResultDTO<Object> deleteDepartment(@RequestBody Long id) {
        return departmentService.delete(id);
    }

    @GetMapping("list")
    public ResultDTO<Object> listDepartment() {
        return departmentService.findAll();
    }

    @PostMapping("join")
    public ResultDTO<Object> join(@RequestBody DeptJoinDto dto) {
        return departmentService.join(dto);
    }
}
