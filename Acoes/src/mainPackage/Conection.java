package mainPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Conection {

    // Informações da sua conexão
    private static final String URL = "jdbc:postgresql://localhost:5432/java-testes";
    private static final String USUARIO = "postegre";
    private static final String SENHA = "1234";

    public static void main(String[] args) {
    	Stocks acao = new Stocks();
    	int idAcao = acao.getID();
		double rmAcao = acao.getRendimentoPacao();
    	String  nmAcao = acao.getnome();
    	double  preco = acao.getPreco();
    	
    	
        // A instrução SQL para inserir
        String sql = "INSERT INTO Acao (idAcao , nmAcao, rmAcao , preco) VALUES (?, ?, ?, ?)";

        try (
            // 1. Estabelece a conexão com o banco de dados
            Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
            // 2. Cria o PreparedStatement com a instrução SQL
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            
			// 3. Define os valores para os placeholders (?)
            // O primeiro parâmetro (1) refere-se ao primeiro '?'
        	stmt.setInt(1, idAcao);
        	
            stmt.setString(2, nmAcao);

            stmt.setDouble(3, rmAcao);
            
            stmt.setDouble(4, preco);

            // 4. Executa a instrução SQL
            // executeUpdate() é usado para INSERT, UPDATE e DELETE
            int linhasAfetadas = stmt.executeUpdate();

            System.out.println(linhasAfetadas + " linha(s) inserida(s) com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

}