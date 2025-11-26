package com.mangelos.mangelos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mangelos.mangelos.databinding.ActivityClientHomeBinding
import com.mangelos.mangelos.models.CartRequest
import com.mangelos.mangelos.models.Product
import com.mangelos.mangelos.network.RetrofitClient
import com.mangelos.mangelos.utils.SessionManager
import kotlinx.coroutines.launch

class ClientHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientHomeBinding
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadProducts()

        binding.btnCheckout.setOnClickListener {
            performCheckout()
        }

        binding.btnLogout.setOnClickListener {
            SessionManager(this).logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun setupRecyclerView() {
        // Inicializamos el adapter con lista vacía y la función de agregar al carrito
        adapter = ProductAdapter(emptyList()) { product ->
            addToCart(product)
        }
        binding.rvProducts.layoutManager = LinearLayoutManager(this)
        binding.rvProducts.adapter = adapter
    }

    private fun loadProducts() {
        lifecycleScope.launch {
            try {
                val api = RetrofitClient.getInstance(this@ClientHomeActivity)
                val products = api.getProducts()
                adapter.updateList(products)
            } catch (e: Exception) {
                Toast.makeText(this@ClientHomeActivity, "Error cargando productos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addToCart(product: Product) {
        lifecycleScope.launch {
            try {
                // ... tu código de intento ...
                val request = CartRequest(product_id = product.id, quantity = 1)
                RetrofitClient.getInstance(this@ClientHomeActivity).addToCart(request)
                Toast.makeText(this@ClientHomeActivity, "Añadido: ${product.name}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                // --- CAMBIO AQUÍ ---
                // Esto imprimirá el error técnico en la pantalla y en la consola
                e.printStackTrace()
                Toast.makeText(this@ClientHomeActivity, "Error Real: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun performCheckout() {
        lifecycleScope.launch {
            try {
                Toast.makeText(this@ClientHomeActivity, "Procesando compra...", Toast.LENGTH_SHORT).show()
                val api = RetrofitClient.getInstance(this@ClientHomeActivity)

                // Ejecuta la transacción compleja en Xano
                val order = api.createOrder()

                Toast.makeText(this@ClientHomeActivity, "¡Compra Exitosa! Orden #${order.id} Total: $${order.total_price}", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(this@ClientHomeActivity, "Error: Carrito vacío o falla en servidor", Toast.LENGTH_LONG).show()
            }
        }
    }
}