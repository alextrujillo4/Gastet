package com.example.alextrujillo.gastetandroid.ui.main.home


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.alextrujillo.gastetandroid.R
import com.example.alextrujillo.gastetandroid.data.model.Post
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.ArrayList
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import android.text.method.TextKeyListener.clear
import com.example.alextrujillo.gastetandroid.util.Database
import com.google.firebase.database.ValueEventListener
import com.google.firebase.FirebaseError




class HomeFragment : androidx.fragment.app.Fragment() {
    lateinit var  ref : DatabaseReference
    lateinit var lost_pets_recycler : RecyclerView
    val POST_LIST_LOST : ArrayList<Post>  = ArrayList()

    lateinit var found_pets_recycler : RecyclerView
    val POST_LIST_FOUND : ArrayList<Post>  = ArrayList()

    lateinit var adopt_pets_recycler : RecyclerView
    val POST_LIST_ADOPT : ArrayList<Post>  = ArrayList()

    lateinit var postadapter : PostAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ref = Database.getDatabase().getReference("posts")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View =  inflater.inflate(R.layout.fragment_home, container, false)
        setHasOptionsMenu(true)
        lost_pets_recycler = v.findViewById(R.id.lost_pets_recycler)
        found_pets_recycler = v.findViewById(R.id.found_pets_recycler)
        adopt_pets_recycler = v.findViewById(R.id.adoption_pets_recycler)

        val llm = LinearLayoutManager(context)
        val llm2 = LinearLayoutManager(context)
        val llm3 = LinearLayoutManager(context)
        llm.orientation = RecyclerView.HORIZONTAL
        llm2.orientation = RecyclerView.HORIZONTAL
        llm3.orientation = RecyclerView.HORIZONTAL
        lost_pets_recycler.setLayoutManager(llm)
        lost_pets_recycler.setHasFixedSize(true)

        found_pets_recycler.setLayoutManager(llm2)
        found_pets_recycler.setHasFixedSize(true)

        adopt_pets_recycler.setLayoutManager(llm3)
        adopt_pets_recycler.setHasFixedSize(true)
        getPostData()
        return v
    }




    private fun getPostData() {
        val postList = ArrayList<Post>()
        ref.keepSynced(true);
        ref.limitToLast(50).orderByChild("timestamp").addValueEventListener( object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                postList.clear()
                for (postSnapshot: DataSnapshot in snapshot.getChildren()) {
                    val post = postSnapshot.getValue(Post::class.java)
                    when (post?.postType) {
                        "lost" ->  POST_LIST_LOST.add(post)
                        "found" -> POST_LIST_FOUND.add(post)
                        "adopt" -> POST_LIST_ADOPT.add(post)
                    }

                }
                lost_pets_recycler.adapter  = PostAdapter(POST_LIST_LOST, POST_LIST_LOST.size, context!!)
                found_pets_recycler.adapter  = PostAdapter(POST_LIST_FOUND, POST_LIST_FOUND.size, context!!)
                adopt_pets_recycler.adapter  = PostAdapter(POST_LIST_ADOPT, POST_LIST_ADOPT.size, context!!)
            }
        });
    }


    private fun updatePostData() {
        ref.addChildEventListener(
            object : ChildEventListener {
                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                    //var mPost = dataSnapshot.getValue(Post::class.java)
                   // mAdapter.removeItemByObject(mPost)
                }



                override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
                    //var mPost = dataSnapshot.getValue(Post::class.java)
                    //mAdapter.update(mPost)
                }


                override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                    //var mPost = dataSnapshot.getValue(Post::class.java)
                   /* mAdapter.add(mPost)
                    mSwipeMain.isRefreshing = false
                    mSwipeMain.isEnabled = false*/
                }
            })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.mapa) {
            Toast.makeText(context, "MAPA", Toast.LENGTH_SHORT).show()

        }
        return super.onOptionsItemSelected(item)
    }
}
