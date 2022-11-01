package com.example.myshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.myshop.databinding.ActivityProfileBinding
import com.example.myshop.databinding.ActivitySignuUpBinding
import com.google.firebase.auth.FirebaseAuth

 public class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var actionBar: ActionBar
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.setTitle("profile")

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.backButton.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            val email= firebaseUser.email
            binding.EmailText.text =email


        }
        else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}