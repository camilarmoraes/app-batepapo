package com.example.appbatepapo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler.Value
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox : EditText
    private lateinit var botaoEnviar: Button
    private lateinit var messageAdapter: MensagemAdapter
    private lateinit var messageList: java.util.ArrayList<Mensagem>
    private lateinit var RefDB: DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val nome = intent.getStringExtra("nome")
        val receverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        RefDB = FirebaseDatabase.getInstance().getReference()

        senderRoom = receverUid + senderUid
        receiverRoom = senderUid + receverUid
        supportActionBar?.title = nome

        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        botaoEnviar = findViewById(R.id.botao_enviar)
        messageList = ArrayList()
        messageAdapter = MensagemAdapter(this,messageList)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter


        //Aqui é a lógica para adicionar as mensagens na tela
        RefDB.child("conversa").child(senderRoom!!).child("mensagem")
            .addValueEventListener(object:ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()

                    for(postSnapshot in snapshot.children){
                        val mensagem = postSnapshot.getValue(Mensagem::class.java)
                        messageList.add(mensagem!!)
                    }

                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        // ao clicar no botaõ enviar, envia a mensagem para o banco de dados
        botaoEnviar.setOnClickListener {

            val message = messageBox.text.toString()
            val messageObject = Mensagem(message,senderUid)

            RefDB.child("conversa").child(senderRoom!!).child("mensagem").push()
                .setValue(messageObject).addOnSuccessListener {
                    RefDB.child("conversa").child(receiverRoom!!).child("mensagem").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")
        }
    }
}