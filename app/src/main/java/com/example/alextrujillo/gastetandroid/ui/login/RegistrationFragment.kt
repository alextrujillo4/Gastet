package com.example.alextrujillo.gastetandroid.ui.login


import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import com.example.alextrujillo.gastetandroid.R
import com.example.alextrujillo.gastetandroid.data.model.User
import com.example.alextrujillo.gastetandroid.util.Database
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_registration.*


private var auth: FirebaseAuth? = null

class RegistrationFragment() : androidx.fragment.app.Fragment(), View.OnClickListener{


    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_registration, container, false)
        val registrarBtn = v.findViewById<MaterialButton>(R.id.registrarButton)
        registrarBtn.setOnClickListener (this)
        val loginBtn = v.findViewById<MaterialButton>(R.id.loginButton)
        loginBtn.setOnClickListener (this)
        return v
    }

    private fun isAValidUser(username: String, email: String, password: String): Boolean {
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(context, "Ups. Ingresa tu nombre de usuario!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(context, "Ups. Ingresa tu email!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "Ups. Ingresa tu contraseña!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.length < 6) {
            Toast.makeText(context, "Contraseña muy corta, el minimo es de  6 caracteres!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    override  fun onClick(v: View?) {
        when(v!!.id) {
            R.id.registrarButton ->{
                var username: String = usernameET.text.toString()
                var email: String = emailET.text.toString()
                var password: String = passwordET.text.toString()
                var myUser = User(username,email)
                if (isAValidUser(username, email, password)) {
                    auth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this.activity!!) { task ->
                        if (!task.isSuccessful) {
                            Toast.makeText(context, "Usuario existente, por favor inicia sesión.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "¡Bienvenido " + username + "!" , Toast.LENGTH_SHORT).show()
                            Database.getDatabase().getReference().child("users").child(FirebaseAuth.getInstance().uid!!).setValue(myUser)
                        }
                    }
                }
            }
            R.id.loginButton -> {
                findNavController(this.activity!!,R.id.my_nav_host_fragment).popBackStack()

            }
        }
    }


}


