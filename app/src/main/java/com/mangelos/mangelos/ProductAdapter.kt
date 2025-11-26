package com.mangelos.mangelos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mangelos.mangelos.databinding.ItemProductBinding
import com.mangelos.mangelos.models.Product

class ProductAdapter(
    private var products: List<Product>,
    private val onAddClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.binding.tvProductName.text = product.name
        holder.binding.tvProductPrice.text = "$ ${product.price}"

        // Cargar imagen con Glide (usamos la primera de la lista)
        if (!product.image.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(product.image[0].url)
                .into(holder.binding.ivProductImage)
        }

        holder.binding.btnAddToCart.setOnClickListener { onAddClick(product) }
    }

    override fun getItemCount() = products.size

    fun updateList(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}