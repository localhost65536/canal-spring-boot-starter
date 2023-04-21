package cn.king.canal.client.spring.boot.framework.util;

import org.apache.commons.lang.time.DateUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

/**
 * 字符串转换工具类
 */
public class StringConvertUtil {

    public StringConvertUtil() {
    }

    private static String[] PARSE_PATTERNS = new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss",
            "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
            "yyyy.MM.dd HH:mm", "yyyy.MM"};
    
    /**
     * @author:
     * @time: 2023-04-21 0:08
     * @param: type
     * @param: columnValue
     * @return: java.lang.Object
     * @description: 将字符串转换为指定类型。
     * 例如：将字符串 "1" 转换为 Integer 类型的 1，将字符串 "true" 转换为 Boolean 类型的 true。
     */
    static Object convertType(Class<?> type, String columnValue) {
        if (columnValue == null) {
            return null;
        } else if (type.equals(Integer.class)) {
            return Integer.parseInt(columnValue);
        } else if (type.equals(Long.class)) {
            return Long.parseLong(columnValue);
        } else if (type.equals(Boolean.class)) {
            return convertToBoolean(columnValue);
        } else if (type.equals(BigDecimal.class)) {
            return new BigDecimal(columnValue);
        } else if (type.equals(Double.class)) {
            return Double.parseDouble(columnValue);
        } else if (type.equals(Float.class)) {
            return Float.parseFloat(columnValue);
        } else if (type.equals(Date.class)) {
            return parseDate(columnValue);
        } else {
            return type.equals(java.sql.Date.class) ? parseSqlDate(columnValue) : columnValue;
        }
    }

    private static Date parseDate(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                return DateUtils.parseDate(str, PARSE_PATTERNS);
            } catch (ParseException var2) {
                return null;
            }
        }
    }

    private static Date parseSqlDate(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                Date date = DateUtils.parseDate(str, PARSE_PATTERNS);
                return new java.sql.Date(date.getTime());
            } catch (ParseException var2) {
                return null;
            }
        }
    }

    private static boolean convertToBoolean(String value) {
        return "1".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value);
    }

}
