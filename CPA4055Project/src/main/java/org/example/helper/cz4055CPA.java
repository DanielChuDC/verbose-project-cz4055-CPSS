package org.example.helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;



public class cz4055CPA {
    private static final Logger logger = LoggerFactory.getLogger(cz4055CPA.class);

    private int numOfTrace = 0;
    private int numOfTP = 0 ; // Total number of TracePoints
    private File powerTraceFile;
    private BufferedReader br;
    private double[][] powerTraceMatrix;
    private String[] plainText;
    private final int frontColumnIsExtra;
    private final int extraColumnBehind = 0;

    /**
     Constructs an instance of cz4055CPA with the specified number of irrelevant front columns.
     The default number of irrelevant front columns is 2.
     @param irrelevantFrontColumns The number of irrelevant front columns.
     */
    public cz4055CPA(final int irrelevantFrontColumns) {
        // default should be 2
        this.frontColumnIsExtra = irrelevantFrontColumns;
    }

    /**
     Initializes the power trace file and the BufferedReader object.
     @param fileName the file name of the power trace file to be initialized
     @throws IOException if there is an I/O error while initializing the file
     */
    public void init(final File fileName) throws IOException {
        this.powerTraceFile = fileName;
        this.br = new BufferedReader(new FileReader(this.powerTraceFile));
        int count = 1;
        this.numOfTP = this.br.readLine().split(",").length - (this.frontColumnIsExtra + this.extraColumnBehind);
        while (this.br.readLine() != null) {
            ++count;
        }
        this.numOfTrace = count;
        this.br.close();
    }

    public void initPowerTraceMatrix() throws IOException {
        this.br = new BufferedReader(new FileReader(this.powerTraceFile));
        this.powerTraceMatrix = new double[this.numOfTrace][this.numOfTP];
        this.plainText = new String[this.numOfTrace];
        for (int count = 0; count < this.numOfTrace; count++) {
            final String[] t = this.br.readLine().split(",");
            int plaintextCol = 0;
            this.plainText[count] = t[plaintextCol];
            for (int j = this.frontColumnIsExtra; j < this.numOfTP - this.extraColumnBehind; j++) {
                this.powerTraceMatrix[count][j - this.frontColumnIsExtra] = Double.parseDouble(t[j]);
            }
        }
        this.br.close();
    }

    /**

     Attempts to crack the key used for AES encryption using the CPA (Known-Plaintext Attack) method.
     @param keySize the size of the key to be cracked in bytes
     @return a HashMap containing the cracked key and the corresponding plaintext
     @throws IOException if there is an error reading the plaintext and ciphertext files
     */
    public HashMap<Object, Object> crackCPA(final int keySize) throws IOException {
        int[] key = new int[keySize];
        this.initPowerTraceMatrix();
        final long start = System.currentTimeMillis();
        for (int i = 1; i <= keySize; i++) {
            int[][] hypothesis = common.hypothesisAES128(i, plainText);
            double[][] myCorrelation = common.findCorrelationFromPowerTrace(numOfTP, numOfTrace, hypothesis, powerTraceMatrix);
            key[i - 1] = common.findKey(myCorrelation, numOfTP);
            // will always output the correlation file into the output folder
            common.saveCSVIntoOutputDir("BruteForceCorrelation_" + i + ".csv", numOfTP, myCorrelation);
        }
        final long end = System.currentTimeMillis();
        final double timeTaken = (double)((end - start) / 1000L);
        StringBuilder stringKey = new StringBuilder();
        String t ; // use for holding intermediate result
        for (int j = 0; j < keySize; j++) {
            t = Integer.toHexString(key[j]).toUpperCase() + " ";
            stringKey.append((t.length() < 3) ? ("0" + t) : t);
        }
        // found the key
        logger.info("Total Time: " + timeTaken + " seconds");
        final HashMap<Object, Object> retVal = new HashMap<>();
        retVal.put("key", stringKey.toString());
        retVal.put("time", timeTaken);
        return retVal;
    }
}
