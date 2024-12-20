package com.lunadev.worktime.Enum;

public enum ApiResponseCode {
    SUCCESS("SUCCESS", "Success"),
    CREATED("CREATED", "Created Success"),
    FAILED("FAILED", "Failed"),
    ACCEPTED ("ACCEPTED"," 클라이언트의 요청은 정상적이나, 서버가 요청을 완료하지 못했습니다."),
    // 해당 코드 반환시  파라미터의 위치(path, query, body), 사용자 입력 값, 에러 이유를 꼭 명시하는 것이 좋다.
    BAD_REQUEST("BAD_REQUEST", "Bad Request"),
    UNAUTHORIZED("UNAUTHORIZED", "권한 정보가 없는 토큰입니다."),
    NOT_FOUND("NOT_FOUND", "요청한 정보를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "서버에서 해당 요청을 처리하지 못했습니다."),
    DUPLICATE("DUPLICATE", "중복된 정보입니다."),
    FORBIDDEN("FORBIDDEN","권한이 없습니다."),
    DELETED("DELETED", "삭제 되었습니다."),
    UPDATED("UPDATED", "수정 되었습니다.");

    private final String code;
    private final String message;

    ApiResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
