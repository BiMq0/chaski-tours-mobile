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
    private var lst_imagenes : List<imagenes> = listOf(
        imagenes("https://upload.wikimedia.org/wikipedia/commons/0/09/Valle_de_La_Luna_La_Paz_Bolivia_NAC.jpg"),
        imagenes("https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Casa_de_la_Moneda_de_Potos%C3%AD_%28Bolivia%29.jpg/1200px-Casa_de_la_Moneda_de_Potos%C3%AD_%28Bolivia%29.jpg"),
        imagenes("https://biocentro-guembe-villa-santa-cruz-de-la-sierra.santacruztophotels.com/data/Images/OriginalPhoto/6107/610793/610793836/image-santa-cruz-de-la-sierra-biocentro-guembe-villa-83.JPEG"),
        imagenes("https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Carnaval_de_Oruro_dia_I_%2860%29.JPG/330px-Carnaval_de_Oruro_dia_I_%2860%29.JPG"),
        imagenes("https://upload.wikimedia.org/wikipedia/commons/5/58/Rios_Orton_en_Pando_Bolivia%2C_nace_de_la_union_de_los_rios_Tahumanu_%28derecha%29_y_Manuripi_%28izquierda%29.jpg"),
        imagenes("https://www.magazinemanagement.gm-bolivia.com/wp-content/uploads/2021/09/image-2.png"),
        imagenes("https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEghxyuBsXmNRM62sqBWNdBCld5B_mval5ZpUFB30vwy186tRHy68XhWIhOyEycTuwM5Q1XY6bFJ5GSfRA_pCpM2-LgkAd64SeMfMGwVkZebUHBMmhQUiGsX_2bpDDXM4eCyN7z_19cOoeg1/s1600/1657549923334613-5.png"),
        imagenes("https://folkloreboliviano.org/resources/media/tourpicture/full/eec9e879df0a03e82c052ce9403f8dec.jpg"),
        imagenes("https://upload.wikimedia.org/wikipedia/commons/5/5e/Cristo_de_la_Concordia%2C_Cochabamba_2.jpg"),
        imagenes("https://upload.wikimedia.org/wikipedia/commons/thumb/4/4a/Salar_Uyuni_au01.jpg/640px-Salar_Uyuni_au01.jpg")
        )

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
                binding.root.adapter = ItemAdapter(lst_sitios,lst_imagenes)
            }
        }
    }



}