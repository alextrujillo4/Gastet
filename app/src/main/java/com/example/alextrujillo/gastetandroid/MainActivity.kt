package com.example.alextrujillo.gastetandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity(), View.OnClickListener,NavController.OnDestinationChangedListener{

    private var mAuth: FirebaseAuth? = null

    override fun onResume() {
        super.onResume()
        FirebaseApp.initializeApp(this)
        mAuth = FirebaseAuth.getInstance()
        if (FirebaseAuth.getInstance().currentUser == null){
            bottomNavigationView.visibility = View.GONE
            mainAppBarLayout.visibility = View.GONE
        }else{
            bottomNavigationView.visibility =  View.VISIBLE
            mainAppBarLayout.visibility=  View.VISIBLE
        }
    }




    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {

        when (destination.id) {
            R.id.homeFragment -> {
                maintoolbarTitle.setText("GASTET")
            }
            R.id.postFragment -> {
                maintoolbarTitle.setText("Anuncio")
            }
            R.id.profileFragment -> {
                maintoolbarTitle.setText("Perfil")
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
            mainAppBarLayout.visibility=  View.VISIBLE

    }



    override fun onSupportNavigateUp() =
        Navigation.findNavController(this, R.id.my_nav_host_fragment).navigateUp()



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.cerrarSesion) {
            FirebaseAuth.getInstance().signOut()
            bottomNavigationView.visibility = View.GONE
            mainAppBarLayout.visibility = View.GONE
            findNavController(R.id.my_nav_host_fragment).navigate(R.id.loginFragment,null, NavOptions.Builder()
                    .setPopUpTo(R.id.homeFragment, true)
                    .build())
        }
        return super.onOptionsItemSelected(item)
    }






}
