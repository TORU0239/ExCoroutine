package sg.toru.excoroutine.common.remote

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CentralRepository {
    fun callCommentApi(){
        val postedItemCall = NetworkModule.retrofit.create(NonCoroutineRequest::class.java)
        postedItemCall.getPostedItem().enqueue(object: Callback<List<Comment>> {
            override fun onFailure(call: Call<List<Comment>>,
                                   t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<Comment>>,
                                    response: Response<List<Comment>>
            ) {
                if(response.isSuccessful){
                    Log.i("TORU", "Test!")
                }
                else{
                    call.clone().enqueue(this)
                }
            }
        })
    }
}