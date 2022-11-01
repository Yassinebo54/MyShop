package com.example.myshop

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.myshop.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.example.myshop.SignuUpActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBar: ActionBar
    private lateinit var progressDialog:ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private var email=""
    private var password=""
    private var type=""
    private var uSername = ""
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        //configure action bar
        actionBar = supportActionBar!!
        actionBar.title = "Login"
        //c progressdialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading...")
        progressDialog.setCanceledOnTouchOutside(false)
        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser();

        //no account activity
        binding.noAccount.setOnClickListener {
            startActivity(Intent(this,SignuUpActivity::class.java ))
        }
        //login activity
        binding.loginButton.setOnClickListener {
            validateData()
            val type = binding.typeText.text.toString()
            val uSername = binding.uSernameText.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Users")
            val User = UserDatabase(email,type,uSername)
            database.child(uSername).setValue(User).addOnFailureListener {
                Toast.makeText(this,"failed to reach database",Toast.LENGTH_SHORT).show()
            }
            startActivity(Intent(this,HomeActivity::class.java ))
            finish()



        }
    }
    private fun validateData() {
        email = binding.EmailText.text.toString()
        password = binding.passwordText.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.EmailText.error= "Invalid email format"
        }
        else if(TextUtils.isEmpty(password)) {
            binding.passwordText.error = "Enter Password"
        }
        else {
            firebaseLogin()
        }

        }
    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser= firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"Logged in as $email ",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,HomeActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this,"${e.message}",Toast.LENGTH_SHORT).show()

            }
    }
    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            startActivity(Intent(this,HomeActivity::class.java))
            finish()


            }
        }
    }
