// Schedule this for deletion as it is a duplicate and/or not needed.

package com.accountable.util;

import java.util.Date;

public class Logger {

    public static void log(String message) {
        System.out.println(new Date() + ": " + message);
    }

    public static void logError(String message) {
        System.err.println(new Date() + " [ERROR]: " + message);
    }

    public static void logDebug(String message) {
        // Debug log, can be enabled or disabled as needed
        System.out.println(new Date() + " [DEBUG]: " + message);
    }

    public static void logInfo(String message) {
        System.out.println(new Date() + " [INFO]: " + message);
    }

    public static void logWarning(String message) {
        System.out.println(new Date() + " [WARNING]: " + message);
    }
}
