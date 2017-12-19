package com.github.jaccek.githubinspector.viper.userslist

import android.app.Activity
import com.github.jaccek.githubinspector.rdp.model.User

import com.hannesdorfmann.mosby.mvp.MvpView
import com.mateuszkoslacz.moviper.iface.interactor.ViperRxInteractor
import com.mateuszkoslacz.moviper.iface.routing.ViperRxRouting
import io.reactivex.Observable
import io.reactivex.Single

internal interface UsersListContract {

    interface View : MvpView {
        val itemSelections: Observable<User>

        // publishes last user on the list
        val endOfListScrolls: Observable<User>

        fun showLoader()

        fun hideLoader()

        fun showUsers(users: List<User>)

        fun showError()
    }

    interface Interactor : ViperRxInteractor {
        fun getUsers(lastUser: User?): Single<List<User>>
    }

    interface Routing : ViperRxRouting<Activity> {
        fun showUserDetails(user: User)
    }
}
