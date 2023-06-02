package com.kotlinegitim.myapplication.ui.school

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
import com.kotlinegitim.myapplication.MainActivity
import com.kotlinegitim.myapplication.Person
import com.kotlinegitim.myapplication.R
import com.kotlinegitim.myapplication.customadaptors.AllPersonCustomAdaptor
import com.kotlinegitim.myapplication.customadaptors.SchoolPersonAdaptor
import com.kotlinegitim.myapplication.databinding.FragmentSchoolBinding
import com.kotlinegitim.myapplication.ui.home.HomeViewModel

class SchoolFragment : Fragment() {
   // private var _binding: FragmentHomeBinding? = null

    lateinit var listview : ListView
    //lateinit var person : EditText

    lateinit var db :PersonDatabase
    var list = mutableListOf<Person>()
    lateinit var list_filtered: List<Person>
    // This property is only valid between onCreateView and
    // onDestroyView.
   // private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)*/

       /* _binding = FragmentSchoolBinding.inflate(inflater, container, false)
        val root: View = binding.root*/

        val root = inflater.inflate(R.layout.fragment_school,null,true)

        /*val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/



        db = Room.databaseBuilder(
            requireContext(),
            PersonDatabase::class.java,
            "PersonDatabase"
        ).build()

        listview= root.findViewById(R.id.userSchool)
        //person = root.findViewById(R.id.personFind)






        return root
    }

    override fun onResume() {
        val adaptor = SchoolPersonAdaptor(requireActivity(),
            R.layout.school_person_layout,list)

        val run = Runnable {

            val ls = db.PersonDao().searchGroup("School Group");
            for ( item in ls ) {
                Log.d("item", item.toString())
                list.add(item)


            }

            requireActivity().runOnUiThread{

                listview.adapter = adaptor
                adaptor.notifyDataSetChanged()
            }


        }
        Thread(run).start()
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }
}