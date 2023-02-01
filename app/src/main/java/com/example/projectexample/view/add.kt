package com.example.projectexample.view

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.core.text.set
import androidx.navigation.Navigation
import androidx.room.Room
import com.example.projectexample.databinding.FragmentAddBinding
import com.example.projectexample.view.model.model
import com.example.projectexample.view.model.modelDao
import com.example.projectexample.view.model.modelDatabase
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.io.IOException


class add : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var dao: modelDao
    private lateinit var db: modelDatabase
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private lateinit var permission: ActivityResultLauncher<String>
    var selectedPicture: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        registerLauncher()
        db = Room.databaseBuilder(requireContext(), modelDatabase::class.java, "model")
            .allowMainThreadQueries().build()
        dao = db.modelDao()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        var id = 0
        var info = "new"
        arguments?.let {
            info = addArgs.fromBundle(it).info
            id = addArgs.fromBundle(it).idw
        }

        if (info != "new") {
            binding.button3.setOnClickListener {
                val b = dao.getmodelbyid(id)
                dao.delete(b)
                val actionw = addDirections.actionAdd2ToList()
                println("girdi")
                Navigation.findNavController(it).navigate(actionw)

            }
            binding.button2.visibility = View.GONE
            val bitmap = BitmapFactory.decodeByteArray(
                dao.getmodelbyid(id).image,
                0,
                dao.getmodelbyid(id).image!!.size
            )
            binding.imageView.setImageBitmap(bitmap)
            binding.editTextTextPersonName.setText(dao.getmodelbyid(id).message)


        } else {
            binding.button3.visibility = View.GONE
            binding.imageView.setOnClickListener {

                if (ContextCompat.checkSelfPermission(
                        requireContext().applicationContext,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    if (shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Snackbar.make(
                            requireView(),
                            "Permission needed",
                            Snackbar.LENGTH_INDEFINITE
                        )
                            .setAction("Permission") {
                                permission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            }.show()
                    } else {
                        permission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)

                    }
                } else {
                    val intentToGallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startForResult.launch(intentToGallery)
                }

                super.onViewCreated(view, savedInstanceState)
            }
            binding.button2.setOnClickListener {
                save(view)
                val actionw = addDirections.actionAdd2ToList()
                println("girdi")
                Navigation.findNavController(it).navigate(actionw)
            }

        }


    }


    fun save(view: View) {

        if (binding.imageView.drawable != null && binding.editTextTextPersonName.text.toString() != "") {
            val outputStream = ByteArrayOutputStream()
            selectedPicture?.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
            val byte = outputStream.toByteArray()
            val message = binding.editTextTextPersonName.text.toString()
            val a = model(byte, message)
            dao.insert(a)
        }


    }

    private fun registerLauncher() {
        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == AppCompatActivity.RESULT_OK) {
                    val intent = it.data
                    if (intent != null) {
                        val ssdf = intent.data
                        try {
                            if (Build.VERSION.SDK_INT >= 28) {
                                val source = ImageDecoder.createSource(
                                    requireActivity().contentResolver,
                                    ssdf!!
                                )

                                selectedPicture = ImageDecoder.decodeBitmap(source)
                                binding.imageView.setImageBitmap(selectedPicture)
                            }

                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }

                    }
                }
            }
        permission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {

                val intentToGallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startForResult.launch(intentToGallery)
            } else {
                Toast.makeText(requireContext(), "Needed Permission", Toast.LENGTH_LONG).show()
            }

        }
    }


}