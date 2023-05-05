package com.eco.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class JacksonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
            .setTimeZone(TimeZone.getTimeZone("GMT+8"))
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    /**
     * 將 JSON 字串轉換為 Java 物件
     *
     * @param json  JSON 字串
     * @param clazz Java 物件的 Class
     * @param <T>   Java 物件的類型
     * @return 轉換後的 Java 物件
     * @throws IOException 當轉換失敗時拋出異常
     */
    public static <T> T jsonToObject(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }

    /**
     * 將 JSON 字串轉換為 Java 物件
     *
     * @param json         JSON 字串
     * @param valueTypeRef Java 物件的 TypeReference
     * @param <T>          Java 物件的類型
     * @return 轉換後的 Java 物件
     * @throws IOException 當轉換失敗時拋出異常
     */
    public static <T> T jsonToObject(String json, TypeReference<T> valueTypeRef) throws IOException {
        return objectMapper.readValue(json, valueTypeRef);
    }

    /**
     * 將 Java 物件轉換為 JSON 字串
     *
     * @param object Java 物件
     * @return 轉換後的 JSON 字串
     * @throws JsonProcessingException 當轉換失敗時拋出異常
     */
    public static String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    /**
     * 將 Java 物件轉換為 JSON Node
     *
     * @param object Java 物件
     * @return 轉換後的 JSON Node
     */
    public static JsonNode toJsonNode(Object object) {
        return objectMapper.valueToTree(object);
    }

    /**
     * 將 Map 轉換為 JSON Node
     *
     * @param map Map
     * @return 轉換後的 JSON Node
     */
    public static ObjectNode toObjectNode(Map<String, ?> map) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        map.forEach((key, value) -> node.set(key, toJsonNode(value)));
        return node;
    }

}
