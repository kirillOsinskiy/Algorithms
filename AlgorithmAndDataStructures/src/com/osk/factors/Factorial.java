package com.osk.factors;

public class Factorial {

    public int fact(int n) {
        return fact(n, 1);
    }

    int fact(int n, int x) {
        if(n == 0) return x;
        return fact(n - 1, n * x);
    }
}

