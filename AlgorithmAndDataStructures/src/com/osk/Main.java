package com.osk;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
//	    PersistentStack<String> stack = new PersistentStack<>();
//	    stack.push("one");
//        stack.push("two");
//        stack.push("three");
//        stack.push("asdfg");
//        stack.push("qwe");
//        System.out.println(stack);

        System.out.println(faktNR(3));
        System.out.println(faktNR(10));
        System.out.println(faktNR(Integer.MAX_VALUE));
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

