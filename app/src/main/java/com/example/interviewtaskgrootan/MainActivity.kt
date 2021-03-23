package com.example.interviewtaskgrootan

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.interviewtaskgrootan.databinding.ActivityMainBinding
import com.example.interviewtaskgrootan.dev.DevActivity
import com.example.interviewtaskgrootan.prod.ProdActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class MainActivity : AppCompatActivity() {
    companion object{
        var mGoogleSignInClient:GoogleSignInClient?=null
        var name="NAME"
        var MyPreference="MyPreference"
        var SELECTED_KEY="SELECTED_KEY"
    }
    var selected=""
    var logincheck:String?=null
    val RC_SIGN_IN:Int=100
    var mainBinding:ActivityMainBinding?=null
    var sharedPreferences:SharedPreferences?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding?.root)

        sharedPreferences = getSharedPreferences(MyPreference, Context.MODE_PRIVATE)
        logincheck= sharedPreferences?.getString(SELECTED_KEY, "na")

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        mainBinding?.signInButton?.setSize(SignInButton.SIZE_STANDARD)
        mainBinding?.signInButton?.setOnClickListener {
            signIn()
        }
        mainBinding?.radiogroup?.setOnCheckedChangeListener{ group, checkedId ->
            if (checkedId==R.id.devradio){
                selected="dev"
            }else if (checkedId==R.id.prodradio){
                selected="prod"
            }else{
                selected="na"
            }

            val editor = sharedPreferences?.edit()
            editor?.putString(SELECTED_KEY, selected)
            editor?.apply()
        }
    }

    private fun signIn() {
        if (selected.equals("na")||selected.isEmpty()){
            Toast.makeText(applicationContext,"Please select the above option ",Toast.LENGTH_SHORT).show()
        }else{
            val signInIntent = mGoogleSignInClient!!.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>?) {
        try {
            val account: GoogleSignInAccount? = task?.getResult(ApiException::class.java)
            if (account != null) {
                logincheck= sharedPreferences?.getString(SELECTED_KEY, "na")
                if (logincheck.equals("dev")){
                    val intent=Intent(this,
                        DevActivity::class.java)
                    intent.putExtra(name,account.displayName)
                    startActivity(intent)
                    finish()
                }else{
                    val intent=Intent(this,
                        ProdActivity::class.java)
                    intent.putExtra(name,account.displayName)
                    startActivity(intent)
                    finish()
                }

            }
        } catch (e: ApiException) {
            Log.w("FragmentActivity.TAG", "signInResult:failed code=" + e.statusCode)

        }

    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(application)
        if (account != null) {
            logincheck= sharedPreferences?.getString(SELECTED_KEY, "na")
            if (logincheck.equals("dev")){
                val intent=Intent(this,
                    DevActivity::class.java)
                intent.putExtra(name,account.displayName)
                startActivity(intent)
                finish()
            }else{
                val intent=Intent(this,
                    ProdActivity::class.java)
                intent.putExtra(name,account.displayName)
                startActivity(intent)
                finish()
            }
        }

    }
}