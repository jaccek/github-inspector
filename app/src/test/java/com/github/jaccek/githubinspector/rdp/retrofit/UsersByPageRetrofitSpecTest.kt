package com.github.jaccek.githubinspector.rdp.retrofit

import com.github.jaccek.githubinspector.rdp.model.User
import com.github.jaccek.githubinspector.rdp.retrofit.model.RetrofitUser
import com.github.jaccek.githubinspector.rdp.retrofit.model.converter.asUserOrNull
import com.nhaarman.mockito_kotlin.any
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UsersByPageRetrofitSpecTest {

    @Mock
    private lateinit var userApi: UserApi

    @InjectMocks
    private lateinit var specification: UsersByPageRetrofitSpec

    private lateinit var usersSubject: Subject<List<RetrofitUser>>

    @Before
    fun setup() {
        usersSubject = PublishSubject.create()
        Mockito.`when`(userApi.getUsers(any())).thenReturn(usersSubject)
    }

    @Test
    fun `queries user api for first users when last user id is not specified`() {
        // when
        specification.query(userApi)

        // then
        Mockito.verify(userApi).getUsers(0)
    }

    @Test
    fun `queries user api for next page of users when last user id is specified`() {
        // given
        val lastUserId = 123

        // when
        specification.withLastUserIdExcluding(lastUserId)
                .query(userApi)

        // then
        Mockito.verify(userApi).getUsers(lastUserId)
    }

    @Test
    fun `passes error when querying returns error`() {
        // given
        val error = Throwable("sample error")
        val response = specification.query(userApi)

        // when
        usersSubject.onError(error)

        // then
        response.test().assertError(error)
    }

    @Test
    fun `maps retrofit users to users when getting response`() {
        // given
        val validRetrofitUsersList = createValidRetrofitUsersList()
        val responseTestObserver = specification.query(userApi).test()

        // when
        usersSubject.onNext(validRetrofitUsersList)
        usersSubject.onComplete()

        // then
        responseTestObserver.awaitCount(1)
                .assertValue { isListCorrect(it, validRetrofitUsersList) }
    }

    @Test
    fun `filters invalid users when receiving them from API`() {
        // given
        val validRetrofitUsersList = createValidRetrofitUsersList()
        val invalidRetrofitUsersList = ArrayList<RetrofitUser>().apply {
            add(createInvalidRetrofitUser())
            addAll(validRetrofitUsersList)
        }
        val responseTestObserver = specification.query(userApi).test()

        // when
        usersSubject.onNext(invalidRetrofitUsersList)
        usersSubject.onComplete()

        // then
        responseTestObserver.awaitCount(1)
                .assertValue { isListCorrect(it, validRetrofitUsersList) }
    }

    private fun createValidRetrofitUsersList() =
            listOf(
                    RetrofitUser(id = 1, login = "login", avatarUrl = "avatarUrl"),
                    RetrofitUser(id = 2, login = "user", avatarUrl = null)
            )

    private fun createInvalidRetrofitUser() =
            RetrofitUser()

    private fun isListCorrect(result: List<User>, retrofitUsers: List<RetrofitUser>) =
            result.mapIndexed { index, user -> retrofitUsers[index].equalsTo(user) }
                    .reduce { accelerator, item -> accelerator && item }

    private fun RetrofitUser.equalsTo(user: User) =
            this.asUserOrNull() == user
}

