package xyz.astradev;

import java.util.regex.Pattern;

public class Validation {
    private static final String URL_REGEX = "(https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*))";
    //REGEX
    public final Pattern detectInvalidHexChars = Pattern.compile("[^0-9a-f]", Pattern.CASE_INSENSITIVE);
    public final Pattern detectURL = Pattern.compile(URL_REGEX, Pattern.CASE_INSENSITIVE);


}
