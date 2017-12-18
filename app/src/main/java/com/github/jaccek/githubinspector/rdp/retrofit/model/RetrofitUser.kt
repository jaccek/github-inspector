package com.github.jaccek.githubinspector.rdp.retrofit.model

import com.google.gson.annotations.SerializedName

data class RetrofitUser(

        var id: Int? = null,

        var login: String? = null,

        @SerializedName("avatar_url")
        var avatarUrl: String? = null
)
