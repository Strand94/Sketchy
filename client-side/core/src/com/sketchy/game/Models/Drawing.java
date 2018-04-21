package com.sketchy.game.Models;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sketchy.game.Config;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.codec.binary.Base64;

public class Drawing extends ArrayList<Dot> {
    public Drawing() {
    }

    public Drawing(int initialCapacity) {
        super(initialCapacity);
    }

    public static Drawing fromBase64(String base64) throws IOException, ClassNotFoundException {
        byte[] bytes = Base64.decodeBase64(base64);
        int n = bytes.length / Dot.SIZE_BYTES;
        Drawing drawing = new Drawing(n);
        for (int i = 0; i < n; i++) {
            byte[] dotBytes = new byte[Dot.SIZE_BYTES];
            System.arraycopy(bytes, i * Dot.SIZE_BYTES, dotBytes, 0, Dot.SIZE_BYTES);
            drawing.add(Dot.fromBytes(dotBytes));
        }
        return drawing;
    }

    public String toBase64() throws IOException {
        byte[] bytes = new byte[size() * Dot.SIZE_BYTES];
        for (int i = 0; i < size(); i++) {
            System.arraycopy(get(i).toBytes(), 0, bytes, i * Dot.SIZE_BYTES, Dot.SIZE_BYTES);
        }
        return Base64.encodeBase64String(bytes);
    }

    public int render(ShapeRenderer shapeRenderer, float screenHeight, int drawIndex) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Dot prev = null;
        while (drawIndex < size()) {
            Dot dot = get(drawIndex++);
            shapeRenderer.setColor(dot.getColor());
            if (prev != null && dot.drawLineFromPrevious) {
                float deltaX = dot.getX() - prev.getX();
                float deltaY = dot.getY() - prev.getY();
                int nDots = (int) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) - 1;
                for (int i = 0; i < nDots; i += Config.DRAW_COARSENESS) {
                    shapeRenderer.circle(
                            prev.getX() + (deltaX / nDots) * i,
                            screenHeight - (prev.getY() + (deltaY / nDots) * i),
                            dot.getRadius()
                    );
                }
            }
            shapeRenderer.circle(dot.getX(), screenHeight - dot.getY(), dot.getRadius());
            prev = dot;
        }
        shapeRenderer.end();
        return drawIndex;
    }
}
