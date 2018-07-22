package com.example;

/**
 * Created by 肖坤 on 2018/5/6.
 *
 * @author 肖坤
 * @date 2018/5/6
 */

public class Sorts
{
    public static void bubbleSort(int[] a)
    {
        for (int i = 0; i < a.length; i++)
        {
            for (int j = a.length - 2; j >= i; j--)
            {
                if (a[j] > a[j + 1])
                {
                    swap(a, j, j + 1);
                }
            }
        }
    }

    private static void swap(int[] a, int j, int i)
    {
        int temp = a[j];
        a[j] = a[i];
        a[i] = temp;
    }

    public static void main(String[] args)
    {
        int[] nums = {23, 34, 456, 12, 7, 89, 2345, 56, 578, 45, 23452,
                235235, 2345235, 23525, 2345646, 1, 23456, 25, 48, 59, 20, 40, 60, 9};
        System.out.println(nums.length);
        System.out.println(nums[nums.length - 1]);
        bubbleSort(nums);
        for (int i : nums)
        {
            System.out.print(i + " ");
        }

        int[] nums2 = new int[5];
        for (int i : nums2)
        {
            System.out.print(i + " ");
        }
    }

}
