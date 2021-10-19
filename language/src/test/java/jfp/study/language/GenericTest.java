package jfp.study.language;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GenericTest {

    static class A{}

    static class B extends A{}

    @Test
    public void superGenericTest(){
        /*
         * 表示所有B类型及其父类型的List都可以转换过来，但是只能存入B类型的子类元素了
         * 因为这样原来的数组取出元素可以保证元素都是A以及A的子类。
         * 对应的kotlin的in
         */
        List<? super B> listIn = new ArrayList<B>();
        listIn.add(new B());
        List<A> listA = new ArrayList<>();
        listIn = listA;
        listIn.add(new B());

        Object objIn = listIn.get(0);
        // 可以保证取出的元素依然是A的子类
        A aIn = listA.get(0);
    }

    @Test
    public void extendsGenericTest(){

        /*
         * 表示A类型及其子类型的List都可以转换过来，但是只可以取出都为A类型
         * 对应的是kotlin的out
         */
        List<? extends A> listOut = new ArrayList<A>();

        // 可以保证取出的元素依然是A的子类
        listOut = new ArrayList<B>();

        A a = listOut.get(0);

        listOut.add(null);
    }

    @Test
    public void unknownGenericTest(){
        // 会有警告
        List listObject = new ArrayList<A>();
        listObject.add("1");

        // 不能这样写
        // List<Object> list = new ArrayList<A>();

        List<?> listUnknown = new ArrayList<A>();
        // 不能正常添加元素，只能加入null
        listUnknown.add(null);
        listUnknown.remove(1);
        for (Object o : listUnknown) {

        }
    }

}
