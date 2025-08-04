package mainPackage;
import java.util.Scanner;

public class User {

	int code;
	
	private boolean tempoandobjetivo;
	private int quantinvest;
	private int quantacao;
	
	Scanner leia = new Scanner(System.in);
	User user = new User();
	
	public void setTempoAndObjetivo(int code) {
		System.out.print("Escolha \n"
				+ "1. O seu objetivo é investindo uma quantia por mês \n"
				+ "2. O seu objetivo é saber em quanto tempo investindo você chegara naquela quantia");
		code = leia.nextInt();
		switch (code) {
		case 1:
			user.setTempoAndObjetivo(0);
		break;
		case 2:
			user.setTempoAndObjetivo(1);
		break;
		default:
			System.out.println("Número escolhido não está dentro entre as opções dadas!");
		break;
		}
	}
}
