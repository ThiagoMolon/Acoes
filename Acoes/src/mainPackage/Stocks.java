package mainPackage;

public class Stocks {

	private double preco;
	private double rendimento;
	private double rendimentoPacao;
	private String nome;
	private String ID;
	
	public void setPreco(double preco) {
		this.preco = preco;
	}

	public void setRendimento(double rendimento) {
		this.rendimento = rendimento;
	}
	
	public void setRendimentoPacao(double rendimento) {
		this.rendimentoPacao = preco * (rendimento / 100);
	}
	
	public void setnome(String nome) {
		this.nome = nome;
	}
	
	public void setID(String ID) {
		this.ID = ID;
	}
	
	public double getPreco() {
		System.out.println(this.preco);
		return this.preco;
	}
	
	public double getRendimento() {
		System.out.println(this.rendimento);
		return this.rendimento;
	}
	
	public double getRendimentoPacao() {
		System.out.println(this.rendimentoPacao);
		return this.rendimentoPacao;
	}
	
	public String getnome() {
		System.out.println(this.nome);
		return this.nome;
	}
	
	public String getID() {
		System.out.println(this.ID);
		return this.ID;
	}
}
