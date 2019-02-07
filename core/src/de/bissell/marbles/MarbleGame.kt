package de.bissell.marbles

import com.badlogic.gdx.Screen
import de.bissell.marbles.screens.GameScreen
import ktx.app.KtxGame

class MarbleGame : KtxGame<Screen>() {

    override fun create() {
        addScreen(GameScreen())

        setScreen<GameScreen>()
    }
}
