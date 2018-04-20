package com.sketchy.game.Models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Dot {
    private float radius;
    private Vector2 position;
    private Color color;

    public Dot(float radius, Vector2 position, Color color) {
        this.radius = radius;
        this.position = position;
        this.color = color;
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
        return color;
    }
}