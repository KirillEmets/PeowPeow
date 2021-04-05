package main.com.kirillemets.kirillpeowpeow.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.ScreenUtils
import main.com.kirillemets.kirillpeowpeow.addOnClickListener
class MainMenuScreen(val game: Peow) : Screen {
    var stage: Stage = Stage()
    val table: Table = Table()

    init {
        stage = Stage()

        Gdx.input.inputProcessor = stage

        val skin = Skin()
        val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.WHITE)
        pixmap.fill()
        skin.add("white", Texture(pixmap))
        skin.add("default", BitmapFont())

        addButtonStyle(skin)

        val table = Table()
        table.setFillParent(true)
        stage.addActor(table)


        val button = TextButton("Click me!", skin)
        table.add(button)
        table.row()

        val button2 = TextButton("Click me! 2", skin)
        table.add(button2)
        table.row()

        val button3 = TextButton("Click me! 3", skin)
        table.add(button3)

        button.addOnClickListener { _, _ -> println(":das")}
    }

    fun addButtonStyle(skin: Skin) {
        val textButtonStyle = TextButton.TextButtonStyle()
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY)
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY)
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE)
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY)
        textButtonStyle.font = skin.getFont("default")
        skin.add("default", textButtonStyle)
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1f);
        stage.act(Gdx.graphics.deltaTime.coerceAtMost(1 / 30f));
        stage.draw();
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true);
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun hide() {
    }

    override fun dispose() {
        stage.dispose();
    }

}