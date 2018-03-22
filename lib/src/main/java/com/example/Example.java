package com.example;

public class Example
{
    //引用类型
    String str = new String("good");
    //引用类型
    char[] ch = {'a', 'b', 'c'};
    //基本类型
    int a = 5;

    public static void main(String[] args)
    {
        Example ex = new Example();

        System.out.println(ex.str.hashCode());
        ex.change(ex.str, ex.ch, ex.a);

        System.out.print(ex.str + " and ");
        System.out.print(ex.ch);
        System.out.println(ex.a);
    }

    public void change(String str, char[] ch, int a)
    {
        //引用类型变量，传递的是地址，属于引用传递。
        str = "test ok";
        System.out.println(str.hashCode());
        ch[0] = 'g';
        a = 10;
    }

}
