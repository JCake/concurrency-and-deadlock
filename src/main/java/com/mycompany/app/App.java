package com.mycompany.app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App 
{

    static ExecutorService executorService = Executors.newFixedThreadPool(4);
    static int count = 0;
    static List<Integer> valuesOfCount = new ArrayList<>();

    static String holderOfChopstick1 = null;
    static String holderOfChopstick2 = null;
    static String holderOfChopstick3 = null;

    static Set<String> whoHasEaten = new HashSet<>();

    public static void main( String[] args ) throws Exception
    {
        for(int i = 0; i < 100; i++){
            incrementCount();
        }
        Thread.sleep(500);
        System.out.println("After 100 rounds, count is: " + count);

        for(int i = 0; i < 100; i++){
            incrementCountSlower();
        }
        Thread.sleep(500);
        System.out.println("After next 100 rounds, count is: " + count);

        for(int i = 0; i < 100; i++){
            storeAndIncrementCount();
        }
        Thread.sleep(500);
        System.out.println("After next 100 rounds, count is: " + count);
        System.out.println("List of count values has: " + valuesOfCount);

        while(whoHasEaten.size() < 3){
            if(!whoHasEaten.contains("A")){
                executorService.submit(() -> {
                    takeChopstick("A");
                });
                executorService.submit(() -> {
                    takeChopstick("A");
                });
                executorService.submit(() -> {
                    tryToEat("A");
                });
            }
            if(!whoHasEaten.contains("B")){
                executorService.submit(() -> {
                    takeChopstick("B");
                });
                executorService.submit(() -> {
                    takeChopstick("B");
                });
                executorService.submit(() -> {
                    tryToEat("B");
                });
            }
            if(!whoHasEaten.contains("C")){
                executorService.submit(() -> {
                    takeChopstick("C");
                });
                executorService.submit(() -> {
                    takeChopstick("C");
                });
                executorService.submit(() -> {
                    tryToEat("C");
                });
            }
        }
        System.out.println("All ate!");
    }

    public static void incrementCount(){
        executorService.submit(() -> {
            count++;
        });
    }

    public static void incrementCountSlower(){
        executorService.submit(() -> {
            int copyOfCount = count;
            int updatedCount = copyOfCount + 1;
            count = updatedCount;
        });
    }

    public static void storeAndIncrementCount(){
        executorService.submit(() -> {
            valuesOfCount.add(count);
            count++;
        });
    }

    public static void takeChopstick(String philosopher) {
        if(holderOfChopstick1 == null){
            holderOfChopstick1 = philosopher;
        } else if(holderOfChopstick2 == null){
            holderOfChopstick2 = philosopher;
        } else if (holderOfChopstick3 == null){
            holderOfChopstick3 = philosopher;
        }
    }

    public static void tryToEat(String philosopher) {
        if(holderOfChopstick1 == philosopher && holderOfChopstick2 == philosopher){
            holderOfChopstick1 = null;
            holderOfChopstick2 = null;
            whoHasEaten.add(philosopher);
        } else if(holderOfChopstick2 == philosopher && holderOfChopstick3 == philosopher){
            holderOfChopstick2 = null;
            holderOfChopstick3 = null;
            whoHasEaten.add(philosopher);
        } else if(holderOfChopstick3 == philosopher && holderOfChopstick1 == philosopher){
            holderOfChopstick3 = null;
            holderOfChopstick1 = null;
            whoHasEaten.add(philosopher);
        }
    }
}
