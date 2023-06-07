package com.example.test.okhttp

import android.content.SharedPreferences
import com.example.test.remote_model.RefreshTokenRequest
import com.example.test.service.SignInService
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Named

class AuthInterceptor @Inject constructor(
    @Named("sharedPreferencesToken") private val prefs: SharedPreferences
) : Interceptor {

    private fun refreshToken(request: Request, chain: Interceptor.Chain): Response {
        val service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://test-api.redro.ru/api/")
            .build()
            .create(SignInService::class.java)
        val refreshToken = prefs.getString("refreshToken", "")!!
        val response = service.refreshToken(RefreshTokenRequest(refreshToken)).execute()

        // make an API call to get new token
        return if (response.isSuccessful) {

            val token = response.body()?.payload?.token?.accessToken
            saveTokenToLocalStorage(token)

            val newRequest = request
                .newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        } else {
            chain.proceed(request)
        }
    }

    private fun saveTokenToLocalStorage(token: String?) {
        prefs.edit().putString("accessToken", token).apply()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = getFromStorage()
        val request = chain.request()
        if (token.isNotEmpty()) {
            val newRequest = request
                .newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            val response = chain.proceed(newRequest)
            return if (response.code == 401) {
                refreshToken(request, chain)
            } else {
                response
            }
        } else {
            refreshToken(request, chain)
        }

        return chain.proceed(request)
    }

    private fun getFromStorage(): String {
        return prefs.getString("token", "")!!
    }
}