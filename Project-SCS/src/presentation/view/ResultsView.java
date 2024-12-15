
package presentation.view;

import org.jfree.chart.*;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import presentation.model.Language;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultsView extends JFrame {
    private JButton backButton;
    private JLabel cppAvgLabel;
    private JLabel javaAvgLabel;
    private JLabel pythonAvgLabel;

    public ResultsView(String command) {
        this.setBounds(100, 100, 1200, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new GridBagLayout());
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        GridBagConstraints constraints = new GridBagConstraints();

        // Create a panel for the left side with average values
        JPanel leftPanel = new JPanel();
        addAverageValuesLabels(leftPanel, constraints);

        addPlot(command);

        backButton = new JButton("BACK");
        constraints.gridx = 1;
        constraints.gridy = 2;
        this.getContentPane().add(backButton, constraints);

        this.setTitle("Measuring execution time of the " + getLabel(command) + " microbenchmark");
        this.setVisible(true);
    }

    public void addBackButtonListener(ActionListener actionListener) {
        this.backButton.addActionListener(actionListener);
    }

    private void addPlot(String command) {
        DefaultXYDataset dataset = getXYDataset(command);

        JFreeChart lineChart = ChartFactory.createXYLineChart(
                getLabel(command),
                "Number of Runs",
                "Execution Time",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = lineChart.getXYPlot();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        for (int i = 0; i < 3; i++) {
            renderer.setSeriesLinesVisible(i, true);
            renderer.setSeriesShapesVisible(i, true);
        }

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(960, 400));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1; // Right side
        constraints.gridy = 0; // Top
        constraints.gridheight = 2; // Span vertically
        this.getContentPane().add(chartPanel, constraints);
    }

    private DefaultXYDataset getXYDataset(String command) {
        DefaultXYDataset dataset = new DefaultXYDataset();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/tests/results/" + command + ".txt"));
            String line;
            Language currentLanguage = null;


            Map<Language, Double> languageThresholdMap = getThresholds(command);
            Map<Language, List<Double>> languageDataMap = new HashMap<>();
            Map<Language, Double> languageAverageMap = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                if (line.equals("CPP") || line.equals("JAVA") || line.equals("PYTHON")) {
                    currentLanguage = Language.valueOf(line.toUpperCase());
                    languageDataMap.put(currentLanguage, new ArrayList<>());
                } else {
                    String[] values = line.split(" ");
                    computeAveragesForSpecificLanguage(currentLanguage, values, languageThresholdMap, languageDataMap, languageAverageMap);
                    // Set the labels for the average
                    setAverageLabel(currentLanguage, languageAverageMap);
                }
            }
            reader.close();

            // Convert the data to XYDataset format
            dataset = convertDataToXYDataset(languageDataMap);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataset;
    }


    private void computeAveragesForSpecificLanguage(Language currentLanguage,
                                                    String[] values, Map<Language, Double> languageThresholdMap,
                                                    Map<Language, List<Double>> languageDataMap,
                                                    Map<Language, Double> languageAverageMap) {
        double sum = 0;
        int numberOfElements = 0;
        boolean isImpossible = false;

        for (int i = 0; i < values.length; i++) {
            if (values[i].equals("Impossible")) {
                isImpossible = true;
                break;
            }

            double value = Double.parseDouble(values[i]);
            // Compare value with the threshold specific to the language, respectively microbenchmark
            if (value <= languageThresholdMap.get(currentLanguage)) {
                sum += value;
                numberOfElements++;
            }

            // Add elements to the data list
            double doubleValue = Double.parseDouble(values[i]);
            BigDecimal roundedValue = new BigDecimal(doubleValue).setScale(5, BigDecimal.ROUND_HALF_UP);
            double result = roundedValue.doubleValue();
            languageDataMap.get(currentLanguage).add(result);
        }

        if (isImpossible) {
            // Handle impossible case for Python (when we have static memory allocation/access)
            languageAverageMap.put(currentLanguage, -1.0);
        } else {
            // Put the average into languageAverageMap
            if (numberOfElements > 0) {
                double average = sum / numberOfElements;
                BigDecimal roundedAverage = new BigDecimal(average).setScale(5, BigDecimal.ROUND_HALF_UP);
                double averageResult = roundedAverage.doubleValue();
                languageAverageMap.put(currentLanguage, averageResult);
            }
        }
    }

    private DefaultXYDataset convertDataToXYDataset(Map<Language, List<Double>> languageDataMap) {
        DefaultXYDataset dataset = new DefaultXYDataset();
        for (Language language : languageDataMap.keySet()) {
            List<Double> languageValues = languageDataMap.get(language);
            double[][] data = new double[2][languageValues.size()];
            for (int i = 0; i < languageValues.size(); i++) {
                data[0][i] = i + 1;
                data[1][i] = languageValues.get(i);
            }
            dataset.addSeries(language, data);
        }
        return dataset;
    }

    private void addAverageValuesLabels(JPanel leftPanel, GridBagConstraints constraints) {
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel averageValues = new JLabel("Averages Values:\t\t");
        averageValues.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
        averageValues.setForeground(new Color(15, 152, 190));
        leftPanel.add(averageValues);

        JLabel cppLabel = new JLabel("-> C++:");
        cppLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        leftPanel.add(cppLabel);

        cppAvgLabel = new JLabel();
        leftPanel.add(cppAvgLabel);

        JLabel javaLabel = new JLabel("-> Java:");
        javaLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        leftPanel.add(javaLabel);

        javaAvgLabel = new JLabel();
        leftPanel.add(javaAvgLabel);

        JLabel pythonLabel = new JLabel("-> Python:");
        pythonLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        leftPanel.add(pythonLabel);

        pythonAvgLabel = new JLabel();
        leftPanel.add(pythonAvgLabel);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        this.getContentPane().add(leftPanel, constraints);
    }

    private void setAverageLabel(Language language, Map<Language, Double> languageAverageMap) {
        switch (language) {
            case CPP:
                cppAvgLabel.setText("\t\t\t\t" + languageAverageMap.get(language) + " ns");
                break;

            case JAVA:
                javaAvgLabel.setText("\t\t\t\t" + languageAverageMap.get(language) + " ns");
                break;

            case PYTHON:
                double average = languageAverageMap.get(language);
                if (average == -1.0) {
                    pythonAvgLabel.setText("\t\tImpossible");
                } else {
                    pythonAvgLabel.setText("\t\t\t\t" + languageAverageMap.get(language) + " ns");
                }
                break;

            default:
                break;
        }
    }

    private String getLabel(String command) {
        switch (command) {
            case "allocateStatically":
                return "Allocate Statically";

            case "allocateDynamically":
                return "Allocate Dynamically";

            case "accessStatically":
                return "Access Statically";

            case "accessDynamically":
                return "Access Dynamically";

            case "threadCreation":
                return "Thread Creation";

            case "threadContextSwitch":
                return "Thread Context Switch";

            case "insertIntoList":
                return "Insert into List";

            case "removeFromList":
                return "Remove from List";

            case "sortList":
                return "Sort List";

            default:
                System.out.println("Invalid command");
                break;
        }
        return "Invalid command";
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
}
