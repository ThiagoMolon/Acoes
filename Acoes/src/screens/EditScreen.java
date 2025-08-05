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

public class EditScreen extends JDialog {
    private JTextField txtName;
    private JFormattedTextField txtYield;
    private JFormattedTextField txtValue;
    private JButton btnSave;
    private JButton btnCancel;
    private Stocks stock;
    private Main choiceScreen;

    public EditScreen(JFrame parent, Stocks stock) {
        super(parent, "Edit Stock: " + stock.getName(), true);
        this.stock = stock;
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
        txtName = new JTextField(stock.getName(), 20);
        add(txtName, gbc);

        // Yield
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Annual Yield (%):"), gbc);

        gbc.gridx = 1;
        txtYield = new JFormattedTextField(NumberFormat.getNumberInstance());
        txtYield.setValue(stock.getYield());
        txtYield.setColumns(10);
        add(txtYield, gbc);

        // Value
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Value per Stock ($):"), gbc);

        gbc.gridx = 1;
        txtValue = new JFormattedTextField(NumberFormat.getCurrencyInstance());
        txtValue.setValue(stock.getValue());
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
                        JOptionPane.showMessageDialog(EditScreen.this,
                            "The stock name cannot be empty.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (yield <= 0 || value <= 0) {
                        JOptionPane.showMessageDialog(EditScreen.this,
                            "Yield and value must be greater than zero.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    stock.setName(name);
                    stock.setYield(yield);
                    stock.setValue(value);

                    new StockDAO().updateStock(stock);
                    choiceScreen.loadStocks();
                    JOptionPane.showMessageDialog(EditScreen.this,
                        "Stock updated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(EditScreen.this,
                        "Error updating stock: " + ex.getMessage(),
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
        SwingUtilities.invokeLater(() -> txtName.requestFocusInWindow());
    }
}