package com.i2i.intern.pixcell.TGF.util;

import com.i2i.intern.pixcell.VoltDbWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomGenerator {

    private static final Random rand = new Random();
    private static long[] allMsisdn;

    public static int randomLocation() {
        return 1 + rand.nextInt(9);
    }
    public static int randomDataUsage() {
        return 50 + rand.nextInt(100);
    }
    public static int randomRatingGroup() {
        return 1 + rand.nextInt(5);
    }
    public static int randomDuration() {
        return 5 + rand.nextInt(115);
    }

    public static long randomMsisdn() {

        if(allMsisdn.length == 0) {
            System.out.println("voltdb is empty...");
            return -1;
        }

        int randIndex = rand.nextInt(allMsisdn.length);
        return allMsisdn[randIndex];
    }
    public static void fetchMsisdn() {
        VoltDbWrapper wrapper = new VoltDbWrapper();

        allMsisdn = wrapper.getAllMsisdns();
        System.out.println("msisdn list was updated...");
        Arrays.stream(allMsisdn).forEach(System.out::println);

/*        System.out.println("fetchMsisdn: size " + allMsisdn.size());
        for (Object msisdn : allMsisdn) {
            System.out.println(msisdn);
        }*/
    }
}
