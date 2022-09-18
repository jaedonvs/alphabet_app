package com.alphabetbook.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle


class Dispatcher : Activity() {

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

        lateinit var activityClass: Class<*>
        val prefs = getSharedPreferences("X", MODE_PRIVATE)

        try {
            activityClass = prefs.getString("lastActivity", MainActivity::class.java.name)?.let {
                Class.forName(
                    it
                )
            }!!
        } catch (ex: ClassNotFoundException) {
            activityClass = MainActivity::class.java
        }

        if(activityClass == LetterPage::class.java){
            val intent = Intent(this, LetterPage::class.java)

            val alphabet = createAlphabet()

            intent.putExtra("alphabet", alphabet)
            intent.putExtra("num", prefs.getInt("num", 0))
            intent.putExtra("name", prefs.getString("name", "a.png"))

            startActivity(intent)
        }
        else {
            startActivity(Intent(this, activityClass))
        }
    }
}