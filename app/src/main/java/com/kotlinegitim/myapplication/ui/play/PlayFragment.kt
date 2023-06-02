package com.kotlinegitim.myapplication.ui.play

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.kotlinegitim.myapplication.*
import com.kotlinegitim.myapplication.Database.PersonDatabase
import com.kotlinegitim.myapplication.customadaptors.PlayUserCustomAdaptor
import com.kotlinegitim.myapplication.databinding.FragmentPlayBinding

class PlayFragment : Fragment() {

    private var _binding: FragmentPlayBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    lateinit var db : PersonDatabase
    private val binding get() = _binding!!

    lateinit var listview : ListView
    var list = mutableListOf<Person>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(PlayViewModel::class.java)

        _binding = FragmentPlayBinding.inflate(inflater, container, false)
        val root: View = binding.root

       /* val textView: TextView = binding.textSlideshow
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        listview= root.findViewById(R.id.userplay)

        db = Room.databaseBuilder(
            requireContext(),
            PersonDatabase::class.java,
            "PersonDatabase"
        ).build()



        return root
    }

    override fun onResume() {

        val adaptor = PlayUserCustomAdaptor(requireActivity(),R.layout.play_user_custom_layout,list)

        Thread{

            val ls = db.PersonDao().searchGroup("Play Group");
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
        _binding = null
    }
}