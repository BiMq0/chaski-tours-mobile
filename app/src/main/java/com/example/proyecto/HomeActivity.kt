package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto.databinding.ActivityHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.btnCategorias.setOnClickListener{
            startActivity(Intent(this, CategoriaActivity::class.java))
        }

        binding.btnVersitio1.setOnClickListener{
            startActivity(Intent(this, SitiosActivity::class.java))
        }
        binding.btnVersitio2.setOnClickListener{
            startActivity(Intent(this, SitiosActivity::class.java))
        }
        binding.btnVersitio3.setOnClickListener{
            startActivity(Intent(this, SitiosActivity::class.java))
        }
        binding.btnVersitio4.setOnClickListener{
            startActivity(Intent(this, SitiosActivity::class.java))
        }

    }

}