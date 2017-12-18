package com.github.jaccek.githubinspector.viper.userslist

import com.github.jaccek.githubinspector.rdp.model.User
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

        this.view?.showLoader()

        addSubscription(subscribeForUserSelections())
        addSubscription(subscribeForEndOfListScrolls())

        getUsers(null)
    }

    private fun subscribeForUserSelections() =
            view?.itemSelections
                    ?.subscribe { routing.showUserDetails(it) } // TODO: retry subscribe and onError

    private fun subscribeForEndOfListScrolls() =
            view?.endOfListScrolls
                    ?.subscribe { getUsers(it) } // TODO: retry subscribe and onError

    private fun getUsers(lastUser: User?) {
        if (queryForUserSubscribtion?.isDisposed == false) {
            return
        }

        queryForUserSubscribtion = interactor.getUsers(lastUser)
                .filter { it.isNotEmpty() }
                .switchIfEmpty(Maybe.error<List<User>>(Throwable("Users list is empty")))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { view?.showUsers(it) },
                        { showError(it) }
                )
    }

    private fun showError(throwable: Throwable) {
        throwable.printStackTrace()
        view?.showError()
    }
}
