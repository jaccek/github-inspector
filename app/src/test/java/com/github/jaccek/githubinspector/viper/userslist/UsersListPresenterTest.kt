package com.github.jaccek.githubinspector.viper.userslist

import com.github.jaccek.githubinspector.rdp.model.User
import com.mateuszkoslacz.moviper.tests.rules.RxAndroidSchedulersOverrideRule
import com.nhaarman.mockito_kotlin.*
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InOrder
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UsersListPresenterTest {

    @get:Rule
    val rxRule = RxAndroidSchedulersOverrideRule()

    @Mock
    private lateinit var view: UsersListActivity
    @Mock
    private lateinit var routing: UsersListRouting
    @Mock
    private lateinit var interactor: UsersListInteractor

    private lateinit var usersListSubject: Subject<List<User>>
    private lateinit var endOfListSubject: Subject<User>
    private lateinit var itemSelectionsSubject: Subject<User>

    @InjectMocks
    private lateinit var presenter: UsersListPresenter

    @Before
    fun setup() {
        usersListSubject = PublishSubject.create()
        Mockito.`when`(interactor.getUsers(anyOrNull())).thenReturn(usersListSubject.singleOrError())

        endOfListSubject = PublishSubject.create()
        Mockito.`when`(view.endOfListScrolls).thenReturn(endOfListSubject)

        itemSelectionsSubject = PublishSubject.create()
        Mockito.`when`(view.itemSelections).thenReturn(itemSelectionsSubject)
    }

    @Test
    fun `shows loader when attaching view`() {
        // when
        presenter.attachView(view)

        // then
        Mockito.verify(view).showLoader()
    }

    @Test
    fun `requests users list when attaching view`() {
        // when
        presenter.attachView(view)

        // then
        Mockito.verify(interactor).getUsers(null)
    }

    @Test
    fun `shows users when receiving users list from interactor`() {
        // given
        presenter.attachView(view)
        val users = listOf(
            User(id = 1, login = "tester"),
            User(id = 2, login = "programmer")
        )

        // when
        usersListSubject.onNext(users)
        usersListSubject.onComplete()

        // then
        Mockito.verify(view).hideLoader()
        Mockito.verify(view).showUsers(users)
    }

    @Test
    fun `shows error when receiving empty users list`() {
        // given
        presenter.attachView(view)
        val users = emptyList<User>()

        // when
        usersListSubject.onNext(users)
        usersListSubject.onComplete()

        // then
        Mockito.verify(view).showError()
    }

    @Test
    fun `shows error when receiving users list downloading error`() {
        // given
        presenter.attachView(view)

        // when
        usersListSubject.onError(Throwable())

        // then
        Mockito.verify(view).hideLoader()
        Mockito.verify(view).showError()
    }

    @Test
    fun `shows user details when selecting user`() {
        // given
        presenter.attachView(view)
        val user = User(id = 1, login = "abc")

        // when
        itemSelectionsSubject.onNext(user)

        // then
        Mockito.verify(routing).showUserDetails(user)
    }

    @Test
    fun `gets next page of users when scrolling to end of users list`() {
        // given
        presenter.attachView(view)
        ignoreGetUsersMethodCalledWhenAttachingView()
        val user = User(id = 12, login = "abc")

        // when
        endOfListSubject.onNext(user)

        // then
        Mockito.verify(interactor).getUsers(user)
        Mockito.verify(view).showLoader()
    }

    private fun ignoreGetUsersMethodCalledWhenAttachingView() {
        usersListSubject.onNext(listOf(mock()))
        usersListSubject.onComplete()

        Mockito.reset(interactor)
        Mockito.reset(view)
    }

    @Test
    fun `ignores getting next page of users when receiving two signals in short time`() {
        // given
        presenter.attachView(view)
        val user = User(id = 12, login = "abc")

        // when
        endOfListSubject.onNext(user)
        endOfListSubject.onNext(user)

        // then
        Mockito.verify(interactor, times(1)).getUsers(anyOrNull())
    }
}
