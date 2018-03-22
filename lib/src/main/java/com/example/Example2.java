package com.example;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/03/21
 *     描述   :
 *     版本   : 1.0
 * </pre>
 */

public class Example2
{
    public static void main(String[] args)
    {
        String a = "abc";
        System.out.println(a.hashCode());
        a = "cde";
        System.out.println(a.hashCode());
    }
}
