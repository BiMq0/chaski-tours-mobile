package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.proyecto.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var service: RetrofitService
    private lateinit var list_turistas:List<Turista>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupRetrofit()
        getTuristas()

        binding.btnVolverLogin.setOnClickListener {
            configvolver()
        }
        binding.btnEnviarLogin.setOnClickListener {
            verificar()
        }

    }

    private fun verificar(){
        val emailingresado= binding.etCorreo.text.toString().trim()
        val contraseniaingresada= binding.etContrasenia.text.toString().trim()

        if (!::list_turistas.isInitialized) {
            Toast.makeText(this, "Cargando datos, intenta de nuevo...", Toast.LENGTH_SHORT).show()
            return
        }
        val turista = list_turistas.find {
            it.correo_electronico == emailingresado && it.contrasenia == contraseniaingresada
        }

        if (turista != null) {
            Toast.makeText(this, "Bienvenido ${turista.nombre}", Toast.LENGTH_SHORT).show()
            configentrar()

        }
        else {
            Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
    }


    private fun setupRetrofit(){
        val retrofit=Retrofit.Builder()
            .baseUrl(Constants.Base_url)
            .addConverterFactory( GsonConverterFactory.create())
            .build()
        service=retrofit.create(RetrofitService::class.java)
    }

    private fun getTuristas(){
        lifecycleScope.launch (Dispatchers.IO) {
             list_turistas=service.getTuristas()
        }
    }

    private fun configvolver(){
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun configentrar(){
        val intent= Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}