package com.example.interviewtaskgrootan.prod

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.interviewtaskgrootan.MainActivity
import com.example.interviewtaskgrootan.R
import com.example.interviewtaskgrootan.databinding.ActivityProdBinding
import java.util.*
import kotlin.collections.ArrayList

class ProdActivity : AppCompatActivity() {

    var activityProdBinding:ActivityProdBinding?=null
    var box = ArrayList<String>()
    var prodadpater: Prodadpater? = null
    var cTimer: CountDownTimer? = null
    var sharedPreferences: SharedPreferences?=null
    var prodstartshuff:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prod)
        activityProdBinding= ActivityProdBinding.inflate(layoutInflater)
        setContentView(activityProdBinding!!.root)
        activityProdBinding?.name?.text=intent.getStringExtra(MainActivity.name)
        sharedPreferences = getSharedPreferences(MainActivity.MyPreference, Context.MODE_PRIVATE)
        box.add("#FB3C00")
        box.add("#FB3C00")
        box.add("#FB3C00")
        box.add("#FB3C00")
        box.add("#FF11FB00")
        box.add("#FF11FB00")
        box.add("#FF11FB00")
        box.add("#FF11FB00")
        box.add("#2196F3")
        box.add("#2196F3")
        box.add("#2196F3")
        box.add("#2196F3")
        box.add("#FFEB3B")
        box.add("#FFEB3B")
        box.add("#FFEB3B")
        box.add("#FFEB3B")

        rvMethod(box)

        activityProdBinding?.prodshuffel?.setOnClickListener({ shuffel() })
        activityProdBinding?.signout?.setOnClickListener { signOut() }
    }
    private fun signOut() {
        MainActivity.mGoogleSignInClient!!.signOut()
            .addOnCompleteListener(this) {
                val editor = sharedPreferences?.edit()
                editor?.remove(MainActivity.SELECTED_KEY)
                editor?.apply()
                val intent= Intent(this,
                    MainActivity::class.java)
                startActivity(intent)
                finish()
            }
    }

    private fun shuffel() {
        prodstartshuff=true
        Collections.shuffle(box)
        rvMethod(box)
        cancelTimer()
        startTimer()
    }

    private fun rvMethod(rvbox: ArrayList<String>) {
        activityProdBinding?.prodRV?.setLayoutManager(GridLayoutManager(applicationContext, 4))
        prodadpater = Prodadpater(this, rvbox,prodstartshuff, object : Prodadpater.clickable {
            override fun swapMethod(checkedArray: ArrayList<Int>?) {
                val position1 = checkedArray?.get(0)
                val position2 = checkedArray?.get(1)
                swapPosition(position1, position2)
            }

        })
        activityProdBinding?.prodRV?.setAdapter(prodadpater)
    }
    private fun swapPosition(position1: Int?, position2: Int?) {
        Collections.swap(box,position1?:0,position2?:1)
        rvMethod(box)
        prodadpater?.filter(box)
        cancelTimer()
        startTimer()
    }

    fun startTimer() {
        cTimer = object : CountDownTimer(61000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val ab=millisUntilFinished / 1000
                activityProdBinding?.prodtimer?.text=ab.toString()

            }
            override fun onFinish() {
                shuffel()
            }
        }
        cTimer?.start()
    }

    fun cancelTimer() {
        if (cTimer != null) cTimer!!.cancel()
        activityProdBinding?.prodtimer?.text="0"
    }
}