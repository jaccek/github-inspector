package com.github.jaccek.githubinspector.util.logger

import com.github.jaccek.githubinspector.di.DiProvider

interface Logger {

    companion object {
        var logger = DiProvider.logger

        fun e(tag: String, message: String) {
            logger.logError(tag, message)
        }

        fun e(obj: Any, message: String) {
            logger.logError(obj::class.java.simpleName, message)
        }

        fun e(throwable: Throwable) {
            logger.logError(throwable)
        }

        fun w(tag: String, message: String) {
            logger.logWarning(tag, message)
        }

        fun w(obj: Any, message: String) {
            logger.logWarning(obj::class.java.simpleName, message)
        }

        fun w(throwable: Throwable) {
            logger.logWarning(throwable)
        }

        fun i(tag: String, message: String) {
            logger.logInfo(tag, message)
        }

        fun i(obj: Any, message: String) {
            logger.logInfo(obj::class.java.simpleName, message)
        }

        fun d(tag: String, message: String) {
            logger.logDebug(tag, message)
        }

        fun d(obj: Any, message: String) {
            logger.logDebug(obj::class.java.simpleName, message)
        }

        fun v(tag: String, message: String) {
            logger.logVerbose(tag, message)
        }

        fun v(obj: Any, message: String) {
            logger.logVerbose(obj::class.java.simpleName, message)
        }
    }

    fun logError(tag: String, message: String)
    fun logError(throwable: Throwable)

    fun logWarning(tag: String, message: String)
    fun logWarning(throwable: Throwable)

    fun logInfo(tag: String, message: String)

    fun logDebug(tag: String, message: String)

    fun logVerbose(tag: String, message: String)

}
