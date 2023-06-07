package com.example.test.service

import com.example.test.model.SupplierDetails
import com.example.test.remote_model.LoginAndPasswordRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {
    @POST("auth/supplier-login")
    suspend fun supplierLogin(@Body request: LoginAndPasswordRequest): SupplierDetails
}