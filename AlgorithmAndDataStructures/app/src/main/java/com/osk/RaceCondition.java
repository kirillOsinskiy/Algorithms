package com.osk;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Kirill on 13.07.2020.
 */
public class RaceCondition {

    static AtomicInteger cnt = new AtomicInteger(0);
    static int num = 0;
    static Object lock = new Object();

    public static void main(String[] args) {
        Runnable r = () -> {
            for (int i = 0; i < 10; i++) {
                synchronized (lock) {
                    int a = num;
                    a = a + 1;
                    num = a;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(num);
    }
}
