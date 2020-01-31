package com.osk.factors;

public class FactorialNonRecursive extends Factorial {

    @Override
    int fact(int n, int x) {
        while(true) {
            if(n == 0) return x;
            x *= n--;
        }
    }
}
