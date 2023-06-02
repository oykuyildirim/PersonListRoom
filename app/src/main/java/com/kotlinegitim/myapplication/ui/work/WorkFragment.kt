package com.kotlinegitim.myapplication.ui.work

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.kotlinegitim.myapplication.Person
import com.kotlinegitim.myapplication.Database.PersonDatabase
import com.kotlinegitim.myapplication.MainActivity
import com.kotlinegitim.myapplication.R
import com.kotlinegitim.myapplication.customadaptors.WorkUserCustomAdaptor
import com.kotlinegitim.myapplication.databinding.FragmentWorkBinding

class WorkFragment : Fragment() {

    private var _binding: FragmentWorkBinding? = null

    lateinit var listview : ListView
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var list = mutableListOf<Person>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(WorkViewModel::class.java)

        _binding = FragmentWorkBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/




        val db = Room.databaseBuilder(
            requireContext(),
            PersonDatabase::class.java,
            "PersonDatabase"
        ).build()

        listview= root.findViewById(R.id.user)

        val adaptor = WorkUserCustomAdaptor(requireActivity(),R.layout.work_user_custom_layout,list)

        Thread {


            val ls = db.PersonDao().searchGroup("Work Group");
            for ( item in ls ) {
                Log.d("item", item.toString())
                list.add(item)

            }

            requireActivity().runOnUiThread {

                listview.adapter = adaptor

                adaptor.notifyDataSetChanged()
            }

        }.start()











       // db.close()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}