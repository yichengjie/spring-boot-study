package com.yicj.study;


import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

//总结：当我们在精度要求非常高的时候，需要进行精确的计算，
// 比如：货币，那我们就需要采用 java.math.BigDecimal 类来进行精确计算。
public class BigDecimalTest {

    //尽量传入string参数不要直接传入double参数
    @Test
    public void testDouble2BigDecimal() {
        Double d = 236711125.123 ;
        System.out.println(new BigDecimal(d.toString()));
        System.out.println(new BigDecimal(d).toString()) ;
    }

    //当我们要做精确的小数操作运算时，就需要用到 BigDecimal
    @Test
    public void testSub(){
        double d1 = 0.3;
        double d2 = 0.2;
        System.out.println("Double:\t 0,3 - 0,2 = " + (d1 - d2));

        float f1 = 0.3f;
        float f2 = 0.2f;
        System.out.println("Float:\t 0,3 - 0,2 = " + (f1 - f2));

        BigDecimal bd1 = new BigDecimal("0.3");
        BigDecimal bd2 = new BigDecimal("0.2");
        System.out.println("BigDec:\t 0,3 - 0,2 = " + (bd1.subtract(bd2)));
    }

    //当结果除不进，并且没有设置进位的状态值，那就会抛出异常。正确的操作如下：
    @Test
    public void testDivide(){
        double d1 = 10;
        double d2 = 3;
        System.out.println("Double:\t 10 / 3 = " + (d1 / d2));
        float f1 = 10f;
        float f2 = 3f;
        System.out.println("Float:\t 10 / 3 = " + (f1 / f2));

        // Exception!
        BigDecimal bd3 = new BigDecimal("10");
        BigDecimal bd4 = new BigDecimal("3");
        //System.out.println("BigDec:\t 10 / 3 = " + (bd3.divide(bd4)));
        System.out.println("BigDec:\t 10 / 3 = " + (bd3.divide(bd4,4,BigDecimal.ROUND_HALF_UP)));
    }

    @Test
    public void testCalculation(){
        BigDecimal a = new BigDecimal("0.02");
        BigDecimal b = new BigDecimal("0.03");
        System.out.println("a + b = " + a.add(b));
        System.out.println("a - b = " + a.subtract(b));
        System.out.println("a * b = " + a.multiply(b));
        System.out.println("a ÷ b = " + a.divide(b,2,BigDecimal.ROUND_HALF_UP));
    }

    @Test
    public void testKeepTwoDecimal(){
        BigDecimal num= new BigDecimal(13.154215);

        //方式一
        DecimalFormat df1 = new DecimalFormat("0.00");
        String str = df1.format(num);
        System.out.println(str);  //13.15

        //方式二
        // #.00 表示两位小数 #.0000四位小数
        DecimalFormat df2 =new DecimalFormat("#.00");
        String str2 =df2.format(num);
        System.out.println(str2);  //13.15

        //方式三
        //%.2f %. 表示 小数点前任意位数   2 表示两位小数 格式后的结果为f 表示浮点型
        String result = String.format("%.2f", num);
        System.out.println(result);  //13.15

    }

    @Test
    public void testScale(){
        double num = 2.35;
        BigDecimal b = new BigDecimal(num);
        // setScale(1) 表示保留一位小数
        System.out.println("ROUND_UP，结果：" + b.setScale(1, BigDecimal.ROUND_UP).doubleValue());
        System.out.println("ROUND_DOWN，结果：" + b.setScale(1, BigDecimal.ROUND_DOWN).doubleValue());
        System.out.println("ROUND_CEILING，结果：" + b.setScale(1, BigDecimal.ROUND_CEILING).doubleValue());
        System.out.println("ROUND_FLOOR，结果：" + b.setScale(1, BigDecimal.ROUND_FLOOR).doubleValue());
        System.out.println("ROUND_HALF_UP，结果：" + b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
        System.out.println("ROUND_HALF_DOWN，结果：" + b.setScale(1, BigDecimal.ROUND_HALF_DOWN).doubleValue());
        System.out.println("ROUND_HALF_EVEN，结果：" + b.setScale(1, BigDecimal.ROUND_HALF_EVEN).doubleValue());
        System.out.println("ROUND_UNNECESSARY，结果：" + b.setScale(1, BigDecimal.ROUND_UNNECESSARY).doubleValue());

        /**
         * ROUND_UP，结果：2.4
         * ROUND_DOWN，结果：2.3
         * ROUND_CEILING，结果：2.4
         * ROUND_FLOOR，结果：2.3
         * ROUND_HALF_UP，结果：2.4
         * ROUND_HALF_DOWN，结果：2.4 (来给我解释解释这个，说好的五舍六入呢)
         * ROUND_HALF_EVEN，结果：2.4 (还有这个)
         * Disconnected from the target VM, address: '127.0.0.1:59637', transport: 'socket'
         * Exception in thread "main" java.lang.ArithmeticException: Rounding necessary
         */
    }

    @Test
    public void testCompareTo(){
        BigDecimal a = new BigDecimal("0.02");
        BigDecimal b = new BigDecimal("0.01");
        BigDecimal a2 = new BigDecimal("0.02");
        System.out.println(" a > b 返回结果：" + a.compareTo(b));
        System.out.println(" a = a2 返回结果：" + a.compareTo(a2));
        System.out.println(" b < a 返回结果：" + b.compareTo(a));
        /**
         * a > b 返回结果：1
         *  a = a2 返回结果：0
         *  b < a 返回结果：-1
         */
    }


    //在使用 BigDecimal 进行赋值的时候，最好使用传入 String 的构造函数，可以确认精度。
    @Test
    public void testDouble(){
        BigDecimal num1 = new BigDecimal(0.005);
        BigDecimal num2 = new BigDecimal(1000000);
        BigDecimal num3 = new BigDecimal(-1000000);
        //尽量用字符串的形式初始化
        BigDecimal num12 = new BigDecimal("0.005");
        BigDecimal num22 = new BigDecimal("1000000");
        BigDecimal num32 = new BigDecimal("-1000000");

        //加法
        BigDecimal result1 = num1.add(num2);
        BigDecimal result12 = num12.add(num22);
        //减法
        BigDecimal result2 = num1.subtract(num2);
        BigDecimal result22 = num12.subtract(num22);
        //乘法
        BigDecimal result3 = num1.multiply(num2);
        BigDecimal result32 = num12.multiply(num22);
        //绝对值
        BigDecimal result4 = num3.abs();
        BigDecimal result42 = num32.abs();
        //除法
        BigDecimal result5 = num2.divide(num1,20,BigDecimal.ROUND_HALF_UP);
        BigDecimal result52 = num22.divide(num12,20,BigDecimal.ROUND_HALF_UP);

        System.out.println("加法用value结果："+result1);
        System.out.println("加法用string结果："+result12);

        System.out.println("减法value结果："+result2);
        System.out.println("减法用string结果："+result22);

        System.out.println("乘法用value结果："+result3);
        System.out.println("乘法用string结果："+result32);

        System.out.println("绝对值用value结果："+result4);
        System.out.println("绝对值用string结果："+result42);

        System.out.println("除法用value结果："+result5);
        System.out.println("除法用string结果："+result52);

        /**
         * 加法用value结果：1000000.005000000000000000104083408558608425664715468883514404296875
         * 加法用string结果：1000000.005
         * 减法value结果：-999999.994999999999999999895916591441391574335284531116485595703125
         * 减法用string结果：-999999.995
         * 乘法用value结果：5000.000000000000104083408558608425664715468883514404296875000000
         * 乘法用string结果：5000.000
         * 绝对值用value结果：1000000
         * 绝对值用string结果：1000000
         * 除法用value结果：199999999.99999999583666365766
         * 除法用string结果：200000000.00000000000000000000
         */

        /**
         * 注意事项
         * System.out.println() 中的数字默认是 double 类型的，double 类型的小数计算不准确
         * 使用 BigDecimal 的构造方法传入 double 类型时，计算的结果也是不准确的！
         */
    }


}
