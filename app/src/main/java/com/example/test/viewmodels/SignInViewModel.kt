package com.example.test.viewmodels

import androidx.lifecycle.ViewModel
import com.example.test.repositories.SignInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SignInViewModel @Inject constructor(
    @Named("SignInRepository") private val repository : SignInRepository
) : ViewModel() {

}