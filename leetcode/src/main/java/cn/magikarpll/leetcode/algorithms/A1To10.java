package cn.magikarpll.leetcode.algorithms;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class A1To10 {

    /**
     * leetcode算法第一题:
     给定一个整数数组 nums和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那两个整数，并返回它们的数组下标。
     你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
     你可以按任意顺序返回答案。

     输入：nums = [2,7,11,15], target = 9
     输出：[0,1]
     解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。

     */
    public int[] a1_twoSum_sort1(int[] nums, int target){
        int[] result = new int[2];
        for(int i = 0; i < nums.length -1; i++){
            for(int j = i+1; j < nums.length; j++){
                if(nums[i] + nums[j] == target){
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return result;
    }

    public int[] a1_twoSum_sort2(int[] nums, int target){
        int[] result = new int[2];
        HashMap<Integer, Integer> temp = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            if(temp.containsKey(nums[i])){
                if(nums[i]*2 == target){
                    result[0] = temp.get(nums[i]);
                    result[1] = i;
                    return result;
                }
            }else{
                temp.put(nums[i],i);
            }
        }
        for(int j = 0; j < nums.length -1; j++){
            temp.remove(nums[j]);
            if(temp.containsKey(target - nums[j])){
                result[0] = j;
                result[1] = temp.get(target - nums[j]);
                return result;
            }
        }
        return result;
    }

    /**
     * 最优解 hashmap 单次循环解决
     * @param nums
     * @param target
     * @return
     */
    public int[] a1_twoSum_sort3(int[] nums, int target){
        HashMap<Integer,Integer> temp = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            if(temp.containsKey(target - nums[i])){
                return new int[]{temp.get(target - nums[i]), i};
            }
            temp.put(nums[i],i);
        }
        return new int[0];
    }




}
