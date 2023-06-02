package com.kotlinegitim.myapplication.customadaptors

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.kotlinegitim.myapplication.Person
import com.kotlinegitim.myapplication.R

class AllPersonCustomAdaptor(private val context: Activity, private val resource: Int, private val objects: MutableList<Person>) :
    ArrayAdapter<Person>(context, resource, objects) {

    lateinit var Name : TextView
    lateinit var Surname :TextView
    lateinit var Group : TextView
    lateinit var Phone : TextView

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val root = context.layoutInflater.inflate(resource,null,true)

        Name = root.findViewById(R.id.name)
        Surname = root.findViewById(R.id.surname)
        Group = root.findViewById(R.id.group)
        Phone = root.findViewById(R.id.phone)

        val person = objects.get(position)


        Name.text = person.personName
        Surname.text = person.personSurname
        Group.text = person.groupPerson
        Phone.text = person.phone


        return root
    }

}