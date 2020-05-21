package com.viewsonic.coroutinesample

import com.viewsonic.coroutinesample.MainActivity.User

object Functions {

    suspend fun fetchFirstUser(): User {
        // make network call
        return User()
    }

    suspend fun fetchSecondUser(): User {
        // make network call
        return User()
    }


    fun showUser(user: User) {
        // show user
    }

    fun showUsers(user1: User, user2: User) {
        // show user
    }

    fun getUser() {
        // show user
    }
}