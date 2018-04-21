package com.sketchy.game.Models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public class Dot implements Serializable{
    private float radius;
    private Vector2 position;
    private float[] color;

    public Dot(float radius, Vector2 position, Color color) {
        this.radius = radius;
        this.position = position;
        this.color = new float[]{color.r, color.g, color.b};
    }

    public float getRadius() {
        return radius;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getPosX(){
        return position.x;
    }

    public float getPosY(){
        return position.y;
    }

    public Color getColor() {
        return new Color(color[0], color[1], color[2], 1);
    }
}