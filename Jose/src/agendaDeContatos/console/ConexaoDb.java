package agendaDeContatos.console;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDb {
    private static final String URL = "jdbc:mysql://localhost:3306/agenda";
    private static final String USER = ""; // Troque pelo seu usuário do MySQL
    private static final String PASSWORD = ""; // Troque pela sua senha do MySQL

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void desconectar(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}