package com.github.jaccek.githubinspector.di.module

import com.github.jaccek.githubinspector.rdp.DataProvider
import com.github.jaccek.githubinspector.rdp.model.User
import com.github.jaccek.githubinspector.rdp.retrofit.RetrofitDataProvider
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideUserDataProvider(): DataProvider<User> =
            RetrofitDataProvider()
}
