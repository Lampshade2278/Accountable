package com.accountable.core;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

    // Utility method to format dates
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    // Utility method for validation, e.g., email validation
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    // Other utility methods can be added here, such as number formatting, data conversion, etc.
}
