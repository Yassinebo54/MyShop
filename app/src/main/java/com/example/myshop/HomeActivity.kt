package com.example.myshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.myshop.databinding.ActivityHomeBinding
import com.example.myshop.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var actionBar: ActionBar

    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()
        checkUser()


        binding.profileButton.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()

        }
        binding.mapButton.setOnClickListener {
            startActivity(Intent(this, mapActivity::class.java))

        }
        binding.signoutButton.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()

        }



    }
    private fun checkUser(){
        val firebaseUser =firebaseAuth.currentUser
        if(firebaseUser==null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }
    }
}