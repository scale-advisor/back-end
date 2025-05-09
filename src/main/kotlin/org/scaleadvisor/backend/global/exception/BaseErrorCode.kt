package org.scaleadvisor.backend.global.exception

import lombok.AccessLevel
import lombok.Getter
import lombok.RequiredArgsConstructor

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
enum class BaseErrorCode(
    val code: String,
    val message: String
) {
    // 400에러(Bad Request 오류)
    VALIDATION_EXCEPTION("B001", "잘못된 요청."),
    MISSING_REQUIRED_PARAMETER("B002", "필수 파라미터 누락."),
    VALIDATION_PARAMETER_EXCEPTION("B003", "파라미터 값 오류."),

    // 401에러(UnAuthorized 오류)
    UNAUTHORIZED_EXCEPTION("U001", "인증되지 않은 접근."),

    // 403에러(Forbidden 오류)
    FORBIDDEN_EXCEPTION("F001", "허용하지 않는 접근."),

    // 404에러(Not Found 오류)
    NOT_FOUND_EXCEPTION("N001", "요청한 리소스 존재하지 않음."),

    // 409에러(Conflict 오류)
    CONFLICT_EXCEPTION("C001", "중복된 정보."),

    // 500에러(Internal Server 오류)
    INTERNAL_SERVER_EXCEPTION("I001", "서버 내부에서 에러 발생."),
}
