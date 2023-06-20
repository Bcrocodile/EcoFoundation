package com.eco.common.web.util;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Administrator
 */
@Slf4j
public class ResponseUtils {

    public static void writeJson(HttpServletResponse response, Object msg){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().write(JSONUtil.toJsonStr(msg));
        } catch (IOException ioException) {
            log.error("writeJson,{}",ioException.getMessage());
        }
    }
}
