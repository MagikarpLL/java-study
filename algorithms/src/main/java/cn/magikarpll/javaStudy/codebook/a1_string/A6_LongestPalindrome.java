package cn.magikarpll.javaStudy.codebook.a1_string;

/**
 * 给点一个字符串，求它的最长回文子串长度
 */
public class A6_LongestPalindrome {

    public static void main(String[] args) {
        longestPalindrome1("abaecdabdba".toCharArray());
    }

    /**
     * 解法一， 中心扩展法
     */
    public static void longestPalindrome1(char[] chars) {
        int length = chars.length;
        int max = 0, index = 0;
        for (int i = 0; i < length; i++) {
            //奇数回文串
            for (int j = 0; (i - j) >= 0 && (i + j) < length; j++) {
                if (chars[i - j] != chars[i + j]) {
                    break;
                } else {
                    int temp = 2 * j + 1;
                    if (temp > max) {
                        max = temp;
                        index = i;
                    }
                }
            }
            //偶数回文串
            for (int j = 0; (i - j) >= 0 && (i + j + 1) < length; j++) {
                if (chars[i - j] != chars[i + j + 1]) {
                    break;
                } else {
                    int temp = 2 * j + 2;
                    if (temp > max) {
                        max = temp;
                        index = i;
                    }
                }
            }
        }
        System.out.println("最长回文串长度为: " + max + "位置为: " + index);
    }

    /**
     * 将一个很长的字符串分割成一段一段的子串，要求子串都是回文串。有回文串 就输出最长的，没有回文串就将字符一个个输出。例如，如果输入"habbafgh",则输
     * 出"h, abba, f, g, h"。
     *
     * 简单解法就是第一次遍历，找出所有回文串，然后第二次根据第一次结果来输出
     */
    public static void longestPalindrome2(char[] chars) {

    }

}
