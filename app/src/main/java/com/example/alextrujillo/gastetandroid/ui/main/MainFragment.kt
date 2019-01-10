package com.example.alextrujillo.gastetandroid.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.alextrujillo.gastetandroid.MainActivity
import com.example.alextrujillo.gastetandroid.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        val v = inflater?.inflate(R.layout.main_fragment, container, false)
        return v

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

}
