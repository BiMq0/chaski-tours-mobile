package com.example.proyecto

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto.databinding.ActivityHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class HomeActivity : AppCompatActivity() {

    private var  lst_sitios: List<ItemSitio> = emptyList()
    private lateinit var binding: ActivityHomeBinding
    private lateinit var service: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        setupRetrofit()
        getSitios()




    }


    private fun setupRetrofit(){
        val retrofit= Retrofit.Builder()
            .baseUrl(Constants.Base_url)
            .addConverterFactory( GsonConverterFactory.create())
            .build()


        service=retrofit.create(RetrofitService::class.java)
    }
    private fun getSitios(){
        lifecycleScope.launch(Dispatchers.IO) {
            val sitios = service.getSitios()
            lst_sitios = sitios
            launch(Dispatchers.Main) {
                binding.root.layoutManager = LinearLayoutManager(this@HomeActivity)
                binding.root.adapter = ItemAdapter(lst_sitios)
            }
        }
    }



}