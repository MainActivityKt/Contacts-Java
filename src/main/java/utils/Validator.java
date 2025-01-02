package utils;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class Validator {
    public static boolean isPhoneNumberValid(String phoneNumber) {
        Pattern firstGroupPattern = Pattern.compile("\\+?\\(?[0-9A-Za-z]+\\)?");
        Pattern nextGroupsPattern = Pattern.compile("\\(?[0-9A-Za-z]{2,}\\)?");
        Pattern separator = Pattern.compile("[- ]");

        int parenthesisCount = phoneNumber.length() - phoneNumber.replaceAll("[()]", "").length();

        if (parenthesisCount > 2) {
            return false;
        }
        String[] groups = separator.split(phoneNumber);

        for (int i = 0; i < groups.length; i++) {
            int groupParenthesis = groups[i].length() - groups[i].replaceAll("[()]", "").length();

            if (groupParenthesis != 0 && groupParenthesis != 2) {
                return false;
            }

            if (i == 0) {
                if (!firstGroupPattern.matcher(groups[i]).matches()) {
                    return false;
                }
            } else if (!nextGroupsPattern.matcher(groups[i]).matches()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isBirthDateValid(String birthDate) {
        try {
            LocalDate.parse(birthDate);
            return true;
        } catch (
                DateTimeException e) {
            return false;
        }
    }
}
