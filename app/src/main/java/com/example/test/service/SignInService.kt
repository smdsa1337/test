package com.example.test.service

import com.example.test.model.SupplierDetails
import com.example.test.remote_model.LoginAndPasswordRequest
import com.example.test.remote_model.RefreshTokenRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {
    @POST("auth/supplier-login")
    suspend fun supplierLogin(@Body request: LoginAndPasswordRequest): SupplierDetails

    @POST("auth/token-refresh")
    fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Call<SupplierDetails>
}