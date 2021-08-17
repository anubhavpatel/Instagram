package com.example.instagram

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


class SignUpActivity : AppCompatActivity() {
    private lateinit var fullNameSignUp : EditText
    private lateinit var usernameSignUp : EditText
    private lateinit var passwordSignUp : EditText
    private lateinit var emailSignUp : EditText
    private lateinit var backToLoginBtn: ImageView
    private lateinit var btnRegister: Button
    private lateinit var auth: FirebaseAuth
    lateinit var storage: FirebaseStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        btnRegister=findViewById(R.id.btnRegister)
        fullNameSignUp=findViewById(R.id.fullNameSignUp)
        usernameSignUp=findViewById(R.id.usernameSignUp)
        passwordSignUp=findViewById(R.id.passwordSignUp)
        emailSignUp=findViewById(R.id.emailSignUp)
        auth = Firebase.auth
        storage = Firebase.storage
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
        val fullName = fullNameSignUp.text.toString()
        val  userName= usernameSignUp.text.toString()
        val  email = emailSignUp.text.toString()
        val  password = passwordSignUp.text.toString()
        when{
            TextUtils.isEmpty(fullName) -> Toast.makeText(this,"full name is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(userName) -> Toast.makeText(this,"user name is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(email) -> Toast.makeText(this,"email is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this,"password name is required", Toast.LENGTH_LONG).show()
         else -> {
             val progressDialog = ProgressDialog(this)
             progressDialog.setTitle("Sign Up")
             progressDialog.setMessage("Please wait,this may take a while")
             progressDialog.setCanceledOnTouchOutside(false)
             progressDialog.show()
             auth.createUserWithEmailAndPassword(email, password)
                 .addOnCompleteListener(this) { task ->
                     if (task.isSuccessful) {
                         // Sign in success, update UI with the signed-in user's information
                         Toast.makeText(baseContext, "Authentication success.",
                             Toast.LENGTH_SHORT).show()
                         val user = auth.currentUser
                         saveInfo(fullName, userName, email, password)
                         progressDialog.dismiss()

                     } else {
                         // If sign in fails, display a message to the user.
                         Toast.makeText(baseContext, " failed.",
                             Toast.LENGTH_SHORT).show()
                         progressDialog.dismiss()
                     }
                 }
                 }
         }
        }

    private fun saveInfo(fullName: String, userName: String, email: String, password: String) {
         val currentUseID= FirebaseAuth.getInstance().currentUser!!.uid
        val userRef : DatabaseReference=FirebaseDatabase.getInstance().reference.child("Users")
        val userMap = HashMap<String,Any>()
        userMap["uid"] = currentUseID
        userMap["fullName"] = fullName
        userMap["userName"] = userName
        userMap["email"] = email
        userMap["password"] = password
        userMap["bio"] = "hello i am anubhav patel"
        userMap["image"] = "gs://instagram-9c07a.appspot.com/Default images/IMG_20210607_173325.jpg"
        userRef.child(currentUseID).setValue(userMap)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(baseContext, "Account created successfully",Toast.LENGTH_LONG).show()
                    val intent = Intent(this,MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
                else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
    }

    private fun reload() {

    }
    }
