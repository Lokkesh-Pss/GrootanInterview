package com.example.interviewtaskgrootan.dev

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.interviewtaskgrootan.MainActivity
import com.example.interviewtaskgrootan.MainActivity.Companion.mGoogleSignInClient
import com.example.interviewtaskgrootan.MainActivity.Companion.name
import com.example.interviewtaskgrootan.databinding.ActivityDevBinding
import java.util.*
import kotlin.collections.ArrayList


class DevActivity : AppCompatActivity() {

    var activityDevBinding: ActivityDevBinding? = null
    var box = ArrayList<String>()
    var devadapter: Devadapter? = null
    var sharedPreferences: SharedPreferences?=null
    var cTimer: CountDownTimer? = null
    var startshuff:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDevBinding = ActivityDevBinding.inflate(layoutInflater)
        setContentView(activityDevBinding!!.root)
        activityDevBinding?.name?.text=intent.getStringExtra(name)
        sharedPreferences = getSharedPreferences(MainActivity.MyPreference, Context.MODE_PRIVATE)
        box.add("#FB3C00")
        box.add("#FB3C00")
        box.add("#FB3C00")
        box.add("#FF11FB00")
        box.add("#FF11FB00")
        box.add("#FF11FB00")
        box.add("#2196F3")
        box.add("#2196F3")
        box.add("#2196F3")

        rvMethod(box)
        activityDevBinding?.devshuffel?.setOnClickListener({ shuffel() })
        activityDevBinding?.signout?.setOnClickListener { signOut() }
    }
    private fun signOut() {
        mGoogleSignInClient!!.signOut()
            .addOnCompleteListener(this) {
                val editor = sharedPreferences?.edit()
                editor?.remove(MainActivity.SELECTED_KEY)
                editor?.apply()
                val intent=Intent(this,
                    MainActivity::class.java)
                startActivity(intent)
                finish()
            }
    }
    private fun shuffel() {
        startshuff=true
        Collections.shuffle(box)
        rvMethod(box)
        cancelTimer()
        startTimer()
    }

    private fun rvMethod(rvbox: ArrayList<String>) {
        activityDevBinding?.devRV?.setLayoutManager(GridLayoutManager(applicationContext, 3))
        devadapter = Devadapter(
            this,
            rvbox,
                startshuff,
            object :
                Devadapter.clickable {
                override fun swapMethod(checkedArray: ArrayList<Int>?) {
                    val position1 = checkedArray?.get(0)
                    val position2 = checkedArray?.get(1)
                    swapPosition(position1, position2)
                }
            })
        activityDevBinding?.devRV?.setAdapter(devadapter)
    }

    private fun swapPosition(position1: Int?, position2: Int?) {
        Collections.swap(box,position1?:0,position2?:1)
        rvMethod(box)
        devadapter?.filter(box)
    }

    fun startTimer() {
        cTimer = object : CountDownTimer(31000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val ab=millisUntilFinished / 1000
               activityDevBinding?.timer?.text=ab.toString()
            }
            override fun onFinish() {
                shuffel()
            }
        }
        cTimer?.start()
    }

    fun cancelTimer() {
        if (cTimer != null) cTimer!!.cancel()
        activityDevBinding?.timer?.text="0"
    }

}