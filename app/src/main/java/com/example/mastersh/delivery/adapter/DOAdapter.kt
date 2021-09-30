package com.example.mastersh.delivery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mastersh.R
import com.example.mastersh.delivery.model.DataRespon
import org.jetbrains.anko.sdk27.coroutines.onClick

class DOAdapter(val data: List<DataRespon>?,
                private val click: onClickDO): RecyclerView.Adapter<DOAdapter.DOHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DOAdapter.DOHolder {
        val viewDO = LayoutInflater.from(parent.context).inflate(R.layout.delivery_item_do,parent,false)
        return DOHolder(viewDO)
    }

    override fun getItemCount() = data?.size ?: 0

    override fun onBindViewHolder(holder: DOAdapter.DOHolder, position: Int) {
        holder.onBind(data?.get(position))
        holder.itemView.onClick{
            click.clicked(data?.get(position))
        }
    }

    class DOHolder(doView: View): RecyclerView.ViewHolder(doView){
        fun onBind(get: DataRespon?){
            val txtDONomor = itemView.findViewById<TextView>(R.id.txtDONomor)
            val txtDODriver = itemView.findViewById<TextView>(R.id.txtDODriver)
            val txtDONopol = itemView.findViewById<TextView>(R.id.txtDONopol)
            val txtDOTotal = itemView.findViewById<TextView>(R.id.txtDOTotal)

            txtDONomor.text = get?.noDeliveryOrder
            txtDODriver.text = get?.namaDriver
            txtDONopol.text = get?.nopolDriver
            txtDOTotal.text = get?.totalDeliveryNote

        }
    }

    interface onClickDO{
        fun clicked(item: DataRespon?)
    }
}