package com.example.proyecto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proyecto.databinding.ActivityCategoriasBinding
import com.example.proyecto.databinding.ActivityHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoriaActivity :AppCompatActivity(){
    private lateinit var binding: ActivityCategoriasBinding
    private lateinit var service: RetrofitService
    private lateinit var lst_categorias: List<ItemCategoria>
    private var lst_img_categorias : List<imagenes> = listOf(
        imagenes("https://www.lostiempos.com/sites/default/files/media_imagen/2022/6/20/sitiospumapunku.jpg"),
        imagenes("https://live.staticflickr.com/8637/16023233624_677ac9ff4c_z.jpg"),
        imagenes("https://media.istockphoto.com/id/1098082332/es/foto/arco-y-m%C3%A1scara-en-la-casa-de-la-moneda-nacional-de-bolivia-del-edificio-en-el-centro-hist%C3%B3rico.jpg?s=612x612&w=0&k=20&c=u85DvmeJd_InT5Le5yaOTpKHW9jtfrjUTf8eeX20biU="),
        imagenes("https://boliviatravelsite.com/Images/Attractionphotos/la-paz-san-francisco-002.jpg"),
        imagenes("https://hemeroteca.larazon.bo/wp-content/uploads/2025/04/casa_patrimonial-2-1.jpg"),
        imagenes("https://spanish.lifestyletravelnetwork.com/wp-content/uploads/2022/08/Alpaca-Sajama-Bolivia-1024x805.webp"),
        imagenes("https://www.laregion.bo/wp-content/uploads/2015/10/MUSEO-NAL.-DE-ARTE.jpg"), //museo
        imagenes("https://upload.wikimedia.org/wikipedia/commons/thumb/c/c3/Ilustraci%C3%B3n_de_la_Batalla_de_Tarija.jpg/1200px-Ilustraci%C3%B3n_de_la_Batalla_de_Tarija.jpg"),
        imagenes("https://i0.wp.com/elcalderoviajero.com/wp-content/uploads/2018/04/bolivia-isla-del-sol-07.jpg?resize=750%2C563&ssl=1"),
        imagenes("https://dynamic-media-cdn.tripadvisor.com/media/photo-o/09/ac/09/c9/casa-dorada.jpg?w=1400&h=800&s=1"),
        imagenes("https://www.lavanguardia.com/files/image_990_484/uploads/2019/11/18/5fa538b3b53f1.jpeg"),
        imagenes("https://unifranz.edu.bo/wp-content/uploads/2023/07/IMG-20230718-WA0119.jpg")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCategoriasBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRetrofit()
        getCategorias()
    }

    private fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.Base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(RetrofitService::class.java)
    }

    private fun getCategorias() {
        lifecycleScope.launch(Dispatchers.IO) {
            val categorias = service.getCategorias()
            lst_categorias = categorias

            launch(Dispatchers.Main) {
                binding.root.layoutManager = GridLayoutManager(this@CategoriaActivity, 2)
                binding.root.adapter = CategoriaAdapter(lst_categorias, lst_img_categorias)
            }
        }
    }
}