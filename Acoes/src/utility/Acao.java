package utility;

public class Acao {
    private int id;
    private String nome;
    private double rendimento;
    private double valor;

    public Acao() {}

    public Acao(int id, String nome, double rendimento, double valor) {
        this.id = id;
        this.nome = nome;
        this.rendimento = rendimento;
        this.valor = valor;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getRendimento() { return rendimento; }
    public void setRendimento(double rendimento) { this.rendimento = rendimento; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    @Override
    public String toString() {
        return nome;
    }
}