package com.github.jaccek.githubinspector.rdp.model

data class User(
        val id: Int,
        val login: String,
        val avatarUrl: String? = null
)