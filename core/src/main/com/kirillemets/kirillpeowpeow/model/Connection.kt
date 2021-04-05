package main.com.kirillemets.kirillpeowpeow.model

import kotlinx.coroutines.CompletableDeferred
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

abstract class Connection(val exchanger: MessageExchanger) {
    companion object {
        object MessageType {
            const val response = "response"
            const val message = "message"
            const val pending = "pending"
        }
    }
    open val wsFunctions: Map<String, (Message) -> Unit> = mapOf()
    open val wsResponses: Map<String, (Message) -> String> = mapOf()
    val idMap: MutableMap<Int, (Message) -> Unit> = mutableMapOf()
    var id = 0

    init {
        exchanger.addListener { onMessage(it) }
    }

    private suspend fun onMessage(msg: String) {
        val message = Json.decodeFromString<Message>(msg)
        when (message.type) {
            MessageType.response -> idMap[message.callId]?.invoke(message)
                ?: throw Exception("callId ${message.callId} is absent")

            MessageType.message -> wsFunctions[message.message]?.invoke(message)
                ?: throw Exception("No such function: ${message.message}")

            MessageType.pending -> {
                try {
                    val res = wsResponses[message.message]?.invoke(message)
                    sendResponse(message.callId!!, res, null)
                }
                catch (e: Exception) {
                    sendResponse(message.callId!!, null, e.message)
                }

            }
        }
    }

    suspend inline fun <reified T> sendMessageForResponse(message: String, args: Args? = null): T  {
        id++
        val deferred = CompletableDeferred<T>()
        idMap[id] = {msg: Message ->
            when {
                msg.error != null -> deferred.completeExceptionally(Exception(msg.error))
                msg.result != null -> {
                    try {
                        val res = Json.decodeFromString<T>(msg.result)
                        deferred.complete(res)
                    }
                    catch (e: Exception) {
                        deferred.completeExceptionally(Exception("Could not decode result: ${msg.result}"))
                    }
                }
                else -> deferred.completeExceptionally(Exception("Unexpected error"))
            }
        }
        val msg = Json.encodeToString(Message(
            MessageType.pending,
            callId = id,
            message = message,
            args = args
        ))
        exchanger.sendMessage(msg)
        return deferred.await()
    }

    suspend fun sendMessage(message: String, args: Args?) {
        val msg = Json.encodeToString(Message(
            MessageType.message,
            message = message,
            args = args
        ))
        exchanger.sendMessage(msg)
    }

    private suspend fun sendResponse(callId: Int, result: String?, error: String?) {
        val msg = Json.encodeToString(Message(
            MessageType.response,
            callId = callId,
            result = result,
            error = error
        ))
        exchanger.sendMessage(msg)
    }
}