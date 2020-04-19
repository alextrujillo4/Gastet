package com.ximeft29.gastetandroid.ui.main


import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.ximeft29.gastetandroid.R
import com.ximeft29.gastetandroid.data.model.Location
import com.ximeft29.gastetandroid.data.model.Post
import com.ximeft29.gastetandroid.util.Database
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DatabaseReference
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_post.*


class PostFragment : androidx.fragment.app.Fragment(), View.OnClickListener {


    lateinit var  mDatabase : DatabaseReference
    override fun onStart() {
        super.onStart()
        mDatabase = Database.getDatabase().getReference()

    }
    var perdido: Boolean = true
    var encontrado: Boolean = false
    var adopcion: Boolean = false
    var recompensa: Boolean = true
    var esMacho: Boolean = true
    var petType: String = "PERRO"
    var estaVacunado: Boolean = true
    var hayImagen: Boolean = false

    var raza : String? = null
    var telefono : String? = null
    var nombre : String? = null
    var comentarios : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_post, container, false)
        Log.w("PostFragment: ", "Entrando...")
        val postEscojeImagenBtn = v.findViewById<ImageButton>(R.id.postEscojeImagenBtn)
        postEscojeImagenBtn.setOnClickListener(this)

        //POST TYPE
        val postPerdidoBtn = v.findViewById<MaterialButton>(R.id.postPerdidoBtn)
        postPerdidoBtn.setOnClickListener(this)
        val postEncontradoBtn = v.findViewById<MaterialButton>(R.id.postEncontradoBtn)
        postEncontradoBtn.setOnClickListener(this)
        val postAdopcionBtn = v.findViewById<MaterialButton>(R.id.postAdopcionBtn)
        postAdopcionBtn.setOnClickListener(this)
        //RECOMPENSA
        val postReSiBtn = v.findViewById<MaterialButton>(R.id.postReSiBtn)
        postReSiBtn.setOnClickListener(this)
        val postReNoBtn = v.findViewById<MaterialButton>(R.id.postReNoBtn)
        postReNoBtn.setOnClickListener(this)


        //PET TYPE
        val postPerroBtn = v.findViewById<MaterialButton>(R.id.postPerroBtn)
        postPerroBtn.setOnClickListener(this)
        val postGatoBtn = v.findViewById<MaterialButton>(R.id.postGatoBtn)
        postGatoBtn.setOnClickListener(this)
        val postOtroPetBtn = v.findViewById<MaterialButton>(R.id.postOtroPetBtn)
        postOtroPetBtn.setOnClickListener(this)

        //SEXO
        val postHembraBtn = v.findViewById<MaterialButton>(R.id.postHembraBtn)
        postHembraBtn.setOnClickListener(this)
        val postMachoBtn = v.findViewById<MaterialButton>(R.id.postMachoBtn)
        postMachoBtn.setOnClickListener(this)

        //Vacunado
        val postVDSiBtn = v.findViewById<MaterialButton>(R.id.postVDSiBtn)
        postVDSiBtn.setOnClickListener(this)
        val postVDNoBtn = v.findViewById<MaterialButton>(R.id.postVDNoBtn)
        postVDNoBtn.setOnClickListener(this)

        val postVacunadoCARD = v.findViewById<CardView>(R.id.postVacunadoCARD)
        postVacunadoCARD.visibility = View.GONE

        val postAgregarBtn = v.findViewById<MaterialButton>(R.id.postAgregarBtn)
        postAgregarBtn.setOnClickListener(this)
        return v

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            //IMAGEN DE POST
            R.id.postEscojeImagenBtn -> {
                if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 2000)
                } else {
                    val cameraIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(cameraIntent, 921)
                }
            }
            //TIPO DE POST
            R.id.postPerdidoBtn -> {
                setPostType("PERDIDO")
                postPostQuestionTV.setText("¿Dónde se perdió?")
                postVacunadoCARD.visibility = View.GONE
                postRecompensaCARD.visibility = View.VISIBLE
                setRecompensaStatus(recompensa)
            }
            R.id.postEncontradoBtn -> {
                setPostType("ENCONTRADO")
                postVacunadoCARD.visibility = View.GONE
                postPostQuestionTV.setText("¿Dónde se encontró?")
                postRecompensaCARD.visibility = View.GONE
                postCostoCARD.visibility = View.GONE
                postNombreCARD.visibility = View.GONE
            }
            R.id.postAdopcionBtn -> {
                setPostType("ADOPCION")
                postVacunadoCARD.visibility = View.VISIBLE
                postPostQuestionTV.setText("¿Dónde se da en adopción?")
                postRecompensaCARD.visibility = View.GONE
                postCostoCARD.visibility = View.GONE
                postNombreCARD.visibility = View.GONE
            }
            //RECOMPLENSA
            R.id.postReSiBtn -> setRecompensaStatus(true)
            R.id.postReNoBtn -> setRecompensaStatus(false)
            //PET TYPE
            R.id.postPerroBtn -> setPet("PERRO")
            R.id.postGatoBtn -> setPet("GATO")
            R.id.postOtroPetBtn -> setPet("OTRO")
            //SEXO
            R.id.postHembraBtn -> setSexo("HEMBRA")
            R.id.postMachoBtn -> setSexo("MACHO")
            //VACUNADO
            R.id.postVDSiBtn -> setVacunado(true)
            R.id.postVDNoBtn -> setVacunado(false)
            //AGREGAR POST
            R.id.postAgregarBtn -> {
                if (verifyPost()) {
                    comentarios = postComentariosET.getText().toString().trim()
                    /*
                              (Post constructor) ==> (
                              val photoUrl : String,
                              val postType : String, ==> found, lost, adopt
                               var location : Location,
                               val responsibleAdoption: Boolean,
                               val petType : String, ==> dog, cat, other
                               val name : String,
                               val gender : String, ==> male, female
                               val breed : String?,
                               val reward: Boolean,
                               val price : Double,
                               val timestamp : String, ==> Usar Formato Universal
                               val phone : String,
                               val comments : String?,
                               var userid : String ,   val user : User)

                               #reward, price, responsibleAdoption ==> are not  variables included into all postTypes
                              */
                    val location  = Location("Monterrey", "Av. Revolución, 2000",
                        "Monterrey",0.0,0.0)

                   // val user = User ("Alex Trujillo", "123",
                        //"9212949195", "alexandro4v@gmail.com")


                    when (getPostType()){
                        "PERDIDO" -> {
                            //Add reward, price
                            val perdido  = Post("","lost",location,
                                null, "dog", "Custom Name",
                                "male", "Pug Carlino",
                                "withReward",100,1541045695762,
                                "921 2040 105", "Mi comentario", "123","Av. Sin nombre #1","Monterrey")
                            writtePost(perdido)

                        }
                        "ENCONTRADO" -> {
                            val encontrado  = Post("","found",location,
                                null, "dog", "Custom Name",
                                "male", "Pug Carlino",
                                null,null,1541045695762,
                                "921 2040 105", "Mi comentario", "123","Av. Sin nombre #1","Monterrey")
                            writtePost(encontrado)

                        }
                        "ADOPCION" -> {
                            //Add responsibleAdoption
                            val adopcion  = Post("","found",location,
                                "no", "dog", "Custom Name",
                                "male", "Pug Carlino",
                                null,null,1541045695762,
                                "921 2040 105", "Mi comentario", "123","Av. Sin nombre #1","Monterrey")
                            writtePost(adopcion)
                        }
                    }
                } else {
                    Toast.makeText(context!!, "Verifica que la información esté completa.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun verifyPost(): Boolean {
        if (!hayImagen)
            Toast.makeText(context!!, "Debes ingresar una imágen", Toast.LENGTH_SHORT).show()
        raza = postRazaET.getText().toString().trim()
        telefono = postPhoneET.getText().toString().trim()

        if (TextUtils.isEmpty(raza))
            postRazaET.setError("Debes Ingresar la raza");

        if (TextUtils.isEmpty(telefono))
            postPhoneET.setError("Debes Ingresar un número telefonico");

        //VERIFICACION DE UBICACION

        when (getPostType()) {
            "PERDIDO" -> {
                nombre = postNombreET.getText().toString().trim()
                if (TextUtils.isEmpty(nombre)) {
                    postNombreET.setError("Debes Ingresar el nombre de la mascota");
                }
                if (recompensa) {
                    val recompensa = postCostoET.getText().toString().trim()
                    if (TextUtils.isEmpty(recompensa)) {
                        postCostoET.setError("Debes Ingresar la recompensa $");
                    }
                    if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(recompensa))
                        return false
                }
                if (TextUtils.isEmpty(nombre))
                    return false

                return true
            }

            "ENCONTRADO" -> {
                return true
            }

            "ADOPCION" -> {
                return true
            }
            "NULL" -> {
                Toast.makeText(
                    context!!,
                    "Por favor selecciona el tipo de anuncio que deseas postear",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
        }
        return false
    }

    private fun setVacunado(b: Boolean) {
        when (b) {
            true -> {
                estaVacunado = true
                postVDSiBtn.setIconResource(R.drawable.checkmark_active)
                postVDNoBtn.setIconResource(R.drawable.cross_inactive)
            }
            false -> {
                estaVacunado = false
                postVDSiBtn.setIconResource(R.drawable.checkmark_inactive)
                postVDNoBtn.setIconResource(R.drawable.cross_active)
            }
        }

    }

    private fun setSexo(s: String) {
        when (s) {
            "MACHO" -> {
                esMacho = true
                postMachoBtn.setIconResource(R.drawable.icon_male_active)
                postHembraBtn.setIconResource(R.drawable.icon_female_inactive)
            }
            "HEMBRA" -> {
                esMacho = false
                postMachoBtn.setIconResource(R.drawable.icon_male_inactive)
                postHembraBtn.setIconResource(R.drawable.icon_female_active)
            }
        }

    }

    private fun setPet(s: String) {
        when (s) {
            "PERRO" -> {
                petType = s
                postPerroBtn.setIconResource(R.drawable.icon_dog_active)
                postGatoBtn.setIconResource(R.drawable.icon_cat_inactive)
                postOtroPetBtn.setIconResource(R.drawable.icon_otherpet_inactive)
            }
            "GATO" -> {
                petType = s
                postPerroBtn.setIconResource(R.drawable.icon_dog_inactive)
                postGatoBtn.setIconResource(R.drawable.icon_cat_active)
                postOtroPetBtn.setIconResource(R.drawable.icon_otherpet_inactive)
            }
            "OTRO" -> {
                petType = s
                postPerroBtn.setIconResource(R.drawable.icon_dog_inactive)
                postGatoBtn.setIconResource(R.drawable.icon_cat_inactive)
                postOtroPetBtn.setIconResource(R.drawable.icon_otherpet_active)
            }
            else -> {
                petType = "NULL"
                postPerroBtn.setIconResource(R.drawable.icon_dog_inactive)
                postGatoBtn.setIconResource(R.drawable.icon_cat_inactive)
                postOtroPetBtn.setIconResource(R.drawable.icon_otherpet_inactive)
            }
        }

    }

    private fun setRecompensaStatus(b: Boolean) {
        if (getPostType() == "PERDIDO") {
            postNombreCARD.visibility = View.VISIBLE
            if (b) {
                recompensa = true
                postReSiBtn.setIconResource(R.drawable.checkmark_active)
                postCostoCARD.visibility = View.VISIBLE
                postReNoBtn.setIconResource(R.drawable.cross_inactive)
            } else {
                recompensa = false
                postReSiBtn.setIconResource(R.drawable.checkmark_inactive)
                postCostoCARD.visibility = View.GONE
                postReNoBtn.setIconResource(R.drawable.cross_active)
            }
        }
    }


    private fun setPostType(s: String) {
        when (s) {
            "PERDIDO" -> {
                if (!perdido) {
                    perdido = true
                    postPerdidoBtn.setIconResource(R.drawable.icon_lost_active)
                    encontrado = false
                    postEncontradoBtn.setIconResource(R.drawable.icon_found_inactive)
                    adopcion = false
                    postAdopcionBtn.setIconResource(R.drawable.icon_adoption_inactive)
                }
            }
            "ENCONTRADO" -> {
                if (!encontrado) {
                    perdido = false
                    postPerdidoBtn.setIconResource(R.drawable.icon_lost_inactive)
                    encontrado = true
                    postEncontradoBtn.setIconResource(R.drawable.icon_found_active)
                    adopcion = false
                    postAdopcionBtn.setIconResource(R.drawable.icon_adoption_inactive)
                }
            }
            "ADOPCION" -> {
                if (!adopcion) {
                    perdido = false
                    postPerdidoBtn.setIconResource(R.drawable.icon_lost_inactive)
                    encontrado = false
                    postEncontradoBtn.setIconResource(R.drawable.icon_found_inactive)
                    adopcion = true
                    postAdopcionBtn.setIconResource(R.drawable.icon_adoption_active)
                }
            }
        }
    }


    private fun getPostType(): String {
        if (perdido)
            return "PERDIDO"

        if (encontrado)
            return "ENCONTRADO"

        if (adopcion)
            return "ADOPCION"

        return "NULL"
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Toast.makeText(context, "Req detected ${resultCode} - ${data}", Toast.LENGTH_SHORT).show()
        //super method removed
        if (resultCode == RESULT_OK && requestCode == 921) {
            Toast.makeText(context, "REQUEST GET IMAGE", Toast.LENGTH_SHORT).show()
            val returnUri = data!!.data

            CropImage.activity(returnUri).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(300, 300)
                .start(context!!, this)

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(context, "CROP_IMAGE_ACTIVITY_REQUEST_CODE", Toast.LENGTH_SHORT).show()
            val result: CropImage.ActivityResult = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                val resultUri: Uri = result.getUri();
                Glide.with(context!!).load(resultUri).into(postEscojeImagenBtn)
                hayImagen = true
            }
        }
    }


    fun writtePost( post: Post) {
        mDatabase.child("model").child("Post").setValue(post)
    }


}
