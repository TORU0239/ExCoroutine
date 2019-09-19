package sg.toru.excoroutine.second.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sg.toru.excoroutine.R
import sg.toru.excoroutine.common.data.Comment
import sg.toru.excoroutine.common.remote.*
import sg.toru.excoroutine.databinding.FragmentNormalDetailBinding
import sg.toru.excoroutine.second.DetailAdapter

class NormalDetailFragment : Fragment() {
    private lateinit var binding:FragmentNormalDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i("Detail", "onCreateView!!")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_normal_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i("Detail", "onViewCreated!!")
//        init()
        testInit().observe(this, Observer {
            it?.let { comments ->
                init(comments)
            }
        })
    }

    private val detailAdapter: DetailAdapter by lazy {
        DetailAdapter{
            findNavController().navigate(R.id.action_normalDetailFragment_to_normalContentsFragment)
        }
    }

    private fun init(){
        NetworkModule.myRetrofit<NonCoroutineRequest>().getPostedItem().enqueue(object:
            Callback<List<Comment>> {
            override fun onFailure(call: Call<List<Comment>>,
                                   t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<Comment>>,
                                    response: Response<List<Comment>>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        init(it)
                    }
                }
            }
        })
    }

    private fun testInit(): LiveData<List<Comment>> {
        val liveData = MutableLiveData<List<Comment>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = NetworkModule.myRetrofit<CommentRequest>().getPostedItems()
                withContext(Dispatchers.Main){
                    if(response.isSuccessful){
                        response.body()?.let { commentList->
                            liveData.value = commentList
                        }
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
        return liveData
    }

    private fun init(item:List<Comment>){
        binding.rcvNormalDetail.adapter = detailAdapter
        binding.rcvNormalDetail.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rcvNormalDetail.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        detailAdapter.submitList(item)
    }
}