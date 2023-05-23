
/******************************************************************************
 *  Compilation:  javac Financial.java
 *  Execution:    java Financial
 *
 * Based upon
 * https://introcs.cs.princeton.edu/java/91float/Financial.java.html
 * downloaded 2020-08-31
 ******************************************************************************/
package de.prototypefund;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class FinancialAccuracy {

    public static void main(String[] args) {

        System.out.println("\n\nTESTING Binary floating-point arithmetic - base 2:");
        double a = 1.09 * 50;
        System.out.println("\t1.09 * 50 = " + new BigDecimal(a));

        double b = 1.14 * 75;
        System.out.println("\n\t1.14 * 75 = " + new BigDecimal(b));

        double c = 1.05 * 0.70;
        System.out.println("\n\t1.05 * 0.70 = " + new BigDecimal(c));

        double d = 0.1 / 900.0;
        System.out.println("\n\t1 / 900 = " + new BigDecimal(d));

        System.out.println("\nTESTING Decimal floating-point arithmetic - base 10 using IEEE 754-2008:");
        System.out.print("\t1.09 * 50 = ");
        BigDecimal a1 = new BigDecimal("1.09");
        BigDecimal a2 = new BigDecimal("50");
        BigDecimal a3 = a1.multiply(a2);
        System.out.println(a3);

        System.out.print("\n\t1.14 * 75 = ");
        BigDecimal b1 = new BigDecimal("1.14");
        BigDecimal b2 = new BigDecimal("75");
        BigDecimal b3 = b1.multiply(b2);
        System.out.println(b3);
        
        System.out.print("\n\t1.05 * 0.70 = ");
        BigDecimal c1 = new BigDecimal("1.05");
        BigDecimal c2 = new BigDecimal("0.70");
        BigDecimal c3 = c1.multiply(c2);
        System.out.println(c3);        

        System.out.print("\n\t1 / 900 = ");
        BigDecimal d1 = new BigDecimal("1");
        BigDecimal d2 = new BigDecimal("900");
        BigDecimal d3 = d1.divide(d2, MathContext.DECIMAL128);
        System.out.println(d3);

        System.out.println("\n======================================================================================\n");
        System.out.println("TESTING JAVA DOUBLE:");
        System.out.print("\t0.1 + 0.1 + 0.1 = ");
        System.out.println(new BigDecimal(0.1).add(new BigDecimal(0.1)).add(new BigDecimal(0.1)));
        System.out.println("\nTESTING JAVA BIG DECIMAL:");
        System.out.print("\t0.1 + 0.1 + 0.1 = ");
        System.out.println(new BigDecimal("0.1").add(new BigDecimal("0.1")).add(new BigDecimal("0.1")));

                                                                                                                    
        System.out.println("\n======================================================================================\n");
        // Decimal Precision - 7, 16 or 34 digits
        // Note: Additional preceding zeros may be given by exponent!
        MathContext mc1 = MathContext.DECIMAL32; // 7 digits - https://en.wikipedia.org/wiki/Decimal32_floating-point_format
        MathContext mc2 = MathContext.DECIMAL64; // 16 digits - https://en.wikipedia.org/wiki/Decimal64_floating-point_format
        MathContext mc3 = MathContext.DECIMAL128; // 34 digits - https://en.wikipedia.org/wiki/Decimal128_floating-point_format

        System.out.println("TESTING Decimal Precision - 7, 16 or 34 digits");
        System.out.println("\tNote: Additional preceding zeros may be given by exponent!\n");
        
        System.out.println("7 digits:");
        System.out.println("https://en.wikipedia.org/wiki/Decimal32_floating-point_format");
        System.out.println("\tDECIMAL-32: Devide 1 by 900 =");
        System.out.println("\t\t" + new BigDecimal(1, mc1).divide(new BigDecimal(900, mc1), mc1));
        System.out.println("\tDECIMAL-32: Devide 1000000000 by 9 =");
        System.out.println("\t\t" + new BigDecimal(1000000000, mc1).divide(new BigDecimal(9, mc1), mc1));
        System.out.print("\n");

        System.out.println("16 digits:");
        System.out.println("https://en.wikipedia.org/wiki/Decimal64_floating-point_format");
        System.out.println("\tDECIMAL-64: Devide 1 by 900 =");
        System.out.println("\t\t" + new BigDecimal(1, mc2).divide(new BigDecimal(900, mc1), mc2));
        System.out.println("\tDECIMAL-64: Devide 1000000000 by 9 =");
        System.out.println("\t\t" + new BigDecimal(1000000000, mc1).divide(new BigDecimal(9, mc1), mc2));
        System.out.print("\n");

        System.out.println("34 digits:");
        System.out.println("https://en.wikipedia.org/wiki/Decimal128_floating-point_format");
        System.out.print("\tDECIMAL-128: Devide 1 by 900 =");
        System.out.println(new BigDecimal(1, mc3).divide(new BigDecimal(900, mc1), mc3));
        System.out.println("\tDECIMAL-128: Devide 1000000000 by 9 =");
        System.out.println("\t\t" + new BigDecimal(1000000000, mc1).divide(new BigDecimal(9, mc1), mc3));

/** The following only works for exact results, otherwise fails with exception, see
 *  https://stackoverflow.com/questions/4591206/arithmeticexception-non-terminating-decimal-expansion-no-exact-representable
 *
        MathContext mc4 = MathContext.UNLIMITED;
        System.out.println("UNLIMITED: Devide 1 by 900");
        System.out.println(new BigDecimal(1, mc4).divide(new BigDecimal(900, mc4), mc4));
        System.out.println("UNLIMITED: Devide 1000000000 by 9");
        System.out.println(new BigDecimal(1000000000, mc4).divide(new BigDecimal(9, mc4), mc4));
*/
    }
}
