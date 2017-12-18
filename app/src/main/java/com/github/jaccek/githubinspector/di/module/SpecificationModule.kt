package com.github.jaccek.githubinspector.di.module

import com.github.jaccek.githubinspector.rdp.UsersByPageSpecification
import com.github.jaccek.githubinspector.rdp.retrofit.UsersByPageRetrofitSpec
import dagger.Module
import dagger.Provides

@Module
open class SpecificationModule {

    @Provides
    open fun provideUsersByPageSpecification(): UsersByPageSpecification =
            UsersByPageRetrofitSpec()
}
