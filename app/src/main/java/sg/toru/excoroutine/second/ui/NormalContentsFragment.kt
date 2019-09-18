package sg.toru.excoroutine.second.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import sg.toru.excoroutine.R

/**
 * A simple [Fragment] subclass.
 */
class NormalContentsFragment : Fragment() {

    private lateinit var onBackPressedCallback: OnBackPressedCallback
    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBackPressedCallback = object:OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onDetach() {
        onBackPressedCallback.remove()
        super.onDetach()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_normal_contents, container, false)
    }
}
