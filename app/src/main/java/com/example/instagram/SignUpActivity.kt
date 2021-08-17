package com.example.instagram

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignUpActivity : AppCompatActivity() {
    private lateinit var fullNameSignUp : EditText
    private lateinit var usernameSignUp : EditText
    private lateinit var passwordSignUp : EditText
    private lateinit var emailSignUp : EditText
    private lateinit var backToLoginBtn: ImageView
    private lateinit var btnRegister: Button
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        btnRegister=findViewById(R.id.btnRegister)
        fullNameSignUp=findViewById(R.id.fullNameSignUp)
        usernameSignUp=findViewById(R.id.usernameSignUp)
        passwordSignUp=findViewById(R.id.passwordSignUp)
        emailSignUp=findViewById(R.id.emailSignUp)
        auth = Firebase.auth
        backToLoginBtn=findViewById(R.id.backToLoginBtn)
        backToLoginBtn.setOnClickListener {
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }
        btnRegister.setOnClickListener {
          createAccount()
        }
    }

    private fun createAccount() {
        val fullname = fullNameSignUp.text.toString()
        val  userName= usernameSignUp.text.toString()
        val  email = emailSignUp.text.toString()
        val  password = passwordSignUp.text.toString()
    }
}