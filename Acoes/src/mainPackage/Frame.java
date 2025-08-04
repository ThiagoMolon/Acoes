package mainPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Frame extends JFrame {

    private JTable tabelaObjetos;
    private DefaultTableModel modeloTabela;

    public Frame() {
        setTitle("Histórico de Objetos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a tela

        // Layout
        setLayout(new BorderLayout());

        // Tabela para exibir os dados
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "Valor"}, 0);
        tabelaObjetos = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaObjetos);
        add(scrollPane, BorderLayout.CENTER);

        // Botão para adicionar novo objeto
        JButton btnAdicionar = new JButton("Adicionar Novo Objeto");
        btnAdicionar.addActionListener(this::abrirTelaAdicao);
        
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnAdicionar);
        add(painelBotoes, BorderLayout.SOUTH);

        // Carrega os dados iniciais
        carregarDadosDoBanco();
    }

    private void carregarDadosDoBanco() {
        // Limpa a tabela
        modeloTabela.setRowCount(0);
        
        String url = "jdbc:sqlite:objetos.db";
        String sql = "SELECT id, nome, valor FROM objetos";
        
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                double valor = rs.getDouble("valor");
                modeloTabela.addRow(new Object[]{id, nome, valor});
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirTelaAdicao(ActionEvent e) {
        // Abre a tela de adição
        AddAcao telaAdicao = new AddAcao(this);
        telaAdicao.setVisible(true);
        this.setEnabled(false); // Desabilita a tela de histórico enquanto a outra está aberta
    }

    // Método para ser chamado pela tela de adição para atualizar a tabela
    public void atualizarDados() {
        carregarDadosDoBanco();
    }
    
    public static void main(String[] args) {
        // Cria o banco de dados e a tabela se não existirem
        criarTabelaObjetos();
        
        SwingUtilities.invokeLater(() -> {
            new Frame().setVisible(true);
        });
    }
    
    // Método para criar o banco de dados e a tabela
    public static void criarTabelaObjetos() {
        String url = "jdbc:sqlite:objetos.db";
        String sql = "CREATE TABLE IF NOT EXISTS objetos ("
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                   + "nome TEXT NOT NULL,"
                   + "valor REAL NOT NULL"
                   + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela: " + e.getMessage());
        }
    }
}