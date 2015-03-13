package com.hyperiongray.ocr;

import com.hyperiongray.ocr.com.hyperiongray.data.CCAJasonParser;
import com.hyperiongray.ocr.com.hyperiongray.util.PlatformUtil;
import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.io.Files;
import java.io.File;


import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import org.apache.commons.cli.ParseException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static Options options;
    private String ccaInputFile;

    public static void main(String args[]) {
        formOptions();
        if (args.length == 0) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("MemexOCR - downloads and OCR's images in a CCA file", options);
            return;
        }
        // TODO parse and use options
        Main main = new Main();
        try {
            main.parseParameters(args);
            main.downloadAndOCR();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void formOptions() {
        options = new Options();
        options.addOption("f", "file", true, "Input CCA file");
    }


    private void downloadAndOCR() throws IOException {
        // read lines one at a time
        LineIterator it = FileUtils.lineIterator(new File(ccaInputFile), "UTF-8");
        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                String urls[] = new CCAJasonParser().getImageUrls(line);
                if (urls != null) {
                    for (String url: urls) {
                        System.out.println(url);

                        OCRConfiguration conf = new OCRConfiguration();
                        conf.setPdfImageExtractionDir("test-output/ocr/out/");
                        conf.setTesseractWorkDir("test-output/ocr/out/");

                        File f = new File("test-output/ocr/out");
                        f.mkdirs();
                        PlatformUtil.runCommand("wget -P test-output " + url);
                        OCRProcessor processor = OCRProcessor.createProcessor(conf);
                        String fileName = new URL(url).getFile().substring(1);
                        List<String> data = processor.getImageText("test-output/" + fileName);
                        System.out.println(data.get(0));
                    }
                }

            }
        } finally {
            it.close();
        }
    }
    private void parseParameters(String[] args) throws ParseException {
        CommandLineParser parser = new GnuParser();
        CommandLine cmd = parser.parse(options, args);
        ccaInputFile = cmd.getOptionValue("file").trim();
    }
}

