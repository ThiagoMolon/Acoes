package mainPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddAcao extends JFrame {

    private JTextField txtNome;
    private JTextField txtValor;
    private Frame telaPai;

    public AddAcao(Frame telaPai) {
        this.telaPai = telaPai;
        setTitle("Adicionar Novo Objeto");
        setSize(300, 200);
        setLocationRelativeTo(telaPai); // Centraliza a tela em relação à tela principal
        
        // Listener para reabilitar a tela principal ao fechar esta
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                telaPai.setEnabled(true);
            }
        });

        // Layout
        setLayout(new GridLayout(3, 2, 10, 10));
        
        // Componentes
        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField();
        JLabel lblValor = new JLabel("Valor:");
        txtValor = new JTextField();
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(this::salvarObjeto);
        
        add(lblNome);
        add(txtNome);
        add(lblValor);
        add(txtValor);
        add(new JLabel()); // Espaço em branco para alinhamento
        add(btnSalvar);
    }
    
    private void salvarObjeto(ActionEvent e) {
        String nome = txtNome.getText();
        String valorStr = txtValor.getText();
        
        if (nome.isEmpty() || valorStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            double valor = Double.parseDouble(valorStr);
            
            String url = "jdbc:sqlite:objetos.db";
            String sql = "INSERT INTO objetos(nome, valor) VALUES(?,?)";
            
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setString(1, nome);
                pstmt.setDouble(2, valor);
                pstmt.executeUpdate();
                
                JOptionPane.showMessageDialog(this, "Objeto salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
                // Fecha a tela de adição
                this.dispose();
                
                // Reabilita e atualiza a tela principal
                telaPai.setEnabled(true);
                telaPai.atualizarDados();
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O valor deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}