package com.example.alextrujillo.gastetandroid.ui.main.home

import android.content.Context
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alextrujillo.gastetandroid.R
import com.example.alextrujillo.gastetandroid.data.model.Post
import com.google.android.material.button.MaterialButton
import java.util.*


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
        //holder.username_tv.setText(post.user.username)
        holder.posttime_tv.setText(getDate(post.timestamp))
        holder.petname_tv.setText(post.name)
        holder.breed_tv.setText(post.breed)
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
            .into(holder.petImage_iv)

        holder.itemView.setOnClickListener {
            /*Intent intent = new Intent(context, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("OBJTYPE","POST");
                bundle.putSerializable(MYPOST,post);
                intent.putExtra("POSITION", position);
                intent.putExtra(MYBUNDLE,bundle);
                ((MainActivity) context).startActivityForResult(intent,PROYECTO_CODE);*/
            Toast.makeText(context, "Post Clicked", Toast.LENGTH_SHORT).show()
        }

    }

     fun getDate( time : Long) : String {
         val cal = Calendar.getInstance(Locale.ENGLISH)
         //cal.setTimeInMillis(time * 1000)
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
        val peoplesearching_btn: MaterialButton = view.findViewById(R.id.peoplesearching_btn)
        val petImage_iv: ImageView = view.findViewById(R.id.petImage_iv)
        val userImage_iv: ImageView = view.findViewById(R.id.userImage_iv)
        val petsex_iv: ImageView = view.findViewById(R.id.petsex_iv)

    }

}