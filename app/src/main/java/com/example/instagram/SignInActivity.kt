package com.example.instagram

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth


class SignInActivity : AppCompatActivity() {

    private lateinit var signUp : TextView
    private lateinit var googleSignIn: Button
    private lateinit var btnLogin : Button
    private lateinit var emailSignIn: EditText
    private lateinit var passwordSignIn: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        var mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        signUp=findViewById(R.id.signUp)
        emailSignIn=findViewById(R.id.emailSignIn)
        passwordSignIn=findViewById(R.id.passwordSignIn)
        googleSignIn=findViewById(R.id.googleSignIn)
        btnLogin=findViewById(R.id.btnLogin)
         auth = FirebaseAuth.getInstance()

        signUp.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)

        }
        btnLogin.setOnClickListener {
            loginUser()
        }
        googleSignIn.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, Companion.RC_SIGN_IN)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            googleSignIn.visibility = View.GONE
        }
    }
    private fun loginUser() {
        val  email = emailSignIn.text.toString()
        val  password = passwordSignIn.text.toString()
        when{
            TextUtils.isEmpty(email) -> Toast.makeText(this,"email is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this,"password name is required", Toast.LENGTH_LONG).show()
            else -> {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Sign Up")
                progressDialog.setMessage("Please wait,this may take a while")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(baseContext, " Login successfully",Toast.LENGTH_SHORT).show()
                            val intent = Intent(this,MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                            progressDialog.dismiss()
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(baseContext, "incorrect data",Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                        }
                    }
            }
        }
    }


    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
    // [END on_start_check_user]
    companion object {
        const val RC_SIGN_IN=123
    }
}