package com.lunadev.worktime.company.service;

import com.lunadev.worktime.company.dto.CreateRequestDto;
import com.lunadev.worktime.utils.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CompanyService {

    ResponseDto create(CreateRequestDto dto);
}
