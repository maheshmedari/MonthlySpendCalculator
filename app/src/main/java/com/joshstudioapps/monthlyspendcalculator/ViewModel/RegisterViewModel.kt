package com.joshstudioapps.monthlyspendcalculator.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.joshstudioapps.monthlyspendcalculator.Model.User
import com.joshstudioapps.monthlyspendcalculator.Model.UserDatabase
import com.joshstudioapps.monthlyspendcalculator.Repository.UserRepository
import com.joshstudioapps.monthlyspendcalculator.utils.Validator
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = UserDatabase.getDatabase(application).userDao()
    private val repository = UserRepository(userDao)

    val registrationStatus = MutableLiveData<String>()

//    private val userDao = UserDatabase.getDatabase(application).userDao()
//    private val repository = UserRepository(userDao)
//
//    val registrationStatus = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()


    fun register(email: String, password: String, confirmPassword: String) {
        if (!Validator.isValidEmail(email)) {
            registrationStatus.value = "Invalid Email"
            return
        }
        if (!Validator.isValidPassword(password)) {
            registrationStatus.value = "Password too short"
            return
        }
        if (password != confirmPassword) {
            registrationStatus.value = "Passwords do not match"
            return
        }

        viewModelScope.launch {
            val existingUser = repository.getUserByEmail(email)
            if (existingUser != null) {
                registrationStatus.postValue("User already exists")
            } else {
                repository.register(User(email = email, password = password))
                registrationStatus.postValue("Success")
            }
        }
    }

    val allUsers = MutableLiveData<List<User>>()

    fun fetchAllUsers() {
        viewModelScope.launch {
            val users = repository.getAllUsers()
            allUsers.postValue(users)

            // Optional: Log for debugging
            users.forEach {
                Log.d("UserCheck", "Email: ${it.email}, Password: ${it.password}")
            }
        }
    }

}
