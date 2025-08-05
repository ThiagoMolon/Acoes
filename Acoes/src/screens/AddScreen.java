package screens;

import javax.swing.*;

import mainPackage.Main;
import utility.NumberUtils;
import utility.StockDAO;
import utility.Stocks;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

public class AddScreen extends JDialog {
    private JTextField txtName;
    private JFormattedTextField txtYield;
    private JFormattedTextField txtValue;
    private JButton btnSave;
    private JButton btnCancel;
    private Main choiceScreen;

    public AddScreen(JFrame parent) {
        super(parent, "Add New Stock", true);
        this.choiceScreen = (Main) parent;
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Stock Name:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtName = new JTextField(20);
        add(txtName, gbc);

        // Yield
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Annual Yield (%):"), gbc);

        gbc.gridx = 1;
        txtYield = new JFormattedTextField(NumberFormat.getNumberInstance());
        txtYield.setColumns(10);
        add(txtYield, gbc);

        // Value
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Value per Stock ($):"), gbc);

        gbc.gridx = 1;
        txtValue = new JFormattedTextField(NumberFormat.getCurrencyInstance());
        txtValue.setColumns(10);
        add(txtValue, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnSave);
        add(buttonPanel, gbc);

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = txtName.getText().trim();
                    double yield = NumberUtils.getDoubleFromFormattedField(txtYield);
                    double value = NumberUtils.getDoubleFromFormattedField(txtValue);

                    if (name.isEmpty()) {
                        JOptionPane.showMessageDialog(AddScreen.this,
                            "The stock name cannot be empty.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (yield <= 0 || value <= 0) {
                        JOptionPane.showMessageDialog(AddScreen.this,
                            "Yield and value must be greater than zero.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Stocks newStock = new Stocks();
                    newStock.setName(name);
                    newStock.setYield(yield);
                    newStock.setValue(value);

                    new StockDAO().addStock(newStock);
                    choiceScreen.loadStocks();
                    JOptionPane.showMessageDialog(AddScreen.this,
                        "Stock added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AddScreen.this,
                        "Error adding stock: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancel.addActionListener(e -> dispose());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // Focus on the first field when the window opens
        SwingUtilities.invokeLater(() -> txtName.requestFocus());
    }
}