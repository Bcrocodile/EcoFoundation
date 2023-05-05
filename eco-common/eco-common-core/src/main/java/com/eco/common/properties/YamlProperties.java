package com.eco.common.properties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author bcro
 * @title: YamlProperties
 * @description: TODO
 * @date 2023/5/4 20:21
 */
public class YamlProperties {


    public  static ToolConfig getProperties(){
        try {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            // 忽略未知属性字段
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 不使用科学计数
            objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
            // null 值不输出(节省内存)
            objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
            InputStream inputStream = YamlProperties.class.getClassLoader().getResourceAsStream("bootstrap.yaml");
            if (inputStream == null) {
                inputStream = YamlProperties.class.getClassLoader().getResourceAsStream("application.yaml");
            }
            if (inputStream == null) {
                return null;
            }
            return objectMapper.readValue(inputStream, ToolConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
