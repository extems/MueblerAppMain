package com.example.mueblerapp20.fragmentsMain

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.example.mueblerapp20.R
import com.example.mueblerapp20.classes.Mueble
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */

class PantallaInicio : Fragment() {

    lateinit var v : View

    lateinit var mutableList : MutableList <String>
    lateinit var displayList : MutableList <String>

    lateinit var menuInflater : MenuInflater

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pantalla_inicio, container, false)

        fun OnCreateOptionsMenu(menu: Menu?) {

            menuInflater.inflate(R.menu.searchbar, menu)
            val menuItem = menu!!.findItem(R.id.search)

            if (menuItem != null){

                val searchView = menuItem.actionView as SearchView

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return  true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {

                        if (newText!!.isNotEmpty()){

                            displayList.clear()
                            val search = newText.toLowerCase(Locale.getDefault())
                            mutableList.forEach{

                                if (it.nombreMueble.toLowerCase(Locale.getDefault()).contains(search)){
                                    displayList.add(it)
                                }
                            }

                            recyclerView.adapter!!.notifyDataSetChanged()
                        }

                        else{
                            displayList.clear()
                            displayList.addAll(mutableList)
                            recyclerView.adapter!!.notifyDataSetChanged()
                        }

                        return true
                    }
                })
            }

            return super.onCreateOptionsMenu(menu)
        }

        return v
    }

}