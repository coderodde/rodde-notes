package net.coderodde.roddenotes.util;

import java.util.concurrent.ThreadLocalRandom;
import net.coderodde.roddenotes.sql.support.MySQLDefinitions;

/**
 * This class provides various utilities for dealing with random strings.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Dec 15, 2017)
 */
public final class RandomUtilities {
    
    private static final char[] ALPHABET = new char[62];
    
    static {
        int index = 0;
        
        for (char c = '0'; c <= '9'; ++c) {
            ALPHABET[index++] = c;
        }
        
        for (char c = 'A'; c <= 'Z'; ++c) {
            ALPHABET[index++] = c;
        }
        
        for (char c = 'a'; c <= 'z'; ++c) {
            ALPHABET[index++] = c;
        }
    }
    
    /**
     * Generates a random document ID.
     * 
     * @return a document ID.
     */
    public static String generateRandomDocumentId() {
        return generateRandomString(
                MySQLDefinitions.DOCUMENT_TABLE.ID_COLUMN.LENGTH);
    }
    
    /**
     * Generates a random edit token.
     * 
     * @return an edit token.
     */
    public static String generateRandomEditToken() {
        return generateRandomString(
                MySQLDefinitions.DOCUMENT_TABLE.EDIT_TOKEN_COLUMN.LENGTH);
    }
    
    /**
     * Generates a random string of given length.
     * 
     * @param length the length of the string to generate.
     * @return a random string.
     */
    public static String generateRandomString(int length) {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        StringBuilder stringBuilder = new StringBuilder(length);
        
        for (int i = 0; i < length; ++i) {
            stringBuilder.append(
                    ALPHABET[threadLocalRandom.nextInt(ALPHABET.length)]);
        }
        
        return stringBuilder.toString();
    }
}
