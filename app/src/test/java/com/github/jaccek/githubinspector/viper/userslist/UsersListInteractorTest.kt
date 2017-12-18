package com.github.jaccek.githubinspector.viper.userslist

import com.github.jaccek.githubinspector.rdp.Repository
import com.github.jaccek.githubinspector.rdp.model.User
import com.github.jaccek.githubinspector.rdp.retrofit.UsersByPageRetrofitSpec
import com.github.jaccek.githubinspector.rules.DiProviderRule
import com.mateuszkoslacz.moviper.tests.rules.RxAndroidSchedulersOverrideRule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UsersListInteractorTest {

    @get:Rule
    val rxRule = RxAndroidSchedulersOverrideRule()
    @get:Rule
    val diProviderRule = DiProviderRule()

    @Mock
    private lateinit var repository: Repository<User>

    @InjectMocks
    private lateinit var interactor: UsersListInteractor

    @Test
    fun `returns observable returned from repository when requesting users list`() {
        // given
        val users = listOf(User(1, "test"))
        Mockito.`when`(repository.query(any<UsersByPageRetrofitSpec>()))
                .thenReturn(Single.just(users))

        // when
        val testObserver = interactor.getUsers(null).test()

        // then
        testObserver.awaitCount(1)
                .assertValue(users)
    }

    @Test
    fun `queries for users with last id equals to 0 when requesting users list without last user`() {
        // given
        val users = listOf(User(1, "test"))
        val captor = argumentCaptor<UsersByPageRetrofitSpec>()
        Mockito.`when`(repository.query(captor.capture())).thenReturn(Single.just(users))

        // when
        interactor.getUsers(null).test()

        // then
        Mockito.verify(captor.firstValue).withLastUserIdExcluding(0)
    }

    @Test
    fun `queries for users with last id equals to last user id when requesting users list with last user`() {
        // given
        val lastUser = User(123, "user")
        val users = listOf(User(1, "test"))
        val captor = argumentCaptor<UsersByPageRetrofitSpec>()
        Mockito.`when`(repository.query(captor.capture())).thenReturn(Single.just(users))

        // when
        interactor.getUsers(lastUser).test()

        // then
        Mockito.verify(captor.firstValue).withLastUserIdExcluding(lastUser.id)
    }
}
