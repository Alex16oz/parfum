package aprillia.shinta.parfum

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // tombol di layout
        val btnPemesanan: Button = findViewById(R.id.btnPemesanan)
        val btnDisplayParfum: Button = findViewById(R.id.btnDisplayParfum)
        val btnLaporan: Button = findViewById(R.id.btnLaporan)

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
    }
}