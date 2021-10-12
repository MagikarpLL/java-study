package cn.magikarpll.javaStudy.algorithms.aSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class A1_Sort {

    //21
    private static int[] unsortedArray = {3, 5, 7, 2, 6, 7, 8, 1, 432, 43, 65, 34, 675, 23, 5, 76, 54, 12, 43, 65, 87};

    public static void main(String[] args) {
        //测试冒泡排序
//        System.out.println(Arrays.toString(unsortedArray));
//        int[] results1 = bubbleSort(unsortedArray);
//        System.out.println(Arrays.toString(results1));

        //测试选择排序
//        System.out.println(Arrays.toString(unsortedArray));
//        int[] results2 = selectionSort(unsortedArray);
//        System.out.println(Arrays.toString(results2));

        //测试插入排序
//        System.out.println(Arrays.toString(unsortedArray));
//        int[] results3 = insertionSort(unsortedArray);
//        System.out.println(Arrays.toString(results3));

        //测试希尔排序
//        System.out.println(Arrays.toString(unsortedArray));
//        int[] results4 = ShellSort(unsortedArray);
//        System.out.println(Arrays.toString(results4));

        //测试归并排序
//        System.out.println(Arrays.toString(unsortedArray));
//        int[] results5 = MergeSort(unsortedArray);
//        System.out.println(Arrays.toString(results5));

        //测试快速排序
        //System.out.println(Arrays.toString(unsortedArray));
        //int[] results6 = QuickSort(unsortedArray, 0, unsortedArray.length - 1);
        //System.out.println(Arrays.toString(results6));

        //测试堆排序
//        System.out.println(Arrays.toString(unsortedArray));
//        int[] results7 = HeapSort(unsortedArray);
//        System.out.println(Arrays.toString(results7));

        //测试计数排序
//        System.out.println(Arrays.toString(unsortedArray));
//        int[] results8 = CountingSort(unsortedArray);
//        System.out.println(Arrays.toString(results8));

        //测试桶排序
//        System.out.println(Arrays.toString(unsortedArray));
//        int[] results9 = bucketSort(unsortedArray);
//        System.out.println(Arrays.toString(results9));

        //测试基数排序
//        System.out.println(Arrays.toString(unsortedArray));
//        int[] results10 = RadixSort(unsortedArray);
//        System.out.println(Arrays.toString(results10));

    }

    /**
     * 冒泡排序
     * <p>
     * 这个算法理解起来很简单，没什么好说的，但是要注意边界条件，不要漏前后
     * <p>
     * 我第一次写的时候内循环的边界条件设的有问题，造成了漏了第一个和第二个数据
     *
     * @param array
     * @return
     */
    public static int[] bubbleSort(int[] array) {
        for (int j = array.length - 1; j > 1; j--) {
            for (int i = 0; i < j; i++) {
                if (array[i] > array[i + 1]) {
                    int temp = array[i + 1];
                    array[i + 1] = array[i];
                    array[i] = temp;
                }
            }
        }
        return array;
    }

    /**
     * 选择排序
     * 它和冒泡排序很像，也是两趟循环，但它的第一趟是有明确目标的，也就是找到这个队列中最小的
     *
     * @param array
     * @return
     */
    public static int[] selectionSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int min = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[min] > array[j]) {
                    min = j;
                }
            }
            int temp = array[i];
            array[i] = array[min];
            array[min] = temp;
        }
        return array;
    }

    /**
     * 插入排序
     * 插入排序是直接把那个数据一次插到位，不要每次往前挪一格，这样就变成冒泡了
     * 这个在边界条件那里容易错，第二个是容易当成冒泡来处理
     *
     * @param array
     * @return
     */
    public static int[] insertionSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int temp = array[i];
            int j = i - 1;
            for (; j >= 0 && temp < array[j]; j--) {
                array[j + 1] = array[j];
            }
            if (j != i - 1) {
                array[j + 1] = temp;
            }
        }
        return array;
    }

    /**
     * 希尔排序(缩小增量排序)
     * <p>
     * 最内循环的条件非常容易写错，写成array[pre]>array[i]，但array[i]是变化的啊，所以一定得用temp!!!!
     * 最内循环的表达式也容易写错，一定要记住，有些下标是动态变化的，分清楚  变与不变
     *
     * @param array
     * @return
     */
    public static int[] ShellSort(int[] array) {
        int length = array.length;
        int gap = array.length / 2;
        while (gap > 0) {
            for (int i = gap; i < length; i++) {
                int pre = i - gap;
                int temp = array[i];
                while (pre >= 0 && array[pre] > temp) {
                    array[pre + gap] = array[pre];
                    pre = pre - gap;
                }
                array[pre + gap] = temp;
            }
            gap = gap / 2;
        }
        return array;
    }

    /**
     * 归并排序
     *
     * @param array
     * @return
     */
    public static int[] MergeSort(int[] array) {
        if (array.length < 2) {
            return array;
        }
        int mid = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);
        return merge(MergeSort(left), MergeSort(right));
    }

    /**
     * 归并排序——将两段排序好的数组结合成一个排序数组
     *
     * @param left
     * @param right
     * @return
     */
    private static int[] merge(int[] left, int[] right) {
        int[] results = new int[left.length + right.length];
        for (int i = 0, r = 0, l = 0; i < results.length; i++) {
            if (l >= left.length) {
                results[i] = right[r++];
            } else if (r >= right.length) {
                results[i] = left[l++];
            } else if (left[l] < right[r]) {
                results[i] = left[l++];
            } else {
                results[i] = right[r++];
            }
        }
        return results;
    }

    /**
     * 快速排序方法
     * 快速排序的核心算法就是，通过每次排序将一个元素排在它所属的位置，左边比它小，右边比它大，那么它的位置就是对的，然后递归的再排序左右的子数组
     * 左右子数组再依次选一个元素排好，再递归下去，最后当递归到只剩下1个的时候，就会直接返回，退出递归，逐级回来就排序好了
     *
     * @param array
     * @param start
     * @param end
     * @return
     */
    public static int[] QuickSort(int[] array, int start, int end) {
        if (array.length < 1 || start < 0 || end > array.length || start > end) {
            return null;
        }
        int index = partition(array, start, end);
        if (index > start) {
            QuickSort(array, start, index - 1);
        }
        if (index < end) {
            QuickSort(array, index + 1, end);
        }
        return array;
    }

    /**
     * 快速排序方法的分片算法
     *
     * @param array
     * @param start
     * @param end
     * @return
     */
    public static int partition(int[] array, int start, int end) {
        //这里+1是必须的，因为random出来的值是大于等于0，小于1，然后又进行了精度转换，所以一定是会损失一位，不加1这里就不能到最右边
        int pivot = (int) (start + Math.random() * (end - start + 1));
        int smallIndex = start - 1;
        //把这个基准丢到最后去，方便比较和交换
        swapArrayItem(array, pivot, end);
        for (int i = start; i <= end; i++) {
            if (array[i] <= array[end]) {
                smallIndex++;
                if (i > smallIndex) {
                    swapArrayItem(array, smallIndex, i);
                }
            }
        }
        return smallIndex;
    }

    public static void swapArrayItem(int[] array, int source, int target) {
        int temp = array[source];
        array[source] = array[target];
        array[target] = temp;
    }


    //堆排序: 声明全局变量，用于记录数组array的长度；
    static int len;

    /**
     * 堆排序算法
     *
     * @param array
     * @return
     */
    public static int[] HeapSort(int[] array) {
        len = array.length;
        if (len <= 1) {
            return array;
        }
        //构建一个最大堆
        buildMaxHeapArray(array);
        while (len > 0) {
            swapArrayItem(array, 0, len - 1);
            len--;
            //调整堆排序
            adjustHeap(array, 0);
        }
        return array;
    }

    /**
     * 构建一个最大堆
     *
     * @param array
     */
    public static void buildMaxHeapArray(int[] array) {
        for (int i = (len / 2 - 1); i >= 0; i--) {
            adjustHeap(array, i);
        }
    }

    /**
     * 调整一个最大堆
     *
     * @param array
     */
    public static void adjustHeap(int[] array, int head) {
        int maxindex = head;
        if (2 * head < len && array[2 * head] > array[maxindex]) {
            maxindex = 2 * head;
        }
        if (2 * head + 1 < len && array[2 * head + 1] > array[maxindex]) {
            maxindex = 2 * head + 1;
        }
        if (maxindex != head) {
            swapArrayItem(array, maxindex, head);
            adjustHeap(array, maxindex);
        }
    }

    /**
     * 计数排序
     *
     * @param array
     * @return
     */
    public static int[] CountingSort(int[] array) {
        int max = 0;
        int min = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
                continue;
            }
            if (array[i] < min) {
                min = array[i];
            }
        }
        int radix = 0 - min;
        int[] countArray = new int[max - min + 1];
        Arrays.fill(countArray, 0);
        for (int j = 0; j < array.length; j++) {
            countArray[array[j] + radix]++;
        }
        int index = 0;
        for (int k = 0; k < countArray.length; k++) {
            while (countArray[k] != 0) {
                array[index] = k - radix;
                countArray[k]--;
                index++;
            }
        }
        return array;
    }


    /**
     * 桶排序
     * 我觉得这里桶排序反而不能递归使用桶排序，这样等到边界条件出去，跟计数排序有什么区别
     * 对里面的桶用计数排序或者插入排序都比递归桶排序好
     *
     * @param array
     * @return
     */
    public static int[] bucketSort(int[] array) {
        //找出最大值和最小值
        int max = 0;
        int min = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
                continue;
            }
            if (array[i] < min) {
                min = array[i];
            }
        }

        //初始化桶(这里不太理解)
        //算桶的存储范围
        //一般桶的数量为数组的长度-1，因为是数字之间的范围是桶，所以-1
        int gap = (max - min) / (array.length - 1);
        ArrayList<ArrayList<Integer>> buckets = new ArrayList<ArrayList<Integer>>();
        for (int j = 0; j < array.length; j++) {
            buckets.add(new ArrayList<Integer>());
        }

        //放入桶
        for(int k = 0;k<array.length;k++){
            buckets.get((array[k]-min)/gap).add(array[k]);
        }

        //对每个桶进行排序
        for(ArrayList bucket: buckets){
            Collections.sort(bucket);
        }

        int index = 0;
        //取出每个桶的元素
        for(ArrayList<Integer> bucket: buckets){
            for(Integer i: bucket){
                array[index++] = i;
            }
        }

        return array;
    }

    /**
     * 基数排序
     * 基数排序不太理解，需要重点关注一下。
     * 大致思路是很清晰，但等到具体编写代码时就很多问题
     * @param array
     * @return
     */
    public static int[] RadixSort(int[] array) {
        //TODO 这个排序问题很多，要仔细琢磨看下


        if(null == array || array.length < 2) {
            return array;
        }
        //先算最大的数
        int max = 0;
        for(int i: array){
            if(i>max){
                max=i;
            }
        }
        //再算位数
        int maxDigit = 0;
        while(max!=0){
            max=max/10;
            maxDigit++;
        }

        //这里还是跟计数排序不一样的，不能用index一维数组存储，这样子无法存储原数组的值的。
        ArrayList<ArrayList<Integer>> numbers = new ArrayList<ArrayList<Integer>>();
        for(int a = 0; a < 10; a++){
            numbers.add(new ArrayList<Integer>());
        }
        //然后进行按位循环计数排序
        for (int m = 0, div=1, mod=10; m < maxDigit; m++, div*=10){
            for(int j = 0; j < array.length; j++){
                int index = (array[j]/div)%mod;
                numbers.get(index).add(array[j]);
            }
            int indexResult = 0;
            for(int n = 0; n < numbers.size(); n++){
                while (numbers.get(n).size()!=0){
                    array[indexResult]= numbers.get(n).remove(0);
                    indexResult++;
                }
            }

        }

        return array;
    }

}
