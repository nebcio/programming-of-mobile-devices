package com.pum.sudenthardlife

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import com.pum.sudenthardlife.databinding.DetailListBinding
import java.util.*

class DetailListFragment : Fragment() {
    private lateinit var binding: DetailListBinding
    private lateinit var listList: MutableList<ListElement>
    private var currentId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { currentId = it.getInt("id") }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DetailListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listList = getLists(requireContext()).toMutableList()

        binding.titleTextView.text = listList[currentId].title
        binding.descriptionTextView.text = listList[currentId].description

        binding.titleEditText.setText(listList[currentId].title)    // hidden
        binding.descriptionEditText.setText(listList[currentId].description)

        if (listList[currentId].isUrl) binding.imageView.setImageURI(Uri.parse(listList[currentId].urlImg))

        binding.buttonImg.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED -> {
                    launchCamera()
                }
                ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(), Manifest.permission.CAMERA) -> {
                    showMessageOKCancel(getString(R.string.rationale_camera))
                }
                else -> {
                    requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }
        }

        binding.buttonDel.setOnClickListener {
            listList.removeAt(currentId)
            saveListList(requireContext(), listList)
            findNavController().navigateUp()
        }

        binding.buttonEdit.setOnClickListener {
            if (binding.titleTextView.isVisible) {
                binding.titleTextView.visibility = View.INVISIBLE
                binding.descriptionTextView.visibility = View.INVISIBLE

                binding.titleEditText.visibility = View.VISIBLE
                binding.descriptionEditText.visibility = View.VISIBLE

                binding.buttonEdit.text = "Zapisz"
            }
            else {
                listList[currentId].title = binding.titleEditText.text.toString()
                listList[currentId].description = binding.titleEditText.text.toString()

                binding.titleTextView.text = listList[currentId].title
                binding.descriptionTextView.text = listList[currentId].description

                binding.titleTextView.visibility = View.VISIBLE
                binding.descriptionTextView.visibility = View.VISIBLE

                binding.titleEditText.visibility = View.INVISIBLE
                binding.descriptionEditText.visibility = View.INVISIBLE

                binding.buttonEdit.text = "Edytuj"
                saveListList(requireContext(), listList)
            }
        }
    }

    private val resultLauncherCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val imageBitmap = data?.extras?.get("data") as Bitmap
                binding.imageView.setImageBitmap(imageBitmap)
                listList[currentId].urlImg = saveImage(imageBitmap).toString()
                listList[currentId].isUrl = true
                saveListList(requireContext(), listList)
            }
        }

    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                launchCamera()
            }
        }

    private fun showMessageOKCancel(message: String) {
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("OK") { dialogInterface: DialogInterface, _: Int ->
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                dialogInterface.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }
    private fun launchCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncherCamera.launch(intent)
    }

    private fun saveImage(bitmap: Bitmap): Uri {
        var file = requireContext().getDir("myGalleryKotlin", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return Uri.parse(file.absolutePath)
    }
}