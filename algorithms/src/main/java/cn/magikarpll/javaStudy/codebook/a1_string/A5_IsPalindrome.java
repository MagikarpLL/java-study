package cn.magikarpll.javaStudy.codebook.a1_string;

/**
 * 给定一个字符串，如何判断这个字符串是否是回文串？
 * 所谓回文串，是指正读和反读都一样的字符串，如madam、我爱我。那么，如 何通过程序判断一个字符串是否是回文串呢？
 *
 * 这个太简单了，直接两边往中间扫  或者中间往两边扫；  这个题不做；
 *
 * 换一个难度更大的：  a1.如何判断一条单向链表是不是回文?  a2.判断一个栈是不是回文?
 *
 * a1解法: https://www.cnblogs.com/HorribleMe/p/4878833.html
 *
 * a2解法:先压入一次，再取出一次，看两次结果是不是完全相同，是则为回文串
 *
 */
public class A5_IsPalindrome {

    public static void main(String[] args) {
        //构造一个 abcba的回文串
        LinkedNode p_a5 = new LinkedNode('a', null);
        LinkedNode p_b4 = new LinkedNode('b', p_a5);
        LinkedNode p_c3 = new LinkedNode('c', p_b4);
        LinkedNode p_b2 = new LinkedNode('b', p_c3);
        LinkedNode p_a1 = new LinkedNode('a', p_b2);

        System.out.println("abcba:" + palindromeLikedList(p_a1));

        //再构造一个abccab的回文串
        LinkedNode np_b6 = new LinkedNode('b', null);
        LinkedNode np_a5 = new LinkedNode('a', np_b6);
        LinkedNode np_c4 = new LinkedNode('c', np_a5);
        LinkedNode np_c3 = new LinkedNode('c', np_c4);
        LinkedNode np_b2 = new LinkedNode('b', np_c3);
        LinkedNode np_a1 = new LinkedNode('a', np_b2);

        System.out.println("abccab:" + palindromeLikedList(np_a1));

    }

    /**
     * 判断单向链表是否为回文串
     * @param start
     */
    public static boolean palindromeLikedList(LinkedNode start){
        if(start == null){
            return false;
        }
        int size = 1;
        LinkedNode front = start;
        LinkedNode back = start;
        LinkedNode temp = null;
        while (start.getNext() != null){
            start = start.getNext();
            size++;
        }

        if(size == 1){
            return true;
        }

        //将指针遍历到对应位置
        int index = 1;
        while (index <=  size/2){
            back = back.getNext();
            front.setNext(temp);
            temp = front;
            front = back;
            index++;
        }
        // = back.getNext();
        front = temp;
        if(size % 2 == 1){
            back = back.getNext();
        }

        //从中间往两边遍历
        for(;;){
            if(front.getNext() == null && back.getNext() == null){
                return true;
            }
            if((front.getNext() == null && back.getNext() != null) || (front.getNext() != null && back.getNext() == null)){
                return false;
            }
            if(front.getData().equals(back.getData())){
                front = front.getNext();
                back = back.getNext();
            }else{
                return false;
            }
        }
    }

}

class LinkedNode{

    private Character data;

    private LinkedNode next;

    public LinkedNode(Character data, LinkedNode next){
        this.data = data;
        this.next = next;
    }

    public Character getData() {
        return data;
    }

    public void setData(Character data) {
        this.data = data;
    }

    public LinkedNode getNext() {
        return next;
    }

    public void setNext(LinkedNode next) {
        this.next = next;
    }
}