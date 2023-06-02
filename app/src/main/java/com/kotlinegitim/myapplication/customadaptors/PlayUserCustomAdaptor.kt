package com.kotlinegitim.myapplication.customadaptors

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.kotlinegitim.myapplication.Person
import com.kotlinegitim.myapplication.PersonDetail
import com.kotlinegitim.myapplication.R

class PlayUserCustomAdaptor(private val context: Activity, private val resource: Int, private val objects: MutableList<Person>) :
    ArrayAdapter<Person>(context, resource, objects) {

    lateinit var name_play : TextView
    lateinit var surname_play : TextView
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val root = context.layoutInflater.inflate(resource,null,true)

        name_play = root.findViewById(R.id.name_play)
        surname_play = root.findViewById(R.id.surname_play)

        val person = objects.get(position)

        name_play.text =  person.personName
        surname_play.text = person.personSurname

       root.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                var intent = Intent(context,PersonDetail::class.java)
                intent.putExtra("id",person.id)
                context.startActivity(intent)

            }
        })

        return root
    }
}