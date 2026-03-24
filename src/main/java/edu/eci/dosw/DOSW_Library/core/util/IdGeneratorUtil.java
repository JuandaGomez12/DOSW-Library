package edu.eci.dosw.DOSW_Library.core.util;

import java.util.UUID;

public class IdGeneratorUtil {

    private IdGeneratorUtil() {}

    public static String generate() {
        return UUID.randomUUID().toString();
    }
}