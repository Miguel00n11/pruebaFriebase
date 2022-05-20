package com.example.pruebafriebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

enum class ProviderType {
    Basic
}

class HomeActivity : AppCompatActivity() {

    private val db=FirebaseFirestore.getInstance()

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
        val direction=findViewById<TextView>(R.id.tvDireccion)
        val telefono=findViewById<TextView>(R.id.tvPhone)

        emailText.text=email
        providerText.text=Provider

        val Guardar=findViewById<Button>(R.id.bGuardar)
        val Eliminar=findViewById<Button>(R.id.bEliminar)
        val Recuerar=findViewById<Button>(R.id.bRuperar)

        Guardar.setOnClickListener(){
            db.collection("users").document(email).set(hashMapOf("Provider" to providerText.text.toString(),
                "Direccion" to direction.text.toString(),
                "Telefono" to telefono.text.toString()))
        }
        Recuerar.setOnClickListener(){
            db.collection("users").document(email).get().addOnSuccessListener{
                direction.setText(it.get("Direccion") as String?)
                telefono.setText(it.get("Telefono") as String?)

            }



        }
        Eliminar.setOnClickListener(){
            db.collection("users").document(email).delete()
        }

    }
}