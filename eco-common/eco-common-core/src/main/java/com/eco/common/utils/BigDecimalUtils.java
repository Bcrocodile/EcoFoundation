package com.eco.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author bcro
 */
@Slf4j
public class BigDecimalUtils {

    /**
     * 直接进位
     */
    public final static int ROUND_UP = 0;
    
    /**
     * 直接退位
     */
    public final static int ROUND_DOWN = 1;
    
    /**
     * 正数时同ROUND_UP，负数时同ROUND_DOWN（绝对值进位）
     */
    public final static int ROUND_CEILING = 2;
    
    /**
     * 如果结果为正，则舍入行为类似于 RoundingMode.DOWN；如果结果为负，则舍入行为类似于RoundingMode.UP
     */
    public final static int ROUND_FLOOR = 3;
    
    /**
     * 四舍五入
     */
    public final static int ROUND_HALF_UP = 4;
    
    /**
     * 五舍六入
     */
    public final static int ROUND_HALF_DOWN = 5;
    
    /**
     * 前一位是奇数，则同ROUND_HALF_UP 前一位是偶数，则同ROUND_HALF_DOWN
     */
    public final static int ROUND_HALF_EVEN = 6;
    public final static int ROUND_UNNECESSARY = 7;

    /**
     * 汉语中数字大写
     */
    private static final String[] CN_UPPER_NUMBER = {"零", "壹", "贰", "叁", "肆",
            "伍", "陆", "柒", "捌", "玖"};
    
    /**
     * 汉语中货币单位大写，这样的设计类似于占位符
     */
    private static final String[] CN_UPPER_MONETRAY_UNIT = {"分", "角", "元",
            "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾",
            "佰", "仟"};
    
    /**
     * 特殊字符：整
     */
    private static final String CN_FULL = "整";
    
    /**
     * 特殊字符：负
     */
    private static final String CN_NEGATIVE = "负";
    
    /**
     * 金额的精度，默认值为2
     */
    private static final int MONEY_PRECISION = 2;
    
    /**
     * 特殊字符：零元整
     */
    private static final String CN_ZEOR_FULL = "零元" + CN_FULL;

    private BigDecimalUtils() {
    }

    /**
     * Integer相加
     *
     * @param in
     * @return bigDecimals 相加后的值
     */
    public static int addInteger(Integer... in) {
        if (in == null || in.length < 1) {
            return 0;
        }
        return Arrays.stream(in).filter(Objects::nonNull).reduce(Integer::sum).orElse(0);
    }

    public static void main(String[] args) {
        System.out.println(subtractInteger(1,3,2));
    }

    /**
     * Integer相减
     *
     * @param minuend
     * @param subtrahends
     * @return
     */
    public static int subtractInteger(Integer minuend, Integer... subtrahends) {
        if (minuend == null) {
            return 0;
        }
        return minuend-addInteger(subtrahends);
    }

    /**
     * BigDecimal相加
     *
     * @param bigDecimals
     * @return bigDecimals 相加后的值
     */
    public static BigDecimal add(BigDecimal... bigDecimals) {
        if (bigDecimals == null || bigDecimals.length < 1) {
            return BigDecimal.ZERO;
        }
        return Arrays.stream(bigDecimals).filter(Objects::nonNull).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    /**
     * BigDecimal相加
     *
     * @param bigDecimals
     * @return bigDecimals 相加后的值
     */
    public static BigDecimal add(List<BigDecimal> bigDecimals) {
        if (bigDecimals == null || bigDecimals.size() < 1) {
            return BigDecimal.ZERO;
        }
        return bigDecimals.stream().filter(Objects::nonNull).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    /**
     * BigDecimal相加
     *
     * @param scale        精度，小数保留位数
     * @param roundingMode 精度舍入模式 {@link RoundingMode}
     * @param bigDecimals
     * @return bigDecimals 相加保留进度后的值
     */
    public static BigDecimal add(int scale, RoundingMode roundingMode, BigDecimal... bigDecimals) {
        BigDecimal addResult = add(bigDecimals);
        if (addResult == null) {
            return new BigDecimal("0.0000") ;
        }
        if (roundingMode == null) {
            return addResult;
        }
        return addResult.setScale(scale, roundingMode);
    }

    /**
     * BigDecimal相加
     *
     * @param scale        精度，小数保留位数
     * @param roundingMode 精度舍入模式 {@link RoundingMode}
     * @param bigDecimals
     * @return bigDecimals 相加保留进度后的值
     */
    public static BigDecimal add(int scale, RoundingMode roundingMode, List<BigDecimal> bigDecimals) {
        BigDecimal addResult = add(bigDecimals);
        if (addResult == null) {
            return new BigDecimal("0.0000") ;
        }
        if (roundingMode == null) {
            return addResult;
        }
        return addResult.setScale(scale, roundingMode);
    }

    /**
     * BigDecimal相加
     *
     * @param scale        精度，小数保留位数
     * @param roundingMode 精度舍入模式 Rounding Modes
     * @param bigDecimals
     * @return bigDecimals 相加保留进度后的值
     */
    public static BigDecimal add(int scale, int roundingMode, BigDecimal... bigDecimals) {
        BigDecimal addResult = add(bigDecimals);
        return addResult == null ? new BigDecimal("0.0000") : addResult.setScale(scale, roundingMode);
    }

    /**
     * BigDecimal相减
     *
     * @param minuend
     * @param subtrahends
     * @return
     */
    public static BigDecimal subtract(BigDecimal minuend, BigDecimal... subtrahends) {
        if (minuend == null) {
            return new BigDecimal("0.0000");
        }
        BigDecimal totalSubtrahend = add(subtrahends);
        if (totalSubtrahend == null) {
            return minuend;
        }
        return minuend.subtract(totalSubtrahend);
    }

    /**
     * BigDecimal相减
     *
     * @param scale        精度，小数保留位数
     * @param roundingMode 精度舍入模式 Rounding Modes
     * @param minuend      被减数
     * @param subtrahends  减数
     * @return
     */
    public static BigDecimal subtract(int scale, int roundingMode, BigDecimal minuend, BigDecimal... subtrahends) {
        return subtract(minuend, subtrahends).setScale(scale, roundingMode);
    }

    /**
     * BigDecimal相乘
     *
     * @param bigDecimals
     * @return bigDecimals 相加后的值
     */
    public static BigDecimal multiply(BigDecimal... bigDecimals) {
        if (bigDecimals == null || bigDecimals.length < 1) {
            return BigDecimal.ZERO;
        }
        return Arrays.stream(bigDecimals).filter(Objects::nonNull).reduce(BigDecimal::multiply).orElse(BigDecimal.ZERO);
    }

    /**
     * BigDecimal相乘
     *
     * @param bigDecimals
     * @return bigDecimals 相加后的值
     */
    public static BigDecimal multiply(List<BigDecimal> bigDecimals) {
        if (bigDecimals == null || bigDecimals.size() < 1) {
            return BigDecimal.ZERO;
        }
        return bigDecimals.stream().filter(Objects::nonNull).reduce(BigDecimal::multiply).orElse(BigDecimal.ZERO);
    }

    /**
     * BigDecimal相乘
     *
     * @param scale        精度，小数保留位数
     * @param roundingMode 精度舍入模式 {@link RoundingMode}
     * @param bigDecimals
     * @return bigDecimals 相加保留进度后的值
     */
    public static BigDecimal multiply(int scale, RoundingMode roundingMode, BigDecimal... bigDecimals) {
        BigDecimal multiplyResult = multiply(bigDecimals);
        if (roundingMode == null) {
            return multiplyResult;
        }
        return multiplyResult == null ? BigDecimal.ZERO : multiplyResult.setScale(scale, roundingMode);
    }

    /**
     * BigDecimal相乘
     *
     * @param scale        精度，小数保留位数
     * @param roundingMode 精度舍入模式 {@link RoundingMode}
     * @param bigDecimals
     * @return bigDecimals 相加保留进度后的值
     */
    public static BigDecimal multiply(int scale, RoundingMode roundingMode, List<BigDecimal> bigDecimals) {
        BigDecimal multiplyResult = multiply(bigDecimals);
        if (roundingMode == null) {
            return multiplyResult;
        }
        return multiplyResult == null ? BigDecimal.ZERO : multiplyResult.setScale(scale, roundingMode);
    }


    /**
     * BigDecimal相乘
     *
     * @param scale        精度，小数保留位数
     * @param roundingMode 精度舍入模式 Rounding Modes
     * @param bigDecimals
     * @return bigDecimals 相加保留进度后的值
     */
    public static BigDecimal multiply(int scale, int roundingMode, BigDecimal... bigDecimals) {
        BigDecimal multiplyResult = multiply(bigDecimals);
        return multiplyResult == null ? BigDecimal.ZERO : multiplyResult.setScale(scale, roundingMode);
    }

    /**
     * 除法
     *
     * @param dividend     被除数
     * @param divisor      除数
     * @param scale        小数位数
     * @param roundingMode 小数精确方式
     * @return 商
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, int scale, RoundingMode roundingMode) {
        if (dividend == null) {
            return BigDecimal.ZERO;
        }
        if (BigDecimal.ZERO.equals(divisor) || StringUtils.isBlank(divisor.toString())) {
            log.error("除数不能为空或者0!");
            return BigDecimal.ZERO;
        }
        return dividend.divide(divisor, scale, roundingMode);

    }

    /**
     * 除法
     *
     * @param dividend
     * @param divisor
     * @return
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
        if (dividend == null) {
            return BigDecimal.ZERO;
        }
        if (BigDecimal.ZERO.equals(divisor) || StringUtils.isBlank(divisor.toString())) {
            log.error("除数不能为空或者0!");
            return BigDecimal.ZERO;
        }
        return dividend.divide(divisor);

    }

    /**
     * 精确相加
     *
     * @param bigDecimals
     * @return
     */
    public static BigDecimal addBigDecimal(BigDecimal... bigDecimals) {
        BigDecimal sum = BigDecimal.ZERO;
        if (bigDecimals == null || bigDecimals.length < 1) {
            return sum;
        }
        for (BigDecimal bigDecimal : bigDecimals) {
            if (bigDecimal != null) {
                sum = sum.add(new BigDecimal(Double.toString(bigDecimal.doubleValue())));
            }
        }
        return sum;
    }

    /**
     * 精确相减
     *
     * @param subtrahends
     * @return
     */
    public static BigDecimal subtractBigDecimal(BigDecimal minuend, BigDecimal... subtrahends) {
        if (minuend == null) {
            minuend = BigDecimal.ZERO;
        }
        BigDecimal minuendFat = new BigDecimal(Double.toString(minuend.doubleValue()));
        if (subtrahends == null || subtrahends.length < 1) {
            return minuendFat;
        }
        for (BigDecimal subtrahend : subtrahends) {
            if (subtrahend != null) {
                BigDecimal subtrahendFat = new BigDecimal(Double.toString(subtrahend.doubleValue()));
                minuendFat = minuendFat.subtract(subtrahendFat);
            }
        }
        return minuendFat;
    }

    /**
     * BigDecimal 保留两位 返回百分比
     *
     * @param decimal
     * @return
     */
    public static String bigDecimalToPercent(BigDecimal decimal) {
        return bigDecimalToPercent(decimal, 2);
    }

    /**
     * 转换百分比
     *
     * @param decimal
     * @param scale   百分比小数位数
     * @return
     */
    public static String bigDecimalToPercent(BigDecimal decimal, int scale) {
        if (decimal == null) {
            return "0%";
        }
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(scale);
        return percent.format(keepBigDecimal(decimal, scale + 2).doubleValue());
    }

    /**
     * 四舍五入保留 小数位
     *
     * @param decimal 参数
     * @param len     小数位
     * @return
     */
    public static BigDecimal keepBigDecimal(BigDecimal decimal, int len) {
        if (decimal == null) {
            return BigDecimal.ZERO;
        }
        return decimal.setScale(len, RoundingMode.HALF_UP);
    }

    /**
     * 把输入的金额转换为汉语中人民币的大写
     *
     * @param numberOfMoney 输入的金额
     * @return 对应的汉语大写
     */
    public static String numberToCN(BigDecimal numberOfMoney) {
        StringBuffer sb = new StringBuffer();
        // -1, 0, or 1 as the value of this BigDecimal is negative, zero, or
        // positive.
        int signum = numberOfMoney.signum();
        
        // 零元整的情况
        if (signum == 0) {
            return CN_ZEOR_FULL;
        }
        
        //这里会进行金额的四舍五入
        long number = numberOfMoney.movePointRight(MONEY_PRECISION)
                .setScale(0, 4).abs().longValue();
        
        // 得到小数点后两位值
        long scale = number % 100;
        int numUnit = 0;
        int numIndex = 0;
        boolean getZero = false;
        
        // 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
        if (!(scale > 0)) {
            numIndex = 2;
            number = number / 100;
            getZero = true;
        }
        if ((scale > 0) && (!(scale % 10 > 0))) {
            numIndex = 1;
            number = number / 10;
            getZero = true;
        }
        int zeroSize = 0;
        while (true) {
            if (number <= 0) {
                break;
            }
            // 每次获取到最后一个数
            numUnit = (int) (number % 10);
            if (numUnit > 0) {
                if ((numIndex == 9) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
                }
                if ((numIndex == 13) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
                }
                sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                getZero = false;
                zeroSize = 0;
            } else {
                ++zeroSize;
                if (!(getZero)) {
                    sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                }
                if (numIndex == 2) {
                    if (number > 0) {
                        sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                    }
                } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                }
                
                getZero = true;
            }
            // 让number每次都去掉最后一个数
            number = number / 10;
            ++numIndex;
        }
        // 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
        if (signum == -1) {
            sb.insert(0, CN_NEGATIVE);
        }
        
        // 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
        if (!(scale > 0)) {
            sb.append(CN_FULL);
        }
        
        return sb.toString();
    }


    /**
     * int 转成 BigDecimal
     *
     * @param num
     * @return
     */
    public static BigDecimal intToBigDecimal(int num) {
        BigDecimal a = new BigDecimal(num);
        
        return a;
    }

    /**
     * Long 类型转换成 bigDecimal
     *
     * @param l
     * @return
     */
    public static BigDecimal longToBigDecimal(Long l) {
        if (l == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal bigDecimal = new BigDecimal(l);
        
        return bigDecimal;
    }
}