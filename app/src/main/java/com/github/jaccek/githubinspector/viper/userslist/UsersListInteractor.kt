package com.github.jaccek.githubinspector.viper.userslist

import com.github.jaccek.githubinspector.di.DiProvider
import com.github.jaccek.githubinspector.rdp.DataProvider
import com.github.jaccek.githubinspector.rdp.model.User
import com.mateuszkoslacz.moviper.base.interactor.BaseRxInteractor
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

internal class UsersListInteractor : BaseRxInteractor(), UsersListContract.Interactor {

    private var userRepository: DataProvider<User> =
            DiProvider.repositoryComponent.provideUserDataProvider()

    override fun getUsers(lastUser: User?): Single<List<User>> {
        val specification = DiProvider.specificationComponent.provideUsersByPageSpec()
                .withLastUserIdExcluding(lastUser?.id ?: 0)

        return userRepository.query(specification)
                .subscribeOn(Schedulers.io())
    }
}
