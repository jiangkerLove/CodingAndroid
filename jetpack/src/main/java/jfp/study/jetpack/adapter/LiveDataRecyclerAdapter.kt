package jfp.study.jetpack.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import jfp.study.jetpack.R
import jfp.study.jetpack.databinding.ItemviewLivedataBinding

class LiveDataRecyclerAdapter(
    private val dataList:List<MutableLiveData<String>>
) : RecyclerView.Adapter<LiveDataRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemviewLivedataBinding>(LayoutInflater.from(parent.context),
            R.layout.itemview_livedata,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.lifecycleOwner = holder
        dataList[position].observe(holder){
            holder.binding.liveValue.text = it
        }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount() = dataList.size

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetachView()
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onBindView()
    }

    class ViewHolder(val binding: ItemviewLivedataBinding) : RecyclerView.ViewHolder(binding.root),LifecycleOwner {

        private val lifecycleOwner = LifecycleRegistry(this)

        override fun getLifecycle() = lifecycleOwner

        fun onBindView(){
            lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
            lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_START)
            lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }

        fun onDetachView(){
            lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        }

    }
}