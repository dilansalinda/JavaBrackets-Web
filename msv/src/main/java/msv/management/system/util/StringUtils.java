package msv.management.system.util;

public class StringUtils {

    public static String trim(String value) {
        return value != null ? value.trim() : null;
    }

    public static String replaceSpaceWithUnderscore(String value) {
        return value.toLowerCase().replace(' ', '_');
    }
}
