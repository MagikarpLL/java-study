package cn.magikarpll.javaStudy.algorithms.a2_basic;

import java.util.Arrays;

public class A2Basic {

    public static void main(String[] args) {
        int[] test = {9, 2, 5, 4, 3, 1};
        System.out.println(Arrays.toString(insertSort(test)));
    }

    //插入排序,从小到大
    /**
     * 插入排序的思想是
     * 双循环，第一层循环从第i(i>=2)个节点开始往后循环，
     * 第二层循环则是将第i个节点的值与i以前的有序数组进行比较，若不满足排序，则将i-1的值赋给i，然后i--，直到找到一个位置，满足i以前的数组有序
     * 最后将提前保存的第i节点的值赋值到i最终节点
     *
     * 内循环其实就是不断的把i与前一个节点位置对换，找到i所在的位置
     * @param inputArrays
     * @return
     */
    public static int[] insertSort(int[] inputArrays) {
        int length = inputArrays.length;
        //参数判断
        if (length <= 1) {
            return inputArrays;
        }
        //外层循环
        for (int i = 1; i < length; i++) {
            //保存每个i节点的值
            int key = inputArrays[i];
            //准备内层循环的起始值
            int j = i - 1;
            //内层循环， 结束循环的条件为数组用尽或者遇到一个值，它的值小于等于第i节点
            while (j >= 0 && inputArrays[j] > key) {
                //如果i-1的值大于i，因为提前保存了i节点的值，则将i-1（j）的值赋给i(j+1)
                inputArrays[j+1] = inputArrays[j];
                //然后内循环继续往前，j--, 继续判断，直到数组用尽或者j的值大于等于Key
                j = j - 1;
            }
            //最后将i节点的值赋给j，因为出循环时j多减了1，所以这里要+1，才能找到i最终的位置
            inputArrays[j+1] = key;
        }
        return inputArrays;
    }

    public static int[] mergeSore(int [] inputArrays){

    }

    public static int[] merge(int[] A, int[] B){



        return
    }




}
