package sg.toru.excoroutine.common.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit


object NetworkModule{
    private val okHttpClient:OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(2000, TimeUnit.MILLISECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val retrofit:Retrofit by lazy {
        Retrofit.Builder().client(okHttpClient)
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}

interface CommentRequest{
    @GET("/comments")
    suspend fun getPostedItems():List<Comment>
}

interface NonCoroutineRequest{
    @GET("/comments")
    fun getPostedItem(): Call<List<Comment>>
}

data class Comment(val postId:String,
                   val id:String,
                   val name:String,
                   val email:String,
                   val body:String)

/*
* "userId": 1,
"id": 2,
"title": "qui est esse",
"body": "est rerum tempore vitae
sequi sint nihil reprehenderit dolor beatae ea dolores neque
fugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis
qui aperiam non debitis possimus qui neque nisi nulla"
* */
