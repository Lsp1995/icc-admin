package com.icc.common.util.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapUtils extends org.apache.commons.collections.MapUtils {

  /** 创建一个map */
  public static <V, V1 extends V> Map<String, V> toMap(String key1, V1 value1) {
    return populateMap(new HashMap<String, V>(16), key1, value1);
  }

  /** 创建一个map */
  public static <V, V1 extends V, V2 extends V> Map<String, V> toMap(
      String key1, V1 value1, String key2, V2 value2) {
    return populateMap(new HashMap<String, V>(16), key1, value1, key2, value2);
  }

  /** 创建一个map */
  public static <V, V1 extends V, V2 extends V, V3 extends V> Map<String, V> toMap(
      String key1, V1 value1, String key2, V2 value2, String key3, V3 value3) {
    return populateMap(new HashMap<String, V>(16), key1, value1, key2, value2, key3, value3);
  }

  /** 创建一个map */
  public static <V, V1 extends V, V2 extends V, V3 extends V, V4 extends V> Map<String, V> toMap(
      String key1,
      V1 value1,
      String key2,
      V2 value2,
      String key3,
      V3 value3,
      String key4,
      V4 value4) {
    return populateMap(
        new HashMap<String, V>(16), key1, value1, key2, value2, key3, value3, key4, value4);
  }

  /** 创建一个map */
  public static <V, V1 extends V, V2 extends V, V3 extends V, V4 extends V, V5 extends V>
      Map<String, V> toMap(
          String key1,
          V1 value1,
          String key2,
          V2 value2,
          String key3,
          V3 value3,
          String key4,
          V4 value4,
          String key5,
          V5 value5) {
    return populateMap(
        new HashMap<String, V>(16),
        key1,
        value1,
        key2,
        value2,
        key3,
        value3,
        key4,
        value4,
        key5,
        value5);
  }

  /** 创建一个map 并 复制指定的 key 到 此map中 */
  public static <V, V1 extends V, V2 extends V> Map<String, V> copyMap(
      Map<String, V> from, String... keys) {
    Map<String, V> to = new HashMap<String, V>(16);
    for (String key : keys) {
      to.put(key, from.get(key));
    }
    return to;
  }

  /** 存入map */
  public static <V, V1 extends V> Map<String, V> put(
      Map<String, V> target, String key1, V1 value1) {
    return populateMap(target, key1, value1);
  }

  /** 批量存入map */
  public static <V, V1 extends V, V2 extends V> Map<String, V> put(
      Map<String, V> target, String key1, V1 value1, String key2, V2 value2) {
    return populateMap(target, key1, value1, key2, value2);
  }

  /** 批量存入map */
  public static <V, V1 extends V, V2 extends V> Map<String, V> put(
      Map<String, V> target,
      String key1,
      V1 value1,
      String key2,
      V2 value2,
      String key3,
      V2 value3) {
    return populateMap(target, key1, value1, key2, value2, key3, value3);
  }

  @SuppressWarnings("unchecked")
  private static <K, V> Map<String, V> populateMap(Map<String, V> map, Object... data) {
    for (int i = 0; i < data.length; ) {
      map.put((String) data[i++], (V) data[i++]);
    }
    return map;
  }

  /** 移除空key */
  public static <K, V> void removeEmptyKey(Map<String, V> map) {
    Iterator<Map.Entry<String, V>> iterator = map.entrySet().iterator();
    while (iterator.hasNext()) {
      String k = iterator.next().getKey();
      if (k == null || k.trim().length() == 0) {
        iterator.remove();
      }
    }
  }

  /** 移除空value */
  public static <K, V> void removeEmptyValue(Map<String, V> map) {
    Iterator<Map.Entry<String, V>> iterator = map.entrySet().iterator();
    while (iterator.hasNext()) {
      Object v = iterator.next().getValue();
      if (v == null || (v instanceof String && v.toString().trim().length() == 0)) {
        iterator.remove();
      }
    }
  }
}
