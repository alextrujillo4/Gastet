package com.example.alextrujillo.gastetandroid.ui.ui


import android.os.Bundle
import android.app.Fragment
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment

import com.example.alextrujillo.gastetandroid.R
import com.example.alextrujillo.gastetandroid.R.id.loginFragment
import com.example.alextrujillo.gastetandroid.R.layout.fragment_home
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.main_activity.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class LoginFragment : androidx.fragment.app.Fragment(), View.OnClickListener {
    private var auth: FirebaseAuth? = null

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        if (FirebaseAuth.getInstance().currentUser != null){
            NavHostFragment.findNavController(this)
                .navigate(R.id.homeFragment,null, NavOptions.Builder()
                    .setPopUpTo(R.id.loginFragment, true)
                    .build())
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_login, container, false)
        val ingresarBtn = v.findViewById<MaterialButton>(R.id.loginingresarButton)
        val resetBtn = v.findViewById<MaterialButton>(R.id.loginResetButton)
        val registrarBtn = v.findViewById<MaterialButton>(R.id.loginRegistrarButton)
        ingresarBtn.setOnClickListener(this)
        resetBtn.setOnClickListener(this)
        registrarBtn.setOnClickListener(this)
        return v
    }


    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.loginRegistrarButton ->{
                Navigation.findNavController(this?.activity!!, R.id.my_nav_host_fragment).navigate(R.id.registrationFragment)
            }
            R.id.loginingresarButton ->{
                var email: String = loginEmailET.text.toString()
                var password: String = loginPasswordET.text.toString()
                //User autentication
                auth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this.activity!!
                    ) { task -> //progressBar.setVisibility(View.GONE)
                        if (!task.isSuccessful) {
                            // if There is an error...
                            if (password.length < 6) {
                                loginPasswordET.setError("Contrasela Incorrecta")
                            } else {
                                Toast.makeText(this.activity!!, "Fallo... Intenta de nuevo ", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(this.activity!!, "Bienvenido " + auth!!.getCurrentUser()!!.email + " .", Toast.LENGTH_LONG).show()
                            Navigation.findNavController(this.activity!!, R.id.my_nav_host_fragment).navigate(loginFragment)

                        }
                    }
            }
        }

    }

}
