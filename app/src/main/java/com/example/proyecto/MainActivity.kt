package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyecto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.btnLogear.setOnClickListener {
            configlog()
        }
        binding.btnRegistrar.setOnClickListener {
            configsign()
        }
    }
    private fun configlog(){
        val intent= Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    private fun configsign(){
        val intent= Intent(this, SignActivity::class.java)
        startActivity(intent)
    }
}