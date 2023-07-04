package com.example.appbatepapo

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth

class MensagemAdapter(val context:Context, val messageList: ArrayList<Mensagem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2;


    class EnviarViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val enviarMensagem = itemView.findViewById<TextView>(R.id.txt_sent_message)
    }

    class ReceberViewHolder(itemView: View): RecyclerView.ViewHolder( itemView){
        val receberMensagem = itemView.findViewById<TextView>(R.id.txt_receive_message)
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        if(viewType == 1){
            val view: View = LayoutInflater.from(context).inflate(R.layout.receber,parent,false)
            return ReceberViewHolder(view)
        }else{
            val view: View = LayoutInflater.from(context).inflate(R.layout.enviar,parent,false)
            return EnviarViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentMessage = messageList[position]

        if(holder.javaClass == EnviarViewHolder::class.java){



            val viewHolder = holder as EnviarViewHolder

            holder.enviarMensagem.text = currentMessage.message
        }else{
            val viewHolder = holder as ReceberViewHolder
            holder.receberMensagem.text = currentMessage.message
        }
    }
}