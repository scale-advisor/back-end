package org.scaleadvisor.backend.user.repository

import org.jooq.DSLContext
import org.jooq.generated.tables.Users.USERS
import org.scaleadvisor.backend.user.dto.UserResponse
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val dsl: DSLContext
) {

    fun insertUser(username: String, email: String, password: String): Long {
        val record = dsl.insertInto(USERS)
            .set(USERS.USERNAME, username)
            .set(USERS.EMAIL, email)
            .set(USERS.PASSWORD, password)
            .returningResult(USERS.ID)
            .fetchOne()

        return record?.value1() ?: throw RuntimeException("User 추가 실패")
    }

    fun findUserById(id: Long): UserResponse? {
        val record = dsl.selectFrom(USERS)
            .where(USERS.ID.eq(id))
            .fetchOne()

        return record?.let {
            UserResponse(
                id = it.id,
                username = it.username,
                email = it.email
            )
        }
    }
}