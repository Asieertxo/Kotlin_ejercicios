package com.example.adpractice3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        var main = Intent(this, MainActivity::class.java)

        val boton_registrar = findViewById<Button>(R.id.boton_registrar)
        val boton_acceder = findViewById<Button>(R.id.boton_acceder)


        boton_registrar.setOnClickListener {
            val text_email = findViewById<TextInputEditText>(R.id.editText_name)
            val text_pass = findViewById<TextInputEditText>(R.id.editText_pass)
            if(text_email != null && text_pass != null) {
                auth.createUserWithEmailAndPassword(text_email.toString(), text_pass.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("OK", "createUserWithEmail:success")
                            startActivity(main)
                            //updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("KO", "createUserWithEmail:failure", task.exception)

                            val error = task.exception?.message ?: ""

                            if (error.contains("INVALID_LOGIN_CREDENTIALS")){
                                Toast.makeText(baseContext, "Email incorrecto", Toast.LENGTH_SHORT,).show()
                            }

                            try{
                                throw task.exception!!
                            }catch (e: FirebaseAuthInvalidUserException){
                                Toast.makeText(baseContext, "Usuario incorrecto", Toast.LENGTH_SHORT,).show()
                            }catch (e: FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(baseContext, "Credenciales incorrectas", Toast.LENGTH_SHORT,).show()
                            }catch (e: Exception){
                                Log.w("OK", e.message!!)
                            }
                        }
                    }

            }else{
                Toast.makeText(baseContext, "Rellene todos los campos", Toast.LENGTH_SHORT,).show()
            }
        }

        boton_acceder.setOnClickListener {
            val text_email = findViewById<TextInputEditText>(R.id.editText_name).text
            val text_pass = findViewById<TextInputEditText>(R.id.editText_pass).text
            if(text_email != null && text_pass != null) {
                auth.signInWithEmailAndPassword(text_email.toString(), text_pass.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("OK", "signInWithEmail:success")
                            startActivity(main)
                            //updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("KO", "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                            //updateUI(null)
                        }
                    }
            }else{
                Toast.makeText(baseContext, "Rellene todos los campos", Toast.LENGTH_SHORT,).show()
            }
        }




    }
}