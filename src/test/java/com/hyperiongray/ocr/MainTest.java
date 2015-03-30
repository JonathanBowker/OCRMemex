package com.hyperiongray.ocr;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {


    //@Test
    public void testMainZipThrough() throws Exception {
        String args[] = new String[3];
        args[0] = "-f";
        args[1] = "test-data/cca_sample.txt";
        args[2] = "-z";
        Main.main(args);
    }

    //@Test
    public void testMainWithOCR() throws Exception {
        String args[] = new String[4];
        args[0] = "-f";
        args[1] = "test-data/cca_sample.txt";
        args[2] = "-o";
        args[3] = "test-output/ocr-d.json";
        Main.main(args);
    }

    @Test
    public void testMainPureImagesWithOCR() throws Exception {
        String args[] = new String[4];
        args[0] = "-f";
        args[1] = "test-data/bhw-images.jl.head.json";
        args[2] = "-o";
        args[3] = "test-output/ocr-e.json";
        Main.main(args);
    }

}