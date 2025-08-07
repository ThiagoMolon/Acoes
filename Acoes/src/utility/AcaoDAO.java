package utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AcaoDAO {

    public void adicionarAcao(Acao acao) throws SQLException {
        String sql = "INSERT INTO acao (nome, rendimento, valor) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             
            ps.setString(1, acao.getNome());
            ps.setDouble(2, acao.getRendimento());
            ps.setDouble(3, acao.getValor());
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    acao.setId(rs.getInt(1));
                }
            }
        }
    }

    public List<Acao> listarAcoes() {
        List<Acao> acoes = new ArrayList<>();
        String sql = "SELECT * FROM acao";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                Acao acao = new Acao();
                acao.setId(rs.getInt("id"));
                acao.setNome(rs.getString("nome"));
                acao.setRendimento(rs.getDouble("rendimento"));
                acao.setValor(rs.getDouble("valor"));
                acoes.add(acao);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar ações", e);
        }
        return acoes;
    }

    public void atualizarAcao(Acao acao) throws SQLException {
        String sql = "UPDATE acao SET nome = ?, rendimento = ?, valor = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, acao.getNome());
            ps.setDouble(2, acao.getRendimento());
            ps.setDouble(3, acao.getValor());
            ps.setInt(4, acao.getId());
            ps.executeUpdate();
        }
    }

    public void excluirAcao(int id) throws SQLException {
        String sql = "DELETE FROM acao WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    public Acao buscarPorId(int id) {
        String sql = "SELECT * FROM acao WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Acao acao = new Acao();
                    acao.setId(rs.getInt("id"));
                    acao.setNome(rs.getString("nome"));
                    acao.setRendimento(rs.getDouble("rendimento"));
                    acao.setValor(rs.getDouble("valor"));
                    return acao;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ação por ID", e);
        }
        return null;
    }
}