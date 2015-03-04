#!/bin/sh
# to build
mvn clean install assembly:single

# to display the list of options
java -cp target/OCRMemex-1.0-SNAPSHOT-jar-with-dependencies.jar com.hyperiongray.ocrmemex.Main

# to actually run
java -cp target/OCRMemex-1.0-SNAPSHOT-jar-with-dependencies.jar com.hyperiongray.ocrmemex.Main --<options>.....
