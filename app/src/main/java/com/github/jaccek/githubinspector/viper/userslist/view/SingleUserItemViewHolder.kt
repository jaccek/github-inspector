package com.github.jaccek.githubinspector.viper.userslist.view

import android.support.v7.widget.RecyclerView
import com.github.jaccek.githubinspector.databinding.SingleUserItemBinding
import com.github.jaccek.githubinspector.rdp.model.User

class SingleUserItemViewHolder(private val binding: SingleUserItemBinding)
    : RecyclerView.ViewHolder(binding.root) {

    private val gitHubMainPage = "https://github.com"

    init {
        binding.viewModel = UserItemViewModel()
    }

    fun bind(user: User) {
        binding.viewModel?.apply {
            username = user.login
            avatarUrl = user.avatarUrl ?: ""
            gitHubPage = "$gitHubMainPage/${user.login}"
        }
    }
}
