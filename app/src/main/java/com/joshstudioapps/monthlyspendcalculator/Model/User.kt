package com.joshstudioapps.monthlyspendcalculator.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

//data class User(val email: String, val password: String)

//DB code
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String,
    val password: String
)

