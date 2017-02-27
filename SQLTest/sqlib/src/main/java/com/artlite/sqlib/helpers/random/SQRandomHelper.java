package com.artlite.sqlib.helpers.random;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.R;
import com.artlite.sqlib.helpers.abs.SQBaseHelper;

import java.util.Random;

/**
 * Created by dlernatovich on 12/22/2016.
 */

public class SQRandomHelper extends SQBaseHelper {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 900000;
    private static final String ALPHABET = "QWERTYUIOP{}ASDFGHJKL:|ZXCVBNM<>?qwertyuiop[]" +
            "asdfghjkl;'zxcvbnm,./1234567890-=+_";

    /**
     * Method which provide the generating of the random integer value
     *
     * @return current random integer value
     */
    public static int generateInt() {
        return generateInt(MIN_VALUE, MAX_VALUE);
    }

    /**
     * Method which provide the generating of the random integer value
     *
     * @param max max value
     * @return current random integer value
     */
    public static int generateInt(int max) {
        return generateInt(MIN_VALUE, max);
    }

    /**
     * Method which provide the generating of the random integer value
     *
     * @param min min value
     * @param max max value
     * @return current random integer value
     */
    public static int generateInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    /**
     * Method which provide the generating of the random String value with length
     *
     * @param length current random string length
     * @return generated String value
     */
    public static String generateString(int length) {
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

    /**
     * Method which provide to generate of the random sentence
     *
     * @param context instance of context
     * @return generated sentence
     */
    public static String generateSentence(@Nullable final Context context, int count) {
        final StringBuilder builder = new StringBuilder("");
        String[] words = words(context);
        if (validate(words)) {
            for (int i = 0; i < count; i++) {
                int random = generateInt(0, words.length - 1);
                String word = words[random];
                builder.append(word).append(" ");
            }
        }
        return builder.toString();
    }


    /**
     * Method which provide the getting of the random words list from xml
     *
     * @param context instance of {@link Context}
     * @return array of {@link String}
     */
    @NonNull
    private static String[] words(@Nullable final Context context) {
        final String methodName = "String[] words(context)";
        try {
            String[] words = context.getResources().getStringArray(R.array.array_english_words);
            return words;
        } catch (Exception ex) {
            log(null, methodName, ex, context);
        }
        return new String[]{};
    }

}
