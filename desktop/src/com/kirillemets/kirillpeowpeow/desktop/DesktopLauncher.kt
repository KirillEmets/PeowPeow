package com.kirillemets.kirillpeowpeow.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import main.com.kirillemets.kirillpeowpeow.core.Peow

fun main(arg: Array<String>) {
    val config = LwjglApplicationConfiguration()
    LwjglApplication(Peow(), config)
}