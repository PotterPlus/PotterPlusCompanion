package io.github.grisstyl.potterplus.api.misc;

import com.google.common.collect.ImmutableMap;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Various utility methods for operating on Strings.
 */
public class StringUtilities {

    public static final char COLOR_CHAR = '&';

    /**
     * Translates color and formatting codes.
     * @param message The message.
     * @return The colored message.
     */
    public static String translateColorCodes(String message) {
        return ChatColor.translateAlternateColorCodes(COLOR_CHAR, message);
    }

    /**
     * Translates hex color codes.
     * @param formatPrefix The prefix to the format.
     * @param formatSuffix The suffix to the format.
     * @param message The message.
     * @return The colored message.
     */
    public static String translateHexColorCodes(String formatPrefix, String formatSuffix, String message) {
        Pattern pattern = Pattern.compile(formatPrefix + "([A-Fa-f0-9]{6})" + formatSuffix);
        Matcher matcher = pattern.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);

        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }

        return matcher.appendTail(buffer).toString();
    }

    /**
     * Translates hex color codes with the default format (ex. &#000000#).
     * @param message The message.
     * @return The colored message.
     */
    public static String translateHexColorCodes(String message) {
        return StringUtilities.translateHexColorCodes("&#", "#", message);
    }

    /**
     * Colors a String. First translates color codes (ex. "&6") then translates hex codes (ex. "&#000000#").
     * @param str The String to color.
     * @return The colored String.
     */
    public static String color(String str) {
        str = translateColorCodes(str);

        if (str.contains("#")) {
            str = translateHexColorCodes(str);
        }

        return str;
    }

    /**
     * Colors a List of Strings.
     * @param strings The Strings to color.
     * @return The List of colored Strings.
     */
    public static List<String> color(List<String> strings) {
        return strings.stream().map(StringUtilities::color).collect(Collectors.toList());
    }

    public static List<String> color(String... strings) {
        return StringUtilities.color(Arrays.asList(strings));
    }

    public static String strip(String str) {
        return ChatColor.stripColor(color(str));
    }

    public static List<String> strip(List<String> strings) {
        return strings.stream().map(StringUtilities::strip).collect(Collectors.toList());
    }

    public static List<String> strip(String... strings) {
        return StringUtilities.color(Arrays.asList(strings));
    }

    public static String replace(String s, Map<String, String> replace) {
        if (replace == null || replace.isEmpty()) return s;

        for (Map.Entry<String, String> entry : replace.entrySet()) {
            s = s.replace(entry.getKey(), entry.getValue());
        }

        return s;
    }

    public static List<String> replace(List<String> list, Map<String, String> replace) {
        if (replace == null || replace.isEmpty()) return list;

        List<String> newList = new ArrayList<>();

        for (String s : list) {
            newList.add(replace(s, replace));
        }

        return newList;
    }

    public static ImmutableMap<String, String> replaceMap(String k1, String v1) {
        return ImmutableMap.of(k1, v1);
    }

    public static Map<String, String> replaceMap(String k1, String v1, String k2, String v2) {
        return ImmutableMap.of(k1, v1, k2, v2);
    }

    public static Map<String, String> replaceMap(String k1, String v1, String k2, String v2, String k3, String v3) {
        return ImmutableMap.of(k1, v1, k2, v2, k3, v3);
    }

    public static Map<String, String> replaceMap(String k1, String v1, String k2, String v2, String k3, String v3, String k4, String v4) {
        return ImmutableMap.of(k1, v1, k2, v2, k3, v3, k4, v4);
    }

    public static Map<String, String> replaceMap(String k1, String v1, String k2, String v2, String k3, String v3, String k4, String v4, String k5, String v5) {
        return ImmutableMap.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
    }

    /**
     * Checks if the first argument equals any of the subsequent arguments while ignoring String case.
     * @param str The String to compare.
     * @param any The Strings to compare against.
     * @return Whether or not the first String equals any of the subsequent Strings, case ignored.
     */
    public static boolean equalsAny(String str, String... any) {
        for (String s : any) {
            if (s.equalsIgnoreCase(str)) return true;
        }

        return false;
    }

    public static Boolean parseBoolean(String str) {
        if (!equalsAny(str, "true", "false", "enable", "disable", "yes", "no")) return null;

        if (equalsAny(str, "true", "enable", "yes")) return true;
        if (equalsAny(str, "false", "disable", "no")) return false;

        return null;
    }
}
