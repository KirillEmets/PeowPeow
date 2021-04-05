package main.com.kirillemets.kirillpeowpeow.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerInfo(val id: Int, val name: String)

@Serializable
data class LobbyInfo(val players: List<PlayerInfo>)

@Serializable
data class Args(
    val players: List<PlayerInfo>? = null
)
@Serializable
data class Message(
    val type: String,
    val message: String? = null,
    val callId: Int? = null,
    val args: Args? = null,
    val result: String? = null,
    val error: String? = null,
)