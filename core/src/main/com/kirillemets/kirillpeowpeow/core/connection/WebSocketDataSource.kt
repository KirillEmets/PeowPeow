package main.com.kirillemets.kirillpeowpeow.core.connection

import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import main.com.kirillemets.kirillpeowpeow.model.*

class WebSocketDataSource(): ConnectionDataSource {
    private lateinit var connection: ClientConnection
    private val client = HttpClient {
        install(WebSockets)
    }

    override suspend fun connect() {
        client.ws(
            method = HttpMethod.Get,
            host = "127.0.0.1",
            port = 8080, path = "/"
        ) {
            val wsExchanger = object : MessageExchanger() {
                override suspend fun sendMessage(message: String) {
                    TODO("Not yet implemented")
                }
            }
            for(frame in incoming) {
                frame as? Frame.Text ?: continue
                wsExchanger.sendMessage(frame.readText())
            }
            connection = ClientConnection(wsExchanger)
        }
    }

    override suspend fun getLobbies() = connection.getLobbies()

    override suspend fun getPlayersOnline(): List<PlayerInfo> {
        TODO("Not yet implemented")
    }

    class ClientConnection(exchanger: MessageExchanger) : Connection(exchanger) {
        override val wsFunctions = mapOf<String, (Message) -> Unit>(

        )

        suspend fun getLobbies(): List<LobbyInfo> =
            sendMessageForResponse("getLobbies")

    }
}