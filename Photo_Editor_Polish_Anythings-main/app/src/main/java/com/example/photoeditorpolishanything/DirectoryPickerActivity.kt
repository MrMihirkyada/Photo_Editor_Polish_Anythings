package com.example.photoeditorpolishanything

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photoeditorpolishanything.Adapter.DirectoryAdapter
import com.example.photoeditorpolishanything.databinding.ActivityDirectoryPickerBinding
import java.io.File

class DirectoryPickerActivity : AppCompatActivity() {

    lateinit var binding : ActivityDirectoryPickerBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DirectoryAdapter
    private var currentDirectory: File = Environment.getExternalStorageDirectory()
    private val REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 1
    private lateinit var directoryAdapter: DirectoryAdapter
    private lateinit var directoryList: MutableList<File>





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDirectoryPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.lightBlack)
            window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
        }

        initview()
    }

    private fun initview() {

        directoryList = mutableListOf()
        directoryAdapter = DirectoryAdapter(directoryList) { directory ->
            // Handle directory click
            Toast.makeText(this, "Clicked: ${directory.name}", Toast.LENGTH_SHORT).show()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewFolder)

        // Initialize directory list and adapter
        directoryList = mutableListOf()
        directoryAdapter = DirectoryAdapter(directoryList) { directory ->
            // Handle directory click
            openDirectory(directory)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = directoryAdapter

        // Set the initial directory to the root directory
        currentDirectory = Environment.getExternalStorageDirectory()
        loadDirectories(currentDirectory)


        binding.imgback.setOnClickListener {
            onBackPressed()
        }


        binding.lnrSelectFolder.setOnClickListener {
//            val folderName = editTextFolderName.text.toString().trim()
//            if (folderName.isNotEmpty()) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                    val folderCreated = createFolder(folderName)
//                    if (folderCreated) {
//                        Toast.makeText(this, "Folder created successfully!", Toast.LENGTH_SHORT).show()
//                    } else {
//                        Toast.makeText(this, "Failed to create folder.", Toast.LENGTH_SHORT).show()
//                    }
//                } else {
//                    Toast.makeText(this, "Permission not granted.", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(this, "Please enter a folder name.", Toast.LENGTH_SHORT).show()
//            }
            showCreateFolderDialog()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun showCreateFolderDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_create_folder, null)
        val editTextFolderName: EditText = dialogView.findViewById(R.id.editTextFolderName)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.buttonDone).setOnClickListener {
            val folderName = editTextFolderName.text.toString().trim()
            editTextFolderName.setTextColor(R.color.white)
            if (folderName.isNotEmpty()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    val folderCreated = createFolder(folderName)
                    if (folderCreated) {
                        loadDirectories(currentDirectory) // Refresh the directory list after creation
                        Toast.makeText(this, "Folder created successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed to create folder.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Permission not granted.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter a folder name.", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()  // Dismiss the dialog after handling
        }

        dialogView.findViewById<Button>(R.id.buttonCancel).setOnClickListener {
            dialog.dismiss()  // Dismiss the dialog if the user cancels
        }

        dialog.setCancelable(false)
        dialog.show()
    }


    private fun createFolder(folderName: String): Boolean {
        val folder = File(currentDirectory, folderName)

        // Check if the directory exists, if not create it
        return if (!folder.exists()) {
            folder.mkdirs()
        } else {
            true
        }
    }

    private fun loadDirectories(directory: File) {
        if (directory.exists()) {
            val directories = directory.listFiles { file -> file.isDirectory }
            if (directories != null) {
                directoryList.clear()
                directoryList.addAll(directories)
                directoryAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun openDirectory(directory: File) {
        currentDirectory = directory
        loadDirectories(directory)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        if (currentDirectory != Environment.getExternalStorageDirectory()) {
            // Navigate up to the parent directory
            currentDirectory = currentDirectory.parentFile ?: Environment.getExternalStorageDirectory()
            loadDirectories(currentDirectory)
        } else {
            super.onBackPressed()
        }
    }


//    private fun loadDirectories() {
//        val directories = currentDirectory.listFiles()?.filter { it.isDirectory } ?: emptyList()
//        adapter = DirectoryAdapter(directories) { selectedDirectory ->
//            if (selectedDirectory.isDirectory) {
//                currentDirectory = selectedDirectory
//                loadDirectories()
//            } else {
//                val resultIntent = Intent().apply {
//                    putExtra("selected_directory", selectedDirectory.absolutePath)
//                }
//                setResult(Activity.RESULT_OK, resultIntent)
//                finish()
//            }
//        }
//        recyclerView.adapter = adapter
//    }
//
//    private fun createFolder(folderName: String): Boolean {
//        // Get the directory for the user's public pictures directory.
//        val folder = File(Environment.getExternalStorageDirectory(), folderName)
//
//        // Check if the directory exists, if not create it
//        if (!folder.exists()) {
//            val success = folder.mkdirs()  // Create the directory, including any necessary but nonexistent parent directories
//            if (success) {
//                println("Directory created successfully")
//            } else {
//                println("Failed to create directory")
//            }
//            return success
//        } else {
//            println("Directory already exists")
//            return true
//        }
//    }
}