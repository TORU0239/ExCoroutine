package sg.toru.excoroutine.main.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sg.toru.excoroutine.R
import sg.toru.excoroutine.common.remote.*
import sg.toru.excoroutine.databinding.ActivityMainBinding
import sg.toru.excoroutine.second.DetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        init()
    }

    private fun init(){
        binding.btnGoOnNext.setOnClickListener {
            CentralRepository.callCommentApi()
            Log.i("Detail", "initiated!")
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }
}