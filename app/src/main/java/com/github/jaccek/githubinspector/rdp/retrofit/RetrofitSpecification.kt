package com.github.jaccek.githubinspector.rdp.retrofit

import com.github.jaccek.githubinspector.rdp.Specification
import io.reactivex.Single

interface RetrofitSpecification<TYPE>: Specification {

    fun query(userApi: UserApi): Single<List<TYPE>>
}
