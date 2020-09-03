package com.appsnado.mvvmapplication.viewmodels

import android.text.TextUtils
import android.util.Patterns
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.appsnado.mvvmapplication.BR
import com.appsnado.mvvmapplication.models.User


object LoginViewModel : BaseObservable() {

    private lateinit var user: User
    var errorMessage = "Login Failed"
    var successMessage = "Login Successful"

    @Bindable
    private var toastMessage: String? = null

    @Bindable
    public fun getToastMessage() = toastMessage

    private fun setToastMessage(toastMessage: String) {
        this.toastMessage = toastMessage
        notifyPropertyChanged(BR.toastMessage)
    }

    fun setUserEmail(email: String?) {
        user.email = email.toString()
        notifyPropertyChanged(BR.userEmail)
    }

    @Bindable
    fun getUserEmail() = user.email

    @Bindable
    fun getUserPassword() = user.password


    fun setUserPassword(password: String?) {
        user.password = password.toString()
        notifyPropertyChanged(BR.userPassword)
    }

    fun LoginModel() {
        user = User("", "")
    }

    fun onLoginClicked() {
        if (isInputDataValid()) setToastMessage(successMessage) else setToastMessage(errorMessage)
    }

    fun isInputDataValid() =
        !TextUtils.isEmpty(getUserEmail()) && Patterns.EMAIL_ADDRESS.matcher(getUserEmail())
            .matches() && getUserPassword().length > 5
}
