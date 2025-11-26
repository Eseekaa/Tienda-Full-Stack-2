package com.mangelos.mangelos.models

// --- AUTENTICACIÓN ---
data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val authToken: String, val user: UserData)
data class UserData(
    val id: Int?,       // Puede venir vacío
    val name: String?,  // Puede venir vacío
    val email: String?, // Puede venir vacío
    val role: String?   // Puede venir vacío
)

// --- PRODUCTOS (Soporta múltiples imágenes) ---
data class Product(
    val id: Int,
    val name: String,
    val description: String?,
    val price: Double,
    val stock: Int,
    val image: List<ProductImage>? // Xano devuelve una lista en el campo image
)

// Xano devuelve metadatos de imagen, nos interesa la URL
data class ProductImage(
    val url: String
)

// --- CARRITO (Para enviar datos a /cart/add) ---
data class CartRequest(
    val product_id: Int,
    val quantity: Int
)

// --- ORDENES (Para recibir el historial con Addons anidados) ---
data class Order(
    val id: Int,
    val created_at: Long,
    val total_price: Double,
    val status: String,
    // Tu amigo usó un addon, así que la info viene en un campo con guion bajo o similar
    // Ajusta el nombre "_order_products" si en Xano se llama diferente (ej: order_details)
    val _order_products: List<OrderDetail>?
)

data class OrderDetail(
    val id: Int,
    val quantity: Int,
    val price_at_purchase: Double,
    val _product: Product // Addon anidado: El detalle tiene el producto adentro
)