package com.eco.common.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public interface GlobalConstants {

    /**
     * 认证鉴权相关
     */
    Integer STATUS_YES = 1;
    String DEFAULT_USER_PASSWORD = "123456";

    /**
     * 根部门ID
     */
    Long ROOT_DEPT_ID = 0L;
    /**
     * 根菜单ID
     */
    Long ROOT_MENU_ID = 0L;
    String ROOT_ROLE_CODE = "ROOT";
    String URL_PERM_ROLES_KEY = "system:perm_roles_rule:url:";
    String BTN_PERM_ROLES_KEY = "system:perm_roles_rule:btn:";
    String AUTH_CLIENT_ID_KEY = "system:auth:client:";


    /**
     * 符号常量
     */
    String COMMA = ",";
    String LINE = "-";
    String UNDERLINE = "_";
    String SLANT = "/";
    String SEMICOLON = ";";
    String COLON = ":";
    String POINT = ".";
    String VERTICAL_LINE = "|";
    String EQUAL = "=";
    String QUESTION_MARK = "?";
    String AND_MARK = "&";

    /**
     * 数字常量
     */
    int NUM_ZERO = 0;
    int NUM_ONE = 1;
    int NUM_TWO = 2;
    int NUM_THREE = 3;
    int NUM_FOUR = 4;
    int NUM_FIVE = 5;


    /**
     * 空集合
     */
    List emptyList = new ArrayList<>();


}
