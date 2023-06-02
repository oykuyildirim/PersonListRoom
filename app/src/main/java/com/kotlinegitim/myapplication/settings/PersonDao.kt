package com.kotlinegitim.myapplication.settings

import androidx.room.*
import com.kotlinegitim.myapplication.Person

@Dao

interface PersonDao {

        @Insert
        fun insert( person: Person) : Long

        @Query("select * from Person")
        fun getAll() : List<Person>

        @Query("select * from Person where groupPerson like :groupPerson ")
        fun searchGroup( groupPerson: String ) : List<Person>

        @Query("SELECT * FROM Person ORDER BY id DESC LIMIT 10")

        fun lastPerson(): List<Person>


        @Query("select * from Person where id like :id ")
        fun person( id: Int) : Person

        @Query("SELECT * FROM Person WHERE personName like:personName")
        fun search(personName : String) : List<Person>

        @Delete
        fun delete(person: Person)

        @Update
        fun update( person: Person)



}