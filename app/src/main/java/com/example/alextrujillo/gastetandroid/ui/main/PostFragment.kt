package com.example.alextrujillo.gastetandroid.ui.main


import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.example.alextrujillo.gastetandroid.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_post.*


class PostFragment : androidx.fragment.app.Fragment() , View.OnClickListener, TabLayout.OnTabSelectedListener{



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v : View =  inflater.inflate(R.layout.fragment_post, container, false)
        Log.w("PostFragment: ","Entrando...")

        val postAgregarImagenBtn = v.findViewById<MaterialButton>(R.id.postAgregarImagenBtn)
        val postAgregarBtn = v.findViewById<MaterialButton>(R.id.postAgregarBtn)
        postAgregarImagenBtn.setOnClickListener(this)
        postAgregarBtn.setOnClickListener(this)

        val postPostTypeTabLayout = v.findViewById<TabLayout>(R.id.postPostTypeTabLayout)
        val postPetypeTabLayout = v.findViewById<TabLayout>(R.id.postPetypeTabLayout)
        val postRecompensaTabLayout = v.findViewById<TabLayout>(R.id.postRecompensaTabLayout)
        val postGenderTabLayout = v.findViewById<TabLayout>(R.id.postGenderTabLayout)


        val postVacunadoTV = v.findViewById<TextView>(R.id.postVacunadoTV)
        val postVacunadoTabLayout = v.findViewById<TabLayout>(R.id.postVacunadoTabLayout)
        postPostTypeTabLayout.addOnTabSelectedListener(this)
        postPetypeTabLayout.addOnTabSelectedListener(this)
        postRecompensaTabLayout.addOnTabSelectedListener(this)
        postGenderTabLayout.addOnTabSelectedListener(this)
        postVacunadoTabLayout.addOnTabSelectedListener(this)

        postVacunadoTV.visibility = View.GONE
        postVacunadoTabLayout.visibility = View.GONE
        return v
    }



    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.postAgregarImagenBtn -> {
                if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 2000)
                } else {
                    //val cameraIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    //startActivityForResult(cameraIntent, 921)
                    // for fragment (DO NOT use `getActivity()`)
                    val cameraIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(cameraIntent, 921)

                }
            }
        }
    }


    private  var postTypeIndicator : Int = 0
    private  var animaltypeIndicator : Int = 0
    private  var hayRecompensa : Boolean = true
    private  var isMale : Boolean = true
    private  var isVacunado : Boolean = true


    override fun onTabSelected(tab: TabLayout.Tab?) {
        Log.d("TabLayout: ","onTabSelected : ${tab!!.text}")
        when(tab.text){//Tab selected diferenciado por String
            //=== Tipo de Post
            getString(R.string.perdido)->{
                Log.d("TAB: ","perdido")
                postTypeIndicator= 0
                postDasRecompTV.visibility = View.VISIBLE
                postRecompensaTabLayout.visibility = View.VISIBLE
                postNombreTIL.visibility = View.VISIBLE

                postCostoET.visibility = View.VISIBLE
                postVacunadoTV.visibility = View.GONE
                postVacunadoTabLayout.visibility = View.GONE

                if (hayRecompensa){
                    postCostoTIL.visibility = View.VISIBLE
                }else{
                    postCostoTIL.visibility = View.GONE
                }


            }
            getString(R.string.encontrado)->{
                postTypeIndicator= 1
                Log.d("TAB: ","encontrado")
                postDasRecompTV.visibility = View.GONE
                postRecompensaTabLayout.visibility = View.GONE
                postCostoTIL.visibility = View.GONE
                postNombreTIL.visibility = View.GONE

                postVacunadoTV.visibility = View.GONE
                postVacunadoTabLayout.visibility = View.GONE

            }
            getString(R.string.adopci_n)->{
                postTypeIndicator= 2
                Log.d("TAB: ","adopci_n")
                postDasRecompTV.visibility = View.GONE
                postRecompensaTabLayout.visibility = View.GONE
                postNombreTIL.visibility = View.GONE

                postCostoTIL.visibility = View.GONE
                postVacunadoTV.visibility = View.VISIBLE
                postVacunadoTabLayout.visibility = View.VISIBLE

            }

            //=== Tipo de Mascota
            getString(R.string.perro)->{
                Log.d("TAB: ","perro")


            }
            getString(R.string.gato)->{
                Log.d("TAB: ","gato")


            }
            getString(R.string.otro)->{
                Log.d("TAB: ","otro")


            }

            //=== Tipo de recompensa
            getString(R.string.si)->{
                Log.d("TAB: ","si")
                if (postTypeIndicator==0){
                    if (!hayRecompensa)
                        hayRecompensa=true
                        postCostoTIL.visibility = View.VISIBLE

                }

            }
            getString(R.string.no)->{
                Log.d("TAB: ","no")
                if (postTypeIndicator==0){
                    if (hayRecompensa)
                        hayRecompensa=false
                        postCostoTIL.visibility = View.GONE

                }

            }

            //=== Tipo de Sexo
            getString(R.string.macho)->{
                Log.d("TAB: ","macho")


            }
            getString(R.string.hembra)->{
                Log.d("TAB: ","hembra")


            }

            //=== Tipo vacunado
            getString(R.string.claro)->{
                Log.d("TAB: ","Claro")
                if (postTypeIndicator == 2){
                    if (!hayRecompensa)
                    isVacunado = true
                }


            }
            getString(R.string.no_lo_creo)->{
                Log.d("TAB: ","No lo creo")
                if (postTypeIndicator == 2){
                    if (hayRecompensa)
                    isVacunado = false
                }

            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        Log.w("TabLayout: ","onTabUnselected...")
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        Log.w("TabLayout: ","onTabReselected...")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Toast.makeText(context,"Req detected ${resultCode} - ${data}",Toast.LENGTH_SHORT).show()

        //super method removed
        if (resultCode == RESULT_OK && requestCode == 921) {
            Toast.makeText(context, "REQUEST GET IMAGE", Toast.LENGTH_SHORT).show()
            val returnUri = data!!.data
            CropImage.activity(returnUri).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(300,300)
                .start(context!!, this)

        }else  if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(context, "CROP_IMAGE_ACTIVITY_REQUEST_CODE", Toast.LENGTH_SHORT).show()
            val  result : CropImage.ActivityResult= CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                val resultUri : Uri = result.getUri();

                Glide.with(context!!).load(resultUri).apply(RequestOptions().circleCrop()).into(postImageIV)

            }
        }
    }


}
