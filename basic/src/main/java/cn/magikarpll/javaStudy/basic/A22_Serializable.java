package cn.magikarpll.javaStudy.basic;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class A22_Serializable implements Serializable {

    public static void main(String[] args) {

    }

    @Test
    public void test3() throws IOException, ClassNotFoundException {
        C c = new C();
        c.i = 1;
        c.j = 2;
        c.s = "a";
        //将obj写入文件
        FileOutputStream fileOutputStream = new FileOutputStream("temp");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(c);
        fileOutputStream.close();
        //通过文件读取obj
        FileInputStream fileInputStream = new FileInputStream("temp");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        C c2 = (C) objectInputStream.readObject();
        fileInputStream.close();
        System.out.println(c2.i);
        System.out.println(c2.j);
        System.out.println(c2.s);
        //打印结果为1 2和a，即赋值
    }

    @Test
    public void writeArrayList() throws IOException, ClassNotFoundException {
        ArrayList list = new ArrayList();
        list.add("a");
        list.add("b");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("arr"));
        objectOutputStream.writeObject(list);
        objectOutputStream.close();
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("arr"));
        ArrayList list1 = (ArrayList) objectInputStream.readObject();
        objectInputStream.close();
        System.out.println(Arrays.toString(list.toArray()));
        //序列化成功，里面的元素保持不变。
    }

}

class Test_Externalizable implements Externalizable {

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }
}

class C implements Externalizable {
    int i;
    int j;
    String s;
    public C() {

    }
    //实现下面两个方法可以选择序列化中需要被复制的成员。
    //并且，写入顺序和读取顺序要一致，否则报错。
    //可以写入多个同类型变量，顺序保持一致即可。
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(i);
        out.writeInt(j);
        out.writeObject(s);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        i = in.readInt();
        j = in.readInt();
        s = (String) in.readObject();
    }
}

