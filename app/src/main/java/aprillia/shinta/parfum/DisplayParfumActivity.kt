package aprillia.shinta.parfum

import android.os.Bundle
import android.widget.ListAdapter
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import aprillia.shinta.parfum.databinding.ActivityDisplayParfumBinding

class DisplayParfumActivity : AppCompatActivity() {

    private lateinit var db: FirebaseDatabase
    private lateinit var adapter: ListAdapter
    private var alParfum = ArrayList<HashMap<String, Any>>()
    private lateinit var b: ActivityDisplayParfumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDisplayParfumBinding.inflate(layoutInflater)
        setContentView(b.root)

        // inisialisasi firebase database
        db = FirebaseDatabase.getInstance()

        // tampilkan data parfum
        showData()
    }

    private fun showData() {
        db.getReference("Pengunjung").addValueEventListener(object :
            com.google.firebase.database.ValueEventListener {
            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                alParfum.clear()

                for (ds in snapshot.children) {
                    val hms = HashMap<String, Any>()
                    hms["nama"] = ds.child("nama").value ?: ""
                    hms["merek"] = ds.child("merek").value ?: ""
                    hms["ukuran"] = ds.child("ukuran").value ?: ""
                    hms["jumlah"] = ds.child("jumlah").value ?: ""
                    alParfum.add(hms)
                }

                // set adapter ke ListView
                adapter = SimpleAdapter(
                    this@DisplayParfumActivity,
                    alParfum,
                    R.layout.row_parfum,
                    arrayOf("nama", "merek", "ukuran", "jumlah"),
                    intArrayOf(
                        R.id.txtNama,
                        R.id.txtMerek,
                        R.id.txtUkuran,
                        R.id.txtJumlah
                    )
                )
                b.lvParfum.adapter = adapter
            }

            override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                Toast.makeText(
                    this@DisplayParfumActivity,
                    "Connection error: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
