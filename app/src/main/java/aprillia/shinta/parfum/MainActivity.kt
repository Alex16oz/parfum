package aprillia.shinta.parfum

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListAdapter
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase
import aprillia.shinta.parfum.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var db: FirebaseDatabase
    private lateinit var adapter: ListAdapter
    private var alPengunjung = ArrayList<HashMap<String, Any>>()
    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // harus dipanggil sebelum getInstance()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        db = FirebaseDatabase.getInstance()

        b.btnInsert.setOnClickListener(this)
        b.btnUpdate.setOnClickListener(this)
        b.btnDelete.setOnClickListener(this)

        b.lvPengunjung.setOnItemClickListener(itemClickListener)
    }

    override fun onStart() {
        super.onStart()
        showData()
    }

    private fun showData() {
        db.getReference("Pengunjung").addValueEventListener(object :
            com.google.firebase.database.ValueEventListener {
            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                alPengunjung.clear()
                for (ds in snapshot.children) {
                    val hms = HashMap<String, Any>()
                    hms["id"] = ds.child("id").value ?: ""
                    hms["nama"] = ds.child("nama").value ?: ""
                    hms["noHp"] = ds.child("noHp").value ?: ""
                    hms["alamat"] = ds.child("alamat").value ?: ""
                    hms["merek"] = ds.child("merek").value ?: ""
                    hms["ukuran"] = ds.child("ukuran").value ?: ""
                    hms["jumlah"] = ds.child("jumlah").value ?: ""
                    alPengunjung.add(hms)
                }

                adapter = SimpleAdapter(
                    this@MainActivity, alPengunjung,
                    R.layout.row_pengunjung,
                    arrayOf("nama", "noHp", "alamat", "merek", "ukuran", "jumlah"),
                    intArrayOf(
                        R.id.txtNama,
                        R.id.txtNoHp,
                        R.id.txtAlamat,
                        R.id.txtMerek,
                        R.id.txtUkuran,
                        R.id.txtJumlah
                    )
                )
                b.lvPengunjung.adapter = adapter
            }

            override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                Toast.makeText(
                    applicationContext,
                    "Connection error: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnInsert -> {
                val id = b.edId.text.toString()
                val data = Pengunjung(
                    id,
                    b.edNama.text.toString(),
                    b.edNoHp.text.toString(),
                    b.edAlamat.text.toString(),
                    b.edMerek.text.toString(),
                    b.edUkuran.text.toString(),
                    b.edJumlah.text.toString()
                )
                db.getReference("Pengunjung").child(id).setValue(data)
            }

            R.id.btnDelete -> {
                db.getReference("Pengunjung").child(b.edId.text.toString()).removeValue()
            }

            R.id.btnUpdate -> {
                val id = b.edId.text.toString()
                val ref = db.getReference("Pengunjung").child(id)
                ref.child("nama").setValue(b.edNama.text.toString())
                ref.child("noHp").setValue(b.edNoHp.text.toString())
                ref.child("alamat").setValue(b.edAlamat.text.toString())
                ref.child("merek").setValue(b.edMerek.text.toString())
                ref.child("ukuran").setValue(b.edUkuran.text.toString())
                ref.child("jumlah").setValue(b.edJumlah.text.toString())
            }
        }
    }

    private val itemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
        val hms = alPengunjung[i]
        b.edId.setText(hms["id"].toString())
        b.edNama.setText(hms["nama"].toString())
        b.edNoHp.setText(hms["noHp"].toString())
        b.edAlamat.setText(hms["alamat"].toString())
        b.edMerek.setText(hms["merek"].toString())
        b.edUkuran.setText(hms["ukuran"].toString())
        b.edJumlah.setText(hms["jumlah"].toString())
    }
}