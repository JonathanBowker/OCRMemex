package com.hyperiongray.ocr;

import static org.junit.Assert.*;

public class MainTest {

    @org.junit.Test
    public void testMain() throws Exception {
        String args[] = new String[2];
        args[0] = "-f";
        args[1] = "test-data/cca_sample.txt";
        Main.main(args);
    }
}