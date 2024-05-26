package project1.shop.enumeration;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ErrorCode {

    // 400 Bad Request
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "COMMON-ERR-400", "Invalid request"), // 잘못된 요청
    EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST, "MEMBER-ERR-400", "Email duplicated"), // 이메일 중복
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "COMMON-ERR-401", "Invalid parameter"), // 잘못된 파라미터
    MISSING_PARAMETER(HttpStatus.BAD_REQUEST, "COMMON-ERR-402", "Missing parameter"), // 누락된 파라미터

    // 401 Unauthorized
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON-ERR-401", "Unauthorized access"), // 권한 없는 접근
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "COMMON-ERR-402", "Invalid token"), // 잘못된 토큰

    // 403 Forbidden
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON-ERR-403", "Access forbidden"), // 접근 금지

    // 404 Not Found
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON-ERR-404", "Page not found"), // 페이지를 찾을 수 없음
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER-ERR-404", "User not found"), // 사용자를 찾을 수 없음
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "RESOURCE-ERR-404", "Resource not found"), // 리소스를 찾을 수 없음

    // 409 Conflict
    CONFLICT(HttpStatus.CONFLICT, "COMMON-ERR-409", "Conflict occurred"), // 충돌 발생
    DATA_INTEGRITY_VIOLATION(HttpStatus.CONFLICT, "COMMON-ERR-410", "Data integrity violation"), // 데이터 무결성 위반

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-ERR-500", "Internal server error"), // 서버 내부 오류
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-ERR-501", "Database error"), // 데이터베이스 오류
    SERVICE_UNAVAILABLE(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-ERR-502", "Service unavailable"), // 서비스 이용 불가

    // 502 Bad Gateway
    BAD_GATEWAY(HttpStatus.BAD_GATEWAY, "COMMON-ERR-502", "Bad gateway"), // 잘못된 게이트웨이

    // 503 Service Unavailable
    SERVICE_TEMPORARILY_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "COMMON-ERR-503", "Service temporarily unavailable"), // 일시적으로 서비스 이용 불가

    // 504 Gateway Timeout
    GATEWAY_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "COMMON-ERR-504", "Gateway timeout"), // 게이트웨이 시간 초과
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
