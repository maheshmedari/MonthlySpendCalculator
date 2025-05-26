package com.joshstudioapps.monthlyspendcalculator.Repository

import com.joshstudioapps.monthlyspendcalculator.Model.User
import com.joshstudioapps.monthlyspendcalculator.Model.UserDao

class UserRepository(private val dao: UserDao) {
    suspend fun register(user: User) = dao.register(user)
    suspend fun login(email: String, password: String) = dao.login(email, password)
    suspend fun getUserByEmail(email: String) = dao.getUserByEmail(email)
    suspend fun getAllUsers(): List<User> = dao.getAllUsers()
}
