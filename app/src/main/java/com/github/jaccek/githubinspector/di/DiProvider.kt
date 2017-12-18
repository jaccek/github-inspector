package com.github.jaccek.githubinspector.di

import com.github.jaccek.githubinspector.di.component.DaggerRepositoryComponent
import com.github.jaccek.githubinspector.di.component.DaggerSpecificationComponent
import com.github.jaccek.githubinspector.di.component.RepositoryComponent
import com.github.jaccek.githubinspector.di.component.SpecificationComponent
import com.github.jaccek.githubinspector.di.module.SpecificationModule

object DiProvider {

    private lateinit var specificationModule: SpecificationModule

    val repositoryComponent: RepositoryComponent by lazy {
        DaggerRepositoryComponent.builder().build()
    }

    val specificationComponent: SpecificationComponent by lazy {
        DaggerSpecificationComponent.builder()
                .specificationModule(specificationModule)
                .build()
    }

    fun init(specificationModule: SpecificationModule = SpecificationModule()) {
        this.specificationModule = specificationModule
    }
}
