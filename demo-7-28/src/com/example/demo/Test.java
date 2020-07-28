package com.example.demo;

/**
 * Created By shelli On 2020/7/28 10:33
 */
public class Test {
    static Object lock1 = new Object();
    static Object lock2 = new Object();

    public static void main(String[] args) {
        Runnable runnable1 = () -> {
            synchronized (lock1) {
                try {
                    System.out.println("线程：" + Thread.currentThread().getName() + "已获得锁" + lock1);
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("线程：" + Thread.currentThread().getName() + "等待获得锁" + lock2);
                synchronized (lock2) {
                    System.out.println("线程：" + Thread.currentThread().getName() + "已获得锁" + lock2);
                }
            }
        };
        Runnable runnable2 = () -> {
            synchronized (lock2) {
                try {
                    System.out.println("线程：" + Thread.currentThread().getName() + "已获得锁" + lock2);
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("线程：" + Thread.currentThread().getName() + "等待获得锁" + lock1);
                synchronized (lock1) {
                    System.out.println("线程：" + Thread.currentThread().getName() + "已获得锁" + lock1);
                }
            }
        };
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        thread1.start();
        thread2.start();
    }
}
