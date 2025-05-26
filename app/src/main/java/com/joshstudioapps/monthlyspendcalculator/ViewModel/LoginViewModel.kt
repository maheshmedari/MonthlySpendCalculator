package com.joshstudioapps.monthlyspendcalculator.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshstudioapps.monthlyspendcalculator.Model.User
import com.joshstudioapps.monthlyspendcalculator.Model.UserDatabase
import com.joshstudioapps.monthlyspendcalculator.Repository.UserRepository
import com.joshstudioapps.monthlyspendcalculator.utils.Validator
import kotlinx.coroutines.launch

//class LoginViewModel: ViewModel() {
//
//    // MutableLiveData — internal value that ViewModel can change
//    private val _loginResult = MutableLiveData<Boolean>()
//
//    // LiveData — read-only version exposed to the View (Activity)
//    val loginResult: LiveData<Boolean> get() = _loginResult
//
//    fun login(email: String, password: String) {
//        // Hardcoded dummy check (replace with repository or Firebase)
//        val validUser = User("test@example.com", "123456")
//
//        // Set the result to true if login matches
//        _loginResult.value = (email == validUser.email && password == validUser.password)
//    }
//
//}

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = UserDatabase.getDatabase(application).userDao()
    private val repository = UserRepository(userDao)

    val loginResult = MutableLiveData<String>()

    fun login(email: String, password: String) {
        if (!Validator.isValidEmail(email)) {
            loginResult.value = "Invalid Email"
            return
        }

        viewModelScope.launch {
            val user = repository.login(email, password)
            loginResult.postValue(if (user != null) "Success" else "Invalid credentials")
        }
    }
}


/*
🔍 MVVM Recap (Model-View-ViewModel)
Model: Data layer (User, Repository, Room/Firebase)
View: UI (Activity/Fragment/XML)
ViewModel: Connects Model to View. Holds and manages UI-related data in a lifecycle-conscious way.*/

/*🔷 What is LiveData?
LiveData<T> is an observable data holder class.
It lets the View (Activity/Fragment) observe changes in data, and updates UI automatically without manual polling.
It's lifecycle-aware: updates only when the view is in active state (e.g., started/resumed).
You can't modify LiveData directly. It’s read-only.

Example:
val loginResult: LiveData<Boolean> get() = _loginResult
loginResult is the version you observe in the UI (View).

🔷 What is MutableLiveData?
MutableLiveData<T> is a subclass of LiveData that allows you to modify the data using:
.value = ... (synchronous)
.postValue(...) (asynchronous)
Only the ViewModel should have access to MutableLiveData.

private val _loginResult = MutableLiveData<Boolean>() // internal write access
val loginResult: LiveData<Boolean> get() = _loginResult // external read-only access
This is called encapsulation — protecting internal state.*/

/*✅ How View Observes LiveData
viewModel.loginResult.observe(this) { success ->
    if (success) {
        // Navigate to MainScreen
    } else {
        // Show error
    }
}
This runs automatically whenever _loginResult is updated.
No need to manually call or re-fetch values.*/

/*⚖️ Summary Table
| Term                 | Purpose                              | Mutable? | Who Uses It              |
| -------------------- | ------------------------------------ | -------- | ------------------------ |
| `LiveData<T>`        | Observes changes in data (read-only) | ❌        | Activity/Fragment (View) |
| `MutableLiveData<T>` | Allows setting value (write access)  | ✅        | ViewModel                |
| `ViewModel`          | Connects Model and View, holds data  | ✅        | Business logic layer     |*/

/*✅ Benefits
Avoid memory leaks (lifecycle-aware)
Clean separation of concerns
Fewer UI bugs from direct state changes
UI reacts automatically to data

Let me know if you want to integrate Firebase login, use Room DB, or handle errors/loading states in the ViewModel next.*/


