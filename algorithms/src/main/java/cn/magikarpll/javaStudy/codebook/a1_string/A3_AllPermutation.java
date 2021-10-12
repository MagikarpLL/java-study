package cn.magikarpll.javaStudy.codebook.a1_string;

import java.util.LinkedList;

/**
 * 输入一个字符串，打印出该字符串中字符的所有排列。例如，输入字符串"abc”，
 * 则输出由字符’a’、'b'、’c’所能排列出来的所有字符串”abc”、”acb”、”bac”、”bca"、"cab”和”cba”
 *
 * 字符串的全排列和全组合算法:  https://blog.csdn.net/x526967803/article/details/77718434
 *
 */
public class A3_AllPermutation {

    public static void main(String[] args) {
        String param = "abcdefg";
//        answerAllPermutation1(param.toCharArray(), 0, param.length()-1);
        //allSort(param.toCharArray(),new char[param.length()],0);
        //allCom(param);
        printCom(param);
    }

    /**
     * 解法一，递归
     */
    public static void answerAllPermutation1(char[] params, int start, int end){

        if(start > end){
            return;
        }

        if(end == start){
            System.out.println(String.valueOf(params));
        }else{
            for(int i = start; i <= end; i++){
                swapCharArray(params, i, start);
                answerAllPermutation1(params, start+1, end);
                swapCharArray(params, start, i);
            }
        }

    }

    private static void swapCharArray(char[] params, int source, int target){
        char temp = params[source];
        params[source] = params[target];
        params[target] = temp;
    }

    /**
     * 字典序的所有排列
     * 已知字符串中的字符是互不相同的，现在把它们任意排列(例如，若己知字符串是”ab”，
     * 则任意排列是"aa”、”ab"、"ba”和”bb",编程按照字典序输出所有的组合
     */
    public static void allSort(char[] words, char[] output, int size){
        if(size == words.length){
            System.out.println(String.valueOf(output));
            return;
        }

        for(int i = 0; i < words.length; i++){
            output[size] = words[i];
            allSort(words, output, size+1);
        }
    }

    /**
     * 如果不是求字符串中所有字符的所有排列，而是求字符的所有组合，应该怎么办呢？
     * 当输入的字符串中含有相同的字符时，相同的字符交换位置是不同的排列，但却是同一个组合。举个例子，如果输入”abc”，它的组合有”a"、”b”、"c”、”ab”、
     * ”ac”、"be”和”abc”
     */
    public static void allCom(String params){
        char[] charArray = params.toCharArray();
        LinkedList<Character> output = new LinkedList<Character>();
        for(int i = 1; i <= params.length(); i++){
            com(charArray, i, output, 0);
        }
        return;
    }

    private static void com(char[] params, int num, LinkedList<Character> output, int start){
        if(num < 0 || params.length - start < num){
            return;
        }
        if(0 == num){
            for(Character c: output){
                System.out.print(c);
            }
            System.out.println();
            return;
        }

        //输出该元素
        output.addLast(params[start]);
        com(params, num-1, output, start+1);
        output.removeLast();

        //不输出该元素
        com(params, num, output, start+1);
    }

    /**
     * 写一个程序打印如下序列
     * @param params
     */
    public static void printCom(String params){
        char[] charArray = params.toCharArray();
        LinkedList<Character> output = new LinkedList<Character>();
        for(int i = 1; i <= params.length(); i++){
            print(charArray, i, output, 0);
            System.out.println();
        }
        return;
    }

    private static void print(char[] params, int num, LinkedList<Character> output, int start){
        if(num < 0 || params.length - start < num){
            return;
        }
        if(0 == num){
            System.out.print("(");
            for(int i = 0; i < output.size(); i++){
                System.out.print(output.get(i));
                if(i!=output.size()-1){
                    System.out.print(",");
                }
            }
            System.out.print(")");
            if(start != params.length){
                System.out.print(",");
            }
            return;
        }

        //输出该元素
        output.addLast(params[start]);
        print(params, num-1, output, start+1);
        output.removeLast();

        //不输出该元素
        print(params, num, output, start+1);
    }

}
