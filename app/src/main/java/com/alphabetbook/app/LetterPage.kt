package com.alphabetbook.app

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.view.MotionEvent
import android.widget.GridView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.io.File


class LetterPage : AppCompatActivity() {

    //get the image from local memory
    private fun getImage(name: String): Bitmap? {
        val baseDir =   Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                        ).absolutePath
        val fileName = "$name.png"
        val fileToUpload = File(baseDir + File.separator + fileName)
        val filePath = fileToUpload.path

        return BitmapFactory.decodeFile(filePath)
    }

    private fun createIcons() : ArrayList<Int> {
        val arrayListImage = ArrayList<Int>()

        arrayListImage.add(R.drawable.ic_baseline_first_page_24)
        arrayListImage.add(R.drawable.ic_baseline_navigate_before_24)
        arrayListImage.add(R.drawable.ic_baseline_home_24)
        arrayListImage.add(R.drawable.ic_baseline_navigate_next_24)
        arrayListImage.add(R.drawable.ic_baseline_last_page_24)

        return arrayListImage

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun createBottomNav(): GridView? {
        val nav = findViewById<GridView>(R.id.nav_grid)
        nav.isVerticalScrollBarEnabled = false

        val arrayListImage = createIcons()

        val customAdapter = CustomAdapter(this, arrayListImage)
        nav.adapter = customAdapter

        nav.setOnTouchListener { _, event -> //makes view un-scrollable
            event.action == MotionEvent.ACTION_MOVE
        }

        return nav
    }

    private fun navFunctionality(nav : GridView?, _num : Int, imageView: ImageView, alphabet : Array<String>){
        var num = _num
        nav?.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> { //go to first page, letter A
                    num = 0
                    imageView.setImageBitmap(alphabet[num].let { this.getImage(it) })
                }
                1 -> { //go back one letter
                    if(num != 0){
                        num--
                        imageView.setImageBitmap(alphabet[num].let { this.getImage(it) })
                    }
                }
                2 -> { //go back to overview
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                3 -> { //go forward one image
                    if(num != 25){
                        num++
                        imageView.setImageBitmap(alphabet[num].let { this.getImage(it) })
                    }
                }
                4 -> { //go to last image
                    num = 25
                    imageView.setImageBitmap(alphabet[num].let { this.getImage(it) })
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letter_page)

        //get values called from previous intent
        val num = intent.getIntExtra("num", 0)
        val alphabet = intent.getStringArrayExtra("alphabet")
        val name = intent.getStringExtra("name")?.lowercase()

        //set photo that was first clicked
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageBitmap(name?.let { this.getImage(it) })

        //create bottom navigation
        val nav = createBottomNav()

        //nav functionality
        if (alphabet != null) {
            navFunctionality(nav, num, imageView, alphabet)
        }

    }
}