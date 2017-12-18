package com.github.jaccek.githubinspector.rdp.retrofit.model.converter

import com.github.jaccek.githubinspector.rdp.model.User
import com.github.jaccek.githubinspector.rdp.retrofit.model.RetrofitUser

fun RetrofitUser.asUserOrNull(): User? =
        if (this.isValid()) {
            this.asUser()
        } else {
            null
        }

fun RetrofitUser.asUser(): User =
        User(
                id = this.id!!,
                login = this.login!!,
                avatarUrl = this.avatarUrl
        )

fun RetrofitUser.isValid() =
        this.id != null && this.login != null
