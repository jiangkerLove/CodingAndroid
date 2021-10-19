package jfp.study.coding_android.demo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import jfp.study.coding_android.R
import jfp.study.coding_android.databinding.ItemContactPersonBinding

class ContactPersonAdapter(
    internal val listValue: List<Person>
) : RecyclerView.Adapter<ContactPersonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemContactPersonBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_contact_person,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.itemPersonName.text = listValue[position].name
    }

    override fun getItemCount() = listValue.size

    class ViewHolder(val binding: ItemContactPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    class Person(
        val name: String,
        val type: Char
    )
}