package com.icc.common.util;

import java.util.regex.Pattern;

public class ValidUtils {

  public static final Pattern INTEGER_PATTERN = Pattern.compile("^\\d+$");
  public static final Pattern NUMBER_PATTERN = Pattern.compile("^(\\-|\\+)?\\d+(\\.\\d+)?$");
  public static final Pattern EMAIL_PATTERN =
      Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
  public static final Pattern MOBILE_PATTERN =
      Pattern.compile(
          "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$");
  public static final Pattern CHINESE_PATTERN = Pattern.compile("([\\u4e00-\\u9fa5]{2,4})");
  public static final Pattern IDCARD15_PATTERN =
      Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
  public static final Pattern IDCARD18_PATTERN =
      Pattern.compile(
          "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[A-Z])$");
  public static final Pattern IP_PATTERN =
      Pattern.compile(
          "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
              + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
              + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
              + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$");

  /** 邮箱 */
  public static boolean isEmail(String email) {
    return email != null && EMAIL_PATTERN.matcher(email).matches();
  }

  /** 手机 */
  public static boolean isMobile(String mobile) {
    return mobile != null && MOBILE_PATTERN.matcher(mobile).matches();
  }

  /** 手机 */
  public static boolean isMobile(String... mobiles) {
    for (String mobile : mobiles) {
      if (!isMobile(mobile)) {
        return false;
      }
    }
    return true;
  }

  /** 中文 */
  public static boolean isChinese(String str) {
    return str != null && CHINESE_PATTERN.matcher(str).matches();
  }

  /** 身份证 */
  public static boolean isIdCard(String idCard) {
    return (idCard != null && IDCARD15_PATTERN.matcher(idCard).matches())
        || (idCard != null && IDCARD18_PATTERN.matcher(idCard).matches());
  }

  /** IP */
  public static boolean isIpAddr(String host) {
    return host != null && IP_PATTERN.matcher(host).matches();
  }

  /** IP */
  public static boolean isIpAddr(String... hosts) {
    for (String host : hosts) {
      if (!isIpAddr(host)) {
        return false;
      }
    }
    return true;
  }

  /** 数字 */
  public static boolean isNumber(String number) {
    return number != null && NUMBER_PATTERN.matcher(number).matches();
  }

  /** 整数 */
  public static boolean isInteger(String number) {
    return number != null && INTEGER_PATTERN.matcher(number).matches();
  }
}
