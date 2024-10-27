package com.lunadev.worktime.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeptDto {

    private Long deptId;

    private String deptName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
