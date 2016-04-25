package br.univel_Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

/*
 * protectedabstract String getCreateTable(Connection con, Object obj);
   protectedabstract String getDropTable(Connection con, Object obj);
// DML
   protectedabstract PreparedStatement getSqlInsert(Connection con, Object obj);
   protectedabstract PreparedStatement getSqlSelectAll(Connection con, Object obj);
   protectedabstract PreparedStatement getSqlSelectById(Connection con, Object obj);
   protectedabstract PreparedStatement getSqlUpdateById(Connection con, Object obj);
   protectedabstract PreparedStatement getSqlDeleteById(Connection con, Object obj);
 */
public abstract class SQL_P {

	protected abstract String getCreateTable(Connection con, Object obj);
	protected abstract String getDropTable(Connection con, Object obj);

	protected abstract PreparedStatement getSqlInsert(Connection con, Object obj);
	protected abstract PreparedStatement getSqlSelectAll(Connection con, Object obj);
	protected abstract PreparedStatement getSqlSelectById(Connection con, Object obj);
	protected abstract PreparedStatement getSqlUpdateById(Connection con, Object obj);
	protected abstract PreparedStatement getSqlDeleteById(Connection con, Object obj);
	
	

}
