package com.example.alextrujillo.gastetandroid.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.alextrujillo.gastetandroid.R
import com.example.alextrujillo.gastetandroid.data.model.Post
import com.google.android.material.button.MaterialButton
import java.util.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.example.alextrujillo.gastetandroid.data.model.User
import com.example.alextrujillo.gastetandroid.ui.MainActivity
import com.example.alextrujillo.gastetandroid.ui.PostDetailActivity
import com.example.alextrujillo.gastetandroid.util.Database
import org.apache.commons.lang3.text.WordUtils


class PostAdapter(val items: List<Post>, val position: Int,val  context: Context) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = items[position]
        Log.d("POST_INSIDE: ", "123456789")
        holder.posttime_tv.setText(getDate(post.timestamp))

        val petName: String = WordUtils.capitalizeFully(post.name)
        val petBreed: String = WordUtils.capitalizeFully(post.breed)


        if(!post.name.isNullOrEmpty()) {
            holder.petname_tv.setText(petName)
        } else
            holder.petname_tv.setText("Sin nombre")

        holder.breed_tv.setText(petBreed)
        holder.phone_tv.setText(post.phone)


        if (!post.location.city.isNullOrEmpty())
            holder.cities_tv.setText(post.location.city)
        else if (!post.city.isNullOrEmpty())
            holder.cities_tv.setText(post.city)
        else
            holder.cities_tv.setText("Sin Ciudad")



        if (!post.address.isNullOrEmpty()) {
            holder.adress_tv.setText(post.address)
        }else if (!post.location.adress.isNullOrEmpty()){
            holder.adress_tv.setText(post.address)
        }else{
            holder.adress_tv.setText("Sin Dirección")
        }



        Glide.with(context)
            .load(post.photoUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.petImage_iv)


        val database = Database.getDatabase()
            val ref = database.getReference("users/${post.userid}")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)
                    holder.username_tv.setText(user!!.username)
                    Glide.with(context)
                        .load(user.photoUrl)
                        .apply(RequestOptions.circleCropTransform())
                        .into(holder.userImage_iv)

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // ...
                }
            })

        if (post.petType == "dog")
            Glide.with(context).load(R.drawable.pettype_dog).into(holder.peticon_iv)
        else if (post.petType == "cat")
            Glide.with(context).load(R.drawable.pettype_cat).into(holder.peticon_iv)
        else{
            Glide.with(context).load(R.drawable.pettype_other).into(holder.peticon_iv)
        }

        if (post.gender == "male")
            Glide.with(context).load(R.drawable.gender_male).into(holder.petsex_iv)
        else if (post.gender == "female")
            Glide.with(context).load(R.drawable.gender_female).into(holder.petsex_iv)
        else
            holder.petsex_iv.visibility= View.GONE

        when (post.postType) {
            "lost" ->  {
                holder.breed_tv.visibility = View.VISIBLE
            }

            "found" -> {
                holder.breed_tv.visibility = View.GONE
                holder.petname_tv.setText(petBreed)
            }
            "adopt" -> {
                holder.breed_tv.visibility = View.GONE
                holder.petname_tv.setText(petBreed)
            }
        }


        holder.itemView.setOnClickListener {
            val intent = Intent(context, PostDetailActivity::class.java)
            intent.putExtra("studentData", items.get(position))
            startActivity(context, intent, null)
        }

    }

     fun getDate( time : Long) : String {
         val cal = Calendar.getInstance(Locale.ENGLISH)
         return DateFormat.format("EEE d MMM yyyy", cal).toString()
    }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val username_tv: TextView = view.findViewById(R.id.username_tv)
        val posttime_tv: TextView= view.findViewById(R.id.posttime_tv)
        val cities_tv: TextView = view.findViewById(R.id.cities_tv)
        val petname_tv: TextView = view.findViewById(R.id.petname_tv)
        val breed_tv: TextView = view.findViewById(R.id.breed_tv)
        val phone_tv: TextView = view.findViewById(R.id.phone_tv)
        val adress_tv: TextView = view.findViewById(R.id.adress_tv)
        val coments: MaterialButton = view.findViewById(R.id.peoplesearching_btn)
        val petImage_iv: ImageView = view.findViewById(R.id.petImage_iv)
        val userImage_iv: ImageView = view.findViewById(R.id.userImage_iv)
            val peticon_iv: ImageView = view.findViewById(R.id.peticon_iv)
            val petsex_iv: ImageView = view.findViewById(R.id.petsex_iv)

    }

}