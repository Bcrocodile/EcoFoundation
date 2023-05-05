package com.eco.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.eco.common.properties.HttpConfig;
import com.eco.common.properties.ToolConfig;
import com.eco.common.properties.YamlProperties;
import com.eco.common.result.BizException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

@Slf4j
public class OkHttpToolsUtil {

    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType XML
            = MediaType.parse("application/xml; charset=utf-8");
    private static OkHttpClient client;


    private OkHttpToolsUtil(HttpConfig httpToolConfig) {
        client = new OkHttpClient.Builder()
                .connectTimeout(httpToolConfig.getConnectTimeSeconds(), TimeUnit.SECONDS)
                .readTimeout(httpToolConfig.getReadTimeSeconds(), TimeUnit.SECONDS)
                .writeTimeout(httpToolConfig.getWriteTimeSeconds(), TimeUnit.SECONDS)
                .build();
    }


    static {
        YamlProperties yamlProperties = new YamlProperties();
        ToolConfig httpToolConfig = yamlProperties.getProperties();
        HttpConfig httpConfig = new HttpConfig();
        if (httpToolConfig != null &&  httpToolConfig.getHttpConfig()!=null) {
            httpConfig = httpToolConfig.getHttpConfig();
        }
        new OkHttpToolsUtil(httpConfig);
    }


    public static String get(String url){
        try {
            Response response = getResponse(url,null);
            return response.body().string();
        }catch (IOException e){
            throw  new BizException("网络异常");
        }

    }

    public static String get(String url,Map<String,String> headerMap){
        try {
            Response response = getResponse(url,headerMap);
            return response.body().string();
        }catch (IOException e){
            throw  new BizException("网络异常");
        }

    }

    public static Response getResponse(String url,Map<String,String> header) throws IOException {
        Request.Builder builder = new Request.Builder();
        if(header != null){
            for(Map.Entry<String, String> entry : header.entrySet()){
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = builder
                .url(url)
                .build();
        return client.newCall(request).execute();
    }

    public static String postJson(String url, String json) {
        try {
            Response response = postJsonResponse(url, json);
            return response.body().string();
        }catch (IOException e){
            log.error(e.getMessage());
            throw  new BizException("网络异常");
        }


    }

    public static String postJson(String url, Map<String, String> header, String json){
        try {
            Response response = postJsonResponse(url, header, json);
            return response.body().string();
        }catch (IOException e){
            log.error(e.getMessage());
            throw  new BizException("网络异常");
        }
    }

    public static Response postJsonResponse(String url, String json) throws IOException {
        return postJsonResponse(url, null, json);
    }

    public static Response postJsonResponse(String url, Map<String, String> header, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(body);
        if(header != null){
            for(Map.Entry<String, String> entry : header.entrySet()){
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        Request request = builder.build();
        return client.newCall(request).execute();
    }

    public static String postXml(String url, String xml) throws IOException {
        Response response = postXmlResponse(url, xml);
        return response.body().string();
    }

    public static Response postXmlResponse(String url, String xml) throws IOException {
        RequestBody body = RequestBody.create(XML, xml);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return client.newCall(request).execute();
    }

    public static String post(String url, FormBody body) throws IOException {
        Response response = postResponse(url, body);
        return response.body().string();
    }

    public static Response postResponse(String url, FormBody body) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return client.newCall(request).execute();
    }

    public static String post(String url, Map<String, String> params) throws IOException{
        FormBody.Builder body = new FormBody.Builder();
        for (Map.Entry<String, String> param : params.entrySet()){
            body.add(param.getKey(), param.getValue());
        }

        return post(url, body.build());
    }

    /**
     * 发送文件
     * @param url
     * @param file 文件
     * @param paramName 接收文件的参数名称
     * @param fileName 文件名称
     * @return
     * @throws IOException
     */
    public static String postFile(String url, File file, String paramName, String fileName)throws IOException{
        Response response = postFileResponse(url, file, paramName, fileName);
        return response.body().string();
    }

    /**
     * 发送文件
     * @param url
     * @param file 文件
     * @param paramName 接收文件的参数名称
     * @param fileName 文件名称
     * @return
     * @throws IOException
     */
    public static String postFile(String url, byte[] file, String paramName, String fileName)throws IOException{
        Response response = postFileResponse(url, file, paramName, fileName);
        return response.body().string();
    }

    /**
     * 发送文件
     * @param url
     * @param file 文件
     * @param paramName 接收文件的参数名称
     * @param fileName 文件名称
     * @return
     * @throws IOException
     */
    public static Response postFileResponse(String url, File file, String paramName, String fileName)throws IOException{
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"),file);
        return postFileResponse(url, fileBody, paramName, fileName);
    }

    public static  Response postFileResponse(String url, byte[] file, String paramName, String fileName)throws IOException{
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"),file);
        return postFileResponse(url, fileBody, paramName, fileName);
    }

    private static  Response postFileResponse(String url, RequestBody fileBody, String paramName, String fileName)throws IOException{
        MultipartBody body = new MultipartBody.Builder()
                .setType(MediaType.parse("multipart/form-data"))
                .addFormDataPart(paramName,fileName,fileBody)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();

        return client.newCall(request).execute();
    }
}
