package com.example.test.service

import com.example.test.model.PayloadProduct
import retrofit2.http.GET

interface ProductsService {

    @GET("products/get-products")
    suspend fun getProducts() : PayloadProduct
}