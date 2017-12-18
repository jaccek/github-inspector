package com.github.jaccek.githubinspector.viper.userslist.view

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.github.jaccek.githubinspector.BR

class UserItemViewModel : BaseObservable() {

    @Bindable
    var avatarUrl: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.avatarUrl)
        }

    @Bindable
    var username: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.username)
        }

    @Bindable
    var gitHubPage: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.gitHubPage)
        }
}
