package agendaDeContatos.console;

import java.sql.*;

public class DaoContato {
    private Connection con;

    public DaoContato(Connection con) {
        this.con = con;
    }

    public int inserir(Contatos contato) {
        String sql = "INSERT INTO contato (nome, email, telefone, endereco, sexo, estado_civil) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement cmd = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            cmd.setString(1, contato.getNome());
            cmd.setString(2, contato.getEmail());
            cmd.setString(3, contato.getTelefone());
            cmd.setString(4, contato.getEndereco());
            cmd.setString(5, contato.getSexo());
            cmd.setString(6, contato.getEstadoCivil());

            if (cmd.executeUpdate() > 0) {
                ResultSet rs = cmd.getGeneratedKeys();
                return rs.next() ? rs.getInt(1) : -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void listar() {
        String sql = "SELECT * FROM contato";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Telefone: " + rs.getString("telefone"));
                System.out.println("Endereço: " + rs.getString("endereco"));
                System.out.println("Sexo: " + rs.getString("sexo"));
                System.out.println("Estado Civil: " + rs.getString("estado_civil"));
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM contato WHERE id = ?";
        try (PreparedStatement cmd = con.prepareStatement(sql)) {
            cmd.setInt(1, id);
            return cmd.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean atualizar(Contatos contato) {
        String sql = "UPDATE contato SET nome = ?, email = ?, telefone = ?, endereco = ?, sexo = ?, estado_civil = ? WHERE id = ?";
        try (PreparedStatement cmd = con.prepareStatement(sql)) {
            cmd.setString(1, contato.getNome());
            cmd.setString(2, contato.getEmail());
            cmd.setString(3, contato.getTelefone());
            cmd.setString(4, contato.getEndereco());
            cmd.setString(5, contato.getSexo());
            cmd.setString(6, contato.getEstadoCivil());
            cmd.setInt(7, contato.getId());

            return cmd.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}