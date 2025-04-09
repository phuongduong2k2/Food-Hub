package com.example.food_hub.data

import com.example.food_hub.data.models.AuthResponse
import com.example.food_hub.data.models.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodApi {
    @GET("/foods/foods")
    suspend fun getFood(): List<Any>

    @POST("/auth/signup")
    suspend fun signUp(@Body request: SignUpRequest): AuthResponse
}