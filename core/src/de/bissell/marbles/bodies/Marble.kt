package de.bissell.marbles.bodies

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.scenes.scene2d.ui.Image

class Marble(world: World, pos: Vector2) : Image(Texture("assets/marble.png")) {

    private val body: Body

    init {
        setScale(0.02f, 0.02f)
        setPosition(pos.x, pos.y)
        setOrigin(width / 2, height / 2)

        val bodyDef = BodyDef().apply {
            type = BodyDef.BodyType.DynamicBody
            position.set(pos.x, pos.y)
        }

        body = world.createBody(bodyDef)

        val shape = CircleShape().apply {
            radius = 7f
        }

        val fixtureDef = FixtureDef().apply {
            this.shape = shape
            density = 5f
            friction = 0.2f
            restitution = 0.8f
        }

        body.createFixture(fixtureDef)

        shape.dispose()
    }

    override fun act(delta: Float) {
        super.act(delta)
        rotation = body.angle * MathUtils.radiansToDegrees

        setPosition(body.position.x - width / 2, body.position.y - height / 2)
    }
}
