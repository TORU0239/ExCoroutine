package sg.toru.excoroutine.second.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sg.toru.excoroutine.R
import sg.toru.excoroutine.common.data.Comment
import sg.toru.excoroutine.common.remote.CentralRepository
import sg.toru.excoroutine.common.remote.NetworkModule
import sg.toru.excoroutine.common.remote.NonCoroutineRequest
import sg.toru.excoroutine.common.remote.STATE
import sg.toru.excoroutine.databinding.ActivityDetailBinding
import sg.toru.excoroutine.databinding.ActivityNormalDetailBinding
import sg.toru.excoroutine.databinding.LayoutDetailBinding

class NormalDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal_detail)
    }
}