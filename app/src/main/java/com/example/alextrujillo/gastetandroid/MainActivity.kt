package com.example.alextrujillo.gastetandroid

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity(), View.OnClickListener,NavController.OnDestinationChangedListener{

    private var mAuth: FirebaseAuth? = null

    override fun onResume() {
        super.onResume()
        FirebaseApp.initializeApp(this)
        mAuth = FirebaseAuth.getInstance()
        if (FirebaseAuth.getInstance().currentUser == null){
            bottomNavigationView.visibility = View.GONE

        }else{
            bottomNavigationView.visibility =  View.VISIBLE

        }
    }




    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        Toast.makeText(this,"This is a listener",Toast.LENGTH_SHORT).show()
        when (destination.id) {
            R.id.homeFragment -> {

            }
            R.id.postFragment -> {

            }
            R.id.profileFragment -> {

            }
        }
    }



    override fun onClick(v: View?) {
        Toast.makeText(this,"Click",Toast.LENGTH_SHORT).show()
        //navigation.setArguments(bundle)
        //navigation.navigation//.show(supportFragmentManager, bottomSheet.getTag())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

            setSupportActionBar(mainToolbar)
            // Setting Toolbar to Tansparent -- Important: AppLayout XML atribute app:elevation="0dp" on activity_main.xml
            mainAppBarLayout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
            mainToolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
            mainToolbar.elevation = 0f
            setSupportActionBar(mainToolbar)
            Navigation.findNavController(this, R.id.my_nav_host_fragment).addOnDestinationChangedListener(this)
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment?
            NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment!!.getNavController())
            bottomNavigationView.visibility =  View.VISIBLE


    }

    override fun onSupportNavigateUp() =
        Navigation.findNavController(this, R.id.my_nav_host_fragment).navigateUp()


    fun setStatusBarColor(view: View, activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = view.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            view.systemUiVisibility = flags
            activity.window.statusBarColor = Color.rgb(200,147,56) //Secondary Color RGB
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.cerrarSesion) {
            // do something
        }
        return super.onOptionsItemSelected(item)
    }





}
