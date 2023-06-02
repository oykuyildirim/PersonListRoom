package com.kotlinegitim.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.kotlinegitim.myapplication.Database.PersonDatabase

class UpdatePerson : AppCompatActivity() {

    lateinit var Name : EditText
    lateinit var SurName : EditText
    lateinit var Adress : EditText
    lateinit var Phone : EditText
    lateinit var Group : Spinner
    lateinit var Done : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_person)

        var id = intent.getIntExtra("id_upt",0)
        var name = intent.getStringExtra("name_upt")
        var surname = intent.getStringExtra("surname_upt")
        var adress = intent.getStringExtra("adress_upt")
        var phone = intent.getStringExtra("phone_upt")
        var group = intent.getStringExtra("group_upt")

        Name = findViewById(R.id.namepersonUpt)
        SurName = findViewById(R.id.personsurnameUpt)
        Adress = findViewById(R.id.personadressUpt)
        Phone = findViewById(R.id.personnumberUpt)
        Group = findViewById(R.id.spinnerUpt)
        Done = findViewById(R.id.done)


        Name.setText(name)
        SurName.setText(surname)
        Adress.setText(adress)
        Phone.setText(phone)

        val groups = resources.getStringArray(R.array.Languages)



        if (Group != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, groups
            )
            Group.adapter = adapter

            val pos: Int = adapter.getPosition(group)
            Group.setSelection(pos)

        }



        Group.onItemSelectedListener = object :
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
            this@UpdatePerson,
            PersonDatabase::class.java,
            "PersonDatabase"
        ).build()



        Done.setOnClickListener { view ->

            Thread {

                var userupt = Person(
                    id,
                    Name.text.toString(),
                    SurName.text.toString(),
                    Adress.text.toString(),
                    Phone.text.toString(),
                    group
                )
                db.PersonDao().update(userupt)


            }.start()

            //db.close()

            Snackbar.make(view, "Kişi Güncellendi no :${id}", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            Handler(Looper.getMainLooper()).postDelayed(
                {
                    var intent = Intent(this@UpdatePerson, MainActivity::class.java)
                    startActivity(intent)
                },
                1000
            )


        }





    }
}