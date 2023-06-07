package com.example.test.repositories

import com.example.test.model.PayloadProduct
import com.example.test.model.Product
import com.example.test.service.ProductsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named

class MainRepository @Inject constructor(
    @Named("mainService") private val service: ProductsService
) {
    fun getProducts() : Flow<PayloadProduct>{
        return flow{
            emit(service.getProducts())
        }
    }
}