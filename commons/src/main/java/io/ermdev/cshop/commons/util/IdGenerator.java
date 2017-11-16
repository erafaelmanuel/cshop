package io.ermdev.cshop.business.util;

import java.util.Calendar;

public class IdGenerator {

    public static long randomUUID() {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final String base = "999999999999";
        final StringBuilder stringBuilder = new StringBuilder();
        for(char c : base.toCharArray()) {
            int temp = (int)((Integer.parseInt(String.valueOf(c))) * Math.random());
            temp += (int) (2 * Math.random());
            stringBuilder.append(temp);
        }
        return Long.parseLong(year+stringBuilder.toString());
    }
}
