package br.univel_Conexao;

import java.sql.Connection;

public class Conexao {
	
	private Connection con;
	private static Conexao con_DB;
	private String url = "jdbc:h2:-test" ;
	private String driver = "org.h2.Driver";
	private String usuario = "sa";

	
	
}
