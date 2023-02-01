package com.example.projectexample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projectexample.databinding.FragmentListBinding
import com.example.projectexample.view.adapter.adapter
import com.example.projectexample.view.model.model
import com.example.projectexample.view.model.modelDao
import com.example.projectexample.view.model.modelDatabase


class list : Fragment() {
    private lateinit var list: List<model>
    private lateinit var binding: FragmentListBinding
    private lateinit var artDb: modelDatabase
    private lateinit var dao: modelDao

    override fun onCreate(savedInstanceState: Bundle?) {
        artDb = Room.databaseBuilder(requireContext(), modelDatabase::class.java, "model")
            .allowMainThreadQueries().fallbackToDestructiveMigration().build()
        dao = artDb.modelDao()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list = dao.getAll()
        binding.button.setOnClickListener {
            val aciton = listDirections.actionListToAdd2(1, "new")
            Navigation.findNavController(it).navigate(aciton)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = adapter(list)
        }
        super.onViewCreated(view, savedInstanceState)
    }


}