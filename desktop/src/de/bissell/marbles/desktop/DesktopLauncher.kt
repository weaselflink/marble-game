package de.bissell.marbles.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import de.bissell.marbles.MarbleGame

fun main() {
    val config = LwjglApplicationConfiguration().apply {
        title = "Marbles!"
        width = 400
        height = 400
        samples = 4
    }
    LwjglApplication(MarbleGame(), config)
}
