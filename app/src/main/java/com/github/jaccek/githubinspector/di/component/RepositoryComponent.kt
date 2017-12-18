package com.github.jaccek.githubinspector.di.component

import com.github.jaccek.githubinspector.di.module.RepositoryModule
import com.github.jaccek.githubinspector.rdp.DataProvider
import com.github.jaccek.githubinspector.rdp.model.User
import dagger.Component

@Component(modules = [(RepositoryModule::class)])
interface RepositoryComponent {

    fun provideUserDataProvider(): DataProvider<User>
}
