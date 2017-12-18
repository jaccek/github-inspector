package com.github.jaccek.githubinspector.rdp.retrofit

import com.github.jaccek.githubinspector.rdp.UsersByPageSpecification
import com.github.jaccek.githubinspector.rdp.model.User
import com.github.jaccek.githubinspector.rdp.retrofit.model.converter.asUser
import com.github.jaccek.githubinspector.rdp.retrofit.model.converter.isValid
import io.reactivex.Observable
import io.reactivex.Single

class UsersByPageRetrofitSpec
    : RetrofitSpecification<User>, UsersByPageSpecification {

    private var lastUserIdExcluding: Int = 0

    override fun withLastUserIdExcluding(userId: Int): UsersByPageRetrofitSpec =
            this.apply {
                lastUserIdExcluding = userId
            }

    override fun query(userApi: UserApi): Single<List<User>> =
            userApi.getUsers(lastUserIdExcluding)
                    .flatMap { Observable.fromIterable(it) }
                    .filter { it.isValid() }
                    .map { it.asUser() }
                    .toList()
                    .map { it.filterNotNull() }
}