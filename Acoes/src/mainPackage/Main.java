package mainPackage;

import javax.swing.*;

import screens.AddScreen;
import screens.CalculatorScreen;
import screens.EditScreen;
import utility.Conection;
import utility.StockDAO;
import utility.Stocks;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Main extends JFrame {
    private JComboBox<Stocks> stockComboBox;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnCalculate;

    public Main() {
        setTitle("Dividend Calculator");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Selection Panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        JPanel selectionPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Select a Stock"));
        
        stockComboBox = new JComboBox<>();
        loadStocks();
        selectionPanel.add(new JLabel("Available Stocks:"));
        selectionPanel.add(stockComboBox);
        add(selectionPanel, gbc);

        // Buttons Panel
        gbc.gridy = 1;
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        btnAdd = new JButton("Add New Stock");
        btnEdit = new JButton("Edit Selected Stock");
        btnDelete = new JButton("Delete Selected Stock");
        btnCalculate = new JButton("Calculate Yields");
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnCalculate);
        
        add(buttonPanel, gbc);
        
        // Configure button actions
        btnAdd.addActionListener(e -> {
            AddScreen addScreen = new AddScreen(this);
            addScreen.setVisible(true);
        });

        btnEdit.addActionListener(e -> {
            Stocks selectedStock = (Stocks) stockComboBox.getSelectedItem();
            if (selectedStock != null) {
                EditScreen editScreen = new EditScreen(this, selectedStock);
                editScreen.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Select a stock to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnDelete.addActionListener(e -> {
            Stocks selectedStock = (Stocks) stockComboBox.getSelectedItem();
            if (selectedStock != null) {
                int confirm = JOptionPane.showConfirmDialog(
                    this, 
                    "Are you sure you want to delete the stock '" + selectedStock.getName() + "'?",
                    "Deletion Confirmation",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        new StockDAO().deleteStock(selectedStock.getId());
                        loadStocks();
                        JOptionPane.showMessageDialog(this, "Stock deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error deleting stock: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a stock to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnCalculate.addActionListener(e -> {
            Stocks selectedStock = (Stocks) stockComboBox.getSelectedItem();
            if (selectedStock != null) {
                CalculatorScreen calculatorScreen = new CalculatorScreen(selectedStock);
                calculatorScreen.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Select a stock to calculate.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        setLocationRelativeTo(null);
    }

    public void loadStocks() {
        SwingUtilities.invokeLater(() -> {
            stockComboBox.removeAllItems();
            List<Stocks> stocks = new StockDAO().listStocks();
            for (Stocks stock : stocks) {
                stockComboBox.addItem(stock);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Main().setVisible(true);
        });
    }
}