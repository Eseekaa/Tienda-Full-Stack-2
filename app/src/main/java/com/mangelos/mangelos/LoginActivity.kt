package com.mangelos.mangelos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mangelos.mangelos.databinding.ActivityLoginBinding
import com.mangelos.mangelos.models.LoginRequest
import com.mangelos.mangelos.network.RetrofitClient
import com.mangelos.mangelos.utils.SessionManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        session = SessionManager(this)

        // Si ya hay sesión, saltar al home
        if (session.getToken() != null) {
            goToHome()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPassword.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                performLogin(email, pass)
            } else {
                Toast.makeText(this, "Faltan datos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performLogin(email: String, pass: String) {
        lifecycleScope.launch {
            try {
                val api = RetrofitClient.getInstance(this@LoginActivity)
                val response = api.login(LoginRequest(email, pass))

                // Guardar sesión
                session.saveSession(response.authToken, response.user.role)

                Toast.makeText(this@LoginActivity, "Bienvenido ${response.user.name}", Toast.LENGTH_SHORT).show()
                goToHome()

            } catch (e: Exception) {
                // Esto nos mostrará el error real técnico en la pantalla
                Toast.makeText(this@LoginActivity, "Error Real: ${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace() // Esto lo imprime en la consola de abajo (Logcat)
            }
        }
    }

    private fun goToHome() {
        startActivity(Intent(this, ClientHomeActivity::class.java))
        finish()
    }
}