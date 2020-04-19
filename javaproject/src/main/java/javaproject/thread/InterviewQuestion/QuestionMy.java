package javaproject.thread.InterviewQuestion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 写一个固定容量的同步容器，拥有put和get方法，以及getcount方法
 * 能够支持2个生产者线程和10个消费者线程的阻塞调用
 *
 * 使用wait，notify，notifyall完成  wait会释放锁，notify不会释放锁
 */
public class QuestionMy {

    static class Container{
        volatile private LinkedList<String> containerlist = new LinkedList<>();
        volatile private Integer size = 10;

        public synchronized void put(){
            while(containerlist.size()>=size){
                try {
                    System.out.println("容器的容量满了："+containerlist.size());
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            containerlist.add(String.valueOf(Math.random()*Integer.MAX_VALUE));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getcount());
        }

        public synchronized String get(){
            String result = "";
            while(containerlist.size()>0){
                if(containerlist.size()>0){
                    result = containerlist.get(0);
                    containerlist.remove(0);
                }
                System.out.println(getcount());

            }
            return result;
        }

        public synchronized Integer getcount(){
            return containerlist.size();
        }
    }

    public static void main(String[] args) {
        List<Thread> producerlist = new ArrayList<>();
        List<Thread> consumerlist = new ArrayList<>();

        Container producer_container = new Container();
        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(() -> producer_container.put());
            producerlist.add(thread);
        }

        Container consumer_container = new Container();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> consumer_container.get());
            consumerlist.add(thread);
        }

        producerlist.forEach(o->o.start());

    }

}
