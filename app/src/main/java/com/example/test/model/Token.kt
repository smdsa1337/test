package com.example.test.model

data class Token(
    val tokenType: String,
    val accessToken: String,
    val accessTokenExpires: String,
    val refreshToken: String
)
