package com.example.alextrujillo.gastetandroid.ui.main


import android.os.Bundle
import android.view.*
import android.widget.Toast

import com.example.alextrujillo.gastetandroid.R

class HomeFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View =  inflater.inflate(R.layout.fragment_home, container, false)
        setHasOptionsMenu(true)
        return v
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.mapa) {
            Toast.makeText(context, "MAPA", Toast.LENGTH_SHORT).show()

        }
        return super.onOptionsItemSelected(item)
    }
}
