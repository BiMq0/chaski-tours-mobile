package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.proyecto.databinding.ActivityLoginBinding
import com.example.proyecto.databinding.ActivitySignBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
class SignActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignBinding
    private lateinit var service: RetrofitService
    private lateinit var lst_Visitantes: List<Visitante>
    var codigo_visitante=""

    var correo = ""
    var contrasenia = ""
    var documento = ""
    var nombre = ""
    var appat = ""
    var apmat = ""
    var fecha_nac = ""
    var nacionalidad = ""
    var telefono = ""




    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivitySignBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupRetrofit()
        getVisitantes()
        binding.btnCancelarSign.setOnClickListener {
            configvolver()
        }
        binding.btnEnviarSign.setOnClickListener {

            if (binding.etCorreoSign.text.toString() == ""||binding.etContraseniaSign.text.toString() == ""||binding.etDocumentoSign.text.toString() == ""
                ||binding.etNombreSign.text.toString() == ""||binding.etAppatSign.text.toString() == ""||binding.etApmatSign.text.toString() == ""
                ||binding.etFechanacSign.text.toString() == ""||binding.etTelefonoSign.text.toString() == "")
            {
                Toast.makeText(this@SignActivity, "Campos incompletos", Toast.LENGTH_SHORT).show()
            }
            else{

                correo = binding.etCorreoSign.text.toString()
                contrasenia = binding.etContraseniaSign.text.toString()
                documento = binding.etDocumentoSign.text.toString()
                nombre = binding.etNombreSign.text.toString()
                appat = binding.etAppatSign.text.toString()
                apmat = binding.etApmatSign.text.toString()
                fecha_nac = binding.etFechanacSign.text.toString()
                nacionalidad = "Bolivia"
                telefono = binding.etTelefonoSign.text.toString()
                var turista=Turista(codigo_visitante,correo,contrasenia,documento,nombre,appat,apmat,fecha_nac,nacionalidad,telefono)

                sentVisitante(turista)
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
    private fun getVisitantes(){
        lifecycleScope.launch(Dispatchers.IO) {
            lst_Visitantes = service.getVisitante()
            launch(Dispatchers.Main) {
                configurarid()
            }
        }
    }

    private fun configurarid() {
        codigo_visitante= "Vis"+lst_Visitantes.size
        Toast.makeText(this@SignActivity, "Codigo creado: $codigo_visitante", Toast.LENGTH_SHORT).show()
    }
    private fun configvolver(){
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun sentVisitante(turista: Turista) {

        var visitante=Visitante("$codigo_visitante","Turista",1)
        lifecycleScope.launch {
            try {
                    val response = service.sentVisitante(visitante)
                    if (response.isSuccessful) {
                        Toast.makeText(this@SignActivity, "Visitante creado", Toast.LENGTH_SHORT).show()
                        launch(Dispatchers.Main) {
                            sentTurista(turista)
                        }
                    } else {
                        Toast.makeText(this@SignActivity, "Visitante no creado", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            catch(e:Exception) {
                    Toast.makeText(this@SignActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun sentTurista( turista: Turista) {
            lifecycleScope.launch {

                try {
                    val resp = service.sentTuristas(turista)
                        if (resp.isSuccessful) {
                            Toast.makeText(this@SignActivity, "Turista creado", Toast.LENGTH_SHORT).show()
                            launch(Dispatchers.Main) {
                                configinicio()
                            }
                        }
                        else {
                            Toast.makeText(this@SignActivity, "Turista no creado", Toast.LENGTH_SHORT).show()
                        }
                }
                catch (e:Exception){
                    Toast.makeText(this@SignActivity, "Error: "+e, Toast.LENGTH_SHORT).show()
                }
        }


    }

    private fun configinicio() {
        val intent= Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


}