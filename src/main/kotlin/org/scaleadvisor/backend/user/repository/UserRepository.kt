package org.scaleadvisor.backend.user.repository

import org.jooq.DSLContext
import org.jooq.generated.tables.User.USER
import org.jooq.generated.tables.records.UserRecord
import org.scaleadvisor.backend.user.domain.User
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val dsl: DSLContext
) {

    fun createUser(user: User, generatedId: Long): Long {
        dsl.insertInto(USER)
            .set(USER.USER_ID, generatedId)
            .set(USER.EMAIL, user.email)
            .set(USER.PASSWORD, user.password)
            .set(USER.NAME, user.name)
            .set(USER.SOCIAL_ID, user.socialId)
            .set(USER.LOGIN_TYPE, user.loginType.name)
            .set(USER.CONFIRMED, user.confirmed.name)
            .execute()

        return generatedId
    }

    fun findById(userId: Long): User? = dsl
        .selectFrom(USER)
        .where(USER.USER_ID.eq(userId))
        .fetchOne { record -> mapRecordToUser(record) }

    fun findByEmail(email: String): User? = dsl
        .selectFrom(USER)
        .where(USER.EMAIL.eq(email))
        .fetchOne { record -> mapRecordToUser(record) }

    fun existsByEmail(email: String): Boolean = dsl
        .fetchCount(USER, USER.EMAIL.eq(email)) > 0

    private fun mapRecordToUser(r: UserRecord): User = User.fromDb(
        userId    = r.userId,
        email     = r.email,
        password  = r.password,
        name      = r.name,
        socialId  = r.socialId,
        loginType = User.LoginType.valueOf(r.loginType),
        confirmed = User.Confirmed.valueOf(r.confirmed),
        createdAt = r.createdAt,
        updatedAt = r.updatedAt
    )
}