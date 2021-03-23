package com.example.interviewtaskgrootan.dev

import android.content.Context
import android.graphics.Color
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.interviewtaskgrootan.R
import kotlin.collections.ArrayList

class Devadapter(): RecyclerView.Adapter<Devadapter.Myholder>() {

    var box: ArrayList<String>? = null
    var mContext: Context? = null
    var checked: SparseBooleanArray? = null
    var count = 0
    var clickables : clickable?=null
    var checkedArray:ArrayList<Int>?=null
    var startshuff:Boolean?=null
    constructor(devActivity: DevActivity?, box: ArrayList<String>?, startshuffs:Boolean,clickable: clickable):this() {
        this.box = box
        this.mContext = devActivity
        this.clickables = clickable
        this.startshuff = startshuffs
        checked = SparseBooleanArray()
        checkedArray = ArrayList()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.dev_adapter_view, parent, false)
        return Myholder(v)
    }

    override fun getItemCount(): Int {
        return box!!.size
    }

    override fun onBindViewHolder(holder: Myholder, position: Int) {
        val ab = box!![position]
        holder.name.setBackgroundColor(Color.parseColor(ab))
        holder.checkBox3.isChecked = checked!![position, false]
        if (startshuff!!) {
        holder.checkBox3.setOnClickListener {
            if (!checked!![position, false]) {
                holder.checkBox3.isChecked = true
                checked!!.put(position, true)
                count += 1
                checkedArray?.add(position)
            } else {
                holder.checkBox3.isChecked = false
                checked!!.put(position, false)
                count -= 1
                checkedArray?.remove(position)
            }

            if (count == 2) {
                clickables?.swapMethod(checkedArray)
                checkedArray?.clear()
            }
        }
    }else{
            holder.checkBox3.visibility=View.GONE
        }
    }

    fun filter(box: ArrayList<String>) {
        this.box = box
        notifyDataSetChanged()
    }

    class Myholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var checkBox3: CheckBox

        init {
            name = itemView.findViewById<View>(R.id.name) as TextView
            checkBox3 = itemView.findViewById<View>(R.id.checkBox3) as CheckBox
        }
    }

    interface clickable {
        fun swapMethod(checkedArray: ArrayList<Int>?)
    }
}