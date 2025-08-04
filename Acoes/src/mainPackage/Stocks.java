package mainPackage;

public class Stocks {

	private double preco;
	private  int rendimentoPacao;
	private String nome;
	private int ID;
	
	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	public void setRendimentoPacao(int rendimento) {
		this.rendimentoPacao = rendimento;
	}
	
	public void setnome(String nome) {
		this.nome = nome;
	}
	
	public int setID(int ID) {
		return this.ID = ID;
	}
	
	public double getPreco() {
		System.out.println(this.preco);
		return this.preco;
	}
	
	public double getRendimentoPacao() {
		System.out.println(this.rendimentoPacao);
		return this.rendimentoPacao;
	}
	
	public String getnome() {
		System.out.println(this.nome);
		return this.nome;
	}
	
	public int getID() {
		System.out.println(this.ID);
		return this.ID;
	}
}
