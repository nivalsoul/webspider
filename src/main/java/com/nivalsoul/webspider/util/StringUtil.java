/*
 * StringUtil.java Copyright san
 */
package com.nivalsoul.webspider.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.CollationKey;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * <p>StringUtil</p>
 * <p>Description:字符串工具类</p>
 * @author xuwl fengsl 2013
 * @version 1.0
 */
public final class StringUtil {

    private static Pattern NUMBER_PATTERN = Pattern.compile("[0-9]+");

    private StringUtil() {

    }
    
    private static final char[] digits = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z'};
    
    
    /**
     * 指定位数，在左对齐，右边空位添加半角空白<br>
     * st是NULL的时候，返回NULL
     * @param st 对象字符串
     * @param cnt 位数
     * @return 指定位数处理后的字符串
     */
    public static String lPadSpace(String st, int cnt) {
        return lPad(st, ' ', cnt);
    }

    /**
     * 指定位数，在左对齐，右边空位添加字符<br>
     * st是NULL的时候，返回NULL
     * @param st 对象字符串
     * @param pad 添加的字符
     * @param cnt 位数
     * @return 指定位数处理后的字符串
     */
    public static String lPad(String st, char pad, int cnt) {
        if (st == null) return "";
        StringBuilder sb = new StringBuilder(st);
        while (sb.toString().length() < cnt) {
            sb.append(pad);
        }
        return sb.toString();
    }

    /**
     * 指定位数，在左对齐，右边空位添加半角空白<br>
     * st是NULL的时候，返回NULL
     * @param st 对象字符串
     * @param cnt 位数
     * @return 指定位数处理后的字符串
     */
    public static String lPadSpaceAbs(String st, int cnt) {
        return lPadAbs(st, ' ', cnt);
    }

    /**
     * 指定位数，在左对齐，右边空位添加字符<br>
     * st是NULL的时候，则全空格处理
     * @param st 对象字符串
     * @param pad 添加的字符
     * @param cnt 位数
     * @return 指定位数处理后的字符串
     */
    public static String lPadAbs(String st, char pad, int cnt) {
        if (st == null) st = "";
        while (st.endsWith("　")) {
            st = st.substring(0, st.length() - 1);
        }
        StringBuilder sb = new StringBuilder(st.trim());
        while (sb.toString().length() < cnt) {
            sb.append(pad);
        }
        return sb.toString();
    }

    /**
     * 指定位数，在左对齐，右边空位添加0<br>
     * st是NULL的时候，返回NULL
     * @param st 对象字符串
     * @param cnt 位数
     * @return 指定位数处理后的字符串
     */
    public static String lPadZero(String st, int cnt) {
        return lPad(st, '0', cnt);
    }

    /**
     * 指定位数，在左对齐，右边空位添加0<br>
     * st是NULL的时候，返回NULL
     * @param i 对象数字
     * @param cnt 位数
     * @return 指定位数处理后的字符串
     */
    public static String lPadZero(int i, int cnt) {
        return lPad(Integer.toString(i), '0', cnt);
    }

    /**
     * 指定位数，在右对齐，左边空位0<br>
     * st是NULL的时候，返回NULL
     * @param i 对象数字
     * @param cnt 位数
     * @return 指定位数处理后的字符串
     */
    public static String rPadZero(int i, int cnt) {
        return rPad(Integer.toString(i), '0', cnt);
    }

    /**
     * 指定位数，在右对齐，左边空位0<br>
     * st是NULL的时候，返回NULL
     * @param st 对象字符串
     * @param cnt 位数
     * @return 指定位数处理后的字符串
     */
    public static String rPadZero(String st, int cnt) {
        return rPad(st, '0', cnt);
    }

    /**
     * 指定位数，在右对齐，左边空位字符<br>
     * st是NULL的时候，返回NULL
     * @param st 对象字符串
     * @param pad 添加的字符
     * @param cnt 位数
     * @return 指定位数处理后的字符串
     */
    public static String rPad(String st, char pad, int cnt) {
        if (st == null) return "";
        StringBuilder sb = new StringBuilder(st);
        while (sb.length() < cnt) {
            sb.insert(0, pad);
        }
        return sb.toString();
    }

    /**
     * 判断给定的动态数组是否为空（NULL或大小为0）
     * @param al ArrayList 动态数组
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static boolean isEmpty(List al) {
        if (al == null) return true;
        if (al.size() == 0) return true;
        return false;
    }

    /**
     * 判断给定的数组是否为空（NULL或空数组）
     * @param objects Object[]
     * @return
     */
    public static boolean isEmpty(Object[] objects) {
        if (objects == null) return true;
        if (objects.length == 0) return true;
        return false;
    }

    /**
     * 判断给定的对象是否为空（NULL），判断是否为字符串，如果是，则还需要判断是否为空字符串
     * @param str
     * @return
     */
    public static boolean isEmpty(Object o) {
        if (o == null) return true;
        if (o instanceof String) {
            return ((String) o).length() == 0;
        }
        return false;
    }

    /**
     * 字符串是NULL或空串的时候返回TRUE（去空白后）
     * @param str 字符串
     * @return true:空 false:非空
     */
    public static final boolean isTrimEmpty(String str) {

        return (str == null || "".equals(str.trim()));
    }

    /**
     * 判断字符串是否为数字
     * @param str 字符串
     * @return true:是数字 false:非数字
     */
    public static boolean isNumber(String str) {
        if (str == null) return false;
        return NUMBER_PATTERN.matcher(str).matches();
    }

