package com.hyperiongray.ocr;

import com.hyperiongray.ocr.db.MongoDbOp;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.io.Files;
import java.io.File;


import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import org.apache.commons.cli.ParseException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static Options options;
    private String keyPhrasesFile;
    private List<String> keyPhrases;

    public static void main(String args[]) {
        formOptions();
        if (args.length == 0) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Ranker - rank crawled pages in MongoDB for display, using Lucene search ranking", options);
            return;
        }
        // TODO parse and use options
        Main main = new Main();
        try {
            main.parseParameters(args);
            main.readKeyPhrases();
            main.createIndex(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void formOptions() {
        options = new Options();
        options.addOption("k", "keyphrases", true, "Keyphrases file");
    }

    private void createIndex(String args[]) throws Exception {
        MongoDbOp.main(args);
    }

    private void readKeyPhrases() throws IOException {
        keyPhrases = Files.readLines(new File(keyPhrasesFile), Charset.defaultCharset());
    }
    private void parseParameters(String[] args) throws ParseException {
        CommandLineParser parser = new GnuParser();
        CommandLine cmd = parser.parse(options, args);
        keyPhrasesFile = cmd.getOptionValue("keyphrases").trim();
    }
}

