// Arquivo: src/main/java/br/com/molim/projeto/acao/telas/TelaCalculadora.java
package screens;

import java.awt.*;
import java.text.DecimalFormat;
import javax.swing.*;
import utility.Acao;

public class TelaCalculadora extends JFrame {
    private Acao acao;
    private JTabbedPane tabbedPane;
    private JFormattedTextField txtInvestimentoMensal;
    private JFormattedTextField txtMontanteDesejado;
    private JFormattedTextField txtInvestimentoFixo;
    private JTextArea txtResultado;

    public TelaCalculadora(Acao acao) {
        this.acao = acao;
        setTitle("Calculadora de Dividendos - " + acao.getNome());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        JPanel painelSuperior = new JPanel();
        painelSuperior.setLayout(new BoxLayout(painelSuperior, BoxLayout.Y_AXIS));
        painelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        DecimalFormat df = new DecimalFormat("#,##0.00");
        JLabel lblInfo = new JLabel("<html><b>Informações da Ação:</b><br>" +
                "Nome: " + acao.getNome() + "<br>" +
                "Rendimento Anual: " + acao.getRendimento() + "%<br>" +
                "Valor por Ação: R$ " + df.format(acao.getValor()) + "</html>");
        
        painelSuperior.add(lblInfo);
        add(painelSuperior, BorderLayout.NORTH);
        
        // Criar abas
        tabbedPane = new JTabbedPane();
        
        // Aba 1: Rendimento em 12 meses
        JPanel painelInvestimentoMensal = criarPainelInvestimentoMensal();
        tabbedPane.addTab("Rendimento em 12 Meses", painelInvestimentoMensal);
        
        // Aba 2: Tempo para retorno
        JPanel painelTempoRetorno = criarPainelTempoRetorno();
        tabbedPane.addTab("Tempo para Retorno", painelTempoRetorno);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Painel de resultados
        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollResultado = new JScrollPane(txtResultado);
        scrollResultado.setBorder(BorderFactory.createTitledBorder("Resultados"));
        add(scrollResultado, BorderLayout.SOUTH);
    }
    
    private JPanel criarPainelInvestimentoMensal() {
        JPanel painel = new JPanel(new GridLayout(0, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        txtInvestimentoMensal = new JFormattedTextField();
        txtInvestimentoMensal.setText("1000");
        JButton btnCalcular = new JButton("Calcular Rendimento");
        
        painel.add(new JLabel("Investimento Mensal (R$):"));
        painel.add(txtInvestimentoMensal);
        painel.add(new JLabel());
        painel.add(btnCalcular);
        
        btnCalcular.addActionListener(e -> calcularRendimentoMensal());
        
        return painel;
    }
    
    private JPanel criarPainelTempoRetorno() {
        JPanel painel = new JPanel(new GridLayout(0, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        txtMontanteDesejado = new JFormattedTextField();
        txtInvestimentoFixo = new JFormattedTextField();
        txtInvestimentoFixo.setText("1000");
        JButton btnCalcular = new JButton("Calcular Tempo");
        
        painel.add(new JLabel("Montante Desejado (R$):"));
        painel.add(txtMontanteDesejado);
        painel.add(new JLabel("Investimento Mensal (R$):"));
        painel.add(txtInvestimentoFixo);
        painel.add(new JLabel());
        painel.add(btnCalcular);
        
        btnCalcular.addActionListener(e -> calcularTempoRetorno());
        
        return painel;
    }
    
    private void calcularRendimentoMensal() {
        try {
            double investimentoMensal = Double.parseDouble(txtInvestimentoMensal.getText().replace(",", "."));
            if (investimentoMensal <= 0) {
                JOptionPane.showMessageDialog(this, "O investimento mensal deve ser maior que zero.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double rendimentoMensal = acao.getRendimento() / 12 / 100;
            double valorAcumulado = 0;
            double totalInvestido = 0;
            
            StringBuilder resultado = new StringBuilder();
            resultado.append("Simulação de investimento mensal: R$ ").append(String.format("%,.2f", investimentoMensal)).append("\n");
            resultado.append("Rendimento mensal: ").append(String.format("%.4f", rendimentoMensal * 100)).append("%\n\n");
            
            resultado.append(String.format("%-10s %-15s %-15s %-15s\n", 
                "Mês", "Investimento", "Rendimento", "Acumulado"));
            resultado.append("------------------------------------------------\n");
            
            DecimalFormat df = new DecimalFormat("#,##0.00");
            
            for (int mes = 1; mes <= 12; mes++) {
                totalInvestido += investimentoMensal;
                double rendimentoMes = valorAcumulado * rendimentoMensal;
                valorAcumulado += investimentoMensal + rendimentoMes;
                
                resultado.append(String.format("%-10d %-15s %-15s %-15s\n", 
                    mes, 
                    df.format(investimentoMensal), 
                    df.format(rendimentoMes), 
                    df.format(valorAcumulado)
                ));
            }
            
            resultado.append("\nResumo Final:\n");
            resultado.append("Total Investido: R$ ").append(df.format(totalInvestido)).append("\n");
            resultado.append("Rendimentos: R$ ").append(df.format(valorAcumulado - totalInvestido)).append("\n");
            resultado.append("Montante Final: R$ ").append(df.format(valorAcumulado)).append("\n");
            
            txtResultado.setText(resultado.toString());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um valor numérico válido para o investimento mensal.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void calcularTempoRetorno() {
        try {
            double montanteDesejado = Double.parseDouble(txtMontanteDesejado.getText().replace(",", "."));
            double investimentoMensal = Double.parseDouble(txtInvestimentoFixo.getText().replace(",", "."));
            
            if (montanteDesejado <= 0 || investimentoMensal <= 0) {
                JOptionPane.showMessageDialog(this, "Os valores devem ser maiores que zero.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double rendimentoMensal = acao.getRendimento() / 12 / 100;
            double valorAcumulado = 0;
            int mes = 0;
            
            StringBuilder resultado = new StringBuilder();
            resultado.append("Simulação para atingir R$ ").append(String.format("%,.2f", montanteDesejado)).append("\n");
            resultado.append("Investimento mensal: R$ ").append(String.format("%,.2f", investimentoMensal)).append("\n");
            resultado.append("Rendimento mensal: ").append(String.format("%.4f", rendimentoMensal * 100)).append("%\n\n");
            
            resultado.append(String.format("%-10s %-15s %-15s %-15s\n", 
                "Mês", "Investimento", "Rendimento", "Acumulado"));
            resultado.append("------------------------------------------------\n");
            
            DecimalFormat df = new DecimalFormat("#,##0.00");
            
            while (valorAcumulado < montanteDesejado) {
                mes++;
                double rendimentoMes = valorAcumulado * rendimentoMensal;
                valorAcumulado += investimentoMensal + rendimentoMes;
                
                if (mes % 12 == 0 || valorAcumulado >= montanteDesejado) {
                    resultado.append(String.format("%-10d %-15s %-15s %-15s\n", 
                        mes, 
                        df.format(investimentoMensal), 
                        df.format(rendimentoMes), 
                        df.format(valorAcumulado))
                    );
                }
                
                if (mes > 600) { // Limite de 50 anos
                    resultado.append("\nAtingido o limite máximo de 50 anos (600 meses) sem alcançar o montante desejado.");
                    break;
                }
            }
            
            resultado.append("\nResumo Final:\n");
            resultado.append("Meses necessários: ").append(mes).append("\n");
            resultado.append("Anos necessários: ").append(String.format("%.1f", mes / 12.0)).append("\n");
            resultado.append("Total Investido: R$ ").append(df.format(mes * investimentoMensal)).append("\n");
            resultado.append("Montante Final: R$ ").append(df.format(valorAcumulado)).append("\n");
            
            txtResultado.setText(resultado.toString());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira valores numéricos válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}