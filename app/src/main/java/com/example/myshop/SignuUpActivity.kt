package com.example.myshop

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.myshop.databinding.ActivitySignuUpBinding
import com.google.firebase.auth.FirebaseAuth


public class SignuUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignuUpBinding
    private lateinit var actionBar: ActionBar
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignuUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.setTitle("sign Up")
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.SignUpButton.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        email = binding.EmailText.text.toString()
        password = binding.passwordText.text.toString()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.EmailText.error = "Invalid Email"

        }
        else if(TextUtils.isEmpty(password)){
            binding.password.error="Enter password"
        }
        else{
            firebaseSignUp()
        }

    }

    private fun firebaseSignUp() {
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText( this, "${e.message}" ,Toast.LENGTH_SHORT).show()

            }
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText( this, "Account created" ,Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()

            }
    }


    //back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}