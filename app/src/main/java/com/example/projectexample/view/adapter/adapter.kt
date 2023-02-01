package com.example.projectexample.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import androidx.navigation.Navigation
import androidx.navigation.navArgument
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.room.Room
import com.example.projectexample.databinding.FragmentListBinding
import com.example.projectexample.databinding.RecyclerBinding
import com.example.projectexample.view.model.model
import com.example.projectexample.view.listDirections
import com.example.projectexample.view.model.modelDao

class adapter(val list: List<model>) : RecyclerView.Adapter<adapter.Holder>() {
    class Holder(val binding: RecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.text.text = list.get(position).message
        val id = list.get(position).id
        holder.itemView.setOnClickListener {
            val action = listDirections.actionListToAdd2(id, "old")
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}