package org.scaleadvisor.backend.user.dto

data class ChangePwdRequest(
    val currentPwd: String,
    val newPwd: String,
    val newPwdConfirm: String
)
