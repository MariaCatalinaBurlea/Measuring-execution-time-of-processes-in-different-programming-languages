package tests.javaTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class JavaTest {
    private static final Random random = new Random();

    static class RunnableClass implements Runnable {

        @Override
        public void run() {
            int x = 5;
        }
    }

    static class MyThreadForThreadCreation extends Thread {
        @Override
        public void run() {
            int x = 5;
        }
    }

    public static int sharedNumber = 0;

    static class MyThreadForThreadContextSwitch extends Thread {

        @Override
        public void run() {
            safeIncrement();
        }

        private synchronized void safeIncrement() {
            sharedNumber++;
        }
    }

    static double allocateStatically() {
        long startTime = System.nanoTime();
        for (int i = 0; i < 500; i++) {
            int x = 5;
        }
        long endTime = System.nanoTime();

        long executionTime = endTime - startTime;

        return Double.parseDouble(String.valueOf(executionTime));
    }

    static double accessStatically() {
        int localVariable = 23;
        int value = 0;
        long startTime = System.nanoTime();
        for (int i = 0; i < 500; i++) {
            value = localVariable;
        }
        long endTime = System.nanoTime();

        long executionTime = endTime - startTime;

        return Double.parseDouble(String.valueOf(executionTime));
    }

    static double allocateDynamically() {
        long startTime = System.nanoTime();
        int[] array = new int[500];
        long endTime = System.nanoTime();

        long executionTime = endTime - startTime;

        return Double.parseDouble(String.valueOf(executionTime));
    }

    static double accessDynamically() {
        int x = 0;
        int[] array = new int[500];
        for (int i = 0; i < 500; i++) {
            array[i] = i;
        }

        long startTime = System.nanoTime();
        for (int i = 0; i < 500; i++) {
            x = array[i];
        }
        long endTime = System.nanoTime();

        long executionTime = (endTime - startTime) / 500;

        return Double.parseDouble(String.valueOf(executionTime));
    }

    static double threadCreation() {
        long startTime = System.nanoTime();
        Thread thread = new MyThreadForThreadCreation();
        long endTime = System.nanoTime();

        // thread.start();
        // try {
        //     thread.join();
        // } catch (InterruptedException e) {
        //     throw new RuntimeException(e);
        // }
        long executionTime = endTime - startTime;

        return Double.parseDouble(String.valueOf(executionTime));
    }

    static double threadContextSwitch(int numOfThreads) {

        long startTime = System.nanoTime();

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < numOfThreads; i++) {
            Thread thread = new MyThreadForThreadContextSwitch();
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.nanoTime();

        long executionTime = endTime - startTime;

        return Double.parseDouble(String.valueOf(executionTime));
    }

    static List<Integer> generateRandomList(int size) {
        List<Integer> randomList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            randomList.add(i, random.nextInt() % size);
        }

        return randomList;
    }

    static double insertIntoList(List<Integer> list, int value, int positionToInsert) {
        long startTime = System.nanoTime();
        list.add(positionToInsert, value);
        long endTime = System.nanoTime();

        long executionTime = endTime - startTime;

        return Double.parseDouble(String.valueOf(executionTime));
    }

    static double removeFromList(List<Integer> list, int position) {
        long startTime = System.nanoTime();
        list.remove(position);
        long endTime = System.nanoTime();

        long executionTime = endTime - startTime;

        return Double.parseDouble(String.valueOf(executionTime));
    }

    static double sortList(List<Integer> list) {
        long startTime = System.nanoTime();
        Collections.sort(list);
        long endTime = System.nanoTime();

        long executionTime = endTime - startTime;

        return Double.parseDouble(String.valueOf(executionTime));
    }

    static void runAll() {
        System.out.print("allocateStatically ");
        for (int i = 0; i < 100; i++) {
            System.out.print(allocateStatically() / 500 + " ");
        }
        System.out.print("\nallocateDynamically ");
        for (int i = 0; i < 100; i++) {
            System.out.print(allocateDynamically() + " ");
        }
        System.out.print("\naccessStatically ");
        for (int i = 0; i < 100; i++) {
            System.out.print(accessStatically() / 500 + " ");
        }
        System.out.print("\naccessDynamically ");
        for (int i = 0; i < 100; i++) {
            System.out.print(accessDynamically()  + " ");
        }
        System.out.print("\nthreadCreation ");
        for (int i = 0; i < 100; i++) {
            System.out.print(threadCreation() + " ");
        }
        System.out.print("\nthreadContextSwitch ");
        int numOfThreads = 100;
        for (int i = 0; i < 100; i++) {
            System.out.print(threadContextSwitch(numOfThreads) / numOfThreads + " ");
        }
        System.out.print("\ninsertIntoList ");
        for (int i = 0; i < 100; i++) {
            List<Integer> randomList = generateRandomList(10000);
            System.out.print(insertIntoList(randomList, 23, 500) + " ");
        }
        System.out.print("\nremoveFromList ");
        for (int i = 0; i < 100; i++) {
            List<Integer> randomList = generateRandomList(10000);
            System.out.print(removeFromList(randomList, 500) + " ");
        }
        System.out.print("\nsortList ");
        for (int i = 0; i < 100; i++) {
            List<Integer> randomList = generateRandomList(10000);
            System.out.print(sortList(randomList) + " ");
        }
    }

    public static void main(String[] args) {

        int argc = args.length;
        if (argc != 1) {
            System.out.println("Usage: <Command>");
            System.exit(1);
        }

        String command = args[0];

        switch (command) {
            case "allocateStatically":
                for (int i = 0; i < 100; i++) {
                    System.out.print(allocateStatically() / 500 + " ");
                }
                break;

            case "allocateDynamically":
                for (int i = 0; i < 100; i++) {
                    System.out.print(allocateDynamically() + " ");
                }
                break;

            case "accessStatically":
                for (int i = 0; i < 100; i++) {
                    System.out.print(accessStatically() / 500 + " ");
                }
                break;

            case "accessDynamically":
                for (int i = 0; i < 100; i++) {
                    System.out.print(accessDynamically() + " ");
                }
                break;

            case "threadCreation":
                for (int i = 0; i < 100; i++) {
                    System.out.print(threadCreation() + " ");
                }
                break;

            case "threadContextSwitch":
                int numOfThreads = 100;
                for (int i = 0; i < 100; i++) {
                    System.out.print(threadContextSwitch(numOfThreads) / numOfThreads + " ");
                }
                break;

            case "insertIntoList":
                for (int i = 0; i < 100; i++) {
                    List<Integer> randomList = generateRandomList(10000);
                    System.out.print(insertIntoList(randomList, 23, 500) + " ");
                }
                break;

            case "removeFromList":
                for (int i = 0; i < 100; i++) {
                    List<Integer> randomList = generateRandomList(10000);
                    System.out.print(removeFromList(randomList, 500) + " ");
                }
                break;

            case "sortList":
                for (int i = 0; i < 100; i++) {
                    List<Integer> randomList = generateRandomList(10000);
                    System.out.print(sortList(randomList) + " ");
                }
                break;

            case "runAll":
                runAll();
                break;

            default:
                System.out.println("Invalid command");
                break;
        }
    }
}
