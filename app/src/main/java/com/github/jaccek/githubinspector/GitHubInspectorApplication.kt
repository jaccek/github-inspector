package com.github.jaccek.githubinspector

import android.app.Application
import com.github.jaccek.githubinspector.di.DiProvider

class GitHubInspectorApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        DiProvider.init()
    }
}
