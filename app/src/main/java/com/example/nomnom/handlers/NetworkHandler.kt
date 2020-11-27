package com.example.nomnom.handlers


import com.example.nomnom.models.MenuModel
import com.example.nomnom.models.NewMenuModel
import com.example.nomnom.models.OrderModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

class NetworkHandler{

    lateinit var imageUuid: String
    private var storageRef: StorageReference = FirebaseStorage.getInstance().reference
    private var firebaseImageLink: String = ""

    // set interceptor
    private fun getInterceptor(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://nomnom-api.azurewebsites.net/")
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService(): ServiceCall = getRetrofit().create(ServiceCall::class.java)

}

interface ServiceCall {
    @GET("data/?q=menus")
    fun getMenu(): Call<List<MenuModel>>

    @GET("data/?q=orders")
    fun getOrder(): Call<List<OrderModel>>

    @POST("/newmenu")
    fun addMenu(@Body body: NewMenuModel?): Call<String>

    @GET("/pesananselesai")
    fun finishOrder(@Query("id") id: String): Call<String>
}