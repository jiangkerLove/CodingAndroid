package jfp.study.calculate;

import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class CollectTest {

    class Node{
        public int size = 4;
    }

    @Test
    public void test(){
        Stack stack = new Stack<Integer>();
        Queue<String> queue = new LinkedList<>();
        Node node = new Node();
        for (int i = 0;i < node.size;i++){
            node.size --;
            System.out.println(i);
        }
        HashSet<Integer> set = new HashSet<Integer>();
    }
}
