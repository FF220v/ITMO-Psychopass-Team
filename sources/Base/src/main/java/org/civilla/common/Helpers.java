package org.civilla.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Helpers {
    public static boolean inArrayLower(String str, String[] arr){
        for (String s: arr)
            if (s.toLowerCase().equals(str.toLowerCase()))
                    return true;
        return false;
    }

    public static String readInputStream(InputStream stream, int bufferSize){
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[bufferSize];
        int length;
        try {
            while ((length = stream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            String res = result.toString("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