    /**
     * 去掉左边给定的字符
     * @param s String 给定的字符串
     * @param exceptChar char 需要去除的字符
     * @return String 处理后的字符串
     */
    public static String lTrim(String s, char exceptChar) {
        if (s == null) return "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != exceptChar) return s.substring(i);
        }
        return "";
    }

    /**
     * 去掉左边为给定字符串中的字符
     * @param s String 给定的字符串
     * @param exceptChars String 包含需要去除字符的字符串
     * @return String 处理后的字符串
     */
    public static String lTrim(String s, String exceptChars) {
        if (s == null || exceptChars == null) return "";
        for (int i = 0; i < s.length(); i++) {
            if (exceptChars.indexOf(s.charAt(i)) == -1) return s.substring(i);
        }
        return "";
    }

    /**
     * 去掉右边给定的字符
     * @param s String 给定的字符串
     * @param exceptChar char 需要去除的字符
     * @return String 处理后的字符串
     */
    public static String rTrim(String s, char exceptChar) {
        if (s == null) return "";
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) != exceptChar) return s.substring(0, i + 1);
        }
        return "";
    }

    /**
     * 去掉右边为给定字符串中的字符
     * @param s String 给定的字符串
     * @param exceptChars String 包含需要去除字符的字符串
     * @return String 处理后的字符串
     */
    public static String rTrim(String s, String exceptChars) {
        if (s == null || exceptChars == null) return "";
        for (int i = s.length() - 1; i >= 0; i--) {
            if (exceptChars.indexOf(s.charAt(i)) == -1) return s.substring(0, i + 1);
        }
        return "";
    }

    /**
     * 去掉给定字符串两边的字符
     * @param s String 给定的字符串
     * @param exceptChar char 需要去除的字符
     * @return String 处理后的字符串
     */
    public static String trim(String s, char exceptChar) {
        return rTrim(lTrim(s, exceptChar), exceptChar);
    }

    /**
     * 去掉给定字符串中字符两边的字符串
     * @param s String 给定的字符串
     * @param exceptChars String 包含需要去除字符的字符串
     * @return String 处理后的字符串
     */
    public static String trim(String s, String exceptChars) {
        return rTrim(lTrim(s, exceptChars), exceptChars);
    }

    /**
     * 去掉左边的空格
     * @param s String 给定的字符串
     * @return String 处理后的字符串
     */
    public static String lTrim(String s) {
        return lTrim(s, ' ');
    }

    /**
     * 去掉右边的空格
     * @param s String 给定的字符串
     * @return String 处理后的字符串
     */
    public static String rTrim(String s) {
        return rTrim(s, ' ');
    }

    /**
     * 去掉两边的空格。String对象已有此方法
     * @param s String 给定的字符串
     * @return String 处理后的字符串
     */
    public static String trim(String s) {
        if (s == null) return "";
        return s.trim();
    }

    /**
     * 比较两个字符串是否相等
     * @param s1 源字符串1
     * @param s2 源字符串2
     * @return 是否相等。两个都为null则为true，只有一个为null，则为false
     */
    public static boolean compareTo(String s1, String s2) {
    	if (s1 == null && s2 == null) return true;
        if (s1 == null || s2 == null) return false;
        return s1.compareTo(s2) == 0;
    }

    /**
     * 比较两个字符串是否相等（忽略大小写）
     * @param s1 源字符串1
     * @param s2 源字符串2
     * @return 是否相等。两个都为null则为true，只有一个为null，则为false
     */
    public static boolean compareToIgnoreCase(String s1, String s2) {
    	if (s1 == null && s2 == null) return true;
        if (s1 == null || s2 == null) return false;
        return s1.compareToIgnoreCase(s2) == 0;
    }

    /**
     * 返回一个对象的字符串，用于解决String.valueOf()对NULL返回字符串“null”的问题
     * @param o Object 源对象
     * @return String
     */
    public static String getString(Object o) {
        if (o == null) return "";
        try {
            if (o.getClass().isAssignableFrom(Date.class)) {
                return DateUtil.getDateString((Date) o);
            } else if (o.getClass().isAssignableFrom(Timestamp.class)) {
                return o.toString().substring(0, 10);
            } else if (o.getClass().isAssignableFrom(Integer.class)) {
                return o.toString();
            } else if (o.getClass().isAssignableFrom(Short.class)) {
                return o.toString();
            } else if (o.getClass().isAssignableFrom(Boolean.class)) {
                return o.toString();
            } else if (o.getClass().isAssignableFrom(Byte.class)) {
                return o.toString();
            } else if (o.getClass().isAssignableFrom(Float.class)) {
                return o.toString();
            } else if (o.getClass().isAssignableFrom(Time.class)) {
                return o.toString();
            } else if (o.getClass().isAssignableFrom(Long.class)) {
                return o.toString();
            } else if (o.getClass().isAssignableFrom(BigDecimal.class)) {
                return o.toString();
            } else if (o.getClass().isAssignableFrom(Double.class)) {
                return o.toString();
            } else if (o.getClass().isAssignableFrom(Blob.class)) {
                return o.toString();
            } else if (o.getClass().isAssignableFrom(Clob.class)) {
                return o.toString();
            } else {
                return String.valueOf(o);
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 此方法将给出的字符串source使用delim划分为单词数组
     * @param source 需要进行划分的原字符串
     * @param delim 单词的分隔字符串
     * @return 划分以后的数组，如果source为null的时候返回以source为唯一元素的数组， 如果delim为null则使用逗号作为分隔字符串。
     * @since 0.1
     */
    public static String[] split(String source, String delim) {
        String[] wordLists;
        if (source == null) {
            wordLists = new String[1];
            wordLists[0] = source;
            return wordLists;
        }
        if (delim == null) {
            delim = ",";
        }
        StringTokenizer st = new StringTokenizer(source, delim);
        int total = st.countTokens();
        wordLists = new String[total];
        for (int i = 0; i < total; i++) {
            wordLists[i] = st.nextToken();
        }
        return wordLists;
    }
    
    /**
     * 以指定的delim分隔符分隔String，并将分隔的每一个String作为List的一个元素。
     *
     * @param str   需要分隔的string
     * @param delim 分隔String的符合 (null表示以空格作为分隔符)
     * @return 存储了分隔的子串的List
     */
    public static List splitToList(String str, String delim) {
        List splitList = null;
        StringTokenizer st = null;
        if (str == null) {
            return splitList;
        }
        if (delim != null) {
            st = new StringTokenizer(str, delim);
        } else {
            st = new StringTokenizer(str);
        }
        if (st != null && st.hasMoreTokens()) {
            splitList = new ArrayList();

            while (st.hasMoreTokens()) {
                splitList.add(st.nextToken());
            }
        }
        return splitList;
    }

    /**
     * 此方法将给出的字符串source使用delim划分为单词数组
     * @param source 需要进行划分的原字符串
     * @param delim 单词的分隔字符
     * @return 划分以后的数组，如果source为null的时候返回以source为唯一元素的数组
     * @since 0.2
     */
    public static String[] split(String source, char delim) {
        return split(source, String.valueOf(delim));
    }

    /**
     * 此方法将给出的字符串source使用逗号划分为单词数组
     * @param source 需要进行划分的原字符串
     * @return 划分以后的数组，如果source为null的时候返回以source为唯一元素的数组。
     * @since 0.1
     */
    public static String[] split(String source) {
        return split(source, ",");
    }
    
    /**
     * 连接List中的所有元素以创建一个String，其中List中的元素间以指定的delim来分隔
     *
     * @param list  需要连接的String为元素的List
     * @param delim List中的元素的分隔符。(null表示直接连接list中的元素，不加入分割符)
     * @return String 形式为：list的元素＋delim＋list的元素＋delim＋...
     */
    public static String join(List list, String delim) {
        if (list == null || list.size() < 1) {
            return null;
        }
        StringBuffer buf = new StringBuffer();
        Iterator i = list.iterator();

        while (i.hasNext()) {
            buf.append((String) i.next());
            if (i.hasNext()) {
                buf.append(delim);
            }
        }
        return buf.toString();
    }

    /**
     * 将字符串数组使用指定的分隔符合并成一个字符串。
     *
     * @param array 字符串数组
     * @param delim 分隔符，为null的时候使用""作为分隔符（即没有分隔符）
     * @return 合并后的字符串
     */
    public static String join(String[] array, String delim) {
        int length = array.length - 1;
        if (delim == null) {
            delim = "";
        }
        StringBuffer result = new StringBuffer(length * 8);
        for (int i = 0; i < length; i++) {
            result.append(array[i]);
            result.append(delim);
        }
        result.append(array[length]);
        return result.toString();
    }

    /**
     * 字符串数组中是否包含指定的字符串，区分大小写
     * @param strings 字符串数组
     * @param string 字符串
     * @return 包含时返回true，否则返回false
     * @since 0.4
     */
    public static boolean contains(String[] strings, String string) {
    	 for (int i = 0; i < strings.length; i++) {
    		 if (strings[i].equals(string)) {
                 return true;
             }
    	 }
         return false;
    }

    /**
     * 判定字符串数组中是否包含指定的字符串，不区分大小写
     * @param strings 字符串数组
     * @param string 字符串
     * @return 包含时返回true，否则返回false
     * @since 0.4
     */
    public static boolean containsIgnoreCase(String[] strings, String string) {
    	 for (int i = 0; i < strings.length; i++) {
    		 if (strings[i].equalsIgnoreCase(string)) {
                 return true;
             }
    	 }
         return false;
    }

    /**
     * 将字符串数组使用指定的分隔符合并成一个字符串
     * @param array 字符串数组
     * @param delim 分隔符，为null的时候使用""作为分隔符（即没有分隔符）
     * @return 合并后的字符串
     * @since 0.4
     */
    public static String combineStringArray(String[] array, String delim) {
        int length = array.length - 1;
        if (delim == null) {
            delim = "";
        }
        StringBuffer result = new StringBuffer(length * 8);
        for (int i = 0; i < length; i++) {
            result.append(array[i]);
            result.append(delim);
        }
        result.append(array[length]);
        return result.toString();
    }

    /**
     * 将字符串转换为长整型。当字符串不是数字时，返回0
     * @param s 含数字的字符串
     * @return 长整数。转换失败时，返回0
     */
    public static long getLong(String s) {
        try {
            if (isEmpty(s) == true) return 0;
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * 利用错误处理机制，判断指定字符串是否是一个长整数
     * @param s
     * @return true/false
     */
    public static boolean isLong(String s) {
        try {
            if (isEmpty(s) == true) return false;
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 将字符串转换为双精度浮点型。当字符串不是数字时，返回0
     * @param s 含数字的字符串
     * @return 双精度浮点型。转换失败时，返回0
     */
    public static double getDouble(String s) {
        try {
            if (isEmpty(s) == true) return 0;
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * 利用错误处理机制，判断指定字符串是否是一个双精度浮点型
     * @param s
     * @return true/false
     */
    public static boolean isDouble(String s) {
        try {
            if (isEmpty(s) == true) return false;
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 将boolean转换为长整型。TRUE为1，FALSE为0
     * @param s 含数字的字符串
     * @return 整数。转换失败时，返回0
     */
    public static long getLong(boolean s) {
        return getInt(s);
    }

    /**
     * 将boolean转换为整型。TRUE为1，FALSE为0
     * @param s 含数字的字符串
     * @return 整数。转换失败时，返回0
     */
    public static int getInt(boolean s) {
        if (s)
            return 1;
        else
            return 0;
    }

    /**
     * 将字符串转换为整型。当字符串不是数字时，返回0
     * @param s 含数字的字符串
     * @return 整数。转换失败时，返回0
     */
    public static int getInt(String s) {
        try {
            if (isEmpty(s) == true) return 0;
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * 将浮点数转换为整型。当字符串不是数字时，返回0
     * @param s double
     * @return 整数。转换失败时，返回0
     */
    public static int getInt(double s) {
        return (new BigDecimal(String.valueOf(s))).intValue();
    }

    /**
     * 将对象转换为字符串，再转换为整型。当字符串不是数字时，返回0
     * @param s 含数字的对象
     * @return 整数。转换失败时，返回0
     */
    public static int getInt(Object s) {
        if (isEmpty(s) == true) return 0;
        return getInt(String.valueOf(s));
    }

    /**
     * 利用错误处理机制，判断指定字符串是否是一个整数
     * @param s
     * @return true/false
     */
    public static boolean isInt(String s) {
        try {
            if (isEmpty(s) == true) return false;
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 根据给定的字符串返回其意义是否为true
     * @param s String 字符串
     * @return boolean true/false，只有s为“true”（大小写不敏感）时才返回true
     */
    public static boolean getBooleanOfString(String s) {
        if (isEmpty(s)) return false;
        if (s.compareToIgnoreCase("true") == 0) return true;
        return false;
    }

    /**
     * 返回布尔值的字符串
     * @param b boolean 布尔变量
     * @return String 布尔值的字符串形式
     */
    public static String getStringOfBoolean(boolean b) {
        if (b) return "true";
        return "false";
    }

    /**
     * 从字符串的尾部取指定字符位置，以C为结束符，找不到C则返回-1
     * @param s String
     * @param c char
     * @return int
     */
    public static int indexFromTail(String s, char c) {
        if (isEmpty(s)) return -1;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == c) return i;
        }
        return -1;
    }

    /**
     * 从字符串的尾部取子字符串，以C为结束符
     * @param s String
     * @param c char
     * @return String
     */
    public static String getSubStringFromTail(String s, char c) {
        if (isEmpty(s)) return null;
        int i = indexFromTail(s, c);
        if (i < 0 || i >= s.length() - 1) return null;
        return s.substring(i + 1);
    }

    /**
     * 以c为分隔符，取删除最后部分的其它内容，不包含c
     * @param s String
     * @param c char
     * @return String
     */
    public static String getSubStringByDelPartFromTail(String s, char c) {
        if (isEmpty(s)) return null;
        int i = indexFromTail(s, c);
        if (i < 0 || i >= s.length()) return null;
        return s.substring(0, i);
    }

    /**
     * 判断两个字符串是否相等
     * @param s1 String 字符串1
     * @param s2 String 字符串2
     * @return boolean true/false
     */
    public static boolean isEqualValue(String s1, String s2) {
        return compareTo(s1, s2);
    }

    /**
     * 判断两个长整型是否相等
     * @param long1 long 长整型1
     * @param long2 long 长整型2
     * @return boolean true/false
     */
    public static boolean isEqualValue(long long1, long long2) {
        return long1 == long2;
    }

    /**
     * 判断两个整型是否相等
     * @param int1 int 整型1
     * @param int2 int 整型2
     * @return boolean true/false
     */
    public static boolean isEqualValue(int int1, int int2) {
        return int1 == int2;
    }

    /**
     * 判断一个长整型，一个字符串是否相等
     * @param long1 long 长整型
     * @param long2 String 字符串
     * @return boolean true/false
     */
    public static boolean isEqualValue(long long1, String long2) {
        if (isLong(long2) == false) return false;
        return long1 == getLong(long2);
    }

    /**
     * 判断一个长整型，一个字符串是否相等
     * @param long1 String 字符串
     * @param long2 long 长整型
     * @return boolean true/false
     */
    public static boolean isEqualValue(String long1, long long2) {
        if (isLong(long1) == false) return false;
        return long2 == getInt(long1);
    }

    /**
     * 判断一个整型，一个字符串是否相等
     * @param int1 int 整型
     * @param int2 String 字符串
     * @return boolean true/false
     */
    public static boolean isEqualValue(int int1, String int2) {
        if (isInt(int2) == false) return false;
        return int1 == getLong(int2);
    }

    /**
     * 判断一个整型，一个字符串是否相等
     * @param int1 String 字符串
     * @param int2 int 整型
     * @return boolean true/false
     */
    public static boolean isEqualValue(String int1, int int2) {
        if (isInt(int1) == false) return false;
        return int2 == getInt(int1);
    }

    /**
     * 将二进制转换成字符串
     * @param b byte[] 二进制
     * @return String
     */
    public static String byteToString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            if ((0xff & b[i]) < 0x10) { //new code
                hexString.append("0" + Integer.toHexString((0xFF & b[i]))); //new code
            } else { //new code
                hexString.append(Integer.toHexString(0xFF & b[i])); //old code
            }
        }
        return hexString.toString();
    }
    
    /**
     * 将字符串转成二进制
     * @param b 字符串
     * @return
     */
    public static byte[] stringToByte(String b) {
		if ((b.length() % 2) != 0) throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length() / 2];
		for (int n = 0; n < b.length(); n += 2) {
			String item = b.substring(n, n + 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

    /**
     * 计算字符在一个字符串中出现的次数
     * @param str String 字符串
     * @param ch char 字符
     * @return int 出现的次数
     */
    public static int getCharCount(String str, char ch) {
        if (isEmpty(str)) return 0;
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ch) count++;
        }
        return count;
    }
    
    /**
     * 返回字符串中出现指定子字符串的数量
     * @param str 字符串
     * @param subStr 字符
     * @return 出现次数
     */
	public static int getStringCount(String str, String subStr) {
        if (StringUtil.isEmpty(str) || StringUtil.isEmpty(subStr)){
            return 0;
        }
        int count = 0, i = 0;
        while((i = str.indexOf(subStr, i)) >= 0){
        	count ++;
        	i += subStr.length();
        }
        return count;
    }

    /**
     * 截取字符串的前面几位，后面的用省略号代替
     * @param str 需要截取的字符串
     * @param len 需要截取的长度
     * @return String 返回截取后的字符串
     */
    public static String subString(String str, int len) {
        if (str == null) return null;
        StringBuffer sb = new StringBuffer();
        char[] cc = str.toCharArray();
        int strLen = cc.length;
        int i = 0;
        int j = 0;
        int newLen = len * 2;
        while (i < newLen && j < strLen) {
            int c = cc[j];
            if (c > 127)
                i += 2;
            else
                i++;
            sb.append(cc[j]);
            j++;
        }
        if (j < strLen) sb.append("…");
        return sb.toString();
    }
    
    /**
     * 补充String类的不足。查找字符串s中，从index开始，出现字符串exceptChars中的任意字符的位置
     * @param s String 给定的被查找字符串
     * @param index int 开始位置
     * @param exceptChars String 定位字符组成的字符串
     * @return int 返回字符串s中，从index开始，出现字符串exceptChars中的任意字符的位置。未找到，则返回-1
     */
    public static int indexOf(String s, int index, String exceptChars) {
        int i = 0;
        for (i = index; i < s.length(); i++) {
            if (exceptChars.indexOf(s.charAt(i)) != -1) return i;
        }
        return -1;
    }

    /**
     * 补充String类的不足。字符串s中，出现字符串exceptChars中的任意字符的位置
     * @param s String 给定的被查找字符串
     * @param exceptChars String 定位字符组成的字符串
     * @return int 返回字符串s中，出现字符串exceptChars中的任意字符的位置。未找到，则返回-1
     */
    public static int indexOf(String s, String exceptChars) {
        return indexOf(s, 0, exceptChars);
    }

    /**
     * 在指定范围内寻找指定的字符串
     * @param s String 主串
     * @param findStr String 被查找串
     * @param startIndex int 开始位置
     * @param endIndex int 结束位置，不包含此位置的字符
     * @return int 出现的位置，不是相对位置。未找到，则返回－1
     */
    public static int indexOf(String s, String findStr, int startIndex, int endIndex) {
        if (s == null || findStr == null) return -1;
        if (startIndex < 0 || endIndex < 0 || endIndex <= startIndex) return -1;
        String p = s.substring(startIndex, endIndex);
        int i = p.indexOf(findStr);
        if (i >= 0) i = i + startIndex;
        return i;
    }

    /**
     * 在指定范围内寻找指定的字符串（大小写不敏感）
     * @param s String 主串
     * @param findStr String 被查找串
     * @param startIndex int 开始位置
     * @param endIndex int 结束位置，不包含此位置的字符
     * @return int 出现的位置，不是相对位置。未找到，则返回－1
     */
    public static int indexOfIgnoreCase(String s, String findStr, int startIndex, int endIndex) {
        if (s == null || findStr == null) return -1;
        if (startIndex < 0 || endIndex < 0 || endIndex <= startIndex) return -1;
        String p = s.substring(startIndex, endIndex);
        int i = p.toLowerCase().indexOf(findStr.toLowerCase());
        if (i >= 0) i = i + startIndex;
        return i;
    }

    /**
     * 在指定位置开始寻找指定的字符串（大小写不敏感）
     * @param s String 主串
     * @param findStr String 被查找串
     * @param startIndex int 开始位置
     * @return int 出现的位置，不是相对位置。未找到，则返回－1
     */
    public static int indexOfIgnoreCase(String s, String findStr, int startIndex) {
        return indexOfIgnoreCase(s, findStr, startIndex, s.length());
    }

    /**
     * 在指定的字符串s中查找指定的字符串findStr（大小写不敏感）
     * @param s String 主串
     * @param findStr String 被查找串
     * @return int 出现的位置，不是相对位置。未找到，则返回－1
     */
    public static int indexOfIgnoreCase(String s, String findStr) {
        return indexOfIgnoreCase(s, findStr, 0);
    }
    
    /**
     * 字符串为Null转换为BLANK
     * @param str 字符串
     * @return 转换后的字符串
     */
    public static String transNull(String str) {
        if (str == null) str = "";
        return str;
    }
    
    /**
	 * 判断字符串中的字符是否全部都是英文字母。出现非英文字母时返回false。如果为null，则返回true
	 * @param content String 
	 * @return 如果为null，则返回true
	 */
	public static boolean isLetterString(String content){
	    if (content == null) return true;
	    for(int i=0; i<content.length(); i++){
	        if (Character.isLetter(content.charAt(i)) == false) return false;
	    }
	    return true;
	}
    
	 /**
     * 转换成输入的编码字符
     * @param str 需要转码的字符串
     * @param encoding 需要转码的编码
     * @return String 返回编码后的字符串
     */
	public static String transEncoding(String str,String encoding) {
	    return transEncoding(str,"ISO8859_1",encoding);
	}

    /**
     * 转换成输入的编码字符
     * @param str 需要转码的字符串
     * @param comeEncoding 输入的字符串编码
     * @param encoding 需要转码的编码
     * @return String 返回编码后的字符串
     */
	public static String transEncoding(String str,String comeEncoding,String encoding) {
	    try {
	        String old_str = str;
	        if(str == null) {
	            return null;
	        }
	        str = new String(str.getBytes(comeEncoding), encoding);
	        if(str.indexOf("????") == -1) {
	            return str;
	        }
	        return old_str;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return str;
	    }
	}
	
    /**
     * 转换成GB2312编码字符
     * @param str 需要转码的字符串
     * @return String 返回编码后的字符串
     */
	  public static String transEncoding(String str) {
		  return transEncoding(str,"GB2312");
	  }
	     
     /**
      * 将字符串转换成sql能够识别的字符串
      * @param s String 字符串
      * @return String 处理后的字符串
      */
     public static String toSqlString(String s) {
         if (s == null) return "";
         return s.replaceAll("'", "''");
     }
     
     /**
      * 替换mainStrin中的第一个oldString为newString，并返回替换后的String<br>
      * 支持对null参数的处理
      * @param mainString 需要替换其中的某个子串的String
      * @param oldString  mainString中的要替换的String
      * @param newString  替换oldString的String
      * @return 替换后的String
      */
     public static String replaceFirst(String mainString, String oldString,String newString) {
    	 if (mainString == null)   return null;
    	 if (oldString == null || oldString.length() == 0)  return mainString;
    	 if (newString == null)   newString = "";
    	 return mainString.replaceFirst(oldString, newString);
     }

     /**
      * 替换mainStrin中的所有oldString为newString，并返回替换后的String<br>
      * 支持对null参数的处理
      * @param mainString 需要替换其中的某个子串的String
      * @param oldString  mainString中的要替换的String
      * @param newString  替换oldString的String
      * @return newString替换mainString中的oldString后的新String
      */
     public static String replaceAll(String mainString, String oldString,String newString) {
         if (mainString == null)   return null;
         if (oldString == null || oldString.length() == 0)  return mainString;
         if (newString == null)   newString = "";
         return mainString.replaceAll(oldString, newString);
     }
     
     /**
      * 将由分隔符的字符串转换成sql中可以放在IN之后的字符串
      * 如: aaa,bbb,ccc,ddd,de
      * 转换结果为：'aaa','bbb','ccc','ddd','de'
      *
      * @param source
      * @param delim
      * @return
      */
     public static String ChangeToInsqlType(String source, String delim) {
         String s = "";
         String[] l = split(source, delim);
         for (int k = 0; k < l.length; k++) {
             s += "'" + l[k] + "'";
             if (k != l.length - 1) s += ",";
         }
         return s;
     }
     
     /**
      * 从存储了name/value对的String中得到Map。并对其中的name和value进行Html的编码。
      *
      * @param str 转换为Map的String，String的形式为：name1=value1?name2=value2?...
      * @return a Map 存储了name/value对
      */
     @SuppressWarnings({ "deprecation", "unchecked" })
	public static Map stringToMap(String str) {
         if (str == null) {
             return null;
         }
         Map decodedMap = new HashMap();
         List elements = splitToList(str, "?");
         Iterator i = elements.iterator();
         while (i.hasNext()) {
             String s = (String) i.next();
             List e = splitToList(s, "=");
             if (e.size() != 2) {
                 continue;
             }
             String name = (String) e.get(0);
             String value = (String) e.get(1);

             decodedMap.put(URLDecoder.decode(name), URLDecoder.decode(value));
         }
         return decodedMap;
     }

     /**
      * 从Map中得到经过Html格式编码的String。注意：map的元素必须都是String。
      *
      * @param map 存储name/value对的Map
      * @return String 经过Html格式编码的String：name1=value1?name2=value2?...
      */
     @SuppressWarnings({ "deprecation", "rawtypes" })
	public static String mapToString(Map map) {
         if (map == null) {
             return null;
         }
         StringBuffer buf = new StringBuffer();
         Set keySet = map.keySet();
         Iterator i = keySet.iterator();
         boolean first = true;
         while (i.hasNext()) {
             Object key = i.next();
             Object value = map.get(key);
             if (!(key instanceof String) || !(value instanceof String)) {
                 continue;
             }
             String encodedName = URLEncoder.encode((String) key);
             String encodedValue = URLEncoder.encode((String) value);
             if (first) {
                 first = false;
             } else {
                 buf.append("?");
             }
             buf.append(encodedName);
             buf.append("=");
             buf.append(encodedValue);
         }
         return buf.toString();
     }
     
     /**
      * 产生指定长度的随机字符串
      * <p>所产生的字符串包括从0到9这些数字及26个大写英文字母
      *
      * @param length 所需字符串的长度，整数。
      * @return 字符串
      */
     public static String getRandomString(int length) {
         int temp;
         String strRnd = "";
         for (int i = 0; i < length; i++) {
             temp = (new Double(Math.random() * 997)).intValue() % digits.length;
             strRnd += String.valueOf(digits[temp]);
         }
         return strRnd;
     }
     
     /**
      * 转换小于6位的数字，开始有0则加"零"，末尾有0则不加
      * @param str
      * @return
      * @author xuwl
      */
     private static String getShort(String str) {
    	 String[] num = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
         String[] unit = {"", "十", "百", "千", "万"};
         String value="";
         int j=str.length()-1;
         for (int i = 0; i < str.length(); j--, i++) {
        	 //遇到0以后则判断其后是否还有数字，没有则跳出循环，有则添加"零"
        	 if(str.charAt(i)=='0'){
        		 if(Long.parseLong(str.substring(i))>0){
        			 value += "零";
        			 while(str.charAt(i+1)=='0'){
        				 i++; j--;
        			 }
        		 }
        	 }else{
        		 value += num[Integer.parseInt(String.valueOf(str.charAt(i)))] + unit[j];
        	 }
         }
         return value;
	 }

     /**
      * 将阿拉伯数字转换为中文大写数字
      * <br><b>注意：</b><br>
      * 1、字符串开头不能有0，若有0，请在传参数前去掉，否则转换不正确<br>
      * 2、数字如果超过千万亿（16位），转换的读法可能不恰当
      * @param number String类型的阿拉伯数字
      * @return 中文大写数字
      * @author xuwl
      */
     public static String toChineseNumber(String number) {
    	 number=number.replaceAll(" ", "").replaceAll(",", "");
    	 if(number.length()>8){
    		 String n1=number.substring(0, number.length()-8);
    		 String n2=number.substring(number.length()-8);
    		 String nn=toChineseNumber(n1)+"亿";
    		 if(n1.endsWith("0")&&!n2.startsWith("0"))
    			 nn+="零";
    		 return nn+toChineseNumber(n2);
    	 }

         String value = "";
         String s1,s2,s3,ss;
         if(number.length()<5){
        	 s1=number;
        	 value=getShort(s1);
         }else if (number.length()<9) {
			s1=number.substring(number.length()-4);
			s2=number.substring(0,number.length()-4);
			value=getShort(s2);
			if(!value.equals(""))
				value+="万";
			if(s2.endsWith("0") && !s1.startsWith("0")){
				value+="零"+getShort(s1);
			}else {
				value+=getShort(s1);
			}
		 }else {
			 //此处是未加递归前为了转换亿到万亿的数而使用，如今开头加了递归，此处已不再需要
			/* s1=number.substring(number.length()-4);
			 s2=number.substring(number.length()-8,number.length()-4);
			 s3=number.substring(0,number.length()-8);
			 value=getShort(s3)+"亿";
			 ss=getShort(s2);
			 if(!ss.equals("")){
				 value+=ss+"万";
			 }
			 if(s1.startsWith("0")){
				 value+=getShort(s1);
			 }else {
				 if(s2.endsWith("0"))
					 value+="零";
				 value+=getShort(s1);
			 }*/
		 }
         //依据读法，这三个数特别处理
         if(value.equals("一十")||value.equals("一十万")||value.equals("一十亿"))
        	 value=value.substring(1);
         return value;
     }

     /**
      * 将阿拉伯数字转换为中文大写数字
      *
      * @param number 阿拉伯数字
      * @return 中文大写数字
      * @author xuwl
      */
     public static String toChineseNumber(long number) {
         return toChineseNumber(String.valueOf(number));
     }
     
     /**
      * 将oracle的clob字段转为相应的字符串
      *
      * @param cl
      * @return 转换后的字符串
      * @throws SQLException
      */
     public static String getClobString(java.sql.Clob cl) throws SQLException {
         if (cl == null) return "";
         Reader clobReader = cl.getCharacterStream();
         if (clobReader == null) {
             return null;
         }
         String str = new String();
         BufferedReader bufferedClobReader = new BufferedReader(clobReader);
         try {
             String line = null;
             while ((line = bufferedClobReader.readLine()) != null) {
                 str += line;
             }
             bufferedClobReader.close();
         } catch (IOException e) {
             throw new SQLException(e.toString());
         }
         return str;
     }
     
     /**
      * 将oracle的clob字段转为相应的字符串
      * @param clob
      * @return
      * @throws Exception
      */
     public static String transClobToString(Clob clob) throws Exception {
		if (clob == null)
			return "";
		String detailinfo = "";
		if (clob != null) {
			detailinfo = clob.getSubString((long) 1, (int) clob.length());
		}
		return detailinfo;
	}
     
     /**
      * 返回文件大小的字符串
      * 如果大于1MB，则以MB为单位，返回两位小数单位
      * 如果大小1KB，则以KB为单位，返回整数，
      * 如果小于1KB，则以Byte为单位
      *
      * @param fileSize
      * @return
      */
     public static String getFileSize(int fileSize) {
         return getFileSize((long) fileSize);
     }


     /**
      * 返回文件大小的字符串
      * 如果大于1MB，则以MB为单位，返回两位小数单位
      * 如果大小1KB，则以KB为单位，返回整数，
      * 如果小于1KB，则以Byte为单位
      *
      * @param fileSize
      * @return
      */
     public static String getFileSize(long fileSize) {
         String reStr = "";
         long Igb, Imb = 0, temp = 0, Ikb = 0, Ib = 0;
         if (fileSize > 1024 * 1024 * 1024) {
             Igb = fileSize / (1024 * 1024 * 1024);
             temp = (fileSize % (1024 * 1024 * 1024)) * 100 / (1024 * 1024 * 1024);
             reStr = Igb + "." + temp + "GB";
         } else if (fileSize > 1024 * 1024) {
             Imb = fileSize / (1024 * 1024);
             temp = (fileSize % (1024 * 1024)) * 100 / (1024 * 1024);
             reStr = Imb + "." + temp + "MB";
         } else if (fileSize > 1024) {
             Ikb = fileSize / 1024;
             reStr = Ikb + "KB";
         } else {
             Ib = fileSize;
             reStr = Ib + "字节";
         }
         return reStr;
     }
     
     /**
      * 在特定的字符前插入指定的字符串
      * @param sourceStr 源串
      * @param insertStr 欲插入串
      * @param splitStr 分割符号
      * @param isBefore 是否在之前插入 （true表示之前插入  false表示之后插入）
      * @return
      */
     public static String insertString(String sourceStr,String insertStr,String splitStr,Boolean isBefore){
         StringBuffer newStr=new StringBuffer();
         try{
             if(sourceStr==null || "".equals(sourceStr)){//源串为空的情况
                 return newStr.toString();
             }
             String[] sourceStrArray=sourceStr.split(splitStr);
             for(int i=0;i<sourceStrArray.length;i++){
                 if(isBefore){ //前插入
                     newStr.append(insertStr);
                     newStr.append(sourceStrArray[i]);
                 }else{//之后插入
                     newStr.append(sourceStrArray[i]);
                     newStr.append(insertStr);
                 }
             }
             System.out.println("insertString newStr:"+newStr.toString());
             return newStr.toString();
         }catch(Exception e){
             System.out.println("StringUtil insertString is error!");
             e.printStackTrace();
             return  newStr.toString();
		}
	 }

    
 
     /**
 	 * @Title: getPercentage
 	 * @description: 得到数字的百分比
 	 * @param percentage  浮点型数值
 	 * @return String 如0.85将返回85%
 	 */
 	public static String getPercentage(float percentage) {
 		percentage *= 100;
 		String str = Long.toString((long) percentage);
 		str += "%";
 		return str; 
 	}
 	
 	public static String encode(String text)
    {
    	String newText = "";
    	byte[] bts;
		try 
		{
			bts = text.getBytes("utf-8");
			for(int i = 0 ; i < bts.length ; i++)
	    	{
				newText += "%"+String.format("%X", bts[i]);
	    	}
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		return newText;
    }
	
	
	public static String decode(String text)
	{
		if(!text.startsWith("%"))
		{
			return text;
		}
		String[] hexs = text.split("%");
		byte[] btyes = new byte[hexs.length - 1];
		int i = 0;
		for(String hex : hexs)
		{
			if(!hex.equalsIgnoreCase(""))
			{
				btyes[i] = (byte) (0xff & Integer.parseInt(hex, 16));
				i++;
			}
		}	
		try {
			text = new String(btyes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return text;
	}
	
	
	
	
	
	
	/***********一下是一些不常用的方法********************/
	
	/**
     * 反转静态数组
     * @param objects 需要被反转的静态数组
    */
	public static void reverseArray(Object[] objects){
		if (objects == null) return ;
		int length = objects.length - 1;
		for(int i=0; i<objects.length / 2; i++){
			// 与倒数第几个交换
			Object t = objects[i];
			objects[i] = objects[length - i];
			objects[length - i] = t;
		}
	}
	
    /**
     * 反转静态数组
     * @param longs 需要被反转的静态数组
     */
	public static void reverseArray(long[] longs){
		if (longs == null) return ;
		int length = longs.length - 1;
		for(int i=0; i<longs.length / 2; i++){
			// 与倒数第几个交换
			long t = longs[i];
			longs[i] = longs[length - i];
			longs[length - i] = t;
		}
	}
	
    /**
     * 反转动态数组
     * @param al 需要被反转的动态数组
     */
	@SuppressWarnings("unchecked")
    public static void reverseArrayList(ArrayList al){
		if (al == null) return ;
		int length = al.size() - 1;
		for(int i=0; i<al.size() / 2; i++){
			// 与倒数第几个交换
			Object t = al.get(i);
			al.add(i, al.get(length - i));
			al.remove(i + 1);
			al.remove(length - i);
			al.add(length - i, t);
		}
	}
    
    /**
     * 从一个对象数据组中的抽取出指定名称函数返回的字符串值组成的字符串数组。
     * @param objectArrayList 对象动态数据
     * @param getMethodName 方法名（区分大小写）
     * @return 字符串数组
     * @throws IllegalAccessException 无效访问 
     * @throws InvocationTargetException 
     * @throws Exception
    */
    @SuppressWarnings("unchecked")
    public static String[] getStringArray(List objectArrayList, String getMethodName) 
    						throws IllegalAccessException, InvocationTargetException, Exception{
        return getStringArray(objectArrayList, getMethodName, null);
    }
    /**
     * 从一个对象数据组中的抽取出指定名称函数返回的字符串值组成的字符串数组。
     * @param objectArrayList 对象动态数据
     * @param getMethodName 方法名（区分大小写）
     * @param args 方法对应的调用参数
     * @return 字符串数组
     * @throws IllegalAccessException 无效访问 
     * @throws InvocationTargetException 
     * @throws Exception
    */
    @SuppressWarnings("unchecked")
    public static String[] getStringArray(List objectArrayList, String getMethodName, Object[] args) 
    						throws IllegalAccessException, InvocationTargetException, Exception{
        if (objectArrayList == null) return null;
        String[] ss = new String[objectArrayList.size()];
        Method[] methods = null;
        int index = -1;
        for(int i=0; i<ss.length; i++){
            Object o = objectArrayList.get(i);
            if (methods == null){
	            methods = o.getClass().getDeclaredMethods(); // 取类的所有方法
	            for(int j=0; j<methods.length; j++){
	                if (StringUtil.compareTo(methods[j].getName(), getMethodName) == true){
	                    index = j;
	                    break;
	                }
	            }
	            if (index == -1){
	                throw new Exception("getStringArray:提供的对象没有指定的方法！");
	            }
            }
            ss[i] = String.valueOf(methods[index].invoke(o, args));
        }
        return ss;
    }
    
	/**
	 * 将ArrayList转换成字符串数组
	 * @param stringArrayList ArrayList
	 * @return String[]
	*/
    @SuppressWarnings("unchecked")
    public static String[] getStringArray(List stringArrayList){
        if (stringArrayList == null) return null;
        String[] ss = new String[stringArrayList.size()];
        for(int i=0; i<stringArrayList.size(); i++){
            ss[i] = (String)stringArrayList.get(i);
        }
        return ss;
    }
    
	/**
	 * 对两个形如：abc,def的串进行处理，返回两个串中共有的item组成的串。比较时，大小写不敏感。 
	 * @param mainItems1 主串1
	 * @param mainItems2 主串2 
	 * @return 两个串中共有的item组成的串，有一个为空，则返回空串
	*/
    public static String getCoexistItemsIgnoreCase(String mainItems1, String mainItems2){
		if (StringUtil.isEmpty(mainItems1) || StringUtil.isEmpty(mainItems2)) return ""; // 有一个为空，则返回空串
		String[] mains1 = mainItems1.split(",");
		String[] mains2 = mainItems2.split(",");
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<mains1.length; i++){
			boolean exist = false;
			for(int j=0; j<mains2.length; j++){
				if (StringUtil.compareToIgnoreCase(mains1[i], mains2[j]) == true){
					exist = true;
					break;
				}
			}
			if (exist == true) sb.append(mains1[i]+",");
		}
		String resultStr = sb.toString();
		if (resultStr.length() == 0) return resultStr;
		return resultStr.substring(0, resultStr.length() -1);
	}
  	
	/**
	 * 对两个形如：abc,def的串进行处理，返回两个串中共有的item组成的串。比较时，大小写敏感。 
	 * @param mainItems1 主串1
	 * @param mainItems2 主串2 
	 * @return 两个串中共有的item组成的串，有一个为空，则返回空串
	*/
    public static String getCoexistItems(String mainItems1, String mainItems2){
		if (StringUtil.isEmpty(mainItems1) || StringUtil.isEmpty(mainItems2)) return ""; // 有一个为空，则返回空串
		String[] mains1 = mainItems1.split(",");
		String[] mains2 = mainItems2.split(",");
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<mains1.length; i++){
			boolean exist = false;
			for(int j=0; j<mains2.length; j++){
				if (StringUtil.compareTo(mains1[i], mains2[j]) == true){
					exist = true;
					break;
				}
			}
			if (exist == true) sb.append(mains1[i]+",");
		}
		String resultStr = sb.toString();
		if (resultStr.length() == 0) return resultStr;
		return resultStr.substring(0, resultStr.length() -1);
	}
    
 	 /**
 	  * 对两个形如：abc,def的串进行处理，从第一个中清除第二个含的字串，返回剩下的串。比较时，大小写敏感。 
 	  * @param mainItems 主串
 	  * @param delItems 需清除的串
 	  * @return 清除后的串
 	 */
     public static String getRemainsItems(String mainItems, String delItems){
 		if (StringUtil.isEmpty(mainItems) || StringUtil.isEmpty(delItems)) return mainItems;
 		String[] mains = mainItems.split(",");
 		String[] dels = delItems.split(",");
 		StringBuffer sb = new StringBuffer();
 		for(int i=0; i<mains.length; i++){
 			boolean exist = false;
 			for(int j=0; j<dels.length; j++){
 				if (StringUtil.compareTo(mains[i], dels[j]) == true){
 					exist = true;
 					break;
 				}
 			}
 			if (exist == false) sb.append(mains[i]+",");
 		}
 		String resultStr = sb.toString();
 		if (resultStr.length() == 0) return resultStr;
 		return resultStr.substring(0, resultStr.length() -1);
 	}
     
 	/**
 	 * 对两个形如：abc,def的串进行处理，从第一个中清除第二个含的字串，返回剩下的串。比较时，大小写不敏感。 
 	 * @param mainItems 主串
 	 * @param delItems 需清除的串
 	 * @return 清除后的串
 	*/
 	public static String getRemainsItemsIgnoreCase(String mainItems, String delItems){
 		if (StringUtil.isEmpty(mainItems) || StringUtil.isEmpty(delItems)) return mainItems;
 		String[] mains = mainItems.split(",");
 		String[] dels = delItems.split(",");
 		StringBuffer sb = new StringBuffer();
 		for(int i=0; i<mains.length; i++){
 			boolean exist = false;
 			for(int j=0; j<dels.length; j++){
 				if (StringUtil.compareToIgnoreCase(mains[i], dels[j]) == true){
 					exist = true;
 					break;
 				}
 			}
 			if (exist == false) sb.append(mains[i]+",");
 		}
 		String resultStr = sb.toString();
 		if (resultStr.length() == 0) return resultStr;
 		return resultStr.substring(0, resultStr.length() -1);
 	}

    /**
     * 取给定字符串在字符串数组中的位置。也可以认为是在指定的字符串数组中查找指定的字符串。比较时，大小写敏感。 
     * @param array 字符串数组
     * @param value 字符串
     * @return 位置。未找到，则返回-1
    */
    public static int getIndexInArray(String[] array, String value){
    	if (array != null && value != null){
    		for(int i=0; i<array.length; i++){
    			if (StringUtil.compareTo(array[i], value)) return i;
    		}
    	}
    	return -1;
    }
    
    /**
     * 取给定字符串在字符串数组中的位置。也可以认为是在指定的字符串数组中查找指定的字符串。比较时，大小写不敏感。 
     * @param array 字符串数组
     * @param value 字符串
     * @return 位置。未找到，则返回-1
    */
    public static int getIndexInArrayIgnoreCase(String[] array, String value){
    	if (array != null && value != null){
    		for(int i=0; i<array.length; i++){
    			if (StringUtil.compareToIgnoreCase(array[i], value)) return i;
    		}
    	}
    	return -1;
    }
    
    /**
     * 取URL最后面一个“/”符号前面的字符串。
     * @param url String
     * @return String
     */
    public static String getUrlParent(String url) {
        if (StringUtil.isEmpty(url)) return null;
        return StringUtil.getSubStringByDelPartFromTail(url, '/');
    }
    
    /**
     * 截取字符串s中从Index开始的字符串中，不等于exceptChar字符的第一个字符。
     * @param s String 源字符串
     * @param index int 起始位置
     * @param exceptChar char 不包含的字符
     * @return String
     */
    public static char getNextChar(String s, int index, char exceptChar) {
        for (int i = index; i < s.length(); i++) {
            if (s.charAt(i) != exceptChar) return s.charAt(i);
        }
        return 0;
    }

    /**
     * 从index开始取以c结束的字符串
     * @param s String 主串
     * @param index int 开始位置
     * @param c char 分隔符
     * @return String
     */
    public static String getSubString(String s, int index, char c) {
        if (StringUtil.isEmpty(s)) return null;
        if (index >= s.length()) return null;
        int i = s.indexOf(c, index);
        if (i < 0) return null;
        return s.substring(index, i);
    }
    
    /**
     * 从index开始取以字符串中字符结束的字符串
     * @param s String 主串
     * @param index int 开始位置
     * @param c String 分隔字符串
     * @return String
     */
    public static String getSubString(String s, int index, String p) {
        if (StringUtil.isEmpty(s)) return null;
        if (index >= s.length()) return null;
        int i = s.indexOf(p, index);
        if (i < 0) return null;
        return s.substring(index, i);
    }

    /**
     * 与getSubString一致，只是，当找不到c时，返回index后的所有内容
     * @param s String
     * @param index int
     * @param c char
     * @return String
     */
    public static String getSubStringNoStrict(String s, int index, char c) {
        if (StringUtil.isEmpty(s))
            return null;
        if (index >= s.length())
            return null;
        int i = s.indexOf(c, index);
        if (i < 0)
            return s.substring(index);
        return s.substring(index, i);
    }

    /**
     * 与getSubString一致，只是，当找不到c时，返回index后的所有内容
     * @param s String
     * @param index int
     * @param p String
     * @return String
     */
    public static String getSubStringNoStrict(String s, int index, String p) {
        if (StringUtil.isEmpty(s)) return null;
        if (index >= s.length()) return null;
        int i = s.indexOf(p, index);
        if (i < 0) return s.substring(index);
        return s.substring(index, i);
    }

    public static String getSubStringBasicExceptChars(String s, int index, String exceptChars) {
        if (StringUtil.isEmpty(s)) return null;
        if (index < 0 || index >= s.length()) return null;
        int i = indexOf(s, index, exceptChars);
        if (i < index) return null;
        return s.substring(index, i);
    }

    public static String getSubStringBasicExceptCharsNoStrict(String s, int index, String exceptChars) {
        if (StringUtil.isEmpty(s)) return null;
        if (index < 0 || index >= s.length()) return null;
        int i = indexOf(s, index, exceptChars);
        if (i < index) return s.substring(index);
        return s.substring(index, i);
    }

    /**
     * 补充String类的不足。大小写不敏感的比较。
     * @param s String 被比较串
     * @param prefix String 开始字符串
     * @param startIndex int 开始位置
     * @return boolean 是否以prefix开始。参数不合法，则返回false
     */
    public static boolean startWithIgnoreCase(String s, String prefix, int startIndex) {
        if (s == null || prefix == null) return false;
        if (startIndex < 0) return false;
        if (s.length() - startIndex < prefix.length()) return false;
        String t = s.substring(startIndex, startIndex + prefix.length());
        return t.compareToIgnoreCase(prefix) == 0;
    }

    /**
     * 补充String类的不足。大小写不敏感的比较。
     * @param s String 被比较串
     * @param prefix String 开始字符串
     * @return boolean 是否以prefix开始。参数不合法，则返回false
     */
    public static boolean startWithIgnoreCase(String s, String prefix) {
        return startWithIgnoreCase(s, prefix, 0);
    }

    /**
     * 补充String类的不足。大小写不敏感的比较。
     * @param s String 被比较串
     * @param suffix String 结束字符串
     * @param endIndex int 结束位置，不包含此位置的字符
     * @return boolean 是否以prefix结束。参数不合法，则返回false
     */
    public static boolean endWithIgnoreCase(String s, String suffix, int endIndex) {
        if (s == null || suffix == null) return false;
        if (endIndex > s.length() || endIndex < suffix.length()) return false;
        String t = s.substring(endIndex - suffix.length(), endIndex);
        return t.compareToIgnoreCase(suffix) == 0;
    }

    /**
     * 补充String类的不足。大小写不敏感的比较。
     * @param s String 被比较串
     * @param suffix String 结束字符串
     * @return boolean 是否以prefix结束。参数不合法，则返回false
     */
    public static boolean endWithIgnoreCase(String s, String suffix) {
        if (s == null) return false;
        return endWithIgnoreCase(s, suffix, s.length());
    }
    
    /**
     * 将数据库表名称转换成符合<code>JAVA</code>命名规范的类名称<br>
     * 如:<br>
     *        <i>数据库表名</i>  <code>SM_DICTIONARY</code><br>
     * 转换成<br>
     *        <i><code>JAVA</code>类名称</i>    <code>SmDictionary</code><br>
     * @param tableName String 数据库名称
     * @return String
     */
    public static String tableNameToJavaClassName(String tableName){
      if(tableName==null)return null;
      tableName = tableName.toLowerCase();
      String[] tableNameStrs = StringUtil.split(tableName,"_");
      String str="";
      for(int i=0;i<tableNameStrs.length;i++){
        str+=firstToUpperCase(tableNameStrs[i]);
      }
      return str;
    }
    
    /**
     * 将数据库表字段名称转换成符合<code>JAVA</code>命名规范的方法名称<br>
     * 如:<br>
     *        <i>数据库表字段名</i>  <code>ITEM_TEXT</code><br>
     * 转换成<br>
     *        <i><code>JAVA</code>方法名称</i>    <code>getItemText</code> <code>setItemText</code><br>
     * @param columnName String 数据库表字段名称
     * @return String
     */
    public static String columnNameToJavaMethodName(String columnName){
      if(columnName==null)return null;
      columnName = columnName.toLowerCase();
      String[] tableNameStrs = StringUtil.split(columnName,"_");
      String str="";
      for(int i=0;i<tableNameStrs.length;i++){
        str+=firstToUpperCase(tableNameStrs[i]);
      }
      return str;
    }

    /**
     * 将数据库表字段名称转换成符合<code>JAVA</code>命名规范的属性名称<br>
     * 如:<br>
     *        <i>数据库表字段名</i>  <code>ITEM_TEXT</code><br>
     * 转换成<br>
     *        <i><code>JAVA</code>属性名称</i>    <code>itemText</code><br>
     * @param columnName String 数据库表字段名称
     * @return String
     */
    public static String columnNameToJavaFieldName(String columnName){
      if(columnName==null)return null;
      columnName=columnName.toLowerCase();
      String[] tableNameStrs = StringUtil.split(columnName,"_");
      String str=tableNameStrs[0];
      for(int i=1;i<tableNameStrs.length;i++){
        str+=firstToUpperCase(tableNameStrs[i]);
      }
      return str;
    }
    
    /**
     * 将字符串的首字符大写
     * @param str 字符串
     * @return 修改后的字符串
     */
    public static String firstToUpperCase(String str) {
      String first = str.substring(0,1).toUpperCase();
      String body = str.substring(1,str.length());
      String newStr = first + body;
      return newStr;
    }


    /**
     * 将字符串中的变量使用values数组中的内容进行替换。
     * 替换的过程是不进行嵌套的，即如果替换的内容中包含变量表达式时不会替换。
     * @param prefix 变量前缀字符串
     * @param source 带参数的原字符串
     * @param values 替换用的字符串数组
     * @return 替换后的字符串。
     *         如果前缀为null则使用“%”作为前缀；
     *         如果source或者values为null或者values的长度为0则返回source；
     *         如果values的长度大于参数的个数，多余的值将被忽略；
     *         如果values的长度小于参数的个数，则后面的所有参数都使用最后一个值进行替换。
     * @since  0.2
     */
    public static String getReplaceString(String prefix, String source,
                                          String[] values) {
      String result = source;
      if (source == null || values == null || values.length < 1) {
        return source;
      }
      if (prefix == null) {
        prefix = "%";
      }

      for (int i = 0; i < values.length; i++) {
        String argument = prefix + Integer.toString(i + 1);
        int index = result.indexOf(argument);
        if (index != -1) {
          String temp = result.substring(0, index);
          if (i < values.length) {
            temp += values[i];
          }
          else {
            temp += values[values.length - 1];
          }
          temp += result.substring(index + 2);
          result = temp;
        }
      }
      return result;
    }

    /**
     * 将字符串中的变量（以“%”为前导后接数字）使用values数组中的内容进行替换。
     * 替换的过程是不进行嵌套的，即如果替换的内容中包含变量表达式时不会替换。
     * @param source 带参数的原字符串
     * @param values 替换用的字符串数组
     * @return 替换后的字符串
     * @since  0.1
     */
    public static String getReplaceString(String source, String[] values) {
    	return getReplaceString("%", source, values);
    }
    
	/**
	 * 对含16进制编码的字符串进行解码。16进制编码总是以“%”开始。主要是对中文以及不可见字符进行编码。
	 * @param s 16进制编码后的字符串，如：%E6%88%91%E6%98%AF%E4%B8%AD%E5%9B%BD%E4%BA%BA
	 * @return 解码后的字符串。
	 * @throws Exception
	*/
	public static String decodeHexString(String text) throws Exception{
		if(!text.startsWith("%"))
		{
			return text;
		}
		String[] hexs = text.split("%");
		byte[] btyes = new byte[hexs.length - 1];
		int i = 0;
		for(String hex : hexs)
		{
			if(!hex.equalsIgnoreCase(""))
			{
				btyes[i] = (byte) (0xff & Integer.parseInt(hex, 16));
				i++;
			}
		}	
		try {
			text = new String(btyes, "gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return text;
	}
	
	/**
	 * 对给定的byte[]进行16进制编码，每个byte都是以'%'开头
	 * @param bts byte[]
	 * @return 形如：%10%0F%23 的字符串。
	*/
    public static String encodeToHexString(byte[] bts){
	    StringBuffer hexString = new StringBuffer();
	    for(int i = 0 ; i < bts.length ; i++)
		{
			hexString.append("%");
			hexString.append(String.format("%X", bts[i]));
		}
	    return hexString.toString();
    }

	/**
	 * 对给定的byte[]进行16进制编码，每个byte都是以'%'开头
	 * @param b byte[]
	 * @return 形如：%10%0F%23 的字符串。
	 * @throws Exception 
	 */
    public static String encodeToHexString(String s) throws Exception{
        return encodeToHexString(s.getBytes("gbk"));
    }

   

     /**
      * 去掉连续的指定字符
      * @param s String 给定的字符串
      * @param exceptChar char 不能连续的字符
      * @return String 处理后的字符串
      */
     public static String trimSeries(String s, char exceptChar) {
         if (s == null) return "";
         StringBuffer sb = new StringBuffer();
         char t = 0, cc = 0;
         for (int i = 0; i < s.length(); i++) {
             cc = s.charAt(i);
             if (cc != exceptChar || cc != t) {
                 sb.append(cc);
                 t = cc;
             }
         }
         return sb.toString();
     }

     /**
      * 去掉连续的指定字符串中包含的字符。（处理对象为字符串P中的字符，而不是整个字符串）
      * @param s String 给定的字符串
      * @param exceptChars String 包含不能连续字符的字符串
      * @return String 处理后的字符串
      */
     public static String trimSeries(String s, String exceptChars) {
         if (s == null || exceptChars == null) return "";
         StringBuffer sb = new StringBuffer();
         char t = 0, cc = 0;
         for (int i = 0; i < s.length(); i++) {
             cc = s.charAt(i); 
             if (exceptChars.indexOf(cc) == -1 || cc != t) {
                 sb.append(cc);
                 t = cc;
             }
         }
         return sb.toString();
     }

     /**
      * 去掉连续的空格
      * @param s String 给定的字符串
      * @return String 处理后的字符串
      */
     public static String trimSeries(String s) {
         return trimSeries(s, ' ');
     }

     /**
      * 从指定位置起，跳过指定的连续字符。
      * @param s String 处理对象，字符串
      * @param index int 开始位置
      * @param exceptChar char 需要跳过的字符
      * @return int 跳过字符后的位置，即从index开始，第一个不是c的字符的位置。如果未找到，则指定s的尾。
      */
     public static int jumpChar(String s, int index, char exceptChar) {
         int i = 0;
         for (i = index; i < s.length(); i++) {
             if (s.charAt(i) != exceptChar) break;
         }
         return i;
     }

     /**
      * 从指定位置起，跳过字符串中包含的所有字符。
      * @param s String 处理对象，字符串
      * @param index int 开始位置
      * @param exceptChars String 包含需要跳过字符的字符串
      * @return int 跳过字符后的位置，即从index开始，第一个不是c的字符的位置。如果未找到，则指定s的尾。
      */
     public static int jumpChar(String s, int index, String exceptChars) {
         int i = 0;
         for (i = index; i < s.length(); i++) {
             if (exceptChars.indexOf(s.charAt(i)) == -1) break;
         }
         return i;
     }

     
     /*************以下来自于原来的StringHelper*****************/
     
     /**
      * 将数组的内容变为由指定字符分隔的字符串
      * @param values Object[] 给定的值
      * @param listSeparator 分隔符
      * @return 形如：xxx,yyy的字符串
      */
     public static String getStringFromArray(Object[] values, String listSeparator){
         StringBuffer sb = new StringBuffer();
         if (values == null) return null;
         for(int i=0; i<values.length; i++){
             sb.append(values[i]);
             if (i < values.length -1) sb.append(listSeparator);
         }
         return sb.toString();
     }
     
     /**
      * 将数组的内容变为由“,”分隔的字符串
      * @param values Object[] 给定的值
      * @return 形如：xxx,yyy的字符串
      */
     public static String getStringFromArray(Object[] values){
         return getStringFromArray(values, ",");
     }
     
     /**
      * 将数组的内容变为由指定字符分隔的字符串
      * @param values long[] 给定的值
      * @return 形如：xxx,yyy的字符串
      */
     public static String getStringFromArray(long[] values, String listSeparator){
         StringBuffer sb = new StringBuffer();
         if (values == null) return null;
         for(int i=0; i<values.length; i++){
             sb.append(values[i]);
             if (i < values.length -1) sb.append(listSeparator);
         }
         return sb.toString();
     }
     
     /**
      * 将数组的内容变为由“,”分隔的字符串
      * @param values long[] 给定的值
      * @return 形如：xxx,yyy的字符串
      */
     public static String getStringFromArray(long[] values){
         return getStringFromArray(values, ",");
     }
     
     /**
      * 将数组的内容变为由指定字符分隔的字符串
      * @param values int[] 给定的值
      * @return 形如：xxx,yyy的字符串
      */
     public static String getStringFromArray(int[] values, String listSeparator){
         return getStringFromArray(values, ",");
     }
     
     /**
      * 将数组的内容变为由“,”分隔的字符串
      * @param values int[] 给定的值
      * @return 形如：xxx,yyy的字符串
      */
     public static String getStringFromArray(int[] values){
         return getStringFromArray(values, ",");
     }
     
     /**
      * 将数组的内容变为由指定字符分隔的字符串
      * @param values ArrayList 给定的值
      * @param listSeparator 分隔符
      * @return 形如：xxx,yyy的字符串
      */
     public static String getStringFromArrayList(ArrayList values, String listSeparator){
         return getStringFromList(values, listSeparator);
     }
     
     /**
      * 将数组的内容变为由指定字符分隔的字符串
      * @param values List 给定的值
      * @param listSeparator 分隔符
      * @return 形如：xxx,yyy的字符串
      */
     public static String getStringFromList(List values, String listSeparator){
         StringBuffer sb = new StringBuffer();
         if (values == null) return null;
         for(int i=0; i<values.size(); i++){
             sb.append(values.get(i));
             if (i < values.size() -1) sb.append(listSeparator);
         }
         return sb.toString();
     }
     
     /**
      * 将数组的内容变为由“,”分隔的字符串
      * @param values ArrayList 给定的值
      * @return 形如：xxx,yyy的字符串
      */
     public static String getStringFromArrayList(ArrayList values){
         return getStringFromArrayList(values, ",");
     }
     
     /**
      * 将数组的内容变为由“,”分隔的字符串
      * @param values List 给定的值
      * @return 形如：xxx,yyy的字符串
      */
     public static String getStringFromList(List values){
         return getStringFromList(values, ",");
     }
     
     /**
      * 从长整型数组转换为字符串数组
      * @param values long[] 字符串数组
      * @return String[] 长整型数组
      */
     public static String[] getStringArrayFromLongArray(long[] values){
         if (values == null) return null;
         String[] newValues = new String[values.length];
         for(int i=0; i<values.length; i++){
             newValues[i] = String.valueOf(values[i]);
         }
         return newValues;
     }
     
     /**
      * 从字符串数组转换为长整型数组
      * @param values String[] 字符串数组
      * @return long[] 长整型数组
      */
     public static long[] getLongArrayFromStringArray(String[] values){
         if (values == null) return null;
         long[] newValues = new long[values.length];
         for(int i=0; i<values.length; i++){
             newValues[i] = Long.parseLong(values[i]);
         }
         return newValues;
     }
     
     /**
      * 从字符串（以“,”分隔）转换为长整型数组
      * @param values String 字符串（以“,”分隔）
      * @return long[] 长整型数组
      */
     public static long[] getLongArrayFromStringArray(String values){
         if (values == null) return null;
         return getLongArrayFromStringArray(values.split(","));
     }
     
     /**
      * 从一个对象数据组中的抽取出指定名称函数返回的字符串值组成的字符串动态数组。
      * @param objectArrayList 对象动态数据
      * @param getMethodName 方法名（区分大小写）
      * @return 字符串动态数组
      * @throws IllegalAccessException 无效访问 
      * @throws InvocationTargetException 
      * @throws Exception
      */
     public static ArrayList getStringArrayList(List objectArrayList, String getMethodName) 
     						throws IllegalAccessException, InvocationTargetException, Exception{
         return getStringArrayList(objectArrayList, getMethodName, null);
     }
     
     /**
      * 从一个对象数据组中的抽取出指定名称函数返回的字符串值组成的字符串动态数组。
      * @param objectArrayList 对象动态数据
      * @param getMethodName 方法名（区分大小写）
      * @param args 方法对应的调用参数
      * @return 字符串动态数组
      * @throws IllegalAccessException 无效访问 
      * @throws InvocationTargetException 
      * @throws Exception
      */
     public static ArrayList getStringArrayList(List objectArrayList, String getMethodName, Object[] args) 
     						throws IllegalAccessException, InvocationTargetException, Exception{
         if (objectArrayList == null) return null;
         ArrayList ss = new ArrayList();
         Method[] methods = null;
         int index = -1;
         for(int i=0; i<objectArrayList.size(); i++){
             Object o = objectArrayList.get(i);
             if (methods == null){
 	            methods = o.getClass().getDeclaredMethods(); // 取类的所有方法
 	            for(int j=0; j<methods.length; j++){
 	                if (StringUtil.compareTo(methods[j].getName(), getMethodName) == true){
 	                    index = j;
 	                    break;
 	                }
 	            }
 	            if (index == -1){
 	                throw new Exception("getStringArray:提供的对象没有指定的方法！");
 	            }
             }
             ss.add(String.valueOf(methods[index].invoke(o, args)));
         }
         return ss;
     }
     
     /**
      * 从一个对象数据组中的抽取出指定名称函数返回的字符串值组成的对象动态数组。
      * @param objectArrayList 对象动态数据
      * @param getMethodName 方法名（区分大小写）
      * @return 字符串动态数组
      * @throws IllegalAccessException 无效访问 
      * @throws InvocationTargetException 
      * @throws Exception
      */
     public static ArrayList getObjectArrayList(List objectArrayList, String getMethodName) 
     						throws IllegalAccessException, InvocationTargetException, Exception{
         return getObjectArrayList(objectArrayList, getMethodName, null);
     }
     
     /**
      * 从一个对象数据组中的抽取出指定名称函数返回的字符串值组成的对象动态数组。
      * @param objectArrayList 对象动态数据
      * @param getMethodName 方法名（区分大小写）
      * @param args 方法对应的调用参数
      * @return 字符串动态数组
      * @throws IllegalAccessException 无效访问 
      * @throws InvocationTargetException 
      * @throws Exception
      */
     public static ArrayList getObjectArrayList(List objectArrayList, String getMethodName, Object[] args) 
     						throws IllegalAccessException, InvocationTargetException, Exception{
         if (objectArrayList == null) return null;
         ArrayList ss = new ArrayList();
         Method[] methods = null;
         int index = -1;
         for(int i=0; i<objectArrayList.size(); i++){
             Object o = objectArrayList.get(i);
             if (methods == null){
 	            methods = o.getClass().getDeclaredMethods(); // 取类的所有方法
 	            for(int j=0; j<methods.length; j++){
 	                if (StringUtil.compareTo(methods[j].getName(), getMethodName) == true){
 	                    index = j;
 	                    break;
 	                }
 	            }
 	            if (index == -1){
 	                throw new Exception("getStringArray:提供的对象没有指定的方法！");
 	            }
             }
             ss.add(methods[index].invoke(o, args));
         }
         return ss;
     }
 	
     /**
      * 返回字符串中出现指定子字符串的数量
      * @param str 字符串
      * @param subStr 字符
      * @return 出现次数
      */
 	public static int getCountOfExist(String str, String subStr) {
         if (StringUtil.isEmpty(str) || StringUtil.isEmpty(subStr)){
             return 0;
         }
         int count = 0, i = 0;
         while((i = str.indexOf(subStr, i)) >= 0){
         	count ++;
         	i += subStr.length();
         }
         return count;
     }
     
     /**
      * 取给定字符串在字符串数组中的位置。也可以认为是在指定的字符串数组中查找指定的字符串。比较时，大小写敏感。 
      * @param array 字符串数组
      * @param value 字符串
      * @return 位置。未找到，则返回-1
      */
     public static int getIndexInArrayList(List array, String value){
     	if (array != null && value != null){
     		for(int i=0; i<array.size(); i++){
     			if (StringUtil.compareTo(String.valueOf(array.get(i)), value)) return i;
     		}
     	}
     	return -1;
     }
     
     /**
      * 取给定字符串在字符串数组中的位置。也可以认为是在指定的字符串数组中查找指定的字符串。比较时，大小写不敏感。 
      * @param array 字符串数组
      * @param value 字符串
      * @return 位置。未找到，则返回-1
      */
     public static int getIndexInArrayListIgnoreCase(List array, String value){
     	if (array != null && value != null){
     		for(int i=0; i<array.size(); i++){
     			if (StringUtil.compareToIgnoreCase(String.valueOf(array.get(i)), value)) return i;
     		}
     	}
     	return -1;
     }
 	
 	/**
 	 * 返回指定串中的各段字符串，这些字符串都是由containChars中的字符组成的。
 	 * @param str 需要处理的字符串
 	 * @param containChars 组成有效子字符串的字符
 	 * @param startIndex 开始位置
 	 * @param endIndex 结束位置
 	 * @return
 	 */
 	public static ArrayList getSegments(String str, String containChars, int startIndex, int endIndex){
 		ArrayList result = new ArrayList();
 		if (StringUtil.isEmpty(str) == false && StringUtil.isEmpty(containChars) == false){
 			StringBuffer sb = new StringBuffer();
 			for(int i=startIndex; i<str.length() && i<endIndex; i++){
 				char ch = str.charAt(i);
 				if (containChars.indexOf(ch) >= 0){
 					sb.append(ch);
 				}else{
 					if (sb.length() > 0){
 						result.add(sb.toString());
 						sb.delete(0, sb.length());
 					}
 				}
 			}
 			if (sb.length() > 0){
 				result.add(sb.toString());
 			}
 		}
 		return result;
 	}
 	
 	/**
 	 * 返回指定串中的各段字符串，这些字符串都是由containChars中的字符组成的。
 	 * @param str 需要处理的字符串
 	 * @param containChars 组成有效子字符串的字符
 	 * @return
 	 */
 	public static ArrayList getSegments(String str, String containChars){
 		return getSegments(str, containChars, 0, str.length());
 	}
 	
 	/**
 	 * 根据指定的分隔符，分解指定字符串。返回分解后的字符串数组。解出的空值（两分隔符间无内容）不算。同String类型的split函数。
 	 * @param str 字符串
 	 * @param separator 分隔符
 	 * @return ArrayList
 	 */
 	public static ArrayList splitToArrayList(String str, String separator) {
 		ArrayList results = new ArrayList();
 		if (str != null) {
 			StringTokenizer st = new StringTokenizer(str, separator);
 			while (st.hasMoreTokens()) {
 				results.add(st.nextToken());
 			}
 		}
 		return results;
 	}
 	
 	/**
 	 * 根据“,”分隔符，分解指定字符串。返回分解后的字符串数组。解出的空值（两分隔符间无内容）不算。同String类型的split函数。
 	 * @param str 字符串
 	 * @return ArrayList
 	 */
 	public static ArrayList splitToArrayList(String str){
 		return splitToArrayList(str, ",");
 	}
 	
 	/**
 	 * 根据指定的分隔符，分解指定字符串。返回分解后的字符串数组。解出的空值（两分隔符间无内容）仍会保留。
 	 * @param str 字符串
 	 * @param separator 分隔符
 	 * @return ArrayList
 	 */
 	public static ArrayList splitAllToArrayList(String str, String separator) {
 		ArrayList result = new ArrayList();
 		if (str != null){
 			int separatorLen = separator.length();
 			while (true) {
 				int index = str.indexOf(separator);
 				if (index != -1) {
 					if (index == 0) {
 						result.add("");
 					} else if (index == str.length()) {
 						result.add(str.substring(0, index));
 						result.add("");
 						break;
 					} else {
 						result.add(str.substring(0, index));
 					}
 					str = str.substring(index + separatorLen);
 				} else {
 					result.add(str);
 					break;
 				}
 			}
 		}
 		return result;
 	}
 	
 	/**
 	 * 根据“,”分隔符，分解指定字符串。返回分解后的字符串数组。解出的空值（两分隔符间无内容）仍会保留。
 	 * @param str 字符串
 	 * @return ArrayList
 	 */
 	public static ArrayList splitAllToArrayList(String str) {
 		return splitAllToArrayList(str, ",");
 	}
 	
 	/**
 	 * 根据指定的分隔符，分解指定字符串。返回分解后的字符串数组。解出的空值（两分隔符间无内容）仍会保留。
 	 * @param str 字符串
 	 * @param separator 分隔符
 	 * @return
 	 */
 	public static String[] splitAll(String str, String separator) {
 		return (String[])splitAllToArrayList(str, separator).toArray(new String[0]);
 	}
 	
 	/**
 	 * 根据“,”分隔符，分解指定字符串。返回分解后的字符串数组。解出的空值（两分隔符间无内容）仍会保留。
 	 * @param str 字符串
 	 * @return
 	 */
 	public static String[] splitAll(String str) {
 		return splitAll(str, ",");
 	}
 	
 	/**
 	 * 清除给定数组中的重复数据，并初始化本类的公共变量。
 	 * @param values
 	 */
 	public static ArrayList getNoRepeatValues(List values){
 		ArrayList newValues = new ArrayList();
 		if (values != null){
 			for(int i=0; i<values.size(); i++){
 				String value = (String)values.get(i);
 				if (StringUtil.isEmpty(value) == true) continue;
 				// 查找新数组中是否已存在当前值。
 				boolean isExist = false;
 				for(int j=0; j<newValues.size(); j++){
 					String newValue = (String)newValues.get(i);
 					if (StringUtil.compareTo(value, newValue)){
 						isExist = true;
 						break;
 					}
 				}
 				if (isExist == false){
 					newValues.add(value);
 				}
 			}
 		}
 		return newValues;
 	}
 	
 	/**
 	 * 清除给定数组中的重复数据，并初始化本类的公共变量。
 	 * @param values
 	 */
 	public static ArrayList getNoRepeatValues(String[] values){
 		ArrayList newValues = new ArrayList();
 		if (values != null){
 			for(int i=0; i<values.length; i++){
 				String value = values[i];
 				if (StringUtil.isEmpty(value) == true) continue;
 				// 查找新数组中是否已存在当前值。
 				boolean isExist = false;
 				for(int j=0; j<newValues.size(); j++){
 					String newValue = (String)newValues.get(i);
 					if (StringUtil.compareTo(value, newValue)){
 						isExist = true;
 						break;
 					}
 				}
 				if (isExist == false){
 					newValues.add(value);
 				}
 			}
 		}
 		return newValues;
 	}
 	
 	/**
 	 * 将BEAN对象中的GET方法输出的值连接为一个字符串，并返回。
 	 * @param bean BEAN对象
 	 * @return BEAN对象中的GET方法输出的值连接为一个字符串
 	 * @throws Exception
 	 */
 	public static String getStringFromBean(Object bean) throws Exception{
         StringBuffer sb = new StringBuffer();
 		// 获取当前类的所有方法。
         Class[] paramTypes;
         Object[] params = new Object[] {};
         //Field[] fields = bean.getClass().getDeclaredFields(); // 取BEAN类的所有属性
         Method[] methods = bean.getClass().getMethods(); // 获取所有方法（包括继承的）liufq 20060828 .getDeclaredMethods(); // 取BEAN类的所有方法
         // 判断getXXX()方法，将其值转换为字符串，并进行叠加。
         for (int i = 0; i < methods.length; i++) {
         	String methodName = methods[i].getName(); // 取方法名
             if (methodName.length() <= 3) continue; // 判断方法名长度小于3的不算。
             if (methodName.startsWith("get") == false) continue; // 仅处理get方法。get方法，表明可以取值
             paramTypes = methods[i].getParameterTypes(); // 取此方法的所有参数
             if (paramTypes.length != 0) continue; // 仅处理无参数的GET方法
             if (StringUtil.compareTo(methods[i].getDeclaringClass().getName(), "java.lang.Object")) continue; // 不处理Object对象的方法
             Object o = methods[i].invoke(bean, params);
             // 判断是否为null。
             sb.append(StringUtil.getString(o));
             //sb.append('|');
         }
 		//if (sb.length() > 0) sb.deleteCharAt(sb.length() -1);
 		return sb.toString();
 	}
 	
 	/** 
 	 * 二行制转字符串 
 	 * @param b 
 	 * @return 
 	 */
 	public static String byte2hex(byte[] b) {
 		String hs = "";
 		String stmp = "";
 		for (int n = 0; n < b.length; n++) {
 			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
 			if (stmp.length() == 1)
 				hs = hs + "0" + stmp;
 			else
 				hs = hs + stmp;
 		}
 		return hs.toUpperCase();
 	}
 	
 	/** 
 	 * 二行制转字符串 
 	 * @param b 
 	 * @return 
 	 */
 	public static String byte2hex(byte b) {
 		String hs = "";
 		String stmp = "";
 		stmp = (java.lang.Integer.toHexString(b & 0XFF));
 		if (stmp.length() == 1)
 			hs = hs + "0" + stmp;
 		else
 			hs = hs + stmp;
 		return hs.toUpperCase();
 	}

 	public static byte[] hex2byte(byte[] b) {
 		if ((b.length % 2) != 0) throw new IllegalArgumentException("长度不是偶数");
 		byte[] b2 = new byte[b.length / 2];
 		for (int n = 0; n < b.length; n += 2) {
 			String item = new String(b, n, 2);
 			b2[n / 2] = (byte) Integer.parseInt(item, 16);
 		}
 		return b2;
 	}
 	public static byte[] hex2byte(String b) {
 		if ((b.length() % 2) != 0) throw new IllegalArgumentException("长度不是偶数");
 		byte[] b2 = new byte[b.length() / 2];
 		for (int n = 0; n < b.length(); n += 2) {
 			String item = b.substring(n, n + 2);
 			b2[n / 2] = (byte) Integer.parseInt(item, 16);
 		}
 		return b2;
 	}
 	
 	public static byte shiftLeftCycle(byte b, int num){
 		return (byte)(((b & 0xff) >> (8 - num))|((b & 0xff) << num));
 	}
 	public static byte shiftRightCycle(byte b, int num){
 		return (byte)(((b & 0xff) << (8 - num))|((b & 0xff) >> num));
 	}
 	private static Collator collator = Collator.getInstance();	//取得本地语种,即Locale.getDefault()
 	/**
 	 * 进行两个字符串的比较，使用本地语种进行比较。如果某字符串为NULL，则为NULL的小；如果两个字符串都为NULL，则为相等。
 	 * @param str1 字符串
 	 * @param str2 字符串
 	 * @return 0：str1==str2；-1：str1<str2；1：str1>str2
 	 */
 	public static int compareToWithLocale(String str1, String str2){
 		if (str1 == null){
 			if (str2 == null){
 				return 0; 
 			}else{
 				return -1;
 			}
 		}else if (str2 == null){
 			return 1;
 		}
 		CollationKey key1 = collator.getCollationKey(str1);
 		CollationKey key2 = collator.getCollationKey(str2);
 		return key1.compareTo(key2);
 	}

 	/**
 	 * 将字符串数组转换成ArrayList
 	 * @param stringArray String[]
 	 * @return ArrayList
 	*/
     public static ArrayList getArrayList(Object[] objectArray){
         if (objectArray == null) return null;
         ArrayList al = new ArrayList();
         for (int i=0; i<objectArray.length; i++){
             al.add(objectArray[i]);
         }
         return al;
     }
     
     /**
      * 打印系统属性
      */
     public static void printSystemProperties() {
         Properties ps = System.getProperties();
         Object oo[] = ps.keySet().toArray();
         for (int i = 0; i < oo.length; i++) {
             Object o = ps.get(oo[i]);
             System.out.println(oo[i].toString() + "\n" + o.toString() + "\n");
         }
     }
}
