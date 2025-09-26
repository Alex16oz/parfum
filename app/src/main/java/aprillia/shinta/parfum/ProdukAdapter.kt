package aprillia.shinta.parfum

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

    class ProdukAdapter(
    private val produkList: List<Produk>,
    private val onEdit: (Produk, Int) -> Unit,
    private val onDelete: (Produk, Int) -> Unit
    ) : RecyclerView.Adapter<ProdukAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduk: ImageView = itemView.findViewById(R.id.imgProduk)
        val tvNamaProduk: TextView = itemView.findViewById(R.id.tvNamaProduk)
        val tvDeskripsiProduk: TextView = itemView.findViewById(R.id.tvDeskripsiProduk)
        val tvHargaProduk: TextView = itemView.findViewById(R.id.tvHargaProduk)
        val tvStokProduk: TextView = itemView.findViewById(R.id.tvStokProduk)
        val btnEdit: Button = itemView.findViewById(R.id.btnEdit)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_produk, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = produkList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produk = produkList[position]
        holder.tvNamaProduk.text = produk.nama
        holder.tvDeskripsiProduk.text = produk.deskripsi
        holder.tvHargaProduk.text = "Rp ${produk.harga}"
        holder.tvStokProduk.text = "${produk.stok} ml"

        if (!produk.fotoUri.isNullOrEmpty()) {
            holder.imgProduk.setImageURI(Uri.parse(produk.fotoUri))
        } else {
            holder.imgProduk.setImageResource(R.mipmap.ic_launcher) // default
        }

        holder.btnEdit.setOnClickListener { onEdit(produk, position) }
        holder.btnDelete.setOnClickListener { onDelete(produk, position) }
    }
}
