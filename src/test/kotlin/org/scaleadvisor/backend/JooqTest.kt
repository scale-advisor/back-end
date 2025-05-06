package org.scaleadvisor.backend

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.scaleadvisor.backend.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class JooqTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun createUserTest() {
        // given
        val username = "testuser"
        val email = "testuser@example.com"
        val password = "securepassword123"

        // when
        val userId = userRepository.insertUser(username, email, password)
        val retrievedUser = userRepository.findUserById(userId)

        // then
        assertThat(retrievedUser).isNotNull
        assertThat(retrievedUser!!.id).isEqualTo(userId)
        assertThat(retrievedUser.username).isEqualTo(username)
        assertThat(retrievedUser.email).isEqualTo(email)
    }

    @Test
    @Transactional
    fun getUserTest() {
        // given
        val userId = 1L

        // when
        val user = userRepository.findUserById(userId)

        // then
        assertThat(user).isNotNull
        assertThat(user!!.id).isEqualTo(userId)
    }


}