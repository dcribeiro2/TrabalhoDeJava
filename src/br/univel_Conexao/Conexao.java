package br.univel_Conexao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class Conexao extends SQL_P {
    public Conexao() {
    }

    @Override
    public String getCreateTable(Connection con, Object obj) {
        try {
            String NomeTabela;
            Class<?> cl = obj.getClass();

            StringBuilder sb = new StringBuilder();

            if (cl.isAnnotationPresent(Tabela.class)) {
                Tabela AnnotationTab = cl.getAnnotation(Tabela.class);
                NomeTabela = AnnotationTab.value();
            } else {
                NomeTabela = cl.getSimpleName().toUpperCase();
            }
            sb.append("CREATE TABLE ").append(NomeTabela).append(" (");

            Field[] attributes = cl.getDeclaredFields();

            for (int i = 0; i < attributes.length; i++) {
                Field field = attributes[i];

                String nameColumn;
                String typeColumn = null;

                if (field.isAnnotationPresent(Coluna.class)) {
                    Coluna annotationColumn = field.getAnnotation(Coluna.class);

                    if (annotationColumn.nome().isEmpty()) {
                        nameColumn = field.getName().toUpperCase();
                    } else {
                        nameColumn = annotationColumn.nome();
                    }
                } else {
                    nameColumn = field.getName().toUpperCase();
                }

                Class<?> typeParemetros = field.getType();

                if (typeParemetros.equals(String.class)) {
                    if (field.getAnnotation(Coluna.class).tamanho() > -1) {
                        typeColumn = "VARCHAR(" + field.getAnnotation(Coluna.class).tamanho() + ")";
                    } else {
                        typeColumn = "VARCHAR(100)";
                    }
                } else if (typeParemetros.equals(int.class)){
                    if (field.getAnnotation(Coluna.class).pk() == true) {
                        typeColumn = "INT NOT NULL";
                    } else {
                        typeColumn = "INT";
                    }
                } else if (typeParemetros.isEnum()) {
                    typeColumn = "INT";
                }

                if (i > 0) sb.append(",");

                sb.append("\n\t").append(nameColumn).append(" ").append(typeColumn);

            }

            sb.append(",\n\tPRIMARY KEY(");
            for (int y = 0; y < attributes.length; y++) {
                int get = 0;
                Field fields = attributes[y];

                if (fields.isAnnotationPresent(Coluna.class)) {
                    Coluna annotationColumn = fields.getAnnotation(Coluna.class);

                    if (annotationColumn.pk()) {
                        if (get > 0) sb.append(", ");

                        if (annotationColumn.nome().isEmpty()) {
                            sb.append(fields.getName().toUpperCase());
                        } else {
                            sb.append(annotationColumn.nome());
                        }
                        get++;
                    }
                }
                if (y == attributes.length - 1) {
                    sb.append(")");
                }
            }
            sb.append("\n);");

            String create = sb.toString();
            System.out.println(create);
            Statement execute = con.createStatement();
            execute.executeUpdate(create);

            return create;

        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getDropTable(Connection con, Object obj) {
        try {
            String nameTable;
            StringBuilder sb = new StringBuilder();

            Class<?> cl = obj.getClass();

            if (cl.isAnnotationPresent(Tabela.class)) {
                Tabela table = cl.getAnnotation(Tabela.class);
                nameTable = table.value();
            } else {
                nameTable = cl.getSimpleName().toUpperCase();
            }

            sb.append("DROP TABLE ").append(nameTable).append(";");
            String drop = sb.toString();

            System.out.println(drop);
            Statement Conexao = con.createStatement();
            Conexao.executeUpdate(drop);
            return drop;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected PreparedStatement getSqlInsert(Connection con, Object obj) {
        Class<?> cl = obj.getClass();
        StringBuilder sb = new StringBuilder();
        String nomeTabela;

        if (cl.isAnnotationPresent(Tabela.class)) {
            Tabela table = cl.getAnnotation(Tabela.class);
            nomeTabela = table.value();
        } else {
            nomeTabela = cl.getSimpleName().toUpperCase();
        }

        sb.append("INSERT INTO ").append(nomeTabela).append(" (");

        Field[] attributes = cl.getDeclaredFields();

        for (int i = 0; i < attributes.length; i++) {
            Field field = attributes[i];
            String nomecoluna;

            if (field.isAnnotationPresent(Coluna.class)) {
                Coluna coluna = field.getAnnotation(Coluna.class);
                if (coluna.nome().isEmpty()) {
                    nomecoluna = field.getName().toUpperCase();
                } else {
                    nomecoluna = coluna.nome();
                }
            } else {
                nomecoluna = field.getName().toUpperCase();
            }

            if (i > 0) {
                sb.append(", ");
            }

            sb.append(nomecoluna);
        }

        sb.append(") VALUES (");

        for (int i = 0; i < attributes.length; i++) {
            if (i > 0) sb.append(", ");

            sb.append("?");
        }
        sb.append(")");
        String insert = sb.toString();
        System.out.println(insert);

        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(insert);

            for (int i = 0; i < attributes.length; i++) {
                Field field = attributes[i];
                Object type = field.getType();

                field.setAccessible(true);
                if (type.equals(int.class)) {
                    ps.setInt(i + 1, field.getInt(obj));
                } else if (type.equals(String.class)) {
                    ps.setString(i + 1, String.valueOf(field.get(obj)));
                } else if (field.getType().isEnum()) {
                    Object value = field.get(obj);
                    Method m = value.getClass().getMethod("ordinal");
                    ps.setInt(i + 1, (Integer) m.invoke(value, null));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return ps;
    }

    @Override
    protected PreparedStatement getSqlSelectAll(Connection con, Object obj) {
        Class<?> cl = obj.getClass();
        StringBuilder sb = new StringBuilder();
        String nomeTabela;

        if (cl.isAnnotationPresent(Tabela.class)) {
            nomeTabela = cl.getAnnotation(Tabela.class).value();
        } else {
            nomeTabela = cl.getSimpleName().toUpperCase();
        }
        sb.append("SELECT * FROM ").append(nomeTabela).append(";");

        String select = sb.toString();
        System.out.println(select);
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(select);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    @Override
    protected PreparedStatement getSqlSelectById(Connection con, Object obj, int id) {
        Class<?> cl = obj.getClass();
        StringBuilder sb = new StringBuilder();
        String nomeTabela;

        if (cl.isAnnotationPresent(Tabela.class)) {
            nomeTabela = cl.getAnnotation(Tabela.class).value();
        } else {
            nomeTabela = cl.getSimpleName().toUpperCase();
        }

        sb.append("SELECT * FROM ").append(nomeTabela).append(" WHERE ID=").append(id).append(";");
        String select = sb.toString();
        System.out.println(select);
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(select);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    @Override
    protected PreparedStatement getSqlUpdateById(Connection con, Object obj, int id) {
        Class<?> cl = obj.getClass();
        StringBuilder sb = new StringBuilder();
        String nomeTabela;

        if (cl.isAnnotationPresent(Tabela.class)) {
            nomeTabela = cl.getAnnotation(Tabela.class).value();
        } else {
            nomeTabela = cl.getSimpleName().toUpperCase();
        }

        sb.append("UPDATE ").append(nomeTabela).append(" SET ");

        Field[] attributes = cl.getDeclaredFields();

        for (int i = 0; i < attributes.length; i++) {
            Field field = attributes[i];
            String nomeColuna;

            if (field.isAnnotationPresent(Coluna.class)) {
                Coluna column = field.getAnnotation(Coluna.class);
                if (column.nome().isEmpty()) {
                    nomeColuna = field.getName().toUpperCase();
                } else {
                    nomeColuna = column.nome();
                }
            } else {
                nomeColuna = field.getName().toUpperCase();
            }

            if (i > 0) {
                sb.append(", ");
            }

            sb.append(nomeColuna).append(" = ?");
        }
        sb.append(" WHERE ID = ").append(id);
        String update = sb.toString();
        System.out.println(update);

        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(update);

            for (int i = 0; i < attributes.length; i++) {
                Field field = attributes[i];
                Object type = field.getType();

                field.setAccessible(true);
                if (type.equals(int.class)) {
                    ps.setInt(i + 1, field.getInt(obj));
                } else if (type.equals(String.class)) {
                    ps.setString(i + 1, String.valueOf(field.get(obj)));
                } else if (field.getType().isEnum()) {
                    Object value = field.get(obj);
                    Method m = value.getClass().getMethod("ordinal");
                    ps.setInt(i + 1, (Integer) m.invoke(value, null));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return ps;
    }

    @Override
    protected PreparedStatement getSqlDeleteById(Connection con, Object obj, int id) {
        PreparedStatement ps = null;
        try {
            Class<?> cl = obj.getClass();
            StringBuilder sb = new StringBuilder();
            String nomeTabela;

            if (cl.isAnnotationPresent(Tabela.class)) {
                nomeTabela = cl.getAnnotation(Tabela.class).value();
            } else {
                nomeTabela = cl.getSimpleName().toUpperCase();
            }

            sb.append("DELETE FROM ").append(nomeTabela).append(" WHERE ID = ").append(id).append(";");
            String delete = sb.toString();
            System.out.println(delete);

            ps = con.prepareStatement(delete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

	@Override
	public void abort(Executor executor) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commit() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStatement() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getCatalog() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getSchema() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String nativeSQL(String sql) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rollback() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCatalog(String catalog) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHoldability(int holdability) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSchema(String schema) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTransactionIsolation(int level) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PreparedStatement getSqlSelectById(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}