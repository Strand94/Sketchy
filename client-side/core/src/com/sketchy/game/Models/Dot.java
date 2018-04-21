package com.sketchy.game.Models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.nio.ByteBuffer;

public class Dot {
    public static final int SIZE_BYTES = 9;

    private static final Color[] colors = new Color[] {
            Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.WHITE
    };

    private float radius;
    private Vector2 position;
    private byte color;
    public final boolean drawLineFromPrevious;

    public Dot(float radius, Vector2 position, Color color, boolean drawLineFromPrevious) {
        this.radius = radius;
        this.position = position;
        this.color = 0;
        for (byte i = 0; i < colors.length; i++) {
            if (color.equals(colors[i])) this.color = i;
        }
        this.drawLineFromPrevious = drawLineFromPrevious;
    }

    public float getRadius() {
        return radius;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getX(){
        return position.x;
    }

    public float getY(){
        return position.y;
    }

    public Color getColor() {
        return colors[color];
    }

    @Override
    public String toString() {
        return "Dot{" +
                "radius=" + radius +
                ", position=" + position +
                ", color=" + colors[color] +
                ", drawLineFromPrevious=" + drawLineFromPrevious +
                '}';
    }

    public byte[] toBytes() {
        return ByteBuffer.allocate(SIZE_BYTES)
                .put((byte)((color << 1) | (drawLineFromPrevious ? 1 : 0)))
                .putFloat(getX())
                .putFloat(getY())
                .array();
    }

    public static Dot fromBytes(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new Dot(
                5.0f,
                new Vector2(byteBuffer.getFloat(1), byteBuffer.getFloat(5)),
                colors[byteBuffer.get(0) >> 1],
                (byteBuffer.get(0) & 1) != 0
        );
    }
}