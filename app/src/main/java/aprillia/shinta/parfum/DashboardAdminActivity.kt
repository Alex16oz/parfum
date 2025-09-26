package aprillia.shinta.parfum

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DashboardAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_admin)

        val btnKelolaProduk: Button = findViewById(R.id.btnKelolaProduk)
        val btnKelolaPemesanan: Button = findViewById(R.id.btnKelolaPemesanan)
        val btnLaporanPenjualan: Button = findViewById(R.id.btnLaporanPenjualan)
        val btnLogout: Button = findViewById(R.id.btnLogout)

        // klik kelola produk
        btnKelolaProduk.setOnClickListener {
            val intent = Intent(this, KelolaProdukActivity::class.java)
            startActivity(intent)
        }

        // klik kelola pemesanan
        btnKelolaPemesanan.setOnClickListener {
            // TODO: startActivity(Intent(this, KelolaPemesananActivity::class.java))
        }

        // klik laporan penjualan
        btnLaporanPenjualan.setOnClickListener {
            // TODO: startActivity(Intent(this, LaporanPenjualanActivity::class.java))
        }

        // logout kembali ke dashboard (user)
        btnLogout.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
