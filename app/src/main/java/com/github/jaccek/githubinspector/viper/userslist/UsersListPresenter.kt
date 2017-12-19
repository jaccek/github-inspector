package com.github.jaccek.githubinspector.viper.userslist

import com.github.jaccek.githubinspector.rdp.model.User
import com.github.jaccek.githubinspector.util.logger.Logger
import com.github.jaccek.githubinspector.util.retrySubscribe
import com.mateuszkoslacz.moviper.base.presenter.BaseRxPresenter
import com.mateuszkoslacz.moviper.iface.presenter.ViperPresenter
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

internal class UsersListPresenter :
        BaseRxPresenter<UsersListContract.View,
                UsersListContract.Interactor,
                UsersListContract.Routing>(),
        ViperPresenter<UsersListContract.View> {

    private var queryForUserSubscribtion: Disposable? = null

    override fun createRouting(): UsersListContract.Routing =
            UsersListRouting()

    override fun createInteractor(): UsersListContract.Interactor =
            UsersListInteractor()

    override fun attachView(view: UsersListContract.View?) {
        super.attachView(view)

        addSubscription(subscribeForUserSelections())
        addSubscription(subscribeForEndOfListScrolls())

        getUsers(null)
    }

    private fun subscribeForUserSelections() =
            view?.itemSelections
                    ?.retrySubscribe(
                            { routing.showUserDetails(it) },
                            { Logger.e(it) }
                    )

    private fun subscribeForEndOfListScrolls() =
            view?.endOfListScrolls
                    ?.retrySubscribe(
                            { getUsers(it) },
                            { Logger.e(it) }
                    )

    private fun getUsers(lastUser: User?) {
        if (queryForUserSubscribtion?.isDisposed == false) {
            return
        }

        view?.showLoader()
        queryForUserSubscribtion = interactor.getUsers(lastUser)
                .filter { it.isNotEmpty() }
                .switchIfEmpty(Maybe.error<List<User>>(Throwable("Users list is empty")))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { showUsers(it) },
                        { showError(it) }
                )
    }

    private fun showUsers(users: List<User>) {
        view?.hideLoader()
        view?.showUsers(users)
    }

    private fun showError(throwable: Throwable) {
        Logger.e(throwable)
        view?.hideLoader()
        view?.showError()
    }
}
