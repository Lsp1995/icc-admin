package com.icc.common.throwable;

/**
 * JSON 转换异常
 *
 * @author wq
 * @date 2016/05/21
 */
public class JsonException extends RuntimeException {

  private static final long serialVersionUID = -1385483628478961736L;

  public JsonException(String message) {
    super(message);
  }

  public JsonException(Throwable e) {
    super(e);
  }
}
