package com.github.jaccek.githubinspector.util.logger

import android.util.Log

class LoggerImpl : Logger {

    override fun logError(tag: String, message: String) {
        Log.e(tag, message)
    }

    override fun logError(throwable: Throwable) {
        throwable.printStackTrace()
        // TODO: report to Crashlytics
    }

    override fun logWarning(tag: String, message: String) {
        Log.w(tag, message)
    }

    override fun logWarning(throwable: Throwable) {
        throwable.printStackTrace()
    }

    override fun logInfo(tag: String, message: String) {
        Log.i(tag, message)
    }

    override fun logDebug(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun logVerbose(tag: String, message: String) {
        Log.v(tag, message)
    }
}
