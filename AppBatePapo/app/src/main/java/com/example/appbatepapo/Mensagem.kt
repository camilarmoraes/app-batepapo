package com.example.appbatepapo
//criação do model para as mensagens
class Mensagem {
    var message: String? = null
    var senderId: String? = null

    constructor(){}


    constructor(message:String?, senderId: String?){
        this.message = message
        this.senderId = senderId
    }
}