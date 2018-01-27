package com.authority.base;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class UUIDUtils {

	  /**
     * Pseudo-random number generator object for use with randomString().
     * The Random class is not considered to be cryptographically secure, so
     * only use these random Strings for low to medium security applications.
     */
    private static final ThreadLocal<Random> randGen = new ThreadLocal<Random>() {
        @Override
        protected Random initialValue() {
            return new Random();
        }
    };

    /**
     * Array of numbers and letters of mixed case. Numbers appear in the list
     * twice so that there is a more equal chance that a number will be picked.
     * We can use the array to get a random number or letter by picking a random
     * array index.
     */
    private static final char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" +
                    "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

    private static final ThreadLocal<SecureRandom> SECURE_RANDOM = new ThreadLocal<SecureRandom>() {
        @Override
        protected SecureRandom initialValue() {
            return new SecureRandom();
        }
    };

    
    public static String insecureRandomString(int length) {
        return randomString(length, randGen.get());
    }
    
    public static String randomString(final int length) {
        return randomString(length, SECURE_RANDOM.get());
    }

    public static String randomUUID() {  
        String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());  
        return insecureRandomString(18)+datetime;
    }  
    
    private static String randomString(final int length, Random random) {
        if (length < 1) {
            return null;
        }

        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        char[] randomChars = new char[length];
        for (int i = 0; i < length; i++) {
            randomChars[i] = getPrintableChar(randomBytes[i]);
        }
        return new String(randomChars);
    }

    private static char getPrintableChar(byte indexByte) {
        assert (numbersAndLetters.length < Byte.MAX_VALUE * 2);

        // Convert indexByte as it where an unsigned byte by promoting it to int
        // and masking it with 0xff. Yields results from 0 - 254.
        int index = indexByte & 0xff;
        return numbersAndLetters[index % numbersAndLetters.length];
    }
    
}
