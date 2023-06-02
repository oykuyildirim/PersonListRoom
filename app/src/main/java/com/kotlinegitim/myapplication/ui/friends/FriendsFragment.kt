package com.kotlinegitim.myapplication.ui.friends

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.kotlinegitim.myapplication.Database.PersonDatabase
import com.kotlinegitim.myapplication.MainActivity
import com.kotlinegitim.myapplication.Person
import com.kotlinegitim.myapplication.R
import com.kotlinegitim.myapplication.customadaptors.FamilyUserCustomAdaptor
import com.kotlinegitim.myapplication.customadaptors.FriendsUserCustomAdaptor

class FriendsFragment : Fragment() {


    lateinit var listview : ListView
    var list = mutableListOf<Person>()

    lateinit var db : PersonDatabase
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_friends,null,true)
        listview= root.findViewById(R.id.userfriends)



        db = Room.databaseBuilder(
            requireContext(),
            PersonDatabase::class.java,
            "PersonDatabase"
        ).build()




        return root
    }

    override fun onResume() {

        val adaptor = FriendsUserCustomAdaptor(requireActivity(), R.layout.friends_user_custom_layout,list)

        Thread{

            val ls = db.PersonDao().searchGroup("Friends Group");
            for ( item in ls ) {
                Log.d("item", item.toString())
                list.add(item)

            }

            requireActivity().runOnUiThread {

                listview.adapter = adaptor
                adaptor.notifyDataSetChanged()

            }

        }.start()
        super.onResume()
    }
}