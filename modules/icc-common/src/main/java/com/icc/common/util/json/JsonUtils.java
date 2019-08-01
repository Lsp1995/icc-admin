package com.icc.common.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.icc.common.throwable.JsonException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * JSON 工具类
 *
 * @author wq
 * @date 2016/05/21
 */
public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper
                .getSerializerProvider()
                .setNullValueSerializer(
                        new JsonSerializer() {
                            @Override
                            public void serialize(
                                    Object value, JsonGenerator generator, SerializerProvider provider)
                                    throws IOException {
                                generator.writeString("");
                            }
                        });
    }

    /**
     * 对象 转换成 json
     */
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new JsonException(e.getMessage());
        }
    }

    /**
     * json 转换成 对象
     */
    public static <T> T toAny(String json, TypeReference<?> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            throw new JsonException(e.getMessage());
        }
    }

    /**
     * json 转换成 对象
     */
    public static <T> T toAny(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new JsonException(e.getMessage());
        }
    }

    /**
     * json 转换成 map
     */
    public static <K, V> Map<K, V> toMap(String json) {
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (Exception e) {
            throw new JsonException(e.getMessage());
        }
    }
}
