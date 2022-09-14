package com.alphabetbook.app

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.widget.ArrayAdapter
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files.exists


class MainActivity : AppCompatActivity() {

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }

    private fun moveImages(extStorageDirectory: String){

        for (i in 65..90) {

            val id = resources.getIdentifier(i.toChar().lowercase(), "drawable", packageName)
            val bm = BitmapFactory.decodeResource(resources, id)

            val file = File(extStorageDirectory, "${i.toChar().lowercase()}.png")
            val outStream = FileOutputStream(file)
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream)

            outStream.flush()
            outStream.close()
        }
    }

    private fun checkIfImagesExist(extStorageDirectory: String) : Boolean {
        for (i in 65..90){
            val file = File(extStorageDirectory, "${i.toChar().lowercase()}.png")
            if(!exists(file.toPath())){
                return false
            }
        }
        return true
    }

    private fun createAlphabet() : Array<String> {
        val alphabet = Array(26){""}
        var counter=65
        for (i in 0..25){
            alphabet[i] = counter.toChar().toString()
            counter++
        }

        return alphabet
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //check if the app has permission to access local storage, and allow user to change
        verifyStoragePermissions(this)

        //check if the images exist in local storage
        val extStorageDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString()
        if(!checkIfImagesExist(extStorageDirectory)) { //if images aren't in local storage, move them there from drawable
            moveImages(extStorageDirectory)
        }

        //gridview for alphabets overview
        val gridview = findViewById<GridView>(R.id.gridview)

        //create alphabet array
        val alphabet = createAlphabet()

        //set gridview to alphabets
        val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_dropdown_item_1line, alphabet)
        gridview.adapter = adapter

        //go to letter when clicked
        gridview.setOnItemClickListener { _, _, i, _ ->
            val intent = Intent(applicationContext, LetterPage::class.java)

            intent.putExtra("alphabet", alphabet)
            intent.putExtra("num", i)
            intent.putExtra("name", alphabet[i])

            startActivity(intent)
        }
    }
}