package cn.magikarpll.javaStudy.codebook.a1_string;

/**
 * 给定一个字符串，要求将字符串前面的若干个字符移到字符串的尾部。
 * 例如， 将字符串"abcdef“的前3个字符’a’、’b‘和’c'移到字符串的尾部，那么原字符串将变成 ”defabc"。请写一个函数实现此功能
 */
public class A1_ReverseString {

    public static void main(String[] args) {
        String param = "abcdef";
        System.out.println("原字符为: " + param +";将前4个字符挪到后面去;挪动后字符为: " + answer1(param, 4));

        String sentence = "I am a student.";
        System.out.println("原句子为: " + sentence +"旋转后字符为: " + rotateSentence(sentence));
    }

    public static String answer1(String param, int n){
        param = rotateString(param,0,n-1);
        param = rotateString(param,n,param.length()-1);
        param = rotateString(param,0,param.length()-1);
        return param;
    }

    private static String rotateString(String param, int start, int end){
        char[] paramArray = param.toCharArray();
        while (start < end){
            char temp = paramArray[start];
            paramArray[start] = paramArray[end];
            paramArray[end] = temp;
            start++;
            end--;
        }
        return String.valueOf(paramArray);
    }

    /**
     * 将一个英文句子 I am a student.  翻转成 student. a am I
     * 即翻转句子中单词的顺序，但是单词内字符的顺序不变，句子中单词是以空格来隔开的，标点符号和普通字母一样处理。
     *
     * @param sentence
     * @return
     */
    public static String rotateSentence(String sentence){
        char[] sentenceArray = sentence.toCharArray();
        int lastIndex = 0;
        for(int i = 0; i < sentenceArray.length; i++){
            if(Character.isSpaceChar(sentenceArray[i])){
                rotateCharArray(sentenceArray, lastIndex, i-1);
                lastIndex = i+1;
            }
        }
        rotateCharArray(sentenceArray, lastIndex, sentenceArray.length-1);
        rotateCharArray(sentenceArray, 0, sentenceArray.length-1);
        return String.valueOf(sentenceArray);
    }

    private static void rotateCharArray(char[] param, int start, int end){
        while (start < end){
            char temp = param[start];
            param[start] = param[end];
            param[end] = temp;
            start++;
            end--;
        }
    }


}
