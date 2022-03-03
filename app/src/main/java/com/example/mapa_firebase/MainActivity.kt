package com.example.mapa_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    // Variable de tipo FirebaseAuth
    private lateinit var auth: FirebaseAuth

    //Creamos variables para enlazar con el layout
    lateinit var email: EditText
    lateinit var contraseña: EditText
    lateinit var iniciarSesion: Button
    lateinit var registro: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
// Enlazamos las variables al layout
        email = findViewById(R.id.email)
        contraseña = findViewById(R.id.contraseña)
        iniciarSesion = findViewById(R.id.iniciarSesion)
        registro = findViewById(R.id.registro)

        auth = Firebase.auth
//Eventos en los botones iniciar sesion y registro
        registro.setOnClickListener {
            crearcuenta(email.text.toString(), contraseña.text.toString())
        }
        iniciarSesion.setOnClickListener {
            iniciarsesion(email.text.toString(), contraseña.text.toString())
        }

    }

    //Método que crea una cuenta recibiendo un email y una contraseña
    private fun crearcuenta(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Cuenta creada", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "createUserWithEmail:success")
                    Log.d("estado", "usuario registrado")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "No es posible crear cuenta", Toast.LENGTH_SHORT).show()
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Log.d("estado", "usuario NO registrado")

                }
            }

    }

    private fun updateUI(user: FirebaseUser?) {
        Log.d("estado", "" + auth.currentUser?.uid)
    }

    //Método que inicia sesion recibiendo un email y una contraseña
    private fun iniciarsesion(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    Log.d("estado", "Se ha iniciado sesion")
                    val user = auth.currentUser
                    updateUI(user)
                    val intent = Intent(this, MapsActivity::class.java).apply{}
                    startActivity(intent)


                } else {

                    Toast.makeText(baseContext, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Log.d("estado", "No es posible iniciar sesion")
                }
            }

    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}