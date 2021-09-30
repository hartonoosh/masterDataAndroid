package com.example.mastersh.delivery

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.widget.*
import com.example.mastersh.R
import com.example.mastersh.auth.model.SessionManager
import com.example.mastersh.delivery.config.ConfigDeliveryAPI
import com.example.mastersh.delivery.model.DataRespon
import com.example.mastersh.delivery.model.HandlerUploadImageCustomer
import com.example.mastersh.delivery.model.ResponseDeliveryServer
import com.example.mastersh.delivery.presenter.PresenterDelivery
import com.example.mastersh.delivery.view.DeliveryView
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.os.Environment
import androidx.core.content.FileProvider
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.io.*


class DeliveryUploadActivity : AppCompatActivity(), DeliveryView, HandlerUploadImageCustomer.UploadCallback {
    private var fotoRealtimeCustomer: Uri? = null
    private var fotoRealtimeCustomerBitmap: Bitmap? = null

    //deklarasikan variabel session
    private lateinit var sessionManager: SessionManager
    private lateinit var presenterDelivery: PresenterDelivery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_upload)

        val txtDNCustomerUpload = findViewById<TextView>(R.id.txtDNCustomerUpload)
        val txtDNAlamatUpload = findViewById<TextView>(R.id.txtDNAlamatUpload)
        val txtDNTelpUpload = findViewById<TextView>(R.id.txtDNTelpUpload)

        sessionManager = SessionManager(this)
        presenterDelivery = PresenterDelivery(this)

        val itemDeliveryNote = intent.getSerializableExtra("dataDetailDeliveryNote")
        val deliveryNote = itemDeliveryNote as DataRespon?

        txtDNCustomerUpload.setText(deliveryNote?.idNote.toString())
        txtDNAlamatUpload.setText(deliveryNote?.alamatCustomer.toString())
        txtDNTelpUpload.setText(deliveryNote?.telpCustomer.toString())

        val imageCustomer = findViewById<ImageView>(R.id.imageDeliveryCustomerView)
        imageCustomer.setOnClickListener {
            ambilFotoRealtime()
        }

        val btnUploadCustomer = findViewById<Button>(R.id.btnDeliveryUploadCustomer)
        btnUploadCustomer.setOnClickListener {
            uploadGambarCustomer(fotoRealtimeCustomerBitmap)
        }

    }

    private fun ambilFotoRealtime() {
        val capturedImage = File(externalCacheDir, "My_Captured_Photo.jpg")
        if(capturedImage.exists()) {
            capturedImage.delete()
        }
        capturedImage.createNewFile()
        fotoRealtimeCustomer =
            FileProvider.getUriForFile(this, "com.example.mastersh.fileprovider",
                capturedImage)

        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoRealtimeCustomer)
        startActivityForResult(intent, OPERATION_CAPTURE_PHOTO)

