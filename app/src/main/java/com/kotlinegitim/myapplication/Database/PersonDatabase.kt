package com.kotlinegitim.myapplication.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kotlinegitim.myapplication.Person
import com.kotlinegitim.myapplication.settings.PersonDao


@Database(entities = [Person::class], version = 1)
abstract class PersonDatabase : RoomDatabase() {

    abstract fun PersonDao() : PersonDao

}