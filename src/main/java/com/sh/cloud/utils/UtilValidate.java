package com.sh.cloud.utils;

import java.util.Collection;
import java.util.Map;

public class UtilValidate {

    public static final String module = UtilValidate.class.getName();

    public static boolean areEqual(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        } else {
            return obj.equals(obj2);
        }
    }

    /**
     * Check whether an object is empty, will see if it is a String, Map, Collection, etc.
     */
    public static boolean isEmpty(Object o) {
        return ObjectType.isEmpty(o);
    }

    /**
     * Check whether an object is NOT empty, will see if it is a String, Map, Collection, etc.
     */
    public static boolean isNotEmpty(Object o) {
        return !ObjectType.isEmpty(o);
    }

    /**
     * Check whether string s is empty.
     */
    public static boolean isEmpty(String s) {
        return ((s == null) || (s.length() == 0));
    }

    /**
     * Check whether collection c is empty.
     */
    public static <E> boolean isEmpty(Collection<E> c) {
        return ((c == null) || (c.size() == 0));
    }

    /**
     * Check whether map m is empty.
     */
    public static <K, E> boolean isEmpty(Map<K, E> m) {
        return ((m == null) || (m.size() == 0));
    }

    /**
     * Check whether charsequence c is empty.
     */
    public static <E> boolean isEmpty(CharSequence c) {
        return ((c == null) || (c.length() == 0));
    }

    /**
     * Check whether string s is NOT empty.
     */
    public static boolean isNotEmpty(String s) {
        return ((s != null) && (s.length() > 0));
    }

    /**
     * Check whether collection c is NOT empty.
     */
    public static <E> boolean isNotEmpty(Collection<E> c) {
        return ((c != null) && (c.size() > 0));
    }

    /**
     * Check whether charsequence c is NOT empty.
     */
    public static <E> boolean isNotEmpty(CharSequence c) {
        return ((c != null) && (c.length() > 0));
    }

    public static boolean isString(Object obj) {
        return ((obj != null) && (obj instanceof String));
    }

    public static boolean isNotHaveChinese(String s) {
        return s.getBytes().length == s.length();
    }
}
