package com.osk;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        B b = new B();
        System.out.println(b.getRoot().getClass());
        System.out.println(b.hashCode());

        C c = new C();

        System.out.println(finallyTest());
    }

    void test(int ...i) {

    }

    public static int finallyTest() {
        try {
            throw new RuntimeException();
        } catch (Exception e) {
            return 1;
        } finally {
            return 2;
        }
    }

    static long fakt(int n) {
        if(n == 0) {
            return 1;
        } else {
            return n * fakt(n-1);
        }
    }

    static BigInteger faktNR(int n) {
        BigInteger res = new BigInteger("1");
        while(n >= 0) {
            if(n==0) {
                res = res.multiply(BigInteger.valueOf(1));
            } else {
                res = res.multiply(BigInteger.valueOf(n));
            }
            n--;
        }
        return res;
    }
}

class C {

    {
        System.out.println("instance block");
    }

    static {
        System.out.println("static block");
    }

    public C() {
        System.out.println("Constructor");
    }
}

abstract class ARoot {}

class BRoot extends ARoot {}

abstract class A {
    protected ARoot root;

    public ARoot getRoot() {
        return root;
    }
}

class B extends A {
//    public BRoot root;

    public B() {
        root = new BRoot();
    }

    @Override
    public BRoot getRoot() {
        return (BRoot) root;
    }

    public static void main(String[] args) {
        B b = new B();
        System.out.println(b.getRoot().getClass());
    }
}

