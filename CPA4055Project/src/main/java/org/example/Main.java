package org.example;
import org.example.helper.cz4055CPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static File tracer;
    private static String powerTraceFileRootDir = "./waveform.csv";
    private static int ColumnContainIrrelant = 2;
    public static void main(String[] args) {
        // The args will have following optional parameter
        // 1 : the location of power trace filename, default is this project root directory
        // 2 : the column contain plain text, default is 0
        // 3 : the column contain irrelavant, count from front, default is 2
        // Usage to run this program
        // java -jar CPA.jar
        // java -jar CPA.jar ./waveform.csv

        if(args.length > 2 )
            printClues();
        else
        {
            try {
                if(args.length == 2)
                    startOfAttack(args[0], args[1]);
                else
                    startOfAttack(null, null);
                // use the file object to read the contents of the file
            } catch (Exception e) {
                logger.error("General Exception found: " + e.getMessage());
                printClues();
                // handle the exception here, for example by creating a new file or showing an error message to the user
            }
        }
    }

    public static void printClues() {
        logger.info("Please check the waveform file is in the same directory with this jar or provide 2 arguments if you need.");
        logger.info("Optional 1st argument: Power trace filename");
        logger.info("Optional 2nd argument: Which column contain plain text (default 0)");
        logger.info("Example: java -jar CPA.jar");
        logger.info("Example: java -jar CPA.jar ./waveform.csv 2");
    }

    public static void startOfAttack(final String filename,
                                     final String irrCol
                                     ) {
        if (filename!= null)
            Main.powerTraceFileRootDir = filename;
        if(irrCol!=null)
            Main.ColumnContainIrrelant =  Integer.parseInt(irrCol);
        Main.tracer = new File(Main.powerTraceFileRootDir);

        try {
           // final cz4055CPA obj = new cz4055CPA(CPAMain.plainTextColNum, CPAMain.irrelevantFrontColumns, CPAMain.irrelevantCOLBehind, csvtrack);
            final cz4055CPA obj = new cz4055CPA(Main.ColumnContainIrrelant);
            obj.init(Main.tracer);
            logger.info("Brute force the possible number of key...");
            logger.info("In this lab, we using AES 128 only...");
            final HashMap<Object, Object> values = obj.crackCPA(16);
            logger.info("Analysing completed.");
            logger.info("|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|");
            logger.info("Obtained key: " + values.get("key").toString() + "\n");
            logger.info("|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|");
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }

    }
}