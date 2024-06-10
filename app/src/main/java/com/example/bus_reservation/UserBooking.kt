package com.example.bus_reservation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bus_reservation.BookingClass
import com.example.bus_reservation.MyAdpter3
import com.example.bus_reservation.databinding.ActivityAllBookingDataBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserBooking : AppCompatActivity() {

    private lateinit var binding: ActivityAllBookingDataBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var eventListener: ValueEventListener
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<BookingClass>
    private lateinit var adapter: MyAdpter3
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllBookingDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView1

        val gridLayoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = gridLayoutManager



        dataList = ArrayList()
        adapter = MyAdpter3(this, dataList)
        recyclerView.adapter = adapter

        // Retrieve the current user ID from Firebase Authentication
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            userId = currentUser.uid

            databaseReference = FirebaseDatabase.getInstance().getReference("booking details")
                .child("users")
                .child(userId)


            eventListener = databaseReference.addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    dataList.clear()

                    for (bookingSnapshot in snapshot.children) {
                        val entry = bookingSnapshot.getValue(BookingClass::class.java)
                        entry?.let {
                            dataList.add(it)
                        }
                    }
                    Log.e("Datas", dataList.toString())
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        } else {
            Log.e("UserBooking", "User not authenticated.")
            // Handle the case where the user is not authenticated.
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        databaseReference.removeEventListener(eventListener)
    }
}
