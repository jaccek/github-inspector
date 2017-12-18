package com.github.jaccek.githubinspector.rdp

interface UsersByPageSpecification : Specification {

    fun withLastUserIdExcluding(userId: Int): UsersByPageSpecification
}
