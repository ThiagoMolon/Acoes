// Arquivo: src/main/java/br/com/molim/projeto/acao/telas/TelaAdicionar.java
package screens;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mainPackage.TelaEscolha;
import utility.Acao;
import utility.AcaoDAO;

public class TelaAdicionar extends JFrame {
    private JTextField txtNome;
    private JFormattedTextField txtRendimento;
    private JFormattedTextField txtValor;
    private JButton btnSalvar;
    private JButton btnCancelar;
    private TelaEscolha telaEscolha;

    public TelaAdicionar(TelaEscolha telaEscolha) {
        this.telaEscolha = telaEscolha;
        setTitle("Adicionar Nova Ação");
        setSize(400, 250);
        setLocationRelativeTo(telaEscolha);
        setLayout(new BorderLayout(10, 10));
        
        JPanel painelForm = new JPanel(new GridLayout(0, 2, 10, 10));
        painelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        txtNome = new JTextField();
        txtRendimento = new JFormattedTextField();
        txtValor = new JFormattedTextField();
        
        painelForm.add(new JLabel("Nome da Ação:"));
        painelForm.add(txtNome);
        painelForm.add(new JLabel("Rendimento Anual (%):"));
        painelForm.add(txtRendimento);
        painelForm.add(new JLabel("Valor por Ação (R$):"));
        painelForm.add(txtValor);
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnSalvar);
        
        add(painelForm, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
        
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = txtNome.getText().trim();
                    double rendimento = Double.parseDouble(txtRendimento.getText().replace(",", "."));
                    double valor = Double.parseDouble(txtValor.getText().replace(",", "."));
                    
                    if (nome.isEmpty()) {
                        JOptionPane.showMessageDialog(TelaAdicionar.this, "O nome da ação não pode estar vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if (rendimento <= 0 || valor <= 0) {
                        JOptionPane.showMessageDialog(TelaAdicionar.this, "Rendimento e valor devem ser maiores que zero.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    Acao novaAcao = new Acao();
                    novaAcao.setNome(nome);
                    novaAcao.setRendimento(rendimento);
                    novaAcao.setValor(valor);
                    
                    new AcaoDAO().adicionarAcao(novaAcao);
                    telaEscolha.carregarAcoes();
                    JOptionPane.showMessageDialog(TelaAdicionar.this, "Ação adicionada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(TelaAdicionar.this, "Por favor, insira valores numéricos válidos para rendimento e valor.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(TelaAdicionar.this, "Erro ao adicionar ação: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        btnCancelar.addActionListener(e -> dispose());
    }
}