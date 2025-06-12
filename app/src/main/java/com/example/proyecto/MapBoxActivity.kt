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
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.mapbox.maps.Style
import android.graphics.BitmapFactory


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
        val ubicacion = lst_Ubicaciones.find { it.id_ubicacion == id_ubi }

        if (ubicacion != null) {
            val point = Point.fromLngLat(lst_Ubicaciones[id_ubi-1].longitud, lst_Ubicaciones[id_ubi-1].latitud)

            binding.mapView.mapboxMap.loadStyle(Style.STANDARD) { style ->
                setCameraPosition(point)
                addMarker(point)
            }
        } else {
            Toast.makeText(this, "Ubicación no encontrada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setCameraPosition(point: Point) {
        binding.mapView.mapboxMap.setCamera(
            CameraOptions.Builder()
                .center(point)
                .zoom(14.0)
                .build()
        )
    }

    private fun addMarker(point: Point) {
        val annotationApi = binding.mapView.annotations
        val pointAnnotationManager = annotationApi.createPointAnnotationManager()
        val markerBitmap = BitmapFactory.decodeResource(
            resources,
            R.drawable.red_marker
        )
        val pointAnnotationOptions = PointAnnotationOptions()
            .withPoint(point)
            .withIconImage(markerBitmap)
            .withIconSize(0.5)

        pointAnnotationManager.create(pointAnnotationOptions)
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