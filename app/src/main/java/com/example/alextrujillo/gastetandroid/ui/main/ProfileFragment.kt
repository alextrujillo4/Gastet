package com.example.alextrujillo.gastetandroid.ui.main


import android.content.Context
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.example.alextrujillo.gastetandroid.R
import com.example.alextrujillo.gastetandroid.data.model.Post
import com.example.alextrujillo.gastetandroid.data.model.User
import com.example.alextrujillo.gastetandroid.ui.main.adapter.PostAdapter
import com.example.alextrujillo.gastetandroid.util.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.main_activity.*
import java.util.ArrayList


lateinit var usernameTV : TextView
lateinit var emailTV : TextView
lateinit var porfileImageIV : ImageView

lateinit var  ref : DatabaseReference
lateinit var my_posts_recycler : RecyclerView
val POST_MY_POSTS : ArrayList<Post> = ArrayList()

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


        setHasOptionsMenu(true)
        my_posts_recycler = v.findViewById(R.id.my_post_recycler)
        val llm = LinearLayoutManager(context)
        llm.orientation = RecyclerView.HORIZONTAL
        my_posts_recycler.setLayoutManager(llm)
        getPostsData()
        return v
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ref = Database.getDatabase().getReference("posts")
    }
    private fun getPostsData() {
        val postList = ArrayList<Post>()
        ref.keepSynced(true)
        ref.limitToLast(50).orderByChild("timestamp")
            .addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                postList.clear()
                for (postSnapshot: DataSnapshot in snapshot.getChildren()) {
                    val post = postSnapshot.getValue(Post::class.java)
                    if (post != null) {
                       // if (post.userid == Database.getFireId()){}
                        POST_MY_POSTS.add(post)
                        my_posts_recycler.adapter = PostAdapter(
                            POST_MY_POSTS,
                            POST_MY_POSTS.size,
                            context!!
                        )
                    }

                }
            }
        })

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
