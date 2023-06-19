package com.eco.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @Bcro
 * @Description 公共工具类
 **/
public class CommonUtils {



    public static Integer convertBoolean(Boolean ... b) {
        List<Boolean> booleanList = Arrays.stream(b).filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(booleanList)) {
            return 0;
        }
        Boolean bo = true;
        for (Boolean aBoolean : booleanList) {
            bo = bo  && aBoolean;
        }
        return bo ? 1:0;
    }

    /**
     * 字符串逗号隔开 转List
     * @param str
     * @return
     */
    public static List<String> convertStringList(String str) {
        if (str == null || str.length() == 0) {
            return new ArrayList<>();
        }
        return Arrays.stream(str.split(","))
                .filter(org.apache.commons.lang3.StringUtils::isNotBlank).collect(Collectors.toList());
    }

    /**
     * 两个字符串比较
     * @param str1
     * @param str2
     * @return
     */
    public static boolean compareTwoString(String str1, String str2) {
        // 将字符串拆分成数组
        List<String> arr1 = convertStringList(str1);
        List<String> arr2 = convertStringList(str2);

        // 比较数组长度
        if (arr1.size() != arr2.size()) {
            return false;
        }

        // 比较数组元素
        for (String a1 : arr1) {
            if (!arr2.contains(a1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 求两个字符串交集
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean intersectionTwoString(String str1, String str2) {

        // 将字符串拆分成数组
        List<String> arr1 = convertStringList(str1);
        List<String> arr2 = convertStringList(str2);

        List<Object> resultList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        arr1.forEach(a1->{
            map.put(a1,a1);
        });

        arr2.forEach(a2->{
            Object obj = map.get(a2);
            if (obj!=null){
                resultList.add(obj);
            }
        });

        return resultList.size() > 0;
    }



    //stream list  根据某个属性去重
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    //处理乱码
    public static  String  convertString(String str) throws UnsupportedEncodingException {
        return  new String(str.getBytes("ISO-8859-1"),"UTF-8");
    }

    /**
     * 随机数  字母+数字 10位
     *
     * @return
     */
    public static String getRandomCode(int num) {
        return RandomStringUtils.randomAlphanumeric(num);
    }

    /**
     * 随机数  i 位数字 雪花算法后i 位
     *
     * @return
     */
    public static String getNumber(int i) {
        String idStr = IdUtils.getIdStr();
        return idStr.substring(idStr.length() - i);
    }


    //生成短信验证码
    public static String getSmsCode() {
        int number = 6;
        StringBuffer stringBuffer = new StringBuffer();
        Random rand = new Random();
        for (int i = 0; i < number; i++) {
            stringBuffer.append(rand.nextInt(10));
        }
        return stringBuffer.toString();
    }

    /**
     * 下划线转驼峰命名
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean curreCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append("_");
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
                sb.append("_");
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    //生成字母编码
    public static String generateStr(int number) {
        String str = "";
        for (int i = 0; i < 2; i++) {
            str = str + (char)(Math.random() * 26 + 'a');
        }
        return str;
    }

    /**
     * 手机号码前三后四脱敏
     *
     * @author yedi
     * @since 2022/9/20 下午5:19
     */
    public static String mobileEncrypt(String mobile) {
        if (StringUtils.isEmpty(mobile) || (mobile.length() != 11)) {
            return mobile;
        }
        return mobile.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
    }
}
