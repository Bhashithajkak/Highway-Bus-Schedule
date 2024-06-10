import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bus_reservation.BookingClass
import com.example.bus_reservation.MyAdpter3
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class UserDatsListActivity : AppCompatActivity() {

 /*   private lateinit var fab: FloatingActionButton
    private lateinit var databaseReference: DatabaseReference
    private lateinit var eventListener: ValueEventListener
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<BookingClass>
    private lateinit var adapter: MyAdpter3
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_booking_data)

        recyclerView = findViewById(R.id.recyclerView1)

        val gridLayoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = gridLayoutManager

        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        dataList = ArrayList()

        adapter = MyAdpter3(this, dataList)
        recyclerView.adapter = adapter

        // Retrieve the user ID from the intent
        val userId = intent.getStringExtra("userId")

        // Check if userId is not null before proceeding
        if (userId != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("booking details")
                .child("users")
                .child(userId)

            dialog.show()
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
                    dialog.dismiss()
                }

                override fun onCancelled(error: DatabaseError) {
                    dialog.dismiss()
                }
            })
        } else {
            Log.e("AllBookingData", "User ID not provided.")
            // Handle the case where the user ID is not provided.
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        databaseReference.removeEventListener(eventListener)
    }

  */
}
