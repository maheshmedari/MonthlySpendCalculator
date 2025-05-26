package com.joshstudioapps.monthlyspendcalculator.utils

import android.util.Patterns

object Validator {
    fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isValidPassword(password: String) = password.length >= 6
}
