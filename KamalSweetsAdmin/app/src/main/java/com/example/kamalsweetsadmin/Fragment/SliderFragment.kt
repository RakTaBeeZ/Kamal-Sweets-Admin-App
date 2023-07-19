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
import com.example.kamalsweetsadmin.R
import com.example.kamalsweetsadmin.databinding.FragmentSliderBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class SliderFragment : Fragment() {
   private lateinit var binding:FragmentSliderBinding
   private var imageUrl:Uri?=null
   private lateinit var dialog: Dialog
   private var launchGallaryActivity=registerForActivityResult(
       ActivityResultContracts.StartActivityForResult()){
       if(it.resultCode==Activity.RESULT_OK){
           imageUrl=it.data!!.data
           binding.imageView.setImageURI(imageUrl)
       }
   }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSliderBinding.inflate(layoutInflater)

        dialog=Dialog(requireContext())
        dialog.setContentView(R.layout.progresslayout)
        dialog.setCancelable(false)

        binding.apply {
            imageView.setOnClickListener {
                val intent=Intent(Intent.ACTION_GET_CONTENT)
                intent.type="image/*"
                launchGallaryActivity.launch(intent)
            }
            button.setOnClickListener {
                if(imageUrl==null){
                    Toast.makeText(requireContext(), "Please Select Image", Toast.LENGTH_SHORT).show()
                }else{
                    uploadImage()
                }
            }
        }
        return binding.root
    }

    private fun uploadImage() {
        dialog.show()
        val filename=UUID.randomUUID().toString()+".jpg"
        val refStorage=FirebaseStorage.getInstance().reference.child("slider/$filename")

        refStorage.putFile(imageUrl!!)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener {image->
                    storeData(image.toString())
                }
            }
            .addOnFailureListener{
                dialog.dismiss()
                Toast.makeText(requireContext(), "Something Went Wrong With Storage", Toast.LENGTH_SHORT).show()

            }
    }

    private fun storeData(image: String) {

        val db =Firebase.firestore
        val data= hashMapOf<String,Any>(
            "image" to image
        )

        db.collection("slider").document("item").set(data)
            .addOnSuccessListener {
                dialog.dismiss()
                Toast.makeText(requireContext(), "Slider Uploaded SuccessFully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                dialog.dismiss()
                Toast.makeText(requireContext(), "Something Went Wrong With Firestore", Toast.LENGTH_SHORT).show()
            }

    }


}