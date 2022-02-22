package com.osk;

/**
 * Created by Kirill on 14.07.2020.
 */
public class Interrupt {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new My());
        t.start();
        t.interrupt();


        System.out.println(t.isInterrupted());

    }
}

class My implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
