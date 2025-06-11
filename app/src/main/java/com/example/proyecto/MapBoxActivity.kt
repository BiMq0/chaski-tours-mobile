package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto.databinding.ActivityMapBoxBinding
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapBoxActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBoxBinding
    private lateinit var mapView: MapView
    private lateinit var service: RetrofitService
    private lateinit var lst_Ubicaciones:List<Ubicaciones>
    var id_ubi = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMapBoxBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        id_ubi = intent.getIntExtra("id_ubicacion", 0)
        setupRetrofit()
        getUbicaciones()


    }

    private fun configurarUbicacion() {
        val ubicacion = lst_Ubicaciones.find {
            it.id_ubicacion == id_ubi
        }

        if (ubicacion != null) {
            Toast.makeText(this, "Ubicacion Encontrada", Toast.LENGTH_SHORT).show()


            val mapView: MapView = binding.mapView
            mapView.getMapboxMap().setCamera(
                CameraOptions.Builder()
                    .center(Point.fromLngLat(lst_Ubicaciones[id_ubi-1].longitud, lst_Ubicaciones[id_ubi-1].latitud))
                    .zoom(14.0)
                    .build()
            )

        }
        else {
            Toast.makeText(this, "Ubicacion No Encontrada", Toast.LENGTH_SHORT).show()
        }



    }


    private fun getUbicaciones(){
        lifecycleScope.launch(Dispatchers.IO) {
            val ubicaciones = service.getUbicaciones()
            lst_Ubicaciones = ubicaciones
            launch(Dispatchers.Main) {
                configurarUbicacion()
            }
        }
    }
    private fun setupRetrofit(){
        val retrofit= Retrofit.Builder()
            .baseUrl(Constants.Base_url)
            .addConverterFactory( GsonConverterFactory.create())
            .build()
        service=retrofit.create(RetrofitService::class.java)
    }


}