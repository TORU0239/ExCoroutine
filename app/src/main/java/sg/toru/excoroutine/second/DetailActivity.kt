package sg.toru.excoroutine.second

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.*
import sg.toru.excoroutine.R
import sg.toru.excoroutine.common.data.Comment
import sg.toru.excoroutine.common.remote.ActivityCallback
import sg.toru.excoroutine.common.remote.CentralRepository
import sg.toru.excoroutine.common.remote.STATE
import sg.toru.excoroutine.databinding.ActivityDetailBinding
import sg.toru.excoroutine.databinding.LayoutDetailBinding

class DetailActivity : AppCompatActivity() {

    init {
        CentralRepository.activityCallback = object:ActivityCallback{
            override fun onResult(state: STATE,
                                  item: List<Comment>?) {
                Log.i("Detail", "current status:: $state, isAlreadyCreated? $isAlreadyCreated")
                if(isAlreadyCreated && state == STATE.SUCCESS){
                    item?.let {
                        init(it)
                    }
                }
            }
        }
    }

    private lateinit var binding:ActivityDetailBinding
    private val detailAdapter:DetailAdapter by lazy {
        DetailAdapter()
    }


    private var isAlreadyCreated = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        Log.i("Detail", "onCreate!!!")
        isAlreadyCreated = true
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
    }


    private fun init(item:List<Comment>){
        binding.rcvDetail.adapter = detailAdapter
        binding.rcvDetail.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcvDetail.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        detailAdapter.submitList(item)
    }
}


class DetailAdapter(private val listener:()->Unit = {}):ListAdapter<Comment, DetailViewHolder>(DetailDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder{
        val binding = DataBindingUtil.inflate<LayoutDetailBinding>(LayoutInflater.from(parent.context), R.layout.layout_detail, parent, false)
        return DetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bindItem(getItem(position))
        holder.itemView.setOnClickListener {
            listener.invoke()
        }
    }
}

class DetailViewHolder(private val binding:LayoutDetailBinding):RecyclerView.ViewHolder(binding.root){
    fun bindItem(comment:Comment){
        binding.item = comment
        binding.executePendingBindings()
    }
}

class DetailDiffCallback:DiffUtil.ItemCallback<Comment>(){
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean = (oldItem.id == newItem.id && oldItem.postId == newItem.postId)
    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean = (oldItem.id == newItem.id && oldItem.postId == newItem.postId)
}