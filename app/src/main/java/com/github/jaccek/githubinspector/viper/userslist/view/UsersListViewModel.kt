package com.github.jaccek.githubinspector.viper.userslist.view

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.github.jaccek.githubinspector.BR

class UsersListViewModel : BaseObservable() {

    @Bindable
    var loaderVisible = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.loaderVisible)
        }
}
