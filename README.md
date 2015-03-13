MemexOCR
=======

## Capabilities

* command-line utility to download images and OCR them

#### To print documentation

     java -classpath target/OCRMemex-1.0-SNAPSHOT-jar-with-dependencies.jar com.hyperiongray.ocr.Main

#### To run as a stand-alone application

     java -classpath target/OCRMemex-1.0-SNAPSHOT-jar-with-dependencies.jar com.hyperiongray.ocr.Main -f test-data/cca_sample.txt -z

     java -classpath target/OCRMemex-1.0-SNAPSHOT-jar-with-dependencies.jar com.hyperiongray.ocr.Main -f test-data/cca_sample.txt -o test-output/ocr-d.json
