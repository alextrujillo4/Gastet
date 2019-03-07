package com.example.alextrujillo.gastetandroid.ui.main


import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.example.alextrujillo.gastetandroid.R
import com.example.alextrujillo.gastetandroid.data.model.User
import com.example.alextrujillo.gastetandroid.util.Database
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.main_activity.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

lateinit var usernameTV : TextView
lateinit var emailTV : TextView
lateinit var porfileImageIV : ImageView

class ProfileFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v : View =  inflater.inflate(R.layout.fragment_profile, container, false)
        Log.w("ProfileFragment: ","Entrando...")
        setHasOptionsMenu(true)
        val actualUser : User  = Database.getUser()
        val  imageView  = v.findViewById<ImageView>(R.id.profileProfileImageIV)
        usernameTV = v.findViewById(R.id.username_usr_tv)
        usernameTV.setText(actualUser.username)
        emailTV = v.findViewById(R.id.email_usr_tv)
        emailTV.setText(actualUser.email)
        Glide.with(context!!)
            .load(actualUser.photoUrl)
            .into(imageView)
        return v
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.porfile_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.cerrarSesion) {
            Toast.makeText(context, "cerrarSesion", Toast.LENGTH_SHORT).show()
            FirebaseAuth.getInstance().signOut()

            activity!!.bottomNavigationView.visibility = View.GONE
            activity!!.mainAppBarLayout.visibility = View.GONE

            Toast.makeText(context, "change", Toast.LENGTH_SHORT).show()
            findNavController(this).navigate(R.id.loginFragment,null, NavOptions.Builder()
                .setPopUpTo(R.id.profileFragment, true)
                .build())
        }else if(id == R.id.editUser){
            Toast.makeText(context, "Editar Usuario", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}
