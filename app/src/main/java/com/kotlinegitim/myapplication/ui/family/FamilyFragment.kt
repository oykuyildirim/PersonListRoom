package com.kotlinegitim.myapplication.ui.family

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.kotlinegitim.myapplication.Database.PersonDatabase
import com.kotlinegitim.myapplication.MainActivity
import com.kotlinegitim.myapplication.Person
import com.kotlinegitim.myapplication.R
import com.kotlinegitim.myapplication.customadaptors.FamilyUserCustomAdaptor
import com.kotlinegitim.myapplication.customadaptors.PlayUserCustomAdaptor
import com.kotlinegitim.myapplication.databinding.FragmentPlayBinding
import com.kotlinegitim.myapplication.ui.play.PlayViewModel

class FamilyFragment : Fragment() {



    //private var _binding: FragmentPlayBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    //private val binding get() = _binding!!

    lateinit var listview : ListView
    lateinit var db : PersonDatabase

    var list = mutableListOf<Person>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_family,null,true)
        listview= root.findViewById(R.id.userfamily)




        db = Room.databaseBuilder(
            requireContext(),
            PersonDatabase::class.java,
            "PersonDatabase"
        ).build()





        return root
    }

    override fun onResume() {

        val adaptor = FamilyUserCustomAdaptor(requireActivity(), R.layout.family_user_custom_layout,list)

        Thread{

            val ls = db.PersonDao().searchGroup("Family Group");
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

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }
}