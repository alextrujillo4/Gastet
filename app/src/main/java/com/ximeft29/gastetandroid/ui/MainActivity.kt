package com.ximeft29.gastetandroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.ximeft29.gastetandroid.R
import com.ximeft29.gastetandroid.ui.main.PostFragment
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.theartofdev.edmodo.cropper.CropImage
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
               /* mainToolbar.setOverflowIcon(ContextCompat.getDrawable(this,
                    R.drawable.round_location_on_24
                ));*/
            }
            R.id.postFragment -> {
                maintoolbarTitle.setText("Anuncio")
                //mainToolbar.setOverflowIcon(null);
                // mainToolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.round_image_24));
            }
            R.id.profileFragment -> {
                maintoolbarTitle.setText("Perfil")
                /*mainToolbar.setOverflowIcon(ContextCompat.getDrawable(this,
                    R.drawable.round_exit_to_app_24
                ));*/
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






    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 921 ) { //Image on Upload Photo Register User
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment?
            val postFragment = navHostFragment!!.childFragmentManager.fragments[0] as PostFragment
            postFragment.onActivityResult(requestCode, resultCode, intent)
        }else  if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment?
            val postFragment = navHostFragment!!.childFragmentManager.fragments[0] as PostFragment
            postFragment.onActivityResult(requestCode, resultCode, intent)

        }
    }


}
