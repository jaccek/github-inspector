package com.github.jaccek.githubinspector.rdp.retrofit

import com.github.jaccek.githubinspector.rdp.retrofit.model.RetrofitUser
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi { // TODO: move to separate file
    @GET("users")
    fun getUsers(@Query("since") lastUserIdExcluding: Int): Observable<List<RetrofitUser>>
}
