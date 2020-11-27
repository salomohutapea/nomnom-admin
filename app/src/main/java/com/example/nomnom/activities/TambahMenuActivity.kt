package com.example.nomnom.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nomnom.R
import com.example.nomnom.handlers.NetworkHandler
import com.example.nomnom.models.NewMenuModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_tambah_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*

class TambahMenuActivity : AppCompatActivity() {

    private var selectedPhotoUri: Uri? = null
    lateinit var imageUuid: String
    private var storageRef: StorageReference = FirebaseStorage.getInstance().reference
    private var firebaseImageLink: String = ""
    private var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_menu)

        //Jika button select profile di click maka, akan muncul pilihan foto dari gallery
        tambahMenu_cardAddPic.setOnClickListener {
            Log.d("RegisterActivity", "Try to show photo selector")

            val intentPhotoSelector = Intent(Intent.ACTION_PICK)
            intentPhotoSelector.type = "image/*"
            startActivityForResult(intentPhotoSelector, 0)
        }

        tambahMenu_backbtn.setOnClickListener {
            finish()
        }
    }

    //Method untuk mengupload foto
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            // proceed and check what the selected image was...

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val imageData = baos.toByteArray()

            uploadImage(imageData)
            Log.d("flag1", "Upload image success")

            tambahMenu_btnSubmit.setOnClickListener {
                addMenu(firebaseImageLink)
            }

            flag = 1

            Log.d("Select Photo", "Photo was selected")

            val bitmapDrawable = BitmapDrawable(bitmap)

            tambahMenu_imgView.setImageDrawable(bitmapDrawable)
            tambahMenu_imgView.setPadding(0, 0, 0, 0)

        }
    }

    private fun addMenu(imageUrl: String) {

        tambahMenu_progressText.text = "Menambah menu..."

        val inputNama = tambahMenu_namaInput.text.toString()
        val inputInfo = tambahMenu_infoInput.text.toString()
        val inputHarga = tambahMenu_hargaInput.text.toString()

        if (inputHarga == "" || inputInfo == "" || inputNama == "") {
            Toast.makeText(this, "Harap masukkan semua data", Toast.LENGTH_SHORT).show();
            return
        } else {
            val requestBody = NewMenuModel(nama = inputNama, info = inputInfo, harga = inputHarga, imgUrl = imageUrl)

            val networkHandler = NetworkHandler().getService()

            networkHandler.addMenu(requestBody).enqueue(object :
                Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("GSON ERROR", t.toString())
                    Toast.makeText(this@TambahMenuActivity, "Sukses menambah menu", Toast.LENGTH_LONG).show()
                    finish()
                }

                @SuppressLint("SetTextI18n", "SimpleDateFormat")
                override fun onResponse(
                    call: Call<String>,
                    model: Response<String>
                ) {
                }
            })
        }
    }

    private fun generateUUID() : String{
        imageUuid = UUID.randomUUID().toString().replace("-", "")
        return imageUuid
    }

    private fun deleteImage(uuid: String) {
        storageRef.child("menu-images/${uuid}.png").delete().addOnSuccessListener {

        }.addOnFailureListener {
            Log.d("Delete image failure", it.toString())
        }
    }

    // Upload selected image to Firebase and get image's download url
    private fun uploadImage(imageData: ByteArray) {
        tambahMenu_Progress.visibility = View.VISIBLE
        if (flag == 1) {
            deleteImage(imageUuid)
            tambahMenu_progressText.text = "Menghapus gambar..."
        }
        tambahMenu_progressText.text = "Mengupload gambar..."
        generateUUID()
        val uploadTask = storageRef.child("menu-images/${imageUuid}").putBytes(imageData)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener {
            Log.d("Upload Task", "Success")

            // Get image download url
            val imgRef = storageRef.child("menu-images/${imageUuid}")
            imgRef.downloadUrl.addOnSuccessListener {
                firebaseImageLink = it.toString().replace("\\", "", ignoreCase = false)
                tambahMenu_Progress.visibility = View.GONE
            }.addOnFailureListener {
                return@addOnFailureListener
            }
        }
        tambahMenu_Progress.visibility = View.GONE
    }
}