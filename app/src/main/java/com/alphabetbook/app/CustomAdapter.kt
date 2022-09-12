package com.alphabetbook.app

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class CustomAdapter(var context: Context, var arrayListImage: ArrayList<Int>) : BaseAdapter() {

    //Auto Generated Method
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var myView = convertView
        val holder: ViewHolder

        if (myView == null) {

            val mInflater = (context as Activity).layoutInflater
            myView = mInflater.inflate(R.layout.activity_letter_page, parent, false)
            holder = ViewHolder()

            holder.mImageView = myView!!.findViewById(R.id.imageView) as ImageView
            myView.tag = holder
        } else {
            holder = myView.tag as ViewHolder

        }
        holder.mImageView!!.setImageResource(arrayListImage[position])

        return myView
    }

    override fun getItem(p0: Int): Any {
        return arrayListImage[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return arrayListImage.size
    }
    class ViewHolder {
        var mImageView: ImageView? = null
    }
}
