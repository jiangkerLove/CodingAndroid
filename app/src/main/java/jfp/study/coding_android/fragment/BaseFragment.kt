package jfp.study.coding_android.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import jfp.study.coding_android.R
import jfp.study.coding_android.base.LiveModel
import jfp.study.coding_android.databinding.FragmentLifeBinding

abstract class BaseFragment : Fragment() {

    abstract val index: String

    private val TAG = "FragmentLife"

    val model: LiveModel by lazy {
        ViewModelProvider(requireActivity())[LiveModel::class.java]
    }

    private var binding : FragmentLifeBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i(TAG, "onAttach: $index")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView: $index")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_life,container,true)
        model.liveData.observe(viewLifecycleOwner, {
            Log.i(TAG, "onCreateView: $it - $index")
            binding?.fragmentContent?.text = index
        })
        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: $index")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: $index")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: $index")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: $index")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView: $index")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(TAG, "onDetach: $index")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: $index")
    }


}