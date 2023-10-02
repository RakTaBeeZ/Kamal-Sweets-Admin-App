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
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView.INVISIBLE
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import com.example.kamalsweetsadmin.Adapter.AddProductImageAdapter
import com.example.kamalsweetsadmin.Model.AddProductModel
import com.example.kamalsweetsadmin.Model.CategoryModel
import com.example.kamalsweetsadmin.R
import com.example.kamalsweetsadmin.databinding.FragmentAddProductBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class AddProductFragment : Fragment() {
    private lateinit var binding:FragmentAddProductBinding
    private lateinit var list: ArrayList<Uri>
    private lateinit var listImage:ArrayList<String>
    private lateinit var adapter:AddProductImageAdapter
    private var coverImage:Uri?=null
    private lateinit var dialog: Dialog
    private var coverImageUrl:String?=""
    private lateinit var categoryList:ArrayList<String>
    private var launchGallaryCoverImage=registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode== Activity.RESULT_OK){
            coverImage=it.data!!.data
            binding.productCoverImage.setImageURI(coverImage)
            binding.productCoverImage.visibility= VISIBLE
        }
    }
    private var launchGallaryProductImage=registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode== Activity.RESULT_OK){
            val imageUrl=it.data!!.data
            list.add(imageUrl!!)
           adapter.notifyDataSetChanged()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAddProductBinding.inflate(layoutInflater)
        list= ArrayList()
        listImage= ArrayList()
        dialog= Dialog(requireContext())
        dialog.setContentView(R.layout.progresslayout)
        dialog.setCancelable(false)
        binding.coverImageButton.setOnClickListener {
            val intent=Intent(Intent.ACTION_GET_CONTENT)
            intent.type="image/*"
            launchGallaryCoverImage.launch(intent)
        }

        binding.productImageButton.setOnClickListener {
            val intent=Intent(Intent.ACTION_GET_CONTENT)
            intent.type="image/*"
            launchGallaryProductImage.launch(intent)
        }

        adapter= AddProductImageAdapter(list)
        binding.productImageRecyclerview.adapter=adapter
        setProductCategory()
        binding.submitProductButton.setOnClickListener {
            validateData()
        }
        return binding.root
    }

    private fun validateData() {
        binding.apply {
            if (productNameEdt.text.toString().isEmpty()){
                productNameEdt.requestFocus()
                productNameEdt.error="Empty"
            }else if (productDiscEdt.text.toString().isEmpty()){
                productDiscEdt.requestFocus()
                productDiscEdt.error="Empty"
            }else if (productMrpEdt.text.toString().isEmpty()){
                productMrpEdt.requestFocus()
                productMrpEdt.error="Empty"
            }else if (productSpEdt.text.toString().isEmpty()){
                productSpEdt.requestFocus()
                productSpEdt.error="Empty"
            }else if(coverImage==null)
                Toast.makeText(requireContext(), "Please Select Cover Image", Toast.LENGTH_SHORT).show()
            else if(list.size<1){
                Toast.makeText(requireContext(), "Please Select Product Image", Toast.LENGTH_SHORT).show()

            }else{
                uploadCoverImage()
            }
        }

    }

    private fun uploadCoverImage() {
        dialog.show()
        val filename= UUID.randomUUID().toString()+".jpg"
        val refStorage= FirebaseStorage.getInstance().reference.child("products/$filename")

        refStorage.putFile(coverImage!!)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener {image->
                    coverImageUrl=image.toString()
                    uploadProductImages()
                }
            }
            .addOnFailureListener{
                dialog.dismiss()
                Toast.makeText(requireContext(), "Something Went Wrong With CoverImage", Toast.LENGTH_SHORT).show()

            }
    }
    private var i=0
    private fun uploadProductImages() {
        val filename= UUID.randomUUID().toString()+".jpg"
        val refStorage= FirebaseStorage.getInstance().reference.child("products/$filename")

        refStorage.putFile(list[i]!!)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener {image->
                    listImage.add(image!!.toString())
                    if(list.size==listImage.size)
                        storeData()
                    else{
                        i+=1
                        Toast.makeText(requireContext(), "$i", Toast.LENGTH_SHORT).show()
                        uploadProductImages()
                    }

                }
            }
            .addOnFailureListener{
                dialog.dismiss()
                Toast.makeText(requireContext(), "Something Went Wrong With ProductImage", Toast.LENGTH_SHORT).show()

            }    }

    private fun storeData() {
         val db =Firebase.firestore.collection("products")
         val key=db.document().id

        val data=AddProductModel(
            binding.productNameEdt.text.toString(),
            binding.productDiscEdt.text.toString(),
            coverImageUrl.toString(),
            categoryList[binding.productCategoryDropdown.selectedItemPosition],
            key,
            binding.productMrpEdt.text.toString(),
            binding.productSpEdt.text.toString(),
            listImage
            )
        db.document(key).set(data).addOnSuccessListener {
            dialog.dismiss()
            Toast.makeText(requireContext(), "Product Added Successfully", Toast.LENGTH_SHORT).show()
            binding.apply {
                productNameEdt.text=null
                productDiscEdt.text=null
                productMrpEdt.text=null
                productSpEdt.text=null
                productCoverImage.visibility= INVISIBLE
                productCategoryDropdown.setSelection(0)
                productNameEdt.requestFocus()
            }

        }.addOnFailureListener {
            dialog.dismiss()
            Toast.makeText(requireContext(), "Product Adding Failed", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setProductCategory(){

        categoryList= ArrayList()
        Firebase.firestore.collection("categories").get().addOnSuccessListener {
            categoryList.clear()
            for(doc in it.documents){
                val data = doc.toObject(CategoryModel::class.java)
                categoryList.add(data!!.cat!!)
            }
            categoryList.add(0,"Select Category")
            val arrayAdapter=ArrayAdapter(requireContext(),R.layout.dropdown_item_layout,categoryList)
            binding.productCategoryDropdown.adapter=arrayAdapter
        }

    }


}