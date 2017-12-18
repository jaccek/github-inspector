package com.github.jaccek.githubinspector.rules

import com.github.jaccek.githubinspector.di.DiProvider
import com.github.jaccek.githubinspector.di.module.SpecificationModule
import com.github.jaccek.githubinspector.rdp.UsersByPageSpecification
import com.github.jaccek.githubinspector.rdp.retrofit.UsersByPageRetrofitSpec
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.mockito.Mockito

class DiProviderRule : TestRule {

    override fun apply(base: Statement, description: Description?): Statement =
            object: Statement() {

                override fun evaluate() {
                    DiProvider.init(SpecificationMockModule())
                    return base.evaluate()
                }
            }

    @Module
    class SpecificationMockModule : SpecificationModule() {

        override fun provideUsersByPageSpecification(): UsersByPageSpecification {
            val spec = mock<UsersByPageRetrofitSpec>()
            Mockito.`when`(spec.withLastUserIdExcluding(any())).thenReturn(spec)

            return spec
        }
    }
}
