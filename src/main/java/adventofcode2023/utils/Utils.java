package adventofcode2023.utils;

public class Utils {
    public static boolean isInt(String input) {
        boolean isInt = true;
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            isInt = false;
        }
        return isInt;
    }
}
