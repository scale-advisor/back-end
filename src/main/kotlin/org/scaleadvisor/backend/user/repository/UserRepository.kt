package org.scaleadvisor.backend.user.repository

import org.jooq.DSLContext
import org.jooq.generated.tables.User.USER
import org.scaleadvisor.backend.user.domain.User
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val dsl: DSLContext
) {

    fun createUser(user: User): Long = dsl
        .insertInto(USER)
        .set(USER.EMAIL, user.email)
        .set(USER.PASSWORD, user.password)
        .set(USER.NAME, user.name)
        .set(USER.SOCIAL_ID, user.socialId)
        .set(USER.LOGIN_TYPE, user.loginType.name)
        .returning(USER.USER_ID)
        .fetchOne()!![USER.USER_ID]

    fun findByEmail(email: String): User? = dsl
        .selectFrom(USER)
        .where(USER.EMAIL.eq(email))
        .fetchOne { r ->
            User.fromDb(
                userId = r[USER.USER_ID],
                email = r[USER.EMAIL],
                password = r[USER.PASSWORD],
                name = r[USER.NAME],
                socialId = r[USER.SOCIAL_ID],
                loginType = User.LoginType.valueOf(r[USER.LOGIN_TYPE]),
                createdAt = r[USER.CREATED_AT],
                updatedAt = r[USER.UPDATED_AT]
            )
        }

    fun existsByEmail(email: String): Boolean = dsl
        .fetchCount(USER, USER.EMAIL.eq(email)) > 0
}