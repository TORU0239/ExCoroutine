package sg.toru.excoroutine.common.remote

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CentralRepository {
    private var currentStatus:STATE = STATE.IDLE

    var activityCallback:ActivityCallback? = null

    fun callCommentApi(){
        val postedItemCall = NetworkModule.retrofit.create(NonCoroutineRequest::class.java)
        currentStatus = STATE.LOADING
        postedItemCall.getPostedItem().enqueue(object: Callback<List<Comment>> {
            override fun onFailure(call: Call<List<Comment>>,
                                   t: Throwable) {
                currentStatus = STATE.FAILED
                activityCallback?.onResult(currentStatus, null)
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<Comment>>,
                                    response: Response<List<Comment>>
            ) {
                if(response.isSuccessful){
                    currentStatus = STATE.SUCCESS
                    activityCallback?.onResult(currentStatus, response.body())

                    Log.i("Detail", "initiated!")
                }
                else{
                    call.clone().enqueue(this)
                }
            }
        })
    }
}

interface ActivityCallback{
    fun onResult(state:STATE, item:List<Comment>?)
}

enum class STATE{
    IDLE, LOADING, SUCCESS, FAILED
}