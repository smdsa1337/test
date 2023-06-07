package com.example.test.repositories

import com.example.test.model.SupplierDetails
import com.example.test.remote_model.LoginAndPasswordRequest
import com.example.test.service.SignInService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named

class SignInRepository @Inject constructor(
    @Named("signInService") private val service: SignInService
) {
    fun signIn(login: String, password: String): Flow<SupplierDetails> {
        return flow {
            emit(service.supplierLogin(LoginAndPasswordRequest(login, password)))
        }
    }
}