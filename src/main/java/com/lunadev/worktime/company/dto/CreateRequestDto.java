package com.lunadev.worktime.company.dto;

import com.lunadev.worktime.company.entity.Company;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "Company_REQ_01 : 회사 생성 요청 DTO")
public class CreateRequestDto {

    @NotBlank
    private String companyName;

    private Double latitude;
    private Double longitude;

    private String email; // 관리자 이메일

    private String name; // 관리자 이름

    private String password; // 관리자 패스워드
}
