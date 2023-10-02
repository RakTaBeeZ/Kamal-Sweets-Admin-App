package com.example.kamalsweetsadmin.Fragment

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.kamalsweetsadmin.Adapter.CategoryAdapter
import com.example.kamalsweetsadmin.Model.CategoryModel
import com.example.kamalsweetsadmin.R
import com.example.kamalsweetsadmin.databinding.FragmentCategoryBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID


class CategoryFragment : Fragment() {
    private lateinit var binding:FragmentCategoryBinding

    private var imageUrl: Uri?=null
    private lateinit var dialog: Dialog
    private var launchGallaryActivity=registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode== Activity.RESULT_OK){
            imageUrl=it.data!!.data
            binding.imageView.setImageURI(imageUrl)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCategoryBinding.inflate(layoutInflater)
        dialog=Dialog(requireContext())
        dialog.setContentView(R.layout.progresslayout)
        dialog.setCancelable(false)
        getData()
        binding.apply {
            imageView.setOnClickListener {
                val intent=Intent(Intent.ACTION_GET_CONTENT)
                intent.type="image/*"
                launchGallaryActivity.launch(intent)
            }
            uploadCategory.setOnClickListener { 
                validateData(binding.categoryName.text.toString())
            }
        }
        return binding.root
    }

    private fun getData() {
        val list =ArrayList<CategoryModel>()
        Firebase.firestore.collection("categories")
            .get().addOnSuccessListener {
            list.clear()
                for(doc in it.documents){
                    val data=doc.toObject(CategoryModel::class.java)
                    list.add(data!!)
                }
                binding.categoryRecycler.adapter=CategoryAdapter(list,requireContext())
            }.addOnFailureListener { error->
                Toast.makeText(requireContext(), "$error", Toast.LENGTH_SHORT).show()
            }
    }

    private fun validateData(categoryName: String) {
        
        if(imageUrl==null){
            Toast.makeText(requireContext(), "Please Select Image", Toast.LENGTH_SHORT).show()
        }else if(categoryName.isEmpty()){
            Toast.makeText(requireContext(), "Please Enter Category Name", Toast.LENGTH_SHORT).show()

        }else{
            uploadImage(categoryName)
        }

    }

    private fun uploadImage(categoryName: String) {
        dialog.show()
        val filename= UUID.randomUUID().toString()+".jpg"
        val refStorage= FirebaseStorage.getInstance().reference.child("category/$filename")

        refStorage.putFile(imageUrl!!)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener {image->
                    storeData(image.toString(),categoryName)
                }
            }
            .addOnFailureListener{
                dialog.dismiss()
                Toast.makeText(requireContext(), "Something Went Wrong With Storage", Toast.LENGTH_SHORT).show()

            }

    }

    private fun storeData(image: String, categoryName: String) {

        val db = Firebase.firestore
        val data= hashMapOf<String,Any>(
            "img" to image,
            "cat" to categoryName
        )

        db.collection("categories").add(data)
            .addOnSuccessListener {
                dialog.dismiss()
                binding.imageView.setImageDrawable(resources.getDrawable(R.drawable.image_preview))
                binding.categoryName.text=null
                getData()
                Toast.makeText(requireContext(), "Category Uploaded SuccessFully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                dialog.dismiss()
                Toast.makeText(requireContext(), "Something Went Wrong With Firestore", Toast.LENGTH_SHORT).show()
            }

    }

}