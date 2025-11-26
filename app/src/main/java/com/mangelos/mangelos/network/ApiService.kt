package com.mangelos.mangelos.network

import com.mangelos.mangelos.models.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // 1. Login
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    // 2. Catálogo Público
    @GET("product")
    suspend fun getProducts(): List<Product>

    // 3. Agregar al Carrito (Lógica condicional de tu amigo)
    @POST("cart/add") // <--- ¡SIN BARRA!
    suspend fun addToCart(@Body request: CartRequest): Any

    // 4. Finalizar Compra (La transacción compleja)
    // No necesita body porque toma el usuario del token y usa lo que hay en la tabla 'cart'
    @POST("orders/create")
    suspend fun createOrder(): Order

    // 5. Ver mis pedidos
    @GET("orders")
    suspend fun getMyOrders(): List<Order>
}