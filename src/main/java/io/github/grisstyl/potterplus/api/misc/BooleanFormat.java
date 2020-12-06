package io.github.grisstyl.potterplus.api.misc;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

/**
 * Represents MC-friendly formats for booleans.
 */
@RequiredArgsConstructor
public enum BooleanFormat {

    TRUE_FALSE("true", "false"),

    YES_NO("yes", "no"),

    ENABLED_DISABLED("enabled", "disabled");

    public static String format(boolean bool, BooleanFormat format, boolean capitalized, boolean colored) {
        String str = bool ? format.trueStr : format.falseStr;
        ChatColor color = bool ? ChatColor.GREEN : ChatColor.RED;

        str = capitalized ? StringUtils.capitalize(str) : str;
        str = colored ? color + str : str;

        return str;
    }

    public static String format(boolean bool, BooleanFormat format) {
        return format(bool, format, false, true);
    }

    @NonNull
    String trueStr, falseStr;

    public String format(boolean bool) {
        return BooleanFormat.format(bool,this);
    }

    public String format(boolean bool, boolean capitalized, boolean colored) {
        return BooleanFormat.format(bool, this, capitalized, colored);
    }
}
