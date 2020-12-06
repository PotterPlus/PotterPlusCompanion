package io.github.grisstyl.potterplus.api.misc;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Copyright (c) 2013-2020 Tyler Grissom
 */
public class FriendlyBooleans {

    private static List<String> trueValues = ImmutableList.of("true", "yes", "enable");
    private static List<String> falseValues = ImmutableList.of("false", "no", "disable");

    public static boolean isFriendlyBoolean(String s) {
        s = s.toLowerCase();

        return trueValues.contains(s) || falseValues.contains(s);
    }

    public static boolean getFriendlyBoolean(String s) {
        s = s.toLowerCase();

        return trueValues.contains(s);
    }
}
