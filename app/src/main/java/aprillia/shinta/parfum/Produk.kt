package aprillia.shinta.parfum

data class Produk(
    var id: Long = 0L,
    var nama: String = "",
    var deskripsi: String = "",
    var harga: Int = 0,      // dalam rupiah
    var stok: Int = 0,       // dalam ml
    var fotoUri: String? = null
)
