// Arquivo: src/main/java/br/com/molim/projeto/acao/telas/TelaEscolha.java
package mainPackage;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import screens.TelaAdicionar;
import screens.TelaCalculadora;
import screens.TelaEditar;
import utility.Acao;
import utility.AcaoDAO;

public class TelaEscolha extends JFrame {
    private JComboBox<Acao> comboBoxAcoes;
    private JButton btnAdicionar;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnCalcular;

    public TelaEscolha() {
        setTitle("Calculadora de Dividendos");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Painel de seleção
        JPanel painelSelecao = new JPanel(new GridLayout(0, 1, 10, 10));
        painelSelecao.setBorder(BorderFactory.createTitledBorder("Selecione uma ação"));
        
        comboBoxAcoes = new JComboBox<>();
        carregarAcoes();
        painelSelecao.add(new JLabel("Ações disponíveis:"));
        painelSelecao.add(comboBoxAcoes);
        
        // Painel de botões
        JPanel painelBotoes = new JPanel(new GridLayout(0, 1, 10, 10));
        btnAdicionar = new JButton("Adicionar Nova Ação");
        btnEditar = new JButton("Editar Ação Selecionada");
        btnExcluir = new JButton("Excluir Ação Selecionada");
        btnCalcular = new JButton("Calcular Rendimentos");
        
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnCalcular);
        
        painelPrincipal.add(painelSelecao, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(painelPrincipal);
        
        // Configurar ações dos botões
        btnAdicionar.addActionListener(e -> {
            TelaAdicionar telaAdicionar = new TelaAdicionar(this);
            telaAdicionar.setVisible(true);
        });

        btnEditar.addActionListener(e -> {
            Acao acaoSelecionada = (Acao) comboBoxAcoes.getSelectedItem();
            if (acaoSelecionada != null) {
                TelaEditar telaEditar = new TelaEditar(acaoSelecionada, this);
                telaEditar.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma ação para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnExcluir.addActionListener(e -> {
            Acao acaoSelecionada = (Acao) comboBoxAcoes.getSelectedItem();
            if (acaoSelecionada != null) {
                int confirm = JOptionPane.showConfirmDialog(
                    this, 
                    "Tem certeza que deseja excluir a ação '" + acaoSelecionada.getNome() + "'?",
                    "Confirmação de Exclusão",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        new AcaoDAO().excluirAcao(acaoSelecionada.getId());
                        carregarAcoes();
                        JOptionPane.showMessageDialog(this, "Ação excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir ação: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma ação para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnCalcular.addActionListener(e -> {
            Acao acaoSelecionada = (Acao) comboBoxAcoes.getSelectedItem();
            if (acaoSelecionada != null) {
                TelaCalculadora telaCalculadora = new TelaCalculadora(acaoSelecionada);
                telaCalculadora.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma ação para calcular.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    public void carregarAcoes() {
        SwingUtilities.invokeLater(() -> {
            comboBoxAcoes.removeAllItems();
            java.util.List<Acao> acoes = new AcaoDAO().listarAcoes();
            for (Acao acao : acoes) {
                comboBoxAcoes.addItem(acao);
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
            
            TelaEscolha tela = new TelaEscolha();
            tela.setLocationRelativeTo(null);
            tela.setVisible(true);
        });
    }
}