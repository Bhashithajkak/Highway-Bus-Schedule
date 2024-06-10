package com.example.bus_reservation

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdpter3(private val context: Context, private val dataList: ArrayList<BookingClass>) :
    RecyclerView.Adapter<MyAdpter3.UserDataViewHolder>() {

    

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDataViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_user_data, parent, false)
        return UserDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserDataViewHolder, position: Int) {
        val entry = dataList[position]

        Log.e("Name", entry.getDataName())
        Log.e("DataListSize", dataList.size.toString())
        holder.textViewName.text = entry.getDataName()
        holder.textViewEmail.text = entry.getDataEmail()
        holder.textViewMobileNumber.text=entry.getDataPhoneNumber()
        holder.textViewFrom.text=entry.getDataFrom()
        holder.textViewTo.text=entry.getDataTo()
        holder.textViewBusName.text=entry.getDataBusName()
        // Set other TextViews for additional fields
    }
    
    override fun getItemCount(): Int {
        return dataList.size
    }



    class UserDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewEmail: TextView = itemView.findViewById(R.id.textViewEmail)
        val textViewMobileNumber: TextView = itemView.findViewById(R.id.textViewMobileNumber)
        val textViewFrom: TextView = itemView.findViewById(R.id.textViewFrom)
        val textViewTo: TextView = itemView.findViewById(R.id.textViewTo)
        val textViewBusName: TextView = itemView.findViewById(R.id.textViewBusName)
        // Add other TextViews for additional fields
    }
}
