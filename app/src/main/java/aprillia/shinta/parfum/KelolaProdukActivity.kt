package aprillia.shinta.parfum

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class KelolaProdukActivity : AppCompatActivity() {

    private lateinit var etNamaProduk: EditText
    private lateinit var etDeskripsiProduk: EditText
    private lateinit var tvHargaLabel: TextView
    private lateinit var seekBarHarga: SeekBar
    private lateinit var etStokMl: EditText
    private lateinit var imgPreview: ImageView
    private lateinit var btnUploadFoto: Button
    private lateinit var btnSimpanProduk: Button
    private lateinit var rvProduk: RecyclerView

    private val produkList = mutableListOf<Produk>()
    private lateinit var adapter: ProdukAdapter

    private var fotoUriString: String? = null
    private var editIndex: Int = -1 // -1 = tambah baru, >=0 = edit index

    // modern image picker
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            fotoUriString = uri.toString()
            imgPreview.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kelola_produk)

        // view binding manual
        etNamaProduk = findViewById(R.id.etNamaProduk)
        etDeskripsiProduk = findViewById(R.id.etDeskripsiProduk)
        tvHargaLabel = findViewById(R.id.tvHargaLabel)
        seekBarHarga = findViewById(R.id.seekBarHarga)
        etStokMl = findViewById(R.id.etStokMl)
        imgPreview = findViewById(R.id.imgPreview)
        btnUploadFoto = findViewById(R.id.btnUploadFoto)
        btnSimpanProduk = findViewById(R.id.btnSimpanProduk)
        rvProduk = findViewById(R.id.rvProduk)

        // setup seekbar (mis: 0..1000 steps, harga = step * 1000)
        seekBarHarga.max = 1000
        seekBarHarga.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                val harga = progress * 1000
                tvHargaLabel.text = "Harga: Rp $harga"
            }
            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })

        // RecyclerView + adapter
        adapter = ProdukAdapter(produkList,
            onEdit = { produk, idx -> masukEditMode(idx) },
            onDelete = { produk, idx -> konfirmasiHapus(idx) }
        )
        rvProduk.layoutManager = LinearLayoutManager(this)
        rvProduk.adapter = adapter

        // upload foto
        btnUploadFoto.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        // simpan / update
        btnSimpanProduk.setOnClickListener {
            simpanAtauUpdateProduk()
        }
    }

    private fun simpanAtauUpdateProduk() {
        val nama = etNamaProduk.text.toString().trim()
        val deskripsi = etDeskripsiProduk.text.toString().trim()
        val harga = seekBarHarga.progress * 1000
        val stok = etStokMl.text.toString().toIntOrNull() ?: 0

        if (nama.isEmpty() || deskripsi.isEmpty() || stok <= 0) {
            Toast.makeText(this, "Isi semua field dengan benar (stok > 0)", Toast.LENGTH_SHORT).show()
            return
        }

        if (editIndex >= 0) {
            // update existing
            val existing = produkList[editIndex]
            existing.nama = nama
            existing.deskripsi = deskripsi
            existing.harga = harga
            existing.stok = stok
            existing.fotoUri = fotoUriString
            adapter.notifyItemChanged(editIndex)
            editIndex = -1
            Toast.makeText(this, "Produk diperbarui", Toast.LENGTH_SHORT).show()
        } else {
            // add new
            val id = if (produkList.isEmpty()) 1L else (produkList.maxOf { it.id } + 1L)
            val produk = Produk(id, nama, deskripsi, harga, stok, fotoUriString)
            produkList.add(produk)
            adapter.notifyItemInserted(produkList.size - 1)
            Toast.makeText(this, "Produk ditambahkan", Toast.LENGTH_SHORT).show()
        }

        clearForm()
    }

    private fun masukEditMode(index: Int) {
        if (index < 0 || index >= produkList.size) return
        val p = produkList[index]
        etNamaProduk.setText(p.nama)
        etDeskripsiProduk.setText(p.deskripsi)
        seekBarHarga.progress = p.harga / 1000
        etStokMl.setText(p.stok.toString())
        fotoUriString = p.fotoUri
        if (!fotoUriString.isNullOrEmpty()) imgPreview.setImageURI(Uri.parse(fotoUriString))
        else imgPreview.setImageDrawable(null)
        editIndex = index
    }

    private fun konfirmasiHapus(index: Int) {
        if (index < 0 || index >= produkList.size) return
        AlertDialog.Builder(this)
            .setTitle("Hapus produk")
            .setMessage("Yakin ingin menghapus produk ini?")
            .setPositiveButton("Hapus") { _, _ ->
                produkList.removeAt(index)
                adapter.notifyItemRemoved(index)
                Toast.makeText(this, "Produk dihapus", Toast.LENGTH_SHORT).show()
                // jika sedang edit index yang lebih besar, sesuaikan editIndex
                if (editIndex == index) {
                    clearForm()
                    editIndex = -1
                } else if (editIndex > index) {
                    editIndex--
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun clearForm() {
        etNamaProduk.text.clear()
        etDeskripsiProduk.text.clear()
        seekBarHarga.progress = 0
        tvHargaLabel.text = "Harga: Rp 0"
        etStokMl.text.clear()
        imgPreview.setImageDrawable(null)
        fotoUriString = null
        editIndex = -1
    }
}
