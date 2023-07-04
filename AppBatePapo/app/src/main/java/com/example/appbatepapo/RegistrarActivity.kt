package com.example.appbatepapo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrarActivity : AppCompatActivity() {

    private lateinit var campoNome : EditText
    private lateinit var campoEmail : EditText
    private lateinit var campoSenha : EditText
    private lateinit var botaoRegistrar : Button
    private lateinit var retornar_login : TextView
    private lateinit var RefDB : DatabaseReference

    //Autenticação do firebase
    private lateinit var auten : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        supportActionBar?.hide()

        auten = FirebaseAuth.getInstance()

        campoNome = findViewById(R.id.id_nome)
        campoEmail = findViewById(R.id.id_email)
        campoSenha = findViewById(R.id.id_senha)
        botaoRegistrar = findViewById(R.id.id_botao_registrar)
        retornar_login = findViewById(R.id.id_ja_tem_conta)

        botaoRegistrar.setOnClickListener{
            val nome = campoNome.text.toString()
            val email = campoEmail.text.toString()
            val senha = campoSenha.text.toString()

            fazerRegistro(nome,email,senha)
        }
    }

    private fun fazerRegistro(nome:String,email:String, senha:String){
        auten.createUserWithEmailAndPassword(email,senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                    addUserBd(nome,email,auten.currentUser?.uid!!)
                    val intent = Intent(this@RegistrarActivity,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }else{
                    Toast.makeText(this@RegistrarActivity,"Erro",Toast.LENGTH_SHORT).show()
                }
            }
    }

    //Banco Real Time
    private fun addUserBd(nome:String, email:String, uid:String){
        RefDB = FirebaseDatabase.getInstance().getReference()
        RefDB.child("user").child(uid).setValue(User(nome, email, uid))
    }
}