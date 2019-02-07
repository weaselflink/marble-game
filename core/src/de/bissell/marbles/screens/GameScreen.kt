package de.bissell.marbles.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ExtendViewport
import de.bissell.marbles.bodies.Floor
import de.bissell.marbles.bodies.Marble
import de.bissell.marbles.bodies.Placeholder
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.log.info

class GameScreen : KtxScreen {

    private val stage: Stage
    private val world: World
    private val debugRenderer: Box2DDebugRenderer

    private var firstClick: Vector2? = null
    private var placeholder: Placeholder? = null

    init {
        val camera = OrthographicCamera(400f, 400f)
        val viewport = ExtendViewport(400f, 400f, camera)
        stage = Stage(viewport)
        Gdx.input.inputProcessor = stage
        world = World(Vector2(0f, -20f), true)
        debugRenderer = Box2DDebugRenderer()

        stage.addListener(object : ClickListener(Input.Buttons.LEFT) {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                    if (firstClick == null) {
                        firstClick = Vector2(x, y)
                    } else {
                        addFloor(firstClick!!, Vector2(x, y))
                    }
            }

            override fun mouseMoved(event: InputEvent, x: Float, y: Float): Boolean {
                if (firstClick != null) {
                    addPlaceholder(firstClick!!, Vector2(x, y))
                }
                return true
            }
        })
        stage.addListener(object : ClickListener(-1) {
            override fun keyTyped(event: InputEvent, character: Char): Boolean {
                if (character == 'a') {
                    val newMarble = Marble(world, Vector2(event.stageX, event.stageY))
                    stage.root.addActorAt(0, newMarble)
                    return true
                }
                return false
            }
        })
    }

    private fun addPlaceholder(middle: Vector2, end: Vector2) {
        val width = middle.dst(end) * 2f
        val angle = end.sub(middle).angle(Vector2(1f, 0f))

        addPlaceholder(middle, width, -angle)
    }

    private fun addPlaceholder(middle: Vector2, width: Float, angle: Float) {
        if (placeholder != null) {
            placeholder?.update(middle, width, angle)
        } else {
            placeholder = Placeholder(middle, width, angle)
            stage.root.addActor(placeholder)
        }
    }

    private fun addFloor(middle: Vector2, end: Vector2) {
        val width = middle.dst(end) * 2f
        if (width < 10) {
            return
        }
        val angle = end.sub(middle).angle(Vector2(1f, 0f))

        addFloor(middle, width, -angle)
    }

    private fun addFloor(middle: Vector2, width: Float, angle: Float) {
        info { "addFloor(new Vector2($middle), $width, $angle)" }

        val floor = Floor(world, middle, width, angle)
        if (placeholder != null) {
            stage.root.removeActor(placeholder)
            placeholder = null
        }
        stage.root.addActor(floor)
        firstClick = null
    }

    override fun show() {
        info { "show" }
    }

    override fun render(delta: Float) {
        clearScreen(Color.BLACK)
        stage.act()
        stage.draw()
        world.step(Gdx.graphics.deltaTime, 6, 2)

        //debugRenderer.render(world, stage.camera.combined)
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        stage.dispose()
    }

    private fun clearScreen(color: Color) = clearScreen(color.r, color.b, color.g, color.a)
}
