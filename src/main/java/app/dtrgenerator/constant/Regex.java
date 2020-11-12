package app.dtrgenerator.constant;

import java.util.regex.Pattern;

public class Regex {
    private static final String timeInPattern = "time in\\s*[:-]?\\s*(?<time>\\d{1,2}:\\d{2})";
    private static final String timeOutPattern = "time out\\s*[:-]?\\s*(?<time>\\d{1,2}:\\d{2})";
    public static final Pattern TIME_IN = Pattern.compile(timeInPattern, Pattern.CASE_INSENSITIVE);
    public static final Pattern TIME_OUT = Pattern.compile(timeOutPattern, Pattern.CASE_INSENSITIVE);
}