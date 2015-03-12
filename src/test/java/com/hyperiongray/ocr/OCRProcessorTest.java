package com.hyperiongray.ocr;

import junit.framework.TestCase;


import java.io.File;
import java.io.IOException;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class OCRProcessorTest extends TestCase {

    //@Test
    public void testGetImageText() {
        OCRConfiguration conf = new OCRConfiguration();
        conf.setPdfImageExtractionDir("test-data/ocr/out/");
        conf.setTesseractWorkDir("test-data/ocr/out/");

        File f = new File("test-data/ocr/out");
        f.mkdirs();
        long start = System.currentTimeMillis();

        OCRProcessor processor = OCRProcessor.createProcessor(conf);
        List<String> data = processor.getImageText("test-data/516.pdf");

        long end = System.currentTimeMillis();

        assertEquals(4, data.size());

        double match = 100;
        try {
            match = OCRUtil.compareText(data.get(0), OCRUtil.readFileContent("test-data/516.txt"));
        } catch (IOException e) {
            e.printStackTrace(System.out);
            fail("Unexpected exception");
        }

        System.out.println("516.pdf = Time: " + (end - start));
        System.out.println("516.pdf = Words matching: " + match);

        start = System.currentTimeMillis();

        data = processor.getImageText("test-data/02-loose-files/docs/ocr/testb.pdf");

        end = System.currentTimeMillis();

        assertEquals(2, data.size());

        double match1 = 100;
        double match2 = 100;
        try {
            match1 = OCRUtil.compareText(data.get(0), OCRUtil.readFileContent("test-data/02-loose-files/docs/ocr/testb_1.txt"));
            match2 = OCRUtil.compareText(data.get(1), OCRUtil.readFileContent("test-data/02-loose-files/docs/ocr/testb_2.txt"));
        } catch (IOException e) {
            e.printStackTrace(System.out);
            fail("Unexpected exception");
        }

        System.out.println("testb.pdf = Time: " + (end - start));
        System.out.println("testb.pdf 1 = Words matching: " + match1);
        System.out.println("testb.pdf 2 = Words matching: " + match2);
    }
}