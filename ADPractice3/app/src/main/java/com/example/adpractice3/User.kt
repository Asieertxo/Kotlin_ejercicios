package com.example.adpractice3

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class User : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val bundle = intent.extras
        val user = bundle?.getString("user")
        var main = Intent(this, MainActivity::class.java)
        Log.d("USER", user.toString())

        findViewById<TextView>(R.id.text_user).text = user.toString()

        findViewById<Button>(R.id.boton_logout).setOnClickListener{
            FirebaseAuth.getInstance().signOut();
            startActivity(main)
        }

    }
}