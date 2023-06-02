package com.kotlinegitim.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.kotlinegitim.myapplication.Database.PersonDatabase

class PersonAdd : AppCompatActivity() {

    lateinit var spinner: Spinner
    lateinit var nameEdit : EditText
    lateinit var surnameEdit : EditText
    lateinit var phoneEdit : EditText
    lateinit var adressEdit : EditText
    lateinit var addBtn : Button
    var group = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_add)


        val groups = resources.getStringArray(R.array.Languages)

        nameEdit = findViewById(R.id.nameperson)
        surnameEdit = findViewById(R.id.personsurname)
        phoneEdit = findViewById(R.id.personnumber)
        adressEdit = findViewById(R.id.personadress)
        addBtn = findViewById(R.id.add)


        spinner = findViewById(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, groups
            )
            spinner.adapter = adapter

        }

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {

                group = groups[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        val db = Room.databaseBuilder(
            this@PersonAdd,
            PersonDatabase::class.java,
            "PersonDatabase"
        ).build()




        addBtn.setOnClickListener { view ->
            val run = Runnable {


                val note = Person(
                    null,
                    nameEdit.text.toString(),
                    surnameEdit.text.toString(),
                    adressEdit.text.toString(),
                    phoneEdit.text.toString(),
                    group
                );
                val status = db.PersonDao().insert(note)
                Log.d("Status", status.toString())


            }
            Thread(run).start()


            Snackbar.make(view, "Kişi Eklendi ${nameEdit.text}", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()



            Handler(Looper.getMainLooper()).postDelayed(
                {


                    var intent = Intent(this@PersonAdd,MainActivity::class.java )
                    startActivity(intent)


                },
                1000
            )


        }
    }


}