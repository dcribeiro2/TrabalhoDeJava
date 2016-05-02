package br.univel_Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Teste_Sql {

	 private Connection con;
	    private static Teste_Sql inst;
	    private String driver = "org.postgresql.Driver";
	    private String url = "jdbc:postgresql://192.168.99.100:5432/sqlgen";
	    private String username = "postgres";
	    private String password = "{root}";

	    public Teste_Sql() {

	    }

	    public static Teste_Sql getInstance() {
	        if (inst == null)
	            return inst = new Teste_Sql();
	        return inst;
	    }

	    public Connection open() {
	        try {
	            Class.forName(driver);
	            con = DriverManager.getConnection(url, username, password);
	            return con;

	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public void close() {
	        try {
	            con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	

	

}
