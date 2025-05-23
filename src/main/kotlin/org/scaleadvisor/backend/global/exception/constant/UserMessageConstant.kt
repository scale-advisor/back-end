package org.scaleadvisor.backend.global.exception.constant

object UserMessageConstant {
    const val DUPLICATE_EMAIL_MESSAGE = "이미 가입된 email입니다: %s"
    const val NOT_FOUND_EMAIL_MESSAGE = "존재하지 않거나 회원가입 승인이 완료되지 않은 email입니다: %s"
    const val INVALID_CREDENTIALS_MESSAGE = "비밀번호가 일치하지 않습니다."
    const val NOT_FOUND_USER_ID_MESSAGE = "존재하지 않는 회원입니다: %d"
    const val INCORRECT_CREDENTIALS_VALID_MESSAGE = "새 비밀번호 확인을 잘못 입력하였습니다."
    const val EMAIL_TOKEN_GONE_MESSAGE = "이메일 토큰이 잘못되었습니다."
}