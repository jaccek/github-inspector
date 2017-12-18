package com.github.jaccek.githubinspector.viper.userslist.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.jaccek.githubinspector.databinding.SingleUserItemBinding
import com.github.jaccek.githubinspector.rdp.model.User

class UsersListAdapter : RecyclerView.Adapter<SingleUserItemViewHolder>() {

    private val users = mutableListOf<User>()

    override fun onBindViewHolder(holder: SingleUserItemViewHolder?, position: Int) {
        holder?.bind(users[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleUserItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleUserItemBinding.inflate(inflater, parent, false)
        return SingleUserItemViewHolder(binding)
    }

    override fun getItemCount(): Int =
            users.size

    fun addUsers(newUsers: List<User>) {
        val startIdx = users.size
        users.addAll(newUsers)

        notifyItemRangeInserted(startIdx, newUsers.size)
    }

    fun getLastItem(): User =
            users.last()
}
