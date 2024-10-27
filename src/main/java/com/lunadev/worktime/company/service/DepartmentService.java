package com.lunadev.worktime.company.service;

import com.lunadev.worktime.company.dto.DeptDto;
import com.lunadev.worktime.company.dto.DeptMemberListDto;
import com.lunadev.worktime.utils.ResponseDto;
import com.lunadev.worktime.utils.ResultDTO;
import org.springframework.security.core.Authentication;

public interface DepartmentService {

    ResultDTO<Object>  create(DeptDto dto);

    ResultDTO<Object> update(DeptDto dto);

    ResultDTO<Object>  delete(Long id);

    DeptMemberListDto findAll();
}
