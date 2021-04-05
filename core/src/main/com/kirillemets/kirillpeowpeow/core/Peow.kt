package main.com.kirillemets.kirillpeowpeow.core

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class Peow : Game() {
    lateinit var batch: SpriteBatch
    lateinit var font: BitmapFont

    override fun create() {
        batch = SpriteBatch()

        setScreen(MainMenuScreen(this))
    }

    override fun render() {
        super.render()

    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

    }

    override fun dispose() {
        batch.dispose()
    }
}