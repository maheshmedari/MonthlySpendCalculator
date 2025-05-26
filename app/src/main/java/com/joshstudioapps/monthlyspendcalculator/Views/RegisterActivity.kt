package com.joshstudioapps.monthlyspendcalculator.Views

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.joshstudioapps.monthlyspendcalculator.R
import com.joshstudioapps.monthlyspendcalculator.ViewModel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel
    private var isPasswordVisible = false
    private var isConfirmVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        val name = findViewById<EditText>(R.id.editTextName)
        val email = findViewById<EditText>(R.id.editTextEmail)
        val password = findViewById<EditText>(R.id.editTextPassword)
        val confirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        val usersList = findViewById<Button>(R.id.getAllUsers)

        // Toggle Password Visibility
        password.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = 2
                if (event.rawX >= (password.right - password.compoundDrawables[drawableEnd].bounds.width())) {
                    isPasswordVisible = !isPasswordVisible
                    password.inputType = if (isPasswordVisible) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    else InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    password.setSelection(password.text.length)
                    true
                }
            }
            false
        }

        confirmPassword.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = 2
                if (event.rawX >= (confirmPassword.right - confirmPassword.compoundDrawables[drawableEnd].bounds.width())) {
                    isConfirmVisible = !isConfirmVisible
                    confirmPassword.inputType = if (isConfirmVisible) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    else InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    confirmPassword.setSelection(confirmPassword.text.length)
                    true
                }
            }
            false
        }

        buttonRegister.setOnClickListener {
            viewModel.register(
                email.text.toString(),
                password.text.toString(),
                confirmPassword.text.toString()
            )
        }

        viewModel.registrationStatus.observe(this) { message ->
            when (message) {
                "Success" -> {
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else -> {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        usersList.setOnClickListener {
            viewModel.fetchAllUsers()

            viewModel.allUsers.observe(this) { users ->
                users.forEach {
                    Log.d("AllUsers", "Email: ${it.email}, Password: ${it.password}")
                }
            }
        }


    }
}

/*- data/
- User.kt
- UserDao.kt
- UserDatabase.kt

- repository/
- UserRepository.kt

- viewmodel/
- RegisterViewModel.kt
- LoginViewModel.kt

- ui/
- RegisterActivity.kt
- LoginActivity.kt

- utils/
- Validator.kt*/
