package com.example.bus_reservation

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bus_reservation.databinding.RecyclerItemBinding

class MyAdapter1(private val context: Context, private val dataList: MutableList<DataClass>) :
    RecyclerView.Adapter<MyAdapter1.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]

        Glide.with(context).load(currentItem.dataImage).into(holder.binding.recImage)
        holder.binding.recTitle.text = currentItem.dataTitle
        holder.binding.recDesc.text = currentItem.dataDesc
        holder.binding.recLang.text = currentItem.dataLang
        holder.binding.recDescTime.text = currentItem.dataDescTime
        holder.binding.recLangTime.text = currentItem.dataLangTime
        holder.binding.recPrice.text = currentItem.dataPrice

        holder.itemView.setOnClickListener(null)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun searchDataList(searchList: ArrayList<DataClass>) {

        dataList.clear()
        dataList.addAll(searchList)
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root)
}
