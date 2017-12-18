package com.github.jaccek.githubinspector.viper.userslist

import android.app.Activity
import com.github.jaccek.githubinspector.rdp.model.User

import com.mateuszkoslacz.moviper.base.routing.BaseRxRouting

internal class UsersListRouting : BaseRxRouting<Activity>(), UsersListContract.Routing {
    override fun showUserDetails(user: User) {
        throw UnsupportedOperationException("not implemented")
    }
}