//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
//            takePictureIntent.resolveActivity(packageManager)?.also {
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//            }
//        }
//        Intent(Intent.ACTION_PICK).also {
//            it.type = "image/*"
//            val mimeTypes = arrayOf("image/jpeg","image/png")
//            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
//            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
//        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 100
        const val REQUEST_CODE_PICK_IMAGE = 101
        const val OPERATION_CAPTURE_PHOTO = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imageCustomer = findViewById<ImageView>(R.id.imageDeliveryCustomerView)

        if (resultCode == Activity.RESULT_OK){
            when(requestCode) {
                OPERATION_CAPTURE_PHOTO ->
                    if (resultCode == Activity.RESULT_OK) {
                        val bitmap = BitmapFactory.decodeStream(
                            getContentResolver().openInputStream(fotoRealtimeCustomer!!))
                        imageCustomer.setImageBitmap(bitmap)
                        imageCustomer.rotation = 90f
                        fotoRealtimeCustomerBitmap = bitmap
                    }
//                REQUEST_IMAGE_CAPTURE -> {
//                    val photo = data?.extras?.get("data") as Bitmap
//                    imageCustomer.setImageBitmap(photo)
//                    fotoRealtimeCustomerBitmap = photo
//
//                }
//            OPERATION_CHOOSE_PHOTO ->
//                if (resultCode == Activity.RESULT_OK) {
//                    handleImageOnKitkat(data)
//                }
//                REQUEST_CODE_PICK_IMAGE -> {
//                    fotoRealtimeCustomer = data?.data
//                    imageCustomer.setImageURI(fotoRealtimeCustomer)
//                }
            }
        }
    }


    private fun createTempFile(bitmap: Bitmap): File? {
        val file = File(
            getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            System.currentTimeMillis().toString() + "_image.jpeg"
        )
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val bitmapdata = bos.toByteArray()
        //write the bytes in file
        try {
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

    override fun suksesGETDO(data: List<DataRespon>?) {
        TODO("Not yet implemented")
    }

    override fun gagalGETDO(msg: String) {
        TODO("Not yet implemented")
    }

    override fun suksesGETDN(data: List<DataRespon>?) {
        TODO("Not yet implemented")
    }

    override fun gagalGETDN(msg: String) {
        TODO("Not yet implemented")
    }

    private fun uploadGambarCustomer(bitmap: Bitmap?) {
        val textUpload = findViewById<TextView>(R.id.textDeliveryUploadCustomer)
        if (fotoRealtimeCustomerBitmap == null) {
            textUpload.text = ("Foto Customer Dulu Brooo")
            return
        } else {
            textUpload.text = ("Otw Upload...")

            val uploadeliveryNote = intent.getSerializableExtra("dataDetailDeliveryNote")
            val deliveryNote1 = uploadeliveryNote as DataRespon?

            sessionManager = SessionManager(this)
            val token = sessionManager.fetchAuthToken()
            val token_user = token.toString().trim()

//            val parcelFileDescriptor = contentResolver.openFileDescriptor(fotoRealtimeCustomer!!, "r", null) ?: return
//
//            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
//            val file = File(cacheDir, contentResolver.ambilNamaFile(fotoRealtimeCustomer!!))
//            val outputStream = FileOutputStream(file)
//            inputStream.copyTo(outputStream)
//            val body = HandlerUploadImageCustomer(file, "image", this)

            val file = createTempFile (bitmap!!)
            val reqFile = RequestBody.create ("image/*".toMediaTypeOrNull(), file!!)

            val progressBar = findViewById<ProgressBar>(R.id.progressBarDeliveryUpload)
            progressBar.progress = 0

            ConfigDeliveryAPI.getDeliveryService().upload_image_customer(
                MultipartBody.Part.createFormData("token",token_user),
                MultipartBody.Part.createFormData("id_note",deliveryNote1?.idNote ?: ""),

                //MultipartBody.Part.createFormData("image", file.name, body)
                MultipartBody.Part.createFormData("image", file.getName(),reqFile)
            ).enqueue(object : Callback<ResponseDeliveryServer> {
                override fun onFailure(call: Call<ResponseDeliveryServer>, t: Throwable) {
                    textUpload.text = t.message!!
                    progressBar.progress = 0
                }

                override fun onResponse(
                    call: Call<ResponseDeliveryServer>,
                    response: Response<ResponseDeliveryServer>
                ) {
                    if (response.isSuccessful && response.body()?.kode == 200){
                        textUpload.text = response.body()?.message?:""
                        progressBar.progress = 100
                    } else {
                        textUpload.text = response.body()?.message?:""
                        progressBar.progress = 0
                    }
                }
            })
        }
    }

    override fun onProgressUpdate(percentage: Int) {
        val progressBar = findViewById<ProgressBar>(R.id.progressBarDeliveryUpload)
        progressBar.progress = percentage
    }

    private fun ContentResolver.ambilNamaFile(fileUri: Uri): String {
        var name = ""
        val returnCursor = this.query(fileUri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }

}