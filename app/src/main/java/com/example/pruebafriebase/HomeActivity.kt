package com.example.pruebafriebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

enum class ProviderType {
    Basic
}

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bundle:Bundle? = intent.extras
        val email: String?= bundle?.getString("email")
        val provider: String?= bundle?.getString("provider")
        Setup(email?:"",provider?:"")


        val cerrarSesion=findViewById<Button>(R.id.bCerrarSesion)
        cerrarSesion.setOnClickListener(){
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }

    }

    private fun Setup(email:String,Provider:String){
        title="Inicio"
        val emailText=findViewById<TextView>(R.id.etEmail)
        val providerText=findViewById<TextView>(R.id.etProvider)

        emailText.text=email
        providerText.text=Provider


    }
}