package main.com.kirillemets.kirillpeowpeow.core.connection

import main.com.kirillemets.kirillpeowpeow.model.LobbyInfo
import main.com.kirillemets.kirillpeowpeow.model.PlayerInfo

interface ConnectionDataSource {
    suspend fun connect()

    suspend fun getLobbies(): List<LobbyInfo>

    suspend fun getPlayersOnline(): List<PlayerInfo>
}