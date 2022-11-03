package com.target.targetcasestudy.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.target.targetcasestudy.R
import com.target.targetcasestudy.model.Product

class DealItemAdapter(val dealList: List<Product>) : RecyclerView.Adapter<DealItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.deals_list_item, parent, false)
        return DealItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dealList.size
    }

    override fun onBindViewHolder(viewHolder: DealItemViewHolder, position: Int) {
        viewHolder.salePrice.text = dealList[position].salePrice?.displayString ?: ""
        viewHolder.regPrice.text = dealList[position].regularPrice?.displayString ?: ""
        viewHolder.title.text = dealList[position].title
        viewHolder.fulfillment.text = dealList[position].fulfillment
    }
}

class DealItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val productImage: ImageView = itemView.findViewById(R.id.product_image)
    val salePrice: TextView = itemView.findViewById(R.id.sale_price_tv)
    val regPrice: TextView = itemView.findViewById(R.id.reg_price_tv)
    val title: TextView = itemView.findViewById(R.id.title_tv)
    val fulfillment: TextView = itemView.findViewById(R.id.fulfillment_tv)
}