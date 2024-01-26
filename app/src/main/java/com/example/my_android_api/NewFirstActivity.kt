package com.example.my_android_api

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class NewFirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_first)

        val buttonSecondActivity: Button = findViewById(R.id.buttonSecondActivity)

        // Ajouter un écouteur de clic sur le bouton
        buttonSecondActivity.setOnClickListener {
            // Créer une intention (Intent) pour passer à la deuxième activité
            val intent = Intent(this, MainActivity::class.java)

            // Démarrer la deuxième activité
            startActivity(intent)
        }

    }
}