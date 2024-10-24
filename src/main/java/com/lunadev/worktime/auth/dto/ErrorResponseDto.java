package com.lunadev.worktime.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "AUTH_RSP_01 : 에러 응답 DTO")
public class ErrorResponseDto {

    @Schema(description = "HTTP 상태 코드", example = "401")
    @NotNull
    private int statusCode;

    @Schema(description = "오류 메시지", example = "Unauthorized")
    @NotNull
    private String message;

    @Schema(description = "오류 발생 시간", example = "2024-10-24T12:34:56")
    @NotNull
    private LocalDateTime timestamp;
}