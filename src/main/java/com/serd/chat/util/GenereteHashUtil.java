package com.serd.chat.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by serdukovaa on 27.12.2014.
 */
public class GenereteHashUtil {


    public static String genHash(String string) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(string.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {

            ex.printStackTrace();
            return "0";//добавить в контроллер проверку на ноль потом
        }

    }
}