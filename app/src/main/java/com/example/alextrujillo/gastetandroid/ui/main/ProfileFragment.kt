package com.example.alextrujillo.gastetandroid.ui.main


import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment.findNavController

import com.example.alextrujillo.gastetandroid.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.main_activity.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ProfileFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v : View =  inflater.inflate(R.layout.fragment_profile, container, false)
        Log.w("ProfileFragment: ","Entrando...")
        setHasOptionsMenu(true)
        val  imageView  = v.findViewById<ImageView>(R.id.profileProfileImageIV)
        val matrix : Matrix = imageView.getImageMatrix()
        val imageWidth: Float = imageView.getDrawable().getIntrinsicWidth().toFloat()
        val screenWidth : Int = getResources().getDisplayMetrics().widthPixels
        val scaleRatio : Float = screenWidth / imageWidth
        matrix.postScale(scaleRatio, scaleRatio);
        imageView.setImageMatrix(matrix);

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
