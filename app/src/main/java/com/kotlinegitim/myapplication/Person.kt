package com.kotlinegitim.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Person")
data class Person(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int?,
    val personName: String?,
    val personSurname:String?,
    val adress:String?,
    val phone:String?,
    val groupPerson : String?,

   /* @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val image:ByteArray?*/

)