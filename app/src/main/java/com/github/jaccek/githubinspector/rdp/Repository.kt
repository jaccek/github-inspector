package com.github.jaccek.githubinspector.rdp

import io.reactivex.Completable

interface Repository<TYPE> : DataProvider<TYPE> {

    fun add(item: TYPE): Completable

    fun update(item: TYPE): Completable

    fun remove(item: TYPE): Completable
}
