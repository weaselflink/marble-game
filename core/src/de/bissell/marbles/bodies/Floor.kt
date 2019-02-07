package de.bissell.marbles.bodies

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align

class Floor(private val world: World, middle: Vector2, width: Float, angle: Float) :
        Image(Texture(Gdx.files.internal("assets/wood.jpg"), null, true)) {

    private val body : Body

    init {
        val height = 10f
        val hx = width / 2f
        val hy = height / 2f
        val radians = Math.toRadians(angle.toDouble()).toFloat()

        setSize(width, height)
        setPosition(middle.x, middle.y, Align.center)
        setOrigin(hx, hy)
        rotation = angle

        val groundBox = PolygonShape().apply {
            setAsBox(hx, hy)
        }
        body = world.createBody(BodyDef()).apply {
            createFixture(groundBox, 0.0f)
            setTransform(middle, radians)
        }

        groundBox.dispose()

        addListener(object : ClickListener(Input.Buttons.RIGHT) {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                removeSelf()
            }
        })
    }

    fun removeSelf() {
        stage.root.removeActor(this)
        world.destroyBody(body)
    }
}
