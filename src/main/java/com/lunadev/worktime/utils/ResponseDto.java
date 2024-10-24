package com.lunadev.worktime.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "PUBLIC_RSP_01 : 응답 DTO")
public class ResponseDto {
    @Schema(description = "HTTP 상태 코드", example = "401")
    @NotNull
    private HttpStatus statusCode;

    @Schema(description = "메시지", example = "Unauthorized")
    @NotNull
    private String message;

    @Schema(description = "오류 발생 시간", example = "2024-10-24T12:34:56")
    @NotNull
    private LocalDateTime timestamp;
}


