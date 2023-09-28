package com.example.adpractice3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth

    }
    public override fun onStart() {
        var login = Intent(this, Login::class.java)
        var userPage = Intent(this, User::class.java)
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Log.w("OK", "existe" + "${currentUser.email}")
            userPage.putExtra("user", currentUser.email.toString())
            startActivity(userPage)
        }else{
            Log.w("KO", "no existe" + "${currentUser}")
            startActivity(login)
        }
    }
}