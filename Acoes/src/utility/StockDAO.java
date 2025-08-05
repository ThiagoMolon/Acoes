package utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import utility.Conection;
import utility.Stocks;

public class StockDAO {

    public void addStock(Stocks stock) throws SQLException {
        String sql = "INSERT INTO acao (nome, rendimento, valor) VALUES (?, ?, ?)";
        try (Connection conn = Conection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             
            ps.setString(1, stock.getName());
            ps.setDouble(2, stock.getYield());
            ps.setDouble(3, stock.getValue());
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    stock.setId(rs.getInt(1));
                }
            }
        }
    }

    public List<Stocks> listStocks() {
        List<Stocks> stocks = new ArrayList<>();
        String sql = "SELECT * FROM acao";
        
        try (Connection conn = Conection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                Stocks stock = new Stocks();
                stock.setId(rs.getInt("id"));
                stock.setName(rs.getString("nome"));
                stock.setYield(rs.getDouble("rendimento"));
                stock.setValue(rs.getDouble("valor"));
                stocks.add(stock);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar ações", e);
        }
        return stocks;
    }

    public void updateStock(Stocks stock) throws SQLException {
        String sql = "UPDATE acao SET nome = ?, rendimento = ?, valor = ? WHERE id = ?";
        try (Connection conn = Conection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, stock.getName());
            ps.setDouble(2, stock.getYield());
            ps.setDouble(3, stock.getValue());
            ps.setInt(4, stock.getId());
            ps.executeUpdate();
        }
    }

    public void deleteStock(int id) throws SQLException {
        String sql = "DELETE FROM acao WHERE id = ?";
        try (Connection conn = Conection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    public Stocks findById(int id) {
        String sql = "SELECT * FROM acao WHERE id = ?";
        try (Connection conn = Conection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Stocks stock = new Stocks();
                    stock.setId(rs.getInt("id"));
                    stock.setName(rs.getString("nome"));
                    stock.setYield(rs.getDouble("rendimento"));
                    stock.setValue(rs.getDouble("valor"));
                    return stock;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ação por ID", e);
        }
        return null;
    }
}