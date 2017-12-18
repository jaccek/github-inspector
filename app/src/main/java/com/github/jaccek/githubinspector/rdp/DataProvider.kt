package com.github.jaccek.githubinspector.rdp

import io.reactivex.Single

interface DataProvider<TYPE> {

    fun query(specification: Specification): Single<List<TYPE>>
}
