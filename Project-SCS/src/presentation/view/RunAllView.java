package presentation.view;

import presentation.model.Language;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class RunAllView extends JFrame {
    JButton backButton;

    public RunAllView() {
        this.setBounds(100, 100, 700, 370);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        JLabel titleLabel = new JLabel("Measuring execution time of processes");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
        titleLabel.setBounds(170, 23, 580, 30);
        this.add(titleLabel);

        backButton = new JButton("BACK");
        backButton.setForeground(new Color(0, 0, 204));
        backButton.setBounds(302, 300, 117, 29);
        this.getContentPane().add(backButton);

        JTable table = new JTable(createModelForTable());

        // Adjust row height and cell padding
        int rowHeight = 20; // Set your desired row height
        table.setRowHeight(rowHeight);

        table.setShowGrid(true);
        table.setGridColor(Color.lightGray);

        // Add lines to rows and column headers
        MatteBorder border = new MatteBorder(1, 0, 0, 0, Color.BLUE);
        table.setBorder(border);


        // Set a custom TableCellRenderer to color the header text
        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setFont(c.getFont().deriveFont(Font.BOLD));
                c.setForeground(Color.BLUE); // Set the text color to blue
                setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(60, 58, 600, 200);
        this.getContentPane().add(scrollPane);

        this.setVisible(true);
        this.setTitle("Measuring execution time of all processes- Run All");
    }

    public void addBackButtonListener(ActionListener actionListener) {
        this.backButton.addActionListener(actionListener);
    }

    private DefaultTableModel createModelForTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Metric to be measured");
        model.addColumn("C++");
        model.addColumn("Java");
        model.addColumn("Python");

        // Add rows to the table
        model.addRow(new Object[]{"Allocate statically", "", "", ""});
        model.addRow(new Object[]{"Allocate dynamically", "", "", ""});
        model.addRow(new Object[]{"Access statically", "", "", ""});
        model.addRow(new Object[]{"Access dynamically", "", "", ""});
        model.addRow(new Object[]{"Thread creation", "", "", ""});
        model.addRow(new Object[]{"Thread context switch", "", "", ""});
        model.addRow(new Object[]{"Insert into list", "", "", ""});
        model.addRow(new Object[]{"Remove from list", "", "", ""});
        model.addRow(new Object[]{"Sort list", "", "", ""});


        // Add rows to the table
        Map<Language, Map<String, Double>> languageBenchmarkMap = getLanguageBenchmarkMap();
        for (Language language : languageBenchmarkMap.keySet()) {
            for (String benchmarkType : languageBenchmarkMap.get(language).keySet()) {
                int row = getRow(benchmarkType);
                if (row != -1) {
                    double average = languageBenchmarkMap.get(language).getOrDefault(benchmarkType, -1.0);
                    if (average == -1) {
                        // In the case we have Python with static memory allocation or access
                        model.setValueAt("Impossible", row, language.ordinal() + 1);
                    } else {
                        model.setValueAt(average, row, language.ordinal() + 1);
                    }
                }
            }
        }
        return model;
    }

    private Map<Language, Map<String, Double>> getLanguageBenchmarkMap() {
        Map<Language, Map<String, Double>> languageBenchmarkMap = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/tests/results/runAll.txt"));
            String line;
            Language currentLanguage = null;

            while ((line = reader.readLine()) != null) {
                if (line.equals("CPP") || line.equals("JAVA") || line.equals("PYTHON")) {
                    currentLanguage = Language.valueOf(line.toUpperCase());
                    languageBenchmarkMap.put(currentLanguage, new HashMap<>());
                } else {
                    String[] parts = line.split(" ");
                    String benchmarkType = parts[0];
                    String[] values = new String[parts.length - 1];
                    System.arraycopy(parts, 1, values, 0, values.length);

                    Map<Language, Double> thresholds = getThresholds(benchmarkType);
                    double threshold = thresholds.get(currentLanguage);
                    double average = computeAverage(values, threshold);

                    languageBenchmarkMap.get(currentLanguage).put(benchmarkType, average);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return languageBenchmarkMap;
    }

    private double computeAverage(String[] values, double threshold) {
        double sum = 0;
        int numberOfElements = 0;
        boolean isImpossible = false;

        for (String value : values) {
            if (value.equals("Impossible")) {
                isImpossible = true;
                break;
            }

            double numericValue = Double.parseDouble(value);
            if (numericValue <= threshold) {
                sum += numericValue;
                numberOfElements++;
            }
        }

        if (isImpossible || numberOfElements == 0) {
            return -1.0;
        } else {
            double average = sum / numberOfElements;
            BigDecimal roundedAverage = new BigDecimal(average).setScale(5, BigDecimal.ROUND_HALF_UP);
            return roundedAverage.doubleValue();
        }
    }


    private Map<Language, Double> getThresholds(String command) {
        Map<Language, Double> languageThresholdMap = new HashMap<>();

        switch (command) {
            case "allocateStatically":
                languageThresholdMap.put(Language.CPP, 10.0);
                languageThresholdMap.put(Language.JAVA, 25.0);
                languageThresholdMap.put(Language.PYTHON, -1.0);
                break;

            case "allocateDynamically":
                languageThresholdMap.put(Language.CPP, 150.0);
                languageThresholdMap.put(Language.JAVA, 2500.0);
                languageThresholdMap.put(Language.PYTHON, 1000.0);
                break;

            case "accessStatically":
                languageThresholdMap.put(Language.CPP, 85.0);
                languageThresholdMap.put(Language.JAVA, 125.0);
                languageThresholdMap.put(Language.PYTHON, -1.0);
                break;

            case "accessDynamically":
                languageThresholdMap.put(Language.CPP, 10.0);
                languageThresholdMap.put(Language.JAVA, 35.0);
                languageThresholdMap.put(Language.PYTHON, 30.0);
                break;

            case "threadCreation":
                languageThresholdMap.put(Language.CPP, 30000.0);
                languageThresholdMap.put(Language.JAVA, 30000.0);
                languageThresholdMap.put(Language.PYTHON, 15000.0);
                break;

            case "threadContextSwitch":
                languageThresholdMap.put(Language.CPP, 100000.0);
                languageThresholdMap.put(Language.JAVA, 400000.0);
                languageThresholdMap.put(Language.PYTHON, 500000.0);
                break;

            case "insertIntoList":
                languageThresholdMap.put(Language.CPP, 200.0);
                languageThresholdMap.put(Language.JAVA, 2600.0);
                languageThresholdMap.put(Language.PYTHON, 8000.0);
                break;

            case "removeFromList":
                languageThresholdMap.put(Language.CPP, 200.0);
                languageThresholdMap.put(Language.JAVA, 3000.0);
                languageThresholdMap.put(Language.PYTHON, 3000.0);
                break;

            case "sortList":
                languageThresholdMap.put(Language.CPP, 5000000.0);
                languageThresholdMap.put(Language.JAVA, 4000000.0);
                languageThresholdMap.put(Language.PYTHON, 1400000.0);
                break;

            default:
                System.out.println("Invalid command");
                break;
        }

        return languageThresholdMap;
    }

    private int getRow(String benchmarkType) {
        switch (benchmarkType) {
            case "allocateStatically":
                return 0;
            case "allocateDynamically":
                return 1;
            case "accessStatically":
                return 2;
            case "accessDynamically":
                return 3;
            case "threadCreation":
                return 4;
            case "threadContextSwitch":
                return 5;
            case "insertIntoList":
                return 6;
            case "removeFromList":
                return 7;
            case "sortList":
                return 8;
            default:
                return -1;
        }
    }
}
