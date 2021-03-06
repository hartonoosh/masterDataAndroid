package com.example.mastersh.captureCamera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider

import android.os.Environment
import java.io.File
import java.io.IOException
import android.R
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues

import android.graphics.BitmapFactory
import android.os.Build
import android.provider.DocumentsContract
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.gms.vision.clearcut.LogUtils
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.app.ActivityCompat.startActivityForResult
import java.io.FileNotFoundException


class UploadCaptureCameraActivity : AppCompatActivity() {

    //Our variables
    private var mImageView: ImageView? = null
    private var mUri: Uri? = null
    //Our widgets
    private lateinit var btnCapture: Button
    private lateinit var btnChoose : Button
    //Our constants
    private val OPERATION_CAPTURE_PHOTO = 1
    private val OPERATION_CHOOSE_PHOTO = 2


    private fun initializeWidgets() {
        val btnCapture_1 = findViewById<Button>(com.example.mastersh.R.id.btnCapture)
        val btnChoose_1 = findViewById<Button>(com.example.mastersh.R.id.btnChoose)
        val mImageView_1 = findViewById<ImageView>(com.example.mastersh.R.id.mImageView)
        btnCapture = btnCapture_1
        btnChoose = btnChoose_1
        mImageView = mImageView_1
    }

    private fun show(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
    private fun capturePhoto(){
        val capturedImage = File(externalCacheDir, "My_Captured_Photo.jpg")
        if(capturedImage.exists()) {
            capturedImage.delete()
        }
        capturedImage.createNewFile()
        mUri =
            FileProvider.getUriForFile(this, "com.example.mastersh.fileprovider",
                capturedImage)

        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri)
        startActivityForResult(intent, OPERATION_CAPTURE_PHOTO)
    }
//    private fun openGallery(){
//        val intent = Intent("android.intent.action.GET_CONTENT")
//        intent.type = "image/*"
//        startActivityForResult(intent, OPERATION_CHOOSE_PHOTO)
//    }
//    private fun renderImage(imagePath: String?){
//        if (imagePath != null) {
//            val bitmap = BitmapFactory.decodeFile(imagePath)
//            mImageView?.setImageBitmap(bitmap)
//        }
//        else {
//            show("ImagePath is null")
//        }
//    }
//    @SuppressLint("Range")
//    private fun getImagePath(uri: Uri?, selection: String?): String {
//        var path: String? = null
//        val cursor = contentResolver.query(uri!!, null, selection, null, null )
//        if (cursor != null){
//            if (cursor.moveToFirst()) {
//                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
//            }
//            cursor.close()
//        }
//        return path!!
//    }
//    @TargetApi(31)
//    private fun handleImageOnKitkat(data: Intent?) {
//        var imagePath: String? = null
//        val uri = data!!.data
//        //DocumentsContract defines the contract between a documents provider and the platform.
//        if (DocumentsContract.isDocumentUri(this, uri)){
//            val docId = DocumentsContract.getDocumentId(uri)
//            if (uri != null) {
//                if ("com.android.providers.media.documents" == uri.authority){
//                    val id = docId.split(":")[1]
//                    val selsetion = MediaStore.Images.Media._ID + "=" + id
//                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                        selsetion)
//                } else if ("com.android.providers.downloads.documents" == uri.authority){
//                    val contentUri = ContentUris.withAppendedId(Uri.parse(
//                        "content://downloads/public_downloads"), java.lang.Long.valueOf(docId))
//                    imagePath = getImagePath(contentUri, null)
//                }
//            }
//        }
//        else if ("content".equals(uri?.scheme, ignoreCase = true)){
//            imagePath = getImagePath(uri, null)
//        }
//        else if ("file".equals(uri?.scheme, ignoreCase = true)){
//            imagePath = uri?.path
//        }
//        renderImage(imagePath)
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>
//                                            , grantedResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantedResults)
//        when(requestCode){
//            1 ->
//                if (grantedResults.isNotEmpty() && grantedResults.get(0) ==
//                    PackageManager.PERMISSION_GRANTED){
//                    openGallery()
//                }else {
//                    show("Unfortunately You are Denied Permission to Perform this Operataion.")
//                }
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            OPERATION_CAPTURE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap = BitmapFactory.decodeStream(
                        getContentResolver().openInputStream(mUri!!))
                    mImageView!!.setImageBitmap(bitmap)
                    mImageView!!.rotation = 90f
                }
//            OPERATION_CHOOSE_PHOTO ->
//                if (resultCode == Activity.RESULT_OK) {
//                    handleImageOnKitkat(data)
//                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.mastersh.R.layout.activity_upload_capture_camera)

        initializeWidgets()

        btnCapture.setOnClickListener{capturePhoto()}
//        btnChoose.setOnClickListener{
//            //check permission at runtime
//            val checkSelfPermission = ContextCompat.checkSelfPermission(this,
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED){
//                //Requests permissions to be granted to this application at runtime
//                ActivityCompat.requestPermissions(this,
//                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
//            }
//            else{
//                openGallery()
//            }
//        }
    }



}