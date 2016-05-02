package br.univel_Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ImplDao implements Dao<Cliente, Integer> {

	    Teste_Sql a1 = new Teste_Sql();
	private Connection con = null;

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public void criarTabela(Cliente t) {
         Teste_Sql a1 = new Teste_Sql();

		try {
			String t1 = a1.getCreateTable(con, t);
			PreparedStatement ps = con.prepareStatement(t1);
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	@Override
	public void Salvar(Cliente t) {
		try {
			PreparedStatement ps = ex.getSqlInsert(con, t);
			ps.setInt(1, t.getId());
			ps.setString(2, t.getNome());
			ps.setString(3, t.getEndereco());
			ps.setString(4, t.getTelefone());
			ps.setInt(5, t.getEstadoCivil().ordinal());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void apagarTabela(Cliente t) {
           Teste_Sql gdr = new Teste_Sql();

		try {
			String sql = gdr.getDropTable(con, t);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	@Override
	public Cliente Buscar(Integer k) {
		Cliente c = new Cliente();

		try {
			PreparedStatement ps = ex.getSqlSelectById(con, new Cliente());
			ps.setInt(1, k);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				c.setId(rs.getInt("Id"));
				c.setNome(rs.getString("Nome"));
				c.setTelefone(rs.getString("Telefone"));
				c.setEndereco(rs.getString("Endereco"));
				// c.EstadoCivil.values()[rs.getInt("UsEstadoCivil")]);
			}

			ps.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return c;
	}

	@Override
	public void Atualizar(Cliente t) {
		Teste_Sql a1 = new Teste_Sql();

		try {
			PreparedStatement ps = a1.getSqlUpdateById(con, t);
			ps.setInt(5, t.getId());
			ps.setString(1, t.getNome());
			ps.setString(2, t.getEndereco());
			ps.setString(3, t.getTelefone());
			ps.setInt(4, t.getEstadoCivil().ordinal());
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		
		}
	}

	@Override
	public void excluir(Integer pk) {
		Teste_Sql a1 = new Teste_Sql();

		
		try {
			PreparedStatement ps = a1.getSqlDeleteById(con, new Cliente());
			ps.setInt(1, pk);
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Cliente> listarTodos() {
		ExecuteSqlGen ex = new ExecuteSqlGen();
		List<Cliente> Cliente = new ArrayList<Cliente>();

		try {
			PreparedStatement ps = ex.getSqlSelectAll(con, new Cliente());
			ResultSet exibir = ps.executeQuery();

			while (exibir.next()) {

				Cliente c = new Cliente();
				c.setId(exibir.getInt("UsID"));
				c.setNome(exibir.getString("UsNome"));
				c.setEndereco(exibir.getString("UsEndereco"));
				c.setTelefone(exibir.getString("UsTelefone"));
				// c.setEstadoCivil(EstadoCivil.getPorCodigo(exibir.getInt("UsEstadoCivil")));

				Cliente.add(c);
			}

			ps.close();
			exibir.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Cliente;
	}
}