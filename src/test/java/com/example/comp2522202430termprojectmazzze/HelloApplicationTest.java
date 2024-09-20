package com.example.comp2522202430termprojectmazzze;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloApplicationTest {

    @Test
    public void testHello(){
        HelloApplication tester = new HelloApplication();
        int result = tester.sumNumbers(3,5);
        assertEquals(8,result);
    }

}