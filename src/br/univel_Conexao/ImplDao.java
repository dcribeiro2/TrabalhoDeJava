package br.univel_Conexao;


import br.univel_Conexao.Teste_Sql;
import br.univel_Conexao.Estado_Civil;
import br.univel_Conexao.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ImplDao implements Dao<Cliente, Integer> {

	private PreparedStatement ps = null;
    private ResultSet rs = null;
    private Connection con = Teste_Sql.getInstance().open();
    private Conexao e1 = new Conexao();
    private List<Cliente> list = null;

    public void salvar(Cliente cliente) {
        try {
            ps = e1.getSqlInsert(con, cliente);
            ps.executeUpdate();
            ps.close();

            System.out.println("Cliente cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Cliente buscar(Integer integer) {
        try {
            Cliente cl = new Cliente();
            ps = e1.getSqlSelectById(con, cl, integer);
            rs = ps.executeQuery();
            rs.next();

            Estado_Civil ec = Estado_Civil.values()[rs.getInt("ESTADOCIVIL")];
            cl = new Cliente(rs.getInt("id"), rs.getString("nome"),
                    rs.getString("endereco"), rs.getString("telefone"), ec);

            ps.close();
            rs.close();
            return cl;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void atualizar(Cliente cliente) {
        try {
            ps = e1.getSqlUpdateById(con, cliente, cliente.getId());
            ps.executeUpdate();
            ps.close();

            System.out.println("Cliente atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void excluir(Integer integer) {
        try {
            Cliente cl = new Cliente();
            ps = e1.getSqlDeleteById(con, cl, integer);
            ps.executeUpdate();
            ps.close();
            System.out.println("Cliente excluido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

        

	@Override
	public List<Cliente>listarTodos() {
		try {
            Cliente cl = new Cliente();
            list = new ArrayList<Cliente>();
            ps = e1.getSqlSelectAll(con, cl);
            rs = ps.executeQuery();

            while (rs.next()) {
                Estado_Civil ec = Estado_Civil.values()[rs.getInt("ESTADOCIVIL")];
                list.add(new Cliente(rs.getInt("id"), rs.getString("nome"),
                        rs.getString("endereco"), rs.getString("telefone"), ec));
            }

            ps.close();
            rs.close();

            if (list != null) {
                return list;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

	

}