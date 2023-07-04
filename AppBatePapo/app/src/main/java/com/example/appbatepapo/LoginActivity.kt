package com.example.appbatepapo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var campoEmail : EditText
    private lateinit var campoSenha : EditText
    private lateinit var botaoLogin : Button
    private lateinit var retornar : TextView

    //Autenticação do firebase
    private lateinit var auten : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        //iniciando a autenticação
        auten = FirebaseAuth.getInstance()


        campoEmail = findViewById(R.id.id_entrar_email)
        campoSenha = findViewById(R.id.id_entrar_senha)
        botaoLogin = findViewById(R.id.id_botao_entrar)
        retornar = findViewById(R.id.id_retornar)

        retornar.setOnClickListener{
            val intent = Intent(this, RegistrarActivity::class.java)
            startActivity(intent)
        }

        botaoLogin.setOnClickListener{
            val email = campoEmail.text.toString()
            val senha = campoSenha.text.toString()

            realizarLogin(email,senha)
        }
    }


    private fun realizarLogin(email:String, senha:String) {
        auten.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Usuário não encontrado!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}