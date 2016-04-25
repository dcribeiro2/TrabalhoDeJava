package br.univel_Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Teste_Sql extends SQL_P {

	@Override
	protected String getCreateTable(Connection con, Object obj) {
		
		
		return null;
	}

	@Override
	protected String getDropTable(Connection con, Object obj) {
		return null;
	}

	@Override
	protected PreparedStatement getSqlInsert(Connection con, Object obj) {
		return null;
	}

	@Override
	protected PreparedStatement getSqlSelectAll(Connection con, Object obj) {
		return null;
	}

	@Override
	protected PreparedStatement getSqlSelectById(Connection con, Object obj) {
		return null;
	}

	@Override
	protected PreparedStatement getSqlUpdateById(Connection con, Object obj) {
		return null;
	}

	@Override
	protected PreparedStatement getSqlDeleteById(Connection con, Object obj) {
		return null;
	}

}
