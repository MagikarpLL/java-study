package cn.magikarpll.javaStudy.algorithms.aSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class A1_Sort_answer {

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
//        System.out.println(Arrays.toString(unsortedArray));
//        int[] results6 = QuickSort(unsortedArray, 0, unsortedArray.length - 1);
//        System.out.println(Arrays.toString(results6));

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
        System.out.println(Arrays.toString(unsortedArray));
        int[] results10 = RadixSort(unsortedArray);
        System.out.println(Arrays.toString(results10));

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
        if(array.length < 2){
            return array;
        }
        for(int i = array.length;i > 1; i--){
            for(int j = 0; j < i-1; j++){
                if(array[j] > array[j+1]){
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
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
        if(array.length < 2){
            return array;
        }
        for(int i = 0; i< array.length; i++){
            //这里容易错，我之前的错误写法是下面那种
            //int minIndex = 0;
            int minIndex = i;
            for(int j = i; j < array.length; j++){
                if(array[j] < array[minIndex]){
                    minIndex = j;
                }
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
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
        if(array.length < 2){
            return array;
        }
        for(int i = 1; i < array.length; i++){
            int temp = array[i];
            int index = i;
            while(index > 0 && array[index-1]>temp){
                array[index] = array[index-1];
                index--;
            }
            if(index != i){
                array[index] = temp;
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
        int gap = array.length;
        while(gap>0){
            for(int i = gap ;i < array.length; i++){
                int temp = array[i];
                int index = i;
                while(index >= gap && array[index-gap] > temp){
                    array[index] = array[index-gap];
                    index = index - gap;
                }
                array[index] = temp;
            }
            gap = gap/2;
        }
        return array;
    }

    /**
     * 归并排序
     *
     * @param array
     * @return
     */
    public static int[] MergeSort(int[] array){
        if(array.length<2){
            return array;
        }
        int mid = array.length / 2;
        int[] leftArray = Arrays.copyOfRange(array, 0, mid);
        int[] rightArray = Arrays.copyOfRange(array,mid,array.length);
        leftArray = MergeSort(leftArray);
        rightArray = MergeSort(rightArray);
        return merge(leftArray, rightArray);
    }

    private static int[] merge(int[] left, int[] right) {
        int leftIndex=0, rightIndex=0,resultIndex = 0;
        int resultLength = left.length + right.length;
        int[] results = new int[resultLength];
        while(resultIndex < resultLength){
            if(leftIndex >= left.length){
                results[resultIndex++] = right[rightIndex++];
                continue;
            }
            if(rightIndex >= right.length){
                results[resultIndex++] = left[leftIndex++];
                continue;
            }
            if(left[leftIndex] > right[rightIndex]){
                results[resultIndex++] = right[rightIndex++];
            }else{
                results[resultIndex++] = left[leftIndex++];
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
    public static int[] QuickSort(int[] array, int start, int end){
        if(array.length < 2 || start < 0 || end < 0|| start >= end){
            return array;
        }
        int index = partition(array, start, end);
        System.out.println(start + ":" + index + ":" + end);
        QuickSort(array, start, index-1);
        QuickSort(array, index+1, end);
        return array;
    }

    private static int partition(int[] array, int start, int end){
        int temp = array[start];
        swapItems(array, start, end);
        int index = start, minIndex = start-1;
        while(index <= end){
            if(array[index] <= temp){
                minIndex++;
                if(index > minIndex){
                    swapItems(array, minIndex, index);
                }
            }
            index++;
        }
        return minIndex;
    }

    private static void swapItems(int[] array, int source, int target){
        int temp = array[source];
        array[source] = array[target];
        array[target] = temp;
    }

    /**
     * 堆排序算法
     *
     * @param array
     * @return
     */
    private static int len = 0;

    public static int[] HeapSort(int[] array){
        if(array.length < 2) return array;
        len = array.length-1;
        buildMaxHeap(array);
        while(len > 0){
            swapHeap(array, 0, len);
            len--;
            adjustHeap(array, 0);
        }
        return array;
    }

    private static void buildMaxHeap(int[] array){
        for(int i = array.length/2 - 1; i>=0; i--){
            adjustHeap(array, i);
        }
    }

    private static void adjustHeap(int[] array, int head){
        int maxIndex = head;
        if(2*head + 1 <= len && array[2*head+1] > array[maxIndex]){
            maxIndex = 2*head + 1;
        }
        if(2*head + 2 <= len && array[2*head + 2] > array[maxIndex]){
            maxIndex = 2*head + 2;
        }
        if(maxIndex != head){
            swapHeap(array,head, maxIndex);
            adjustHeap(array, maxIndex);
        }
    }

    private static void swapHeap(int[] array, int source, int target){
        int temp = array[source];
        array[source] = array[target];
        array[target] = temp;
    }

    /**
     * 计数排序
     *
     * @param array
     * @return
     */
    public static int[] CountingSort(int[] array) {
        if(array.length < 2) return array;
        //找到最大值
        int min = array[0], max = array[0];
        for(int i: array){
            if(i < min){
                min = i;
                continue;
            }
            if(i > max){
                max = i;
            }
        }

        int[] countArray = new int[max - min + 1];
        //方便数组中最小的数，刚好映射在0的位置上
        int gap = 0 - min;
        for(int i = 0;i < array.length; i++){
            countArray[array[i]+gap]++;
        }
        //最后遍历这个统计数组取出数据
        for(int j = 0,index = 0; j < countArray.length; j++){
            while(countArray[j]-- > 0){
                array[index++] = j - gap;
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
        if(array.length < 2) return array;
        //找出最大值
        int max = array[0],min = array[0];
        for(int i: array){
            if(i < min){
                min = i;
                continue;
            }
            if(i > max) max = i;
        }
        //算出桶的个数和范围，并初始化桶
        //这里默认范围是按照数组的最大值-最小值除以数组的长度-1，但是我感觉这样不行，没什么效率，应该根据实际数据集的分布情况选用不同的桶长度和区间
        int bucketSize = array.length / 2;
        int gap = (max -min)/bucketSize + 1;
        ArrayList<ArrayList<Integer>> buckets = new ArrayList<ArrayList<Integer>>();
        for(int b = 0; b < bucketSize; b++){
            buckets.add(new ArrayList<Integer>());
        }

        //遍历原来的数组将数据放入桶内
        for(int a = 0; a < array.length; a++){
            int index = (array[a] - min)/gap;
            buckets.get(index).add(array[a]);
        }

        //对每个桶进行排序
        for(ArrayList<Integer> bucket: buckets){
            Collections.sort(bucket);
        }

        //最后遍历桶取出数据
        for(int bN = 0,index = 0; bN < buckets.size(); bN++){
            while(buckets.get(bN).size() > 0){
                array[index++] = buckets.get(bN).remove(0);
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
        if(array.length < 2) return array;
        //算出最大位数
        int max = array[0],maxRadix = 0;
        for(int number: array){
            if(number > max) max = number;
        }
        while(max > 0){
            max = max / 10;
            maxRadix++;
        }

        //构造桶数组
        ArrayList<ArrayList<Integer>> buckets = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < 10; i++){
            buckets.add(new ArrayList<Integer>());
        }

        //进行按位循环
        int mod = 10, div = 1;
        while(maxRadix > 0){
            //将数字放入桶
            for(int j = 0; j < array.length; j++){
                int currentNumber = (array[j]/div)%mod;
                buckets.get(currentNumber).add(array[j]);
            }
            //遍历取回
            int arrayIndex = 0;
            for(int m = 0; m < buckets.size(); m++){
                while(buckets.get(m).size()>0){
                    array[arrayIndex++] = buckets.get(m).remove(0);
                }
            }
            div = div * 10;
            maxRadix--;
        }
        return array;
    }

}
