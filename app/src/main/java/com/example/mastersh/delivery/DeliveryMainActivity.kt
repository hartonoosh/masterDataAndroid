package com.example.mastersh.delivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mastersh.R
import com.example.mastersh.auth.model.SessionManager
import com.example.mastersh.delivery.adapter.DOAdapter
import com.example.mastersh.delivery.model.DataRespon
import com.example.mastersh.delivery.presenter.PresenterDelivery
import com.example.mastersh.delivery.view.DeliveryView
import org.jetbrains.anko.startActivity

class DeliveryMainActivity : AppCompatActivity() ,DeliveryView {
    //deklarasikan variabel session
    private lateinit var sessionManager: SessionManager

    private lateinit var presenterDelivery: PresenterDelivery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_main)
        sessionManager = SessionManager(this)
        presenterDelivery = PresenterDelivery(this)

        val token = sessionManager.fetchAuthToken()
        val token_user = token.toString().trim()
        presenterDelivery.get_data_do(token_user)
    }

    override fun suksesGETDO(data: List<DataRespon>?) {
        val rvDeliveryOrder = findViewById<RecyclerView>(R.id.rvDeliveryOrder)
        rvDeliveryOrder.adapter = DOAdapter(data,object :DOAdapter.onClickDO{
            override fun clicked(item: DataRespon?) {
                startActivity<DeliveryDetailDoActivity>("dataDeliveryNote" to item)
            }
        })
    }

    override fun gagalGETDO(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun suksesGETDN(data: List<DataRespon>?) {
        TODO("Not yet implemented")
    }

    override fun gagalGETDN(msg: String) {
        TODO("Not yet implemented")
    }

}
