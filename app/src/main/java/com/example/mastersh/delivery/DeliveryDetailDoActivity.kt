package com.example.mastersh.delivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mastersh.R
import com.example.mastersh.auth.model.SessionManager
import com.example.mastersh.delivery.adapter.DNAdapter
import com.example.mastersh.delivery.adapter.DOAdapter
import com.example.mastersh.delivery.model.DataRespon
import com.example.mastersh.delivery.presenter.PresenterDelivery
import com.example.mastersh.delivery.view.DeliveryView
import org.jetbrains.anko.startActivity

class DeliveryDetailDoActivity : AppCompatActivity(),DeliveryView {
    //deklarasikan variabel session
    private lateinit var sessionManager: SessionManager
    private lateinit var presenterDelivery: PresenterDelivery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_detail_do)
        sessionManager = SessionManager(this)
        presenterDelivery = PresenterDelivery(this)

        val token = sessionManager.fetchAuthToken()
        val token_user = token.toString().trim()

        val itemDeliveryNote = intent.getSerializableExtra("dataDeliveryNote")
        val deliveryNote = itemDeliveryNote as DataRespon?
        presenterDelivery.get_data_dn(
            token_user,
            deliveryNote?.idOrder ?: ""
        )


    }

    override fun suksesGETDO(data: List<DataRespon>?) {
        TODO("Not yet implemented")
    }

    override fun gagalGETDO(msg: String) {
        TODO("Not yet implemented")
    }

    override fun suksesGETDN(data: List<DataRespon>?) {
        val rvDeliveryNote = findViewById<RecyclerView>(R.id.rvDeliveryNote)
        rvDeliveryNote.adapter = DNAdapter(data,object : DNAdapter.onClickDN{
            override fun clicked(item: DataRespon?) {
                customDialog(item)
                startActivity<DeliveryUploadActivity>("dataDetailDeliveryNote" to item)
            }
        })

    }

    private fun customDialog(item: DataRespon?) {
        Toast.makeText(this, item?.namaCustomer, Toast.LENGTH_LONG).show()
    }

    override fun gagalGETDN(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}