package com.sketchy.game.Models;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import org.apache.commons.codec.binary.Base64;

public class Drawing extends ArrayList<Dot> {
    public static Drawing fromBase64(String base64) throws IOException, ClassNotFoundException {
        byte[] bytes = Base64.decodeBase64(base64);
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
            return (Drawing) o;
        } finally {
            if (in != null) in.close();
        }
    }

    public String toBase64() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutput out;
        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(this);
            out.flush();
            byte[] byteArray = baos.toByteArray();
            return Base64.encodeBase64String(byteArray);
        } finally {
            baos.close();
        }
    }
}
