package com.example.bus_reservation



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AlluserViewpage : AppCompatActivity() {
    val items = arrayOf("Hambantota","Barawakubuka","Beliaththa","Mathar","Mirissa","Galle")
    lateinit var selectStartFrom: AutoCompleteTextView
    lateinit var selectDestination: AutoCompleteTextView
    lateinit var adapterItems : ArrayAdapter<String>

    private lateinit var fab: FloatingActionButton
    private lateinit var databaseReference: DatabaseReference
    private lateinit var eventListener: ValueEventListener
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: MutableList<DataClass>
    private lateinit var adapter: MyAdapter1
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_alluser_viewpage)

        selectStartFrom = findViewById(R.id.select_start_from)
        selectDestination = findViewById(R.id.select_destination)
        adapterItems =  ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,items)
        selectStartFrom.setAdapter(adapterItems)
        selectDestination.setAdapter(adapterItems)
        recyclerView = findViewById(R.id.recyclerView)


        val gotoLogginButton : Button = findViewById(R.id.goToLogin)
        gotoLogginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        var startFrom : String?=null
        var destination :String?=null

        selectStartFrom.setOnItemClickListener { _, _, position, _ ->
            startFrom = adapterItems.getItem(position).toString()
        }
        selectDestination.setOnItemClickListener { _, _, position, _ ->
            destination = adapterItems.getItem(position).toString()
        }

        val searchButton: Button = findViewById(R.id.searchButton)
        searchButton.setOnClickListener {
            searchList(startFrom, destination)
        }
        val refreshButton : Button = findViewById(R.id.refreshButton)
        refreshButton.setOnClickListener{
            selectStartFrom.clearListSelection()
            selectStartFrom.text.clear()
            selectDestination.clearListSelection()
            selectDestination.text.clear()
            searchList(null,null)
        }

        retreiwAllData()

    }

    private fun searchList(start: String?, destination: String?) {
        val searchList = ArrayList<DataClass>()
        if(start!=null&&destination!=null) {
            for (dataClass in dataList) {

                val startMatch = dataClass.dataDesc.toLowerCase().contains(start.toLowerCase())
                val destinationMatch = dataClass.dataLang.toLowerCase().contains(destination.toLowerCase())

                if (startMatch && destinationMatch) {
                    searchList.add(dataClass)
                }
            }


        } else {
            retreiwAllData()
        }
        adapter.searchDataList(searchList)
    }
    private fun retreiwAllData() {
        val gridLayoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = gridLayoutManager

        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        dataList = ArrayList()

        adapter = MyAdapter1(this, dataList)
        recyclerView.adapter = adapter

        databaseReference = FirebaseDatabase.getInstance().getReference("Android Tutorials")
        dialog.show()
        eventListener = databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(DataClass::class.java)
                    dataClass?.let {
                        it.key = itemSnapshot.key
                        dataList.add(it)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }
        })}
}