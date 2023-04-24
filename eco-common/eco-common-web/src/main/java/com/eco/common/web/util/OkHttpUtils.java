package com.eco.common.web.util;

import com.eco.common.utils.FileUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * http请求工具，基于OKHTTP3
 * @author czapi
 */
@Component
public class OkHttpUtils{
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    private Map<String, List<Cookie>> cookieStore = new HashMap<String, List<Cookie>>();

    private OkHttpClient httpClient;

    /**
     * 请求超时时间
     */
    @Value("${http.connectTimeoutSeconds:60}")
    private int connectTimeoutSeconds;
    /**
     * http读取超时时间
     */
    @Value("${http.readTimeoutSeconds:30}")
    private int readTimeoutSeconds;
    /**
     * http写超时时间
     */
    @Value("${http.writeTimeoutSeconds:30}")
    private int writeTimeoutSeconds;

    public OkHttpUtils() {
        this.initHttpClient();
    }

    protected void initHttpClient() {
        httpClient = new OkHttpClient.Builder()
                // 设置链接超时时间，默认10秒
                .connectTimeout(connectTimeoutSeconds, TimeUnit.SECONDS)
                .readTimeout(readTimeoutSeconds, TimeUnit.SECONDS)
                .writeTimeout(writeTimeoutSeconds, TimeUnit.SECONDS)
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                        cookieStore.put(httpUrl.host(), list);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                        List<Cookie> cookies = cookieStore.get(httpUrl.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                }).build();
    }

    /**
     * get请求
     *
     * @param url
     * @param header
     * @return
     * @throws IOException
     */
    public String get(String url, Map<String, String> header) throws IOException {
        Request.Builder builder = new Request.Builder().url(url).get();
        // 添加header
        addHeader(builder, header);

        Request request = builder.build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 提交表单
     *
     * @param url    url
     * @param form   参数
     * @param header header
     * @param method 请求方式，post，get等
     * @return
     * @throws IOException
     */
    public String request(String url, Map<String, ?> form, Map<String, String> header, HTTPMethod method) throws IOException {
        Request.Builder requestBuilder = buildRequestBuilder(url, form, method);
        // 添加header
        addHeader(requestBuilder, header);

        Request request = requestBuilder.build();
        Response response = httpClient
                .newCall(request)
                .execute();
        try {
            return response.body().string();
        } finally {
            response.close();
        }
    }

    /**
     * 请求json数据，contentType=application/json
     * @param url 请求路径
     * @param json json数据
     * @param header header
     * @return 返回响应结果
     * @throws IOException
     */
    public String requestJson(String url, String json, Map<String, String> header) throws IOException {
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(body);
        // 添加header
        addHeader(requestBuilder, header);

        Request request = requestBuilder.build();
        Response response = httpClient
                .newCall(request)
                .execute();
        try {
            return response.body().string();
        } finally {
            response.close();
        }
    }

    public static Request.Builder buildRequestBuilder(String url, Map<String, ?> form, HTTPMethod method) {
        switch (method) {
            case GET:
                return new Request.Builder()
                        .url(buildHttpUrl(url, form))
                        .get();
            case HEAD:
                return new Request.Builder()
                        .url(buildHttpUrl(url, form))
                        .head();
            case PUT:
                return new Request.Builder()
                        .url(url)
                        .put(buildFormBody(form));
            case DELETE:
                return new Request.Builder()
                        .url(url)
                        .delete(buildFormBody(form));
            default:
                return new Request.Builder()
                        .url(url)
                        .post(buildFormBody(form));
        }
    }

    public static HttpUrl buildHttpUrl(String url, Map<String, ?> form) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        for (Map.Entry<String, ?> entry : form.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return urlBuilder.build();
    }

    public static FormBody buildFormBody(Map<String, ?> form) {
        FormBody.Builder paramBuilder = new FormBody.Builder(StandardCharsets.UTF_8);
        for (Map.Entry<String, ?> entry : form.entrySet()) {
            paramBuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return paramBuilder.build();
    }

    private void addHeader(Request.Builder builder, Map<String, String> header) {
        if (header != null) {
            Set<Map.Entry<String, String>> entrySet = header.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                builder.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
    }

    /**
     * 提交表单，并且上传文件
     *
     * @param url
     * @param form
     * @param header
     * @param files
     * @return
     * @throws IOException
     */
    public String requestFile(String url, Map<String, ?> form, Map<String, String> header, List<UploadFile> files)
            throws IOException {
        // 创建MultipartBody.Builder，用于添加请求的数据
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM);

        for (UploadFile uploadFile : files) {
            // 请求的名字
            bodyBuilder.addFormDataPart(uploadFile.getName(),
                    // 文件的文字，服务器端用来解析的
                    uploadFile.getFileName(),
                    // 创建RequestBody，把上传的文件放入
                    RequestBody.create(null, uploadFile.getFileData())
            );
        }

        for (Map.Entry<String, ?> entry : form.entrySet()) {
            bodyBuilder.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
        }

        RequestBody requestBody = bodyBuilder.build();

        Request.Builder builder = new Request.Builder().url(url).post(requestBody);

        // 添加header
        addHeader(builder, header);

        Request request = builder.build();
        Response response = httpClient.newCall(request).execute();
        try {
            return response.body().string();
        } finally {
            response.close();
        }
    }

    public enum HTTPMethod {
        /** http GET */
        GET,
        /** http POST */
        POST,
        /** http PUT */
        PUT,
        /** http HEAD */
        HEAD,
        /** http DELETE */
        DELETE;

        private HTTPMethod() {
        }

        public String value() {
            return this.name();
        }

        public static HTTPMethod fromValue(String v) {
            return valueOf(v.toUpperCase());
        }
    }

    /**
     * 文件上传类
     * @author tanghc
     */
    @Getter
    @Setter
    public static class UploadFile implements Serializable {
        private static final long serialVersionUID = -1100614660944996398L;

        /**
         * @param name 表单名称，不能重复
         * @param file 文件
         * @throws IOException
         */
        public UploadFile(String name, File file) throws IOException {
            this(name, file.getName(), FileUtil.toBytes(file));
        }

        /**
         * @param name 表单名称，不能重复
         * @param fileName 文件名
         * @param input 文件流
         * @throws IOException
         */
        public UploadFile(String name, String fileName, InputStream input) throws IOException {
            this(name, fileName, FileUtil.toBytes(input));
        }

        /**
         * @param name 表单名称，不能重复
         * @param fileName 文件名
         * @param fileData 文件数据
         */
        public UploadFile(String name, String fileName, byte[] fileData) {
            super();
            this.name = name;
            this.fileName = fileName;
            this.fileData = fileData;
            this.md5 = DigestUtils.md5DigestAsHex(fileData);
        }

        private String name;
        private String fileName;
        private byte[] fileData;
        private String md5;

    }
}
