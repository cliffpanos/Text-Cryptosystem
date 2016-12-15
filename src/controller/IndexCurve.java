package controller;

import java.util.ArrayList;

public class IndexCurve {

    private static String passwordHashAlpha = "9831685127";
    private static String passwordHashBeta = "5647191348";

    public IndexCurve(String passwordHashAlpha, String passwordHashBeta) {
        this.passwordHashAlpha = passwordHashAlpha;
        this.passwordHashBeta = passwordHashBeta;
    }

    public static int curve(int key, int lowerBound, int upperBound) {


        /*This function works like a sinusoidal curve to return values
          between the given range [lowerBound, upperBound].

            The function follows the form a * sin(b(x - c)) + d

          b should be somewhat constant and somewhat small so that even close
          values of x will produce pretty different f(x) values */


        double d = (lowerBound + upperBound) / 2.0;

        double majorAmplitude = (double) (upperBound - d) * (2.0 / 3.0);
        double minorAmplitude = (double) (upperBound - d) * (1.0 / 3.0);


        int index1 = key % passwordHashAlpha.length();
        int translator1 =
            Character.getNumericValue(passwordHashAlpha.charAt(index1));

        int index2 = (key * translator1) % passwordHashBeta.length();
        int translator2 =
            Character.getNumericValue(passwordHashBeta.charAt(index2));


        return (int) Math.round(
            (majorAmplitude * Math.sin(56 * (key - translator1)))
            + (minorAmplitude * Math.cos(key * (key - translator2)))
            + d);

    }

    public static void main(String[] args) {

        int lowerBound = 0;
        int upperBound = 200;

        ArrayList<Integer> ints = new ArrayList<>(upperBound);

        for (int i = lowerBound; i < upperBound; i++) {
            ints.add(i);
        }

        for (int k = 1; k < 400; k++) {
            int num = curve(k, lowerBound, upperBound);
            System.out.println(k + ": " + num);
            ints.remove((Integer) num);
        }

        System.out.println(ints + "\nNums remaining: " + ints.size());
    }

}
