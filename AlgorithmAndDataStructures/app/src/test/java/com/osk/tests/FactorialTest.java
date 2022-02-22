package com.osk.tests;

import com.osk.factors.Factorial;
import com.osk.factors.FactorialNonRecursive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FactorialTest {

    @Test
    public void testFact() {
        Factorial f = new Factorial();

        assertEquals(1, f.fact(0));
        assertEquals(6, f.fact(3));
        assertEquals(24, f.fact(4));
        assertEquals(120, f.fact(5));
        assertEquals(720, f.fact(6));

    }

    @Test
    public void testFactNR() {
        Factorial f = new FactorialNonRecursive();

        assertEquals(1, f.fact(0));
        assertEquals(6, f.fact(3));
        assertEquals(24, f.fact(4));
        assertEquals(120, f.fact(5));
        assertEquals(720, f.fact(6));

    }

}
