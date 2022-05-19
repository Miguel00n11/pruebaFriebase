package com.example.pruebafriebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val email=findViewById<EditText>(R.id.etEmail)
        val password=findViewById<EditText>(R.id.etPassword)
        val registrarse=findViewById<Button>(R.id.bRegistrar)
        val acceder=findViewById<Button>(R.id.acceder)

        // Initialize Firebase Auth
        auth = Firebase.auth

        registrarse.setOnClickListener(){
            registrarse1(email.text.toString(),password.text.toString())
        }
        acceder.setOnClickListener(){
            acceder1(email.text.toString(),password.text.toString())
        }
    }
    private fun registrarse1(email:String,password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = auth.currentUser
                    showHome(task.result?.user?.email?:"",ProviderType.Basic)
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    showAlert()
//                    updateUI(null)
                }
            }


    }

    private fun acceder1(email:String,password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = auth.currentUser
                    showHome(task.result?.user?.email?:"",ProviderType.Basic)
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    showAlert()
//                    updateUI(null)
                }
            }


    }

    private fun showAlert(){
        val builder=AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error de autentificacion al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog:AlertDialog=builder.create()
        dialog.show()


    }
    private fun showHome(email:String,provider:ProviderType){

        val homeIntent=Intent(this,HomeActivity::class.java).apply {
            putExtra("email",email)
            putExtra("Provider",provider.name)

        }
        startActivity(homeIntent)

    }

}