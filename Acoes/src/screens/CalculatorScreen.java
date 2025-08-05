package screens;

import javax.swing.*;

import utility.NumberUtils;
import utility.Stocks;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CalculatorScreen extends JFrame {
    private Stocks stock;
    private JTabbedPane tabbedPane;
    private JFormattedTextField txtMonthlyInvestment;
    private JFormattedTextField txtDesiredAmount;
    private JFormattedTextField txtFixedInvestment;
    private JTextArea txtResult;

    public CalculatorScreen(Stocks stock) {
        this.stock = stock;
        setTitle("Dividend Calculator - " + stock.getName());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        DecimalFormat df = new DecimalFormat("#,##0.00");
        JLabel lblInfo = new JLabel("<html><b>Stock Information:</b><br>" +
                "Name: " + stock.getName() + "<br>" +
                "Annual Yield: " + stock.getYield() + "%<br>" +
                "Value per Stock: R$ " + df.format(stock.getValue()) + "</html>");

        topPanel.add(lblInfo);
        add(topPanel, BorderLayout.NORTH);

        // Create tabs
        tabbedPane = new JTabbedPane();

        // Tab 1: Yield in 12 months
        JPanel monthlyInvestmentPanel = createMonthlyInvestmentPanel();
        tabbedPane.addTab("Yield in 12 Months", monthlyInvestmentPanel);

        // Tab 2: Time to return
        JPanel timeToReturnPanel = createTimeToReturnPanel();
        tabbedPane.addTab("Time to Return", timeToReturnPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Results panel
        txtResult = new JTextArea();
        txtResult.setEditable(false);
        txtResult.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollResult = new JScrollPane(txtResult);
        scrollResult.setBorder(BorderFactory.createTitledBorder("Results"));
        add(scrollResult, BorderLayout.SOUTH);
    }

    private JPanel createMonthlyInvestmentPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtMonthlyInvestment = new JFormattedTextField(NumberFormat.getCurrencyInstance());
        txtMonthlyInvestment.setValue(1000.0);
        JButton btnCalculate = new JButton("Calculate Yield");

        panel.add(new JLabel("Monthly Investment ($):"));
        panel.add(txtMonthlyInvestment);
        panel.add(new JLabel());
        panel.add(btnCalculate);

        btnCalculate.addActionListener(e -> calculateMonthlyYield());

        return panel;
    }

    private JPanel createTimeToReturnPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtDesiredAmount = new JFormattedTextField(NumberFormat.getCurrencyInstance());
        txtFixedInvestment = new JFormattedTextField(NumberFormat.getCurrencyInstance());
        txtFixedInvestment.setValue(1000.0);
        JButton btnCalculate = new JButton("Calculate Time");

        panel.add(new JLabel("Desired Amount ($):"));
        panel.add(txtDesiredAmount);
        panel.add(new JLabel("Monthly Investment ($):"));
        panel.add(txtFixedInvestment);
        panel.add(new JLabel());
        panel.add(btnCalculate);

        btnCalculate.addActionListener(e -> calculateTimeToReturn());

        return panel;
    }

    private void calculateMonthlyYield() {
        try {
            double monthlyInvestment = NumberUtils.getDoubleFromFormattedField(txtMonthlyInvestment);

            if (monthlyInvestment <= 0) {
                JOptionPane.showMessageDialog(this,
                    "Monthly investment must be greater than zero.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double monthlyYield = stock.getYield() / 12 / 100;
            double accumulatedValue = 0;
            double totalInvested = 0;

            StringBuilder result = new StringBuilder();
            result.append("Monthly investment simulation: R$ ")
                     .append(String.format("%,.2f", monthlyInvestment))
                     .append("\n");
            result.append("Monthly Yield: ")
                     .append(String.format("%.4f", monthlyYield * 100))
                     .append("%\n\n");

            result.append(String.format("%-10s %-15s %-15s %-15s\n",
                "Month", "Investment", "Yield", "Accumulated"));
            result.append("------------------------------------------------\n");

            DecimalFormat df = new DecimalFormat("#,##0.00");

            for (int month = 1; month <= 12; month++) {
                totalInvested += monthlyInvestment;
                double yieldThisMonth = accumulatedValue * monthlyYield;
                accumulatedValue += monthlyInvestment + yieldThisMonth;

                result.append(String.format("%-10d %-15s %-15s %-15s\n",
                    month,
                    df.format(monthlyInvestment),
                    df.format(yieldThisMonth),
                    df.format(accumulatedValue)
                )
               );
            }

            result.append("\nFinal Summary:\n");
            result.append("Total Invested: R$ ")
                     .append(df.format(totalInvested))
                     .append("\n");
            result.append("Yields: R$ ")
                     .append(df.format(accumulatedValue - totalInvested))
                     .append("\n");
            result.append("Final Amount: R$ ")
                     .append(df.format(accumulatedValue))
                     .append("\n");

            txtResult.setText(result.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Calculation error: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateTimeToReturn() {
        try {
            double desiredAmount = utility.NumberUtils.getDoubleFromFormattedField(txtDesiredAmount);
            double monthlyInvestment = utility.NumberUtils.getDoubleFromFormattedField(txtFixedInvestment);

            if (desiredAmount <= 0 || monthlyInvestment <= 0) {
                JOptionPane.showMessageDialog(this,
                    "Values must be greater than zero.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double monthlyYield = stock.getYield() / 12 / 100;
            double accumulatedValue = 0;
            int month = 0;

            StringBuilder result = new StringBuilder();
            result.append("Simulation to reach R$ ")
                     .append(String.format("%,.2f", desiredAmount))
                     .append("\n");
            result.append("Monthly investment: R$ ")
                     .append(String.format("%,.2f", monthlyInvestment))
                     .append("\n");
            result.append("Monthly Yield: ")
                     .append(String.format("%.4f", monthlyYield * 100))
                     .append("%\n\n");

            result.append(String.format("%-10s %-15s %-15s %-15s\n",
                "Month", "Investment", "Yield", "Accumulated"));
            result.append("------------------------------------------------\n");

            DecimalFormat df = new DecimalFormat("#,##0.00");

            while (accumulatedValue < desiredAmount) {
                month++;
                double yieldThisMonth = accumulatedValue * monthlyYield;
                accumulatedValue += monthlyInvestment + yieldThisMonth;

                if (month % 12 == 0 || accumulatedValue >= desiredAmount) {
                    result.append(String.format("%-10d %-15s %-15s %-15s\n",
                        month,
                        df.format(monthlyInvestment),
                        df.format(yieldThisMonth),
                        df.format(accumulatedValue))
                    );
                }

                if (month > 600) { // Limit of 50 years
                    result.append("\nReached the maximum limit of 50 years (600 months) without achieving the desired amount.");
                    break;
                }
            }

            result.append("\nFinal Summary:\n");
            result.append("Months needed: ").append(month).append("\n");
            result.append("Years needed: ").append(String.format("%.1f", month / 12.0)).append("\n");
            result.append("Total Invested: R$ ")
                     .append(df.format(month * monthlyInvestment))
                     .append("\n");
            result.append("Final Amount: R$ ")
                     .append(df.format(accumulatedValue))
                     .append("\n");

            txtResult.setText(result.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Calculation error: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}