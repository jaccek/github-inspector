package com.github.jaccek.githubinspector.rdp.retrofit

import com.github.jaccek.githubinspector.rdp.DataProvider
import com.github.jaccek.githubinspector.rdp.Specification
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitDataProvider<TYPE> : DataProvider<TYPE> {

    private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val userApi = retrofit.create(UserApi::class.java)

    override fun query(specification: Specification): Single<List<TYPE>> =
            (specification as RetrofitSpecification<TYPE>).query(userApi)
}
