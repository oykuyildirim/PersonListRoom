package com.kotlinegitim.myapplication.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.kotlinegitim.myapplication.Database.PersonDatabase
import com.kotlinegitim.myapplication.Person
import com.kotlinegitim.myapplication.R
import com.kotlinegitim.myapplication.customadaptors.AllPersonCustomAdaptor
import com.kotlinegitim.myapplication.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    lateinit var db : PersonDatabase
    lateinit var listview : ListView
    lateinit var person : EditText
    var list = mutableListOf<Person>()
    var listLast = mutableListOf<Person>()
    lateinit var list_filtered: List<Person>
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        listview= root.findViewById(R.id.userAll)
        person = root.findViewById(R.id.personFind)





        db = Room.databaseBuilder(
            requireContext(),
            PersonDatabase::class.java,
            "PersonDatabase"
        ).build()




        person.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                /*list_filtered = list.filter { item -> item.personName!!.contains(person.text) }
                val adaptor2 = AllPersonCustomAdaptor(requireActivity(),R.layout.all_person_custom_layout,
                    list_filtered as MutableList<Person>
                )
                listview.adapter = adaptor2
                adaptor2.notifyDataSetChanged()*/

                val adaptor2 = AllPersonCustomAdaptor(requireActivity(),R.layout.all_person_custom_layout,list)




                Thread {


                    val ls = db.PersonDao().search(person.text.toString());
                    for ( item in ls ) {
                        Log.d("item", item.toString())
                        list.add(item)

                    }

                    requireActivity().runOnUiThread {

                        listview.adapter = adaptor2
                        adaptor2.notifyDataSetChanged()

                    }



                }.start()

                list.removeAll(list)


            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {


            }
        })







        return root
    }


    override fun onResume() {

        val adaptor = AllPersonCustomAdaptor(requireActivity(),R.layout.all_person_custom_layout,listLast)
        Thread {


            val ls = db.PersonDao().lastPerson();
            for ( item in ls ) {
                Log.d("item", item.toString())
                listLast.add(item)

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
        _binding = null
    }
}