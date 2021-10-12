package cn.magikarpll.javaStudy.codebook.a1_string;

/**
 * 给定一长字符串a和一短字符串b
 * 请问，如何最快地判断出短字符串b中的所有字符是否都在长字符串a中？请编写函数bool StringContain(string &a, string &b) 实现此功能。
 * 为简单起见，假设输入的字符串只包含大写英文字母。下面举几个例子。
 * •如果字符串a是"ABCD”，字符串b是”BAD”，答案是true,因为字符串b 中的字母都在字符串a中，或者说b是a的真子集。
 * •如果字符串a是"ABCD",字符串b是”BCE",答案是false,因为字符串b 中的字母E不在字符串a中。
 * •如果字符串a是”ABCD”，字符串b是”AA”，答案是true,因为字符串b中 的字母A包含在字符串a中。
 */
public class A2_StringContain {

    public static void main(String[] args) {
        String paramA = "ABCDEFG";
        String paramB = "AACCDD";

        String paramNo = "ACRRTT";
        System.out.println("参数A是否包含参数B: " + stringContain(paramA, paramB));
        System.out.println("参数A是否包含参数No: " + stringContain(paramA, paramNo));

        String param10 = "ABBAC";
        String param20 = "AACBB";
        String param21 = "AACB";
        System.out.println("参数param20是否是参数param10的兄弟字符串: " + stringBrother(param10, param20));
        System.out.println("参数param21是否是参数param10的兄弟字符串: " + stringBrother(param10, param21));


    }

    public static Boolean stringContain(String a, String b){
        int hash = 0;
        for(char c: a.toCharArray()){
            hash = hash |(1 << (c - 'A'));
        }
        for(char c2: b.toCharArray()){
            if((hash & (1 << (c2 - 'A'))) <= 0){
                return false;
            }
        }
        return true;
    }

    public static Boolean stringBrother(String a, String b){
        int[] array = new int[26];
        for(char c: a.toCharArray()){
            int index = c - 'A';
            array[index]++;
        }
        for(char c2: b.toCharArray()){
            int index = c2 - 'A';
            array[index]--;
        }
        for(int i: array){
            if(i != 0){
                return false;
            }
        }
        return true;
    }


}
