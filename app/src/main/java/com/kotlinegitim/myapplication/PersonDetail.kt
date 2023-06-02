package com.kotlinegitim.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.kotlinegitim.myapplication.Database.PersonDatabase

class PersonDetail : AppCompatActivity() {

    lateinit var adress : TextView
    lateinit var name : TextView
    lateinit var surname : TextView
    lateinit var phone : TextView
    lateinit var group: TextView
    lateinit var deleteBtn : Button
    lateinit var updateBtn : Button
    lateinit var user : Person

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_detail)

        adress = findViewById(R.id.adress_detail)
        name = findViewById(R.id.name_detail)
        surname = findViewById(R.id.surname_detail)
        group = findViewById(R.id.group_detail)
        phone = findViewById(R.id.phone_detail)
        deleteBtn = findViewById(R.id.delete)
        updateBtn = findViewById(R.id.update)




        val db = Room.databaseBuilder(
            this@PersonDetail,
            PersonDatabase::class.java,
            "PersonDatabase"
        ).build()


        var id = intent.getIntExtra("id", 0)

        Thread {

            user = db.PersonDao().person(id)


            println(user)

            runOnUiThread{

                name.text = user.personName
                surname.text = user.personSurname
                phone.text = user.phone
                group.text = user.groupPerson
                adress.text = user.adress
                adress.movementMethod = ScrollingMovementMethod()


            }
        }.start()

        deleteBtn.setOnClickListener{ view ->

            Thread{


                db.PersonDao().delete(user)


            }.start()
            Snackbar.make(view, "Kişi Silindi : ${user.personName}", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            Handler(Looper.getMainLooper()).postDelayed(
                {
                    var intent = Intent(this@PersonDetail, MainActivity::class.java)
                    startActivity(intent)
                },
                1000
            )



        }

        updateBtn.setOnClickListener{

            var intent = Intent(this@PersonDetail, UpdatePerson::class.java)
            intent.putExtra("name_upt",user.personName)
            intent.putExtra("surname_upt",user.personSurname)
            intent.putExtra("adress_upt",user.adress)
            intent.putExtra("group_upt",user.groupPerson)
            intent.putExtra("phone_upt",user.phone)
            intent.putExtra("id_upt",user.id)

            startActivity(intent)
        }





    }
}