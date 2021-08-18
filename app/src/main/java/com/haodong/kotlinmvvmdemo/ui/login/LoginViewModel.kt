package com.haodong.kotlinmvvmdemo.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.haodong.kotlinmvvmdemo.model.repository.LoginRepository
import com.haodong.lib.common.core.BaseViewModel
import com.haodong.lib.common.model.bean.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Author: tangyuan
 * Time : 2021/8/17
 * Description:
 */
class LoginViewModel(val repository: LoginRepository) : BaseViewModel() {

    val userName = ObservableField<String>("")
    val passWord = ObservableField<String>("")

    private val _uiState = MutableLiveData<LoginUiState<User>>()
    val uiState: LiveData<LoginUiState<User>>
        get() = _uiState

    private fun isInputValid(userName: String, passWord: String) = userName.isNotBlank() && passWord.isNotBlank()

    fun login() {
        viewModelScope.launch(Dispatchers.Main) {
            repository.loginFlow(userName.get() ?: "", passWord = passWord.get() ?: "")
                .collect {
                    _uiState.postValue(it)
                }

        }
    }

    fun register() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.loginFlow(userName = userName.get() ?: "", passWord = passWord.get() ?: "").collect {
                _uiState.postValue(it)
            }
        }
    }

    val verifyInput: (String) -> Unit = { loginDataChanged() }
    fun loginDataChanged() {
        _uiState.value = LoginUiState(
            enableLoginButton = isInputValid(
                userName.get()
                    ?: "", passWord.get() ?: ""
            )
        )
    }


}