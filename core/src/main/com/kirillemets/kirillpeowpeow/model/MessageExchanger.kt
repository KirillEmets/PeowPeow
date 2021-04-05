package main.com.kirillemets.kirillpeowpeow.model

abstract class MessageExchanger {
    private val listeners: MutableList<suspend (String) -> Unit> = mutableListOf()
    suspend fun onMessage(message: String) {
        listeners.forEach { listener -> listener.invoke(message) }
    }
    fun addListener(fn: suspend (String) -> Unit) {
        listeners += fn
    }
    abstract suspend fun sendMessage(message: String)
}