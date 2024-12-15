package presentation.controller;

import presentation.view.HomepageView;
import presentation.view.ResultsView;
import presentation.view.RunAllView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class HomepageController {
    private HomepageView homepageView;

    public HomepageController(HomepageView homepageView) {
        this.homepageView = homepageView;
        this.homepageView.addAllocateStaticallyButton(new AllocateStaticallyListener());
        this.homepageView.addAllocateDynamicallyButton(new AllocateDynamicallyListener());
        this.homepageView.addAccessStaticallyButton(new AccessStaticallyListener());
        this.homepageView.addAccessDynamicallyButton(new AccessDynamicallyListener());
        this.homepageView.addThreadCreationButton(new ThreadCreationListener());
        this.homepageView.addThreadContextSwitchButton(new ThreadContextSwitchListener());
        this.homepageView.addInsertIntoListButton(new InsertIntoListListener());
        this.homepageView.addRemoveFromListButton(new RemoveFromListListener());
        this.homepageView.addSortListButton(new SortListListener());
        this.homepageView.addRunAllButton(new RunAllListener());
    }

    class AllocateStaticallyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String command = "allocateStatically";
                String resultFilePath = "src/tests/results/allocateStatically.txt";
                String[] cmd = {"bash", "./src/tests/create_tests.sh", "./src/tests/c++/c++_test",
                        "./src/tests/javaTest/JavaTest", "./src/tests/python/python-test", resultFilePath
                        , command};

                JOptionPane.showMessageDialog(null, "Script is running for allocate statically microbenchmark..",
                        "SCRIPT INFO- ALLOCATE STATICALLY", JOptionPane.INFORMATION_MESSAGE);

                clearFile(resultFilePath);

                Process process = Runtime.getRuntime().exec(cmd);
                // Wait for the script to complete
                process.waitFor();

                createFileIfNotExist(resultFilePath);

                homepageView.setVisible(false);
                ResultsView resultsView = new ResultsView(command);
                new ResultsController(resultsView);
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class AllocateDynamicallyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String command = "allocateDynamically";
                String resultFilePath = "src/tests/results/allocateDynamically.txt";
                String[] cmd = {"bash", "./src/tests/create_tests.sh", "./src/tests/c++/c++_test",
                        "./src/tests/javaTest/JavaTest", "./src/tests/python/python-test",
                        resultFilePath, command};

                JOptionPane.showMessageDialog(null, "Script is running for allocate dynamically microbenchmark..",
                        "SCRIPT INFO- ALLOCATE DYNAMICALLY", JOptionPane.INFORMATION_MESSAGE);

                clearFile(resultFilePath);

                Process process = Runtime.getRuntime().exec(cmd);
                // Wait for the script to complete
                process.waitFor();

                createFileIfNotExist(resultFilePath);

                homepageView.setVisible(false);
                ResultsView resultsView = new ResultsView(command);
                new ResultsController(resultsView);

            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class AccessStaticallyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String command = "accessStatically";
                String resultFilePath = "src/tests/results/accessStatically.txt";
                String[] cmd = {"bash", "./src/tests/create_tests.sh", "./src/tests/c++/c++_test",
                        "./src/tests/javaTest/JavaTest", "./src/tests/python/python-test", resultFilePath
                        , command};

                JOptionPane.showMessageDialog(null, "Script is running for access statically microbenchmark..",
                        "SCRIPT INFO- ACCESS STATICALLY", JOptionPane.INFORMATION_MESSAGE);

                clearFile(resultFilePath);

                Process process = Runtime.getRuntime().exec(cmd);
                // Wait for the script to complete
                process.waitFor();

                createFileIfNotExist(resultFilePath);

                homepageView.setVisible(false);
                ResultsView resultsView = new ResultsView(command);
                new ResultsController(resultsView);

            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class AccessDynamicallyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String command = "accessDynamically";
                String resultFilePath = "src/tests/results/accessDynamically.txt";
                String[] cmd = {"bash", "./src/tests/create_tests.sh", "./src/tests/c++/c++_test",
                        "./src/tests/javaTest/JavaTest", "./src/tests/python/python-test", resultFilePath
                        , command};

                JOptionPane.showMessageDialog(null, "Script is running for access dynamically microbenchmark..",
                        "SCRIPT INFO- ACCESS DYNAMICALLY", JOptionPane.INFORMATION_MESSAGE);

                clearFile(resultFilePath);

                Process process = Runtime.getRuntime().exec(cmd);
                // Wait for the script to complete
                process.waitFor();

                createFileIfNotExist(resultFilePath);

                homepageView.setVisible(false);
                ResultsView resultsView = new ResultsView(command);
                new ResultsController(resultsView);

            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    class ThreadCreationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String command = "threadCreation";
                String resultFilePath = "src/tests/results/threadCreation.txt";
                String[] cmd = {"bash", "./src/tests/create_tests.sh", "./src/tests/c++/c++_test",
                        "./src/tests/javaTest/JavaTest", "./src/tests/python/python-test", resultFilePath
                        , command};

                JOptionPane.showMessageDialog(null, "Script is running for thread creation microbenchmark..",
                        "SCRIPT INFO- THREAD CREATION", JOptionPane.INFORMATION_MESSAGE);

                clearFile(resultFilePath);

                Process process = Runtime.getRuntime().exec(cmd);
                // Wait for the script to complete
                process.waitFor();

                createFileIfNotExist(resultFilePath);

                homepageView.setVisible(false);
                ResultsView resultsView = new ResultsView(command);
                new ResultsController(resultsView);

            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class ThreadContextSwitchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String command = "threadContextSwitch";
                String resultFilePath = "src/tests/results/threadContextSwitch.txt";
                String[] cmd = {"bash", "./src/tests/create_tests.sh", "./src/tests/c++/c++_test",
                        "./src/tests/javaTest/JavaTest", "./src/tests/python/python-test", resultFilePath
                        , "threadContextSwitch"};

                JOptionPane.showMessageDialog(null, "Script is running for thread context switch microbenchmark..",
                        "SCRIPT INFO- THREAD CONTEXT SWITCH", JOptionPane.INFORMATION_MESSAGE);

                clearFile(resultFilePath);

                Process process = Runtime.getRuntime().exec(cmd);
                // Wait for the script to complete
                process.waitFor();

                homepageView.setVisible(false);
                ResultsView resultsView = new ResultsView(command);
                new ResultsController(resultsView);

            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class InsertIntoListListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                String command = "insertIntoList";
                String resultFilePath = "src/tests/results/insertIntoList.txt";
                String[] cmd = {"bash", "./src/tests/create_tests.sh", "./src/tests/c++/c++_test",
                        "./src/tests/javaTest/JavaTest", "./src/tests/python/python-test", resultFilePath
                        , command};

                JOptionPane.showMessageDialog(null, "Script is running for inserting into list microbenchmark..",
                        "SCRIPT INFO- INSERT INTO LIST", JOptionPane.INFORMATION_MESSAGE);

                clearFile(resultFilePath);

                Process process = Runtime.getRuntime().exec(cmd);
                // Wait for the script to complete
                process.waitFor();

                createFileIfNotExist(resultFilePath);

                homepageView.setVisible(false);
                ResultsView resultsView = new ResultsView(command);
                new ResultsController(resultsView);

            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class RemoveFromListListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                String command = "removeFromList";
                String resultFilePath = "src/tests/results/removeFromList.txt";
                String[] cmd = {"bash", "./src/tests/create_tests.sh", "./src/tests/c++/c++_test",
                        "./src/tests/javaTest/JavaTest", "./src/tests/python/python-test", resultFilePath
                        , command};

                JOptionPane.showMessageDialog(null, "Script is running for removing from list microbenchmark..",
                        "SCRIPT INFO- REMOVE FROM LIST", JOptionPane.INFORMATION_MESSAGE);

                clearFile(resultFilePath);

                Process process = Runtime.getRuntime().exec(cmd);
                // Wait for the script to complete
                process.waitFor();

                createFileIfNotExist(resultFilePath);

                homepageView.setVisible(false);
                ResultsView resultsView = new ResultsView(command);
                new ResultsController(resultsView);

            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class SortListListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                String command = "sortList";
                String resultFilePath = "src/tests/results/sortList.txt";
                String[] cmd = {"bash", "./src/tests/create_tests.sh", "./src/tests/c++/c++_test",
                        "./src/tests/javaTest/JavaTest", "./src/tests/python/python-test", resultFilePath
                        , command};

                JOptionPane.showMessageDialog(null, "Script is running for sorting list microbenchmark..",
                        "SCRIPT INFO- SORT LIST", JOptionPane.INFORMATION_MESSAGE);

                clearFile(resultFilePath);

                Process process = Runtime.getRuntime().exec(cmd);
                // Wait for the script to complete
                process.waitFor();

                createFileIfNotExist(resultFilePath);

                homepageView.setVisible(false);
                ResultsView resultsView = new ResultsView(command);
                new ResultsController(resultsView);

            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class RunAllListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String resultFilePath = "src/tests/results/runAll.txt";
                String[] cmd = {"bash", "./src/tests/create_tests.sh", "./src/tests/c++/c++_test",
                        "./src/tests/javaTest/JavaTest", "./src/tests/python/python-test", resultFilePath
                        , "runAll"};

                JOptionPane.showMessageDialog(null, "Script is running for all microbenchmark..",
                        "SCRIPT INFO- RUN ALL MICROBENCHMARKS", JOptionPane.INFORMATION_MESSAGE);

                clearFile(resultFilePath);

                Process process = Runtime.getRuntime().exec(cmd);
                // Wait for the script to complete
                process.waitFor();

                createFileIfNotExist(resultFilePath);

                homepageView.setVisible(false);
                RunAllView runAllView = new RunAllView();
                new RunAllController(runAllView);

            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearFile(String filePathName) {
        try (FileWriter writer = new FileWriter(filePathName, false)) {
            writer.write("");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void createFileIfNotExist(String filePathName) {
        File file = new File(filePathName);
        if (!file.exists()) {
            try {
                file.createNewFile(); // This line creates a new file if it doesn't exist.
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
