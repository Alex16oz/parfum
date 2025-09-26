package aprillia.shinta.parfum

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        auth = FirebaseAuth.getInstance()

        // tombol di layout
        val btnPemesanan: Button = findViewById(R.id.btnPemesanan)
        val btnDisplayParfum: Button = findViewById(R.id.btnDisplayParfum)
        val btnLaporan: Button = findViewById(R.id.btnLaporan)
        val btnLogout: Button = findViewById(R.id.btnLogout)

        // buka form pemesanan (MainActivity yang sudah ada)
        btnPemesanan.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        // buka display parfum (lihat pemesanan)
        btnDisplayParfum.setOnClickListener {
            startActivity(Intent(this, DisplayParfumActivity::class.java))
        }

        // buka laporan (nanti bisa diarahkan ke activity laporan)
        btnLaporan.setOnClickListener {
            // sementara kosong, bisa tambahkan intent kalau sudah buat LaporanActivity
            // startActivity(Intent(this, LaporanActivity::class.java))
        }

        btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}