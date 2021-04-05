package main.com.kirillemets.kirillpeowpeow

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener


fun TextButton.addOnClickListener(fn: (event: ChangeListener.ChangeEvent?, actor: Actor?) -> Unit) {
    this.addListener(object : ChangeListener() {
        override fun changed(event: ChangeEvent?, actor: Actor?) {
            if(isChecked)
                fn(event, actor)
            isChecked = false
        }
    })
}