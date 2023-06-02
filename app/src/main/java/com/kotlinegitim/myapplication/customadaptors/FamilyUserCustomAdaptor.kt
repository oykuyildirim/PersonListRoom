package com.kotlinegitim.myapplication.customadaptors

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.kotlinegitim.myapplication.Person
import com.kotlinegitim.myapplication.PersonDetail
import com.kotlinegitim.myapplication.R

class FamilyUserCustomAdaptor(private val context: Activity, private val resource: Int, private val objects: MutableList<Person>) :
    ArrayAdapter<Person>(context, resource, objects) {

    lateinit var name_family : TextView
    lateinit var surname_family : TextView

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val root = context.layoutInflater.inflate(resource,null,true)

        name_family = root.findViewById(R.id.name_family)
        surname_family = root.findViewById(R.id.surname_family)

        var person = objects.get(position)

        name_family.text = person.personName
        surname_family.text = person.personSurname
        root.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                var intent = Intent(context, PersonDetail::class.java)
                intent.putExtra("id",person.id)
                context.startActivity(intent)

            }
        })


        return root
    }
}