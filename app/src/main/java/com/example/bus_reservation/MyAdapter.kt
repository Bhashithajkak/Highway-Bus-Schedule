package com.example.bus_reservation

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bus_reservation.databinding.RecyclerItem1Binding

class MyAdapter(private val context: Context, private val dataList: MutableList<DataClass>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecyclerItem1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]

        Glide.with(context).load(currentItem.dataImage).into(holder.binding.recImage1)
        holder.binding.recTitle.text = currentItem.dataTitle


        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("Image", currentItem.dataImage)
                putExtra("Description", currentItem.dataDesc)
                putExtra("Title", currentItem.dataTitle)
                putExtra("Key", currentItem.key)
                putExtra("Language", currentItem.dataLang)
                putExtra("DesTime", currentItem.dataDescTime)
                putExtra("arrTime", currentItem.dataLangTime)
                putExtra("Price", currentItem.dataPrice)
                putExtra("availability",currentItem.dataAvilability)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun searchDataList(searchList: ArrayList<DataClass>) {

        dataList.clear()
        dataList.addAll(searchList)
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: RecyclerItem1Binding) : RecyclerView.ViewHolder(binding.root)
}
