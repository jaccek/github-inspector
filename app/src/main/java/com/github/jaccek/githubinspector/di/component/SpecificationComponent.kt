package com.github.jaccek.githubinspector.di.component

import com.github.jaccek.githubinspector.di.module.SpecificationModule
import com.github.jaccek.githubinspector.rdp.UsersByPageSpecification
import dagger.Component

@Component(modules = [(SpecificationModule::class)])
interface SpecificationComponent {

    fun provideUsersByPageSpec(): UsersByPageSpecification
}
