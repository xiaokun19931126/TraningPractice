package com.example;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/03/21
 *     描述   :
 *     版本   : 1.0
 * </pre>
 */

public class Example1
{

    public static void main(String[] args)
    {

        int[] b = {9, 1, 4, 2, 3, 6, 5, 8, 7};
        System.out.println("The original data:");
        for (int i = 0; i < b.length; i++)
        {
            System.out.print(b[i]);
        }
        fun(b, b.length);
        System.out.println();
        System.out.println("The data after mocinng");
        for (int i = 0; i < b.length; i++)
        {
            System.out.print(b[i]);
        }
    }

    /**
     * @param a 数组
     * @param n 数组的长度
     */
    static void fun(int[] a, int n)
    {
        int i, j, max, min, px, pn, t;
        for (i = 0; i < n - 1; i += 2)
        {
            max = min = a[i];
            px = pn = i;
            System.out.println("max:" + max + ";min:" + min);
            System.out.println("px:" + px + ";pn:" + pn);
            for (j = i + 1; j < n; j++)
            {
                if (max < a[j])
                {
                    max = a[j];
                    px = j;
                }
                if (min > a[j])
                {
                    min = a[j];
                    pn = j;
                }
            }
            if (pn != i)
            {
                t = a[i];
                a[i] = min;
                a[pn] = t;
                if (px == i)
                {
                    px = pn;
                }
            }
            if (px != i + 1)
            {
                t = a[i + 1];
                a[i + 1] = max;
                a[px] = t;
            }
        }
    }
}
