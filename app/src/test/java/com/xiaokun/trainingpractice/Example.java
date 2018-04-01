package com.xiaokun.trainingpractice;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/03/20
 *     描述   :
 *     版本   : 1.0
 * </pre>
 */

public class Example
{

    String str = new String("good");
    char[] ch = {'a', 'b', 'c'};

    public static void main(String[] args)
    {
        Example ex = new Example();
        ex.change(ex.str, ex.ch);
        System.out.print(ex.str + " and ");
        System.out.print(ex.ch);
    }

    public void change(String str, char ch[])
    {
        //引用类型变量，传递的是地址，属于引用传递。
        str = "test ok";
        ch[0] = 'g';
    }

}
