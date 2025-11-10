package org.taxionline.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter DD_MM_AA_hh_mm_ss = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

    public static String formatDate(LocalDateTime date) {
        return date.format(DD_MM_AA_hh_mm_ss);
    }
}
