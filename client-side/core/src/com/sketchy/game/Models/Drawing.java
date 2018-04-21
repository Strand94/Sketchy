package com.sketchy.game.Models;

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
}
