package com.ximeft29.gastetandroid.ui.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.ximeft29.gastetandroid.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.main_activity.*
import androidx.navigation.fragment.NavHostFragment

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

    override fun onResume() {
        super.onResume()
        auth = FirebaseAuth.getInstance()
        if (FirebaseAuth.getInstance().currentUser != null) {
            NavHostFragment.findNavController(this)
                .navigate(
                    R.id.homeFragment, null, NavOptions.Builder()
                        .setPopUpTo(R.id.loginFragment, true)
                        .build()
                )
            activity!!.bottomNavigationView.visibility = View.VISIBLE
            activity!!.mainAppBarLayout.visibility = View.VISIBLE
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
        when (v!!.id) {
            R.id.loginRegistrarButton -> {
                Navigation.findNavController(this.activity!!, R.id.my_nav_host_fragment)
                    .navigate(R.id.registrationFragment)
            }
            R.id.loginingresarButton -> {

                if (loginEmailET.text.isNotEmpty() && loginPasswordET.text.isNotEmpty()) {
                    var email: String = loginEmailET.text.toString()
                    var password: String = loginPasswordET.text.toString()
                    //User autentication
                    auth!!.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            this.activity!!
                        ) { task ->
                            //progressBar.setVisibility(View.GONE)
                            if (!task.isSuccessful) {
                                // if There is an error...
                                if (password.length < 6) {
                                    loginPasswordET.setError("Contrasela Incorrecta")
                                } else {
                                    Toast.makeText(this.activity!!, "Fallo... Intenta de nuevo ", Toast.LENGTH_LONG)
                                        .show()
                                }
                            } else {
                                Toast.makeText(
                                    this.activity!!,
                                    "Bienvenido " + auth!!.getCurrentUser()!!.email + " .",
                                    Toast.LENGTH_LONG
                                ).show()
                                NavHostFragment.findNavController(this)
                                    .navigate(
                                        R.id.homeFragment, null, NavOptions.Builder()
                                            .setPopUpTo(R.id.loginFragment, true)
                                            .build()
                                    )
                                activity!!.bottomNavigationView.visibility = View.VISIBLE
                                activity!!.mainAppBarLayout.visibility = View.VISIBLE

                            }
                        }
                } else {
                    Toast.makeText(this.activity!!, "Por favor, Ingresa tus datos correctamente.", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

}
