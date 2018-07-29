package com.rem.cs.commons;

import javax.validation.constraints.NotNull;

public class NumberUtils {

    public static Number getOrDefault(Number num1, @NotNull Number num2) {
        return num1 != null ? num1 : num2 != null ? num2 : 0;
    }

    public static int zeroBased(Number num) {
        return (int) (num != null && num.longValue() > 0 ? num.longValue() - 1 : 0);
    }
}
