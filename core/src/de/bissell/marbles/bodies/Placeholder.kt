package de.bissell.marbles.bodies

import com.badlogic.gdx.graphics.Color.BLUE
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Align
import ktx.graphics.copy

class Placeholder(middle: Vector2, width: Float, angle: Float) : Actor() {

    private val shapeRenderer = ShapeRenderer()

    init {
        update(middle, width, angle)
    }

    fun update(middle: Vector2, width: Float, angle: Float) {
        val height = 10f
        val hx = width / 2f
        val hy = height / 2f

        setSize(width, height)
        setPosition(middle.x, middle.y, Align.center)
        setOrigin(hx, hy)
        rotation = angle
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch?.end()

        shapeRenderer.setAutoShapeType(true)
        shapeRenderer.color = BLUE.copy(alpha = parentAlpha)
        shapeRenderer.projectionMatrix = batch?.projectionMatrix

        shapeRenderer.begin()
        shapeRenderer.rect(x, y, originX, originY, width, height, 1f, 1f, rotation)
        shapeRenderer.end()

        batch?.begin()
    }
}
