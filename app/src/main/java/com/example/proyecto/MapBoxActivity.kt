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
    private lateinit var lst_Sitios:List<ItemSitio>
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
    private fun setupRetrofit(){
        val retrofit= Retrofit.Builder()
            .baseUrl(Constants.Base_url)
            .addConverterFactory( GsonConverterFactory.create())
            .build()
        service=retrofit.create(RetrofitService::class.java)
    }
    private fun getUbicaciones(){
        lifecycleScope.launch(Dispatchers.IO) {
            lst_Ubicaciones = service.getUbicaciones()
            launch(Dispatchers.Main) {
                getSitios()
                configurarUbicacion()
            }
        }
    }
    private fun getSitios(){
        lifecycleScope.launch(Dispatchers.IO) {
            lst_Sitios =service.getSitios()
            launch(Dispatchers.Main) {
                datos()
            }
        }
    }
    private fun datos() {
        binding.tvNombreSitio.text= lst_Sitios[id_ubi-1].nombre
        binding.tvDescconceptualSitio.text= "Descripcion Conceptual:\t"+ lst_Sitios[id_ubi-1].desc_conceptual_sitio
        binding.tvDescsitioSitio.text="Descripcion Historica:\t"+ lst_Sitios[id_ubi-1].desc_historica_sitio
        binding.tvCostoSitioSitio.text="Costo:"+ lst_Sitios[id_ubi-1].costo_sitio.toString()
        binding.tvClimaRecomendadoSitio.text="Recomendacion Climatica:\t"+ lst_Sitios[id_ubi-1].recomendacion_climatica
        binding.tvTemporadaRecomendadaSitio.text="Temporada Recomendada:\t"+ lst_Sitios[id_ubi-1].temporada_recomendada
        binding.tvHorarioaperturaSitio.text="Horario de Apertura:\t"+ lst_Sitios[id_ubi-1].horario_apertura
        binding.tvHorariocierreSitio.text="Horario de Cierre\t"+ lst_Sitios[id_ubi-1].horario_cierre
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


}