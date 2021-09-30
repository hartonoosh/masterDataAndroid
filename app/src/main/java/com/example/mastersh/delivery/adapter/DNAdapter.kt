package com.example.mastersh.delivery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mastersh.R
import com.example.mastersh.delivery.model.DataRespon
import org.jetbrains.anko.sdk27.coroutines.onClick

class DNAdapter(val data: List<DataRespon>?,
                private val click: onClickDN): RecyclerView.Adapter<DNAdapter.DNHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DNAdapter.DNHolder {
        val viewDN = LayoutInflater.from(parent.context).inflate(R.layout.delivery_item_dn,parent,false)
        return DNHolder(viewDN)
    }

    override fun getItemCount() = data?.size ?: 0

    override fun onBindViewHolder(holder: DNAdapter.DNHolder, position: Int) {
        holder.onBind(data?.get(position))
        holder.itemView.onClick{
            click.clicked(data?.get(position))
        }
    }

    class DNHolder(dnView: View): RecyclerView.ViewHolder(dnView){
        fun onBind(get: DataRespon?){
            val txtDNCustomer = itemView.findViewById<TextView>(R.id.txtDNCustomer)
            val txtDNAlamat = itemView.findViewById<TextView>(R.id.txtDNAlamat)
            val txtDNTotal = itemView.findViewById<TextView>(R.id.txtDNTotal)
            val txtDNStatus = itemView.findViewById<TextView>(R.id.txtDNStatus)
            if(get?.statusCustomer != null) {
                txtDNCustomer.text = get?.namaCustomer
                txtDNAlamat.text = get?.alamatCustomer
                txtDNTotal.text = get?.totalItem
                txtDNStatus.text = "Sudah Diterima Gaess"
            }else {
                txtDNCustomer.text = get?.namaCustomer
                txtDNAlamat.text = get?.alamatCustomer
                txtDNTotal.text = get?.totalItem
                txtDNStatus.text = "Belum Diterima"
            }
        }
    }

    interface onClickDN{
        fun clicked(item: DataRespon?)
    }
}