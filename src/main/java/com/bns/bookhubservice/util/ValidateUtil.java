package com.bns.bookhubservice.util;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class ValidateUtil {
    private ValidateUtil() {
        // 유틸클래스 선언 방지
    }
    public static boolean isEmpty(final Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String) {
            return !StringUtils.hasText(String.valueOf(object));
        }
        if (object instanceof Map) {
            return ((Map<?, ?>) object).isEmpty();
        }
        if (object instanceof List) {
            return ((List<?>) object).isEmpty();
        }
        if (object instanceof Object[]) {
            return (((Object[]) object).length == 0);
        }
        return false;
    }
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }
}
