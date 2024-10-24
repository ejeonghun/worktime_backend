package com.lunadev.worktime.company.dto;

import com.lunadev.worktime.company.entity.Company;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "Company_REQ_02 : 회사 관리자 생성 요청 DTO")
public class CreateAdminRequestDto {
    private String email;
    private String password;
    private String name;
    private Company company;
}
