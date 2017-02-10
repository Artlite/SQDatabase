package com.artlite.sqltest.helpers;

import java.util.Random;

/**
 * Created by dlernatovich on 2/10/2017.
 */

public final class RandomHelper {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 900000;
    private static final String ALPHABET = "QWERTYUIOP{}ASDFGHJKL:|ZXCVBNM<>?qwertyuiop[]asdfghjkl;'zxcvbnm,./1234567890-=+_";

    /**
     * Method which provide the generating of the random integer value
     *
     * @return current random integer value
     */
    public static int generateRandomInteger() {
        Random rand = new Random();
        int randomNum = rand.nextInt((MAX_VALUE - MIN_VALUE) + 1) + MIN_VALUE;
        return randomNum;
    }

    /**
     * Method which provide the generating of the random String value with length
     *
     * @param length current random string length
     * @return generated String value
     */
    public static String generateRandomString(int length) {
        char[] chars = ALPHABET.toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return output;
    }
}
