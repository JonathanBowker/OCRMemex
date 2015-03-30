#!/bin/sh
# to build
mvn clean install assembly:single

# to display the list of options
java -cp target/OCRMemex-1.0-SNAPSHOT-jar-with-dependencies.jar com.hyperiongray.ocr.Main

# to actually run
java -cp target/OCRMemex-1.0-SNAPSHOT-jar-with-dependencies.jar com.hyperiongray.ocr.Main --<options>.....
