package org.example.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class common {
    private static final Logger logger = LoggerFactory.getLogger(common.class);

    /**
     * Calculates the Hamming weight of an integer.
     * The Hamming weight is the number of non-zero bits in the integer.
     *
     * @param n the integer to calculate the Hamming weight of.
     * @return the number of non-zero bits in the integer.
     */
    public static int HammingWeight(final int n) {
        return Integer.bitCount(n);
    }

    /**

     Substitutes an input byte using a fixed S-box table.
     @param a The input byte to be substituted.
     @return The output byte obtained by performing a one-to-one substitution using a fixed S-box table.
     */
    public static int SubstitutionBOX(final int a) {
        final int[] sArr = { 99, 124, 119, 123, 242, 107, 111, 197, 48, 1, 103, 43, 254, 215, 171, 118, 202, 130, 201, 125, 250, 89, 71, 240, 173, 212, 162, 175, 156, 164, 114, 192, 183, 253, 147, 38, 54, 63, 247, 204, 52, 165, 229, 241, 113, 216, 49, 21, 4, 199, 35, 195, 24, 150, 5, 154, 7, 18, 128, 226, 235, 39, 178, 117, 9, 131, 44, 26, 27, 110, 90, 160, 82, 59, 214, 179, 41, 227, 47, 132, 83, 209, 0, 237, 32, 252, 177, 91, 106, 203, 190, 57, 74, 76, 88, 207, 208, 239, 170, 251, 67, 77, 51, 133, 69, 249, 2, 127, 80, 60, 159, 168, 81, 163, 64, 143, 146, 157, 56, 245, 188, 182, 218, 33, 16, 255, 243, 210, 205, 12, 19, 236, 95, 151, 68, 23, 196, 167, 126, 61, 100, 93, 25, 115, 96, 129, 79, 220, 34, 42, 144, 136, 70, 238, 184, 20, 222, 94, 11, 219, 224, 50, 58, 10, 73, 6, 36, 92, 194, 211, 172, 98, 145, 149, 228, 121, 231, 200, 55, 109, 141, 213, 78, 169, 108, 86, 244, 234, 101, 122, 174, 8, 186, 120, 37, 46, 28, 166, 180, 198, 232, 221, 116, 31, 75, 189, 139, 138, 112, 62, 181, 102, 72, 3, 246, 14, 97, 53, 87, 185, 134, 193, 29, 158, 225, 248, 152, 17, 105, 217, 142, 148, 155, 30, 135, 233, 206, 85, 40, 223, 140, 161, 137, 13, 191, 230, 66, 104, 65, 153, 45, 15, 176, 84, 187, 22 };
        return sArr[a];
    }

    /**
     * Converts a hex string to an integer.
     *
     * @param hex the hex string to be converted.
     * @return the integer value of the hex string.
     */
    public static int convertHexToInt(final String hex) {
        final HashMap<String, Integer> hexMap = new HashMap<>();
        for(int i = 0 ; i <16; i++)
        {
            String keyInHex = Integer.toHexString(i).toUpperCase();
            hexMap.put(keyInHex, i);
        }
        return hexMap.get(String.valueOf(hex.charAt(0)))
                << 4 ^ hexMap.get(
                String.valueOf(hex.charAt(1))
        );
    }

    /**
     * Used to measure the degree of correlation between the plaintext and ciphertext, or between the key and ciphertext.
     Calculates the correlation between two arrays of doubles.
     @param xs the first array of doubles
     @param ys the second array of doubles
     @return the correlation between xs and ys
     @throws IllegalArgumentException if the length of xs and ys are different
     */
    public static double Correlation(final double[] xs, final double[] ys) {
        double sx = 0.0;
        double sy = 0.0;
        double sxx = 0.0;
        double syy = 0.0;
        double sxy = 0.0;
        final int n = xs.length;
        for (int i = 0; i < n; i++) {
            final double x = xs[i];
            final double y = ys[i];
            sx += x;
            sy += y;
            sxx += x * x;
            syy += y * y;
            sxy += x * y;
        }
        final double cov = sxy / n - sx * sy / n / n;
        final double sigMax = Math.sqrt(sxx / n - sx * sx / n / n);
        final double sigMay = Math.sqrt(syy / n - sy * sy / n / n);
        return cov / sigMax / sigMay;
    }

    /**
     * Only save in output folder
     Saves the given 2D array of double values as a CSV file with the specified filename.
     @param filename the name of the file to be saved.
     @param numOfTP the number of TracePoints.
     @param myCorrelation the 2D array of correlation values.
     @throws IOException if there is an error while writing to the file.
     */
    public static void saveCSVIntoOutputDir(final String filename, int numOfTP, double [][] myCorrelation) throws IOException {
        File dir = new File("./output");
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                logger.info("Directory created successfully");
            } else {
                logger.info("Failed to create directory");
                return;
            }
        }

        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < numOfTP; j++) {
                builder.append(myCorrelation[i][j]);
                if (j < numOfTP - 1) {
                    builder.append(",");
                }
            }
            builder.append("\n");
        }
        BufferedWriter bw;
        (bw = new BufferedWriter(new FileWriter(dir.getAbsolutePath()+ "/"+filename))).write(builder.toString());
        bw.close();
    }

    /**
     * This method finds the correlation coefficient between the hypothesis and the power trace matrix
     * for each possible key value and each time point.
     *
     * @param numOfTP          number of trace points
     * @param numOfTrace       number of power traces
     * @param hypothesis       2D array of hypothetical values
     * @param powerTraceMatrix 2D array of power traces
     * @return myCorrelation 2D array of correlation coefficients
     */
    public static double[][] findCorrelationFromPowerTrace(int numOfTP, int numOfTrace, int[][] hypothesis, double[][] powerTraceMatrix){
        double[][] myCorrelation = new double[256][numOfTP];
        final double[] x = new double[numOfTrace];
        final double[] y = new double[numOfTrace];
        for (int count = 0; count <= 255; count++) {
            for (int j = 0; j < numOfTrace; j++) {
                y[j] = hypothesis[j][count] / 256.0;
            }
            for (int i = 0; i < numOfTP; i++) {
                for (int k = 0; k < numOfTrace; k++) {
                    x[k] = powerTraceMatrix[k][i];
                }
                myCorrelation[count][i] = common.Correlation(x, y);
            }
        }
        return myCorrelation;
    }

    /**
     This method finds the index of the maximum correlation value in a 2D correlation matrix.
     @param myCorrelation a 2D array of correlation values
     @param numOfTP the number of Trace points used in the correlation calculation
     @return the index of the maximum correlation value
     */
    public static int findKey(double[][] myCorrelation,  int numOfTP) {
        double max = myCorrelation[0][0];
        int location = 0;
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < numOfTP; j++) {
                if (myCorrelation[i][j] > max) {
                    max = myCorrelation[i][j];
                    location = i;
                }
            }
        }
        return location;
    }

    /**
     * It can be used to generalize 192 and 256 bits AES encryption key
     * Generates a hypothesis matrix for a specific byte number of a 128-bit AES encryption key.
     * The hypothesis matrix is generated using the given plain text and the initial hypothesis matrix.
     *
     * @param byteNumber The byte number (1-16) of the AES key for which the hypothesis matrix is to be generated.
     * @param plainText  An array of plain texts to use for generating the hypothesis matrix.
     * @return The new hypothesis matrix generated using the given plain text and initial hypothesis matrix.
     */
    public static int[][] hypothesisAES128(final int byteNumber, String[] plainText) {
        final int[] keyHyp = new int[256];
        for (int i = 0; i < 255; i++) {
            keyHyp[i] = i;
        }
        int[][] hypothesis = new int[plainText.length][keyHyp.length];
        for (int i = 0; i < plainText.length; i++) {
            final String P = plainText[i].substring(2 * (byteNumber - 1), 2 * byteNumber);
            for (int j = 0; j < keyHyp.length; j++) {
                hypothesis[i][j] = common.HammingWeight(common.SubstitutionBOX(common.convertHexToInt(P) ^ keyHyp[j]));
            }
        }
        return hypothesis;
    }


}
