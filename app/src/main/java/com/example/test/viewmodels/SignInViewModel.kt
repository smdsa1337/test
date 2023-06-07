package com.example.test.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.model.SupplierDetails
import com.example.test.repositories.SignInRepository
import com.example.test.utils.ApiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SignInViewModel @Inject constructor(
    @Named("signInRepository") private val repository: SignInRepository
) : ViewModel() {
    private val _authStatus: MutableLiveData<ApiStatus> = MutableLiveData()
    val authStatus: LiveData<ApiStatus> get() = _authStatus
    private val _tokenStatus: MutableLiveData<ApiStatus> = MutableLiveData()
    val tokenStatus: LiveData<ApiStatus> get() = _tokenStatus
    private val _authData: MutableLiveData<SupplierDetails> = MutableLiveData()
    val authData: LiveData<SupplierDetails> get() = _authData

    fun auth(login: String, password: String) {
        viewModelScope.launch {
            _authStatus.value = ApiStatus.LOADING
            repository.signIn(login, password).collect {
                // try {
                _authData.value = it
                _authStatus.value = ApiStatus.COMPLETE
                /*} catch (e: HttpException) {
                    Log.e("PIZDA", e.stackTraceToString())
                    _authStatus.value = ApiStatus.FAILED
                }*/
            }
        }
    }

    fun refreshToken(accessToken: String, refreshToken: String) {
        viewModelScope.launch {
            _tokenStatus.value = ApiStatus.LOADING
            try {
                repository.refreshToken(accessToken, refreshToken).collect {
                    _authData.value = it
                    _authStatus.value = ApiStatus.COMPLETE
                }
            } catch (e: HttpException) {
                _authStatus.value = ApiStatus.FAILED
            }
        }
    }


}