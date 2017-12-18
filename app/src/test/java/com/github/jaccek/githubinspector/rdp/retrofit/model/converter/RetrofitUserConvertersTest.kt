package com.github.jaccek.githubinspector.rdp.retrofit.model.converter

import com.github.jaccek.githubinspector.rdp.retrofit.model.RetrofitUser
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RetrofitUserConvertersTest {

    @Test
    fun `converts retrofit user to user when all informations are correct`() {
        // given
        val retrofitUser = RetrofitUser(
                id = 1,
                login = "test",
                avatarUrl = "http://avatar.url"
        )

        // when
        val user = retrofitUser.asUserOrNull()

        // then
        Assertions.assertThat(user?.id).isEqualTo(retrofitUser.id)
        Assertions.assertThat(user?.login).isEqualTo(retrofitUser.login)
        Assertions.assertThat(user?.avatarUrl).isEqualTo(retrofitUser.avatarUrl)
    }

    @Test
    fun `converts retrofit user to user when there is no avatar url`() {
        // given
        val retrofitUser = RetrofitUser(
                id = 1,
                login = "test",
                avatarUrl = null
        )

        // when
        val user = retrofitUser.asUserOrNull()

        // then
        Assertions.assertThat(user?.id).isEqualTo(retrofitUser.id)
        Assertions.assertThat(user?.login).isEqualTo(retrofitUser.login)
        Assertions.assertThat(user?.avatarUrl).isEqualTo(retrofitUser.avatarUrl)
    }

    @Test
    fun `returns null when there is no user id`() {
        // given
        val retrofitUser = RetrofitUser(
                id = null,
                login = "test",
                avatarUrl = "http://avatar.url"
        )

        // when
        val user = retrofitUser.asUserOrNull()

        // then
        Assertions.assertThat(user).isNull()
    }

    @Test
    fun `returns null when there is no user login`() {
        // given
        val retrofitUser = RetrofitUser(
                id = 1,
                login = null,
                avatarUrl = "http://avatar.url"
        )

        // when
        val user = retrofitUser.asUserOrNull()

        // then
        Assertions.assertThat(user).isNull()
    }
}
