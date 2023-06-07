package com.example.test.repositories

import com.example.test.di.ApiClient
import javax.inject.Inject

class SignInRepository @Inject constructor(
    private val service : ApiClient
){
    suspend fun SignIn(){

    }
}