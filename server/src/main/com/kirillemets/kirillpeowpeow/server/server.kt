package main.com.kirillemets.kirillpeowpeow.server

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.cio.websocket.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import java.time.Duration



fun main(arg: Array<String>) {
    embeddedServer(Netty, port = 8000) {
        install(WebSockets) {
            pingPeriod = Duration.ofSeconds(60)
            timeout = Duration.ofSeconds(15)
            maxFrameSize = Long.MAX_VALUE
            masking = false
        }

        install(ContentNegotiation) {
            json()
        }

        routing {
            webSocket("/") {
                val channel = Channel()
                channel.onConnect()
                try {
                    for (frame in incoming){
                        val text = (frame as Frame.Text).readText()
                        channel.onMessage(text)
                    }
                } catch (e: ClosedReceiveChannelException) {
                    channel.onClose()
                    println("onClose ${closeReason.await()}")
                } catch (e: Throwable) {
                    channel.onError()
                    println("onError ${closeReason.await()}")
                    e.printStackTrace()
                }
            }

            get("/") {
                call.respondText("Hello, world!")
            }
        }
    }.start(wait = true)
}

