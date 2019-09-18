package sg.toru.excoroutine.second.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sg.toru.excoroutine.R
import sg.toru.excoroutine.common.data.Comment
import sg.toru.excoroutine.common.remote.CentralRepository
import sg.toru.excoroutine.common.remote.NetworkModule
import sg.toru.excoroutine.common.remote.NonCoroutineRequest
import sg.toru.excoroutine.common.remote.STATE
import sg.toru.excoroutine.databinding.FragmentNormalDetailBinding
import sg.toru.excoroutine.second.DetailAdapter

class NormalDetailFragment : Fragment() {
    private lateinit var binding:FragmentNormalDetailBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_normal_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }

    private val detailAdapter: DetailAdapter by lazy {
        DetailAdapter()
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

    private fun init(item:List<Comment>){
        binding.rcvNormalDetail.adapter = detailAdapter
        binding.rcvNormalDetail.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rcvNormalDetail.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        detailAdapter.submitList(item)
    }
}