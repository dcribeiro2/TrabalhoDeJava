package br.univel_Conexao;

import java.util.List;
import java.sql.Connection;

public class Main{

	public Main() {

    }

	 public void createTable() {
	        Conexao a1 = new Conexao();
	        Connection con = Teste_Sql.getInstance().open();
	        a1.getCreateTable(con, new Cliente());
	    }

    public void deleteTable() {
        Conexao a1 = new Conexao();
        Connection con = Teste_Sql.getInstance().open();
        a1.getDropTable(con, new Cliente());
    }

    public void insert(Cliente cl) {
          ImplDao dao = new ImplDao();
        dao.salvar(cl);
    }

    public void update(Cliente cl) {
    	ImplDao dao = new ImplDao();
        dao.atualizar(cl);
    }

    public void delete(Integer id) {
        ImplDao dao = new ImplDao();
        dao.excluir(2);
    }

    public void buscar(Integer id) {
    	ImplDao dao = new ImplDao();
        Cliente cl = dao.buscar(id);

        System.out.println("ID: " + cl.getId()+"\n");
        System.out.println("NOME: " + cl.getNome()+"\n");
        System.out.println("ENDERECO: " + cl.getEndereco()+"\n");
        System.out.println("TELEFONE: " + cl.getTelefone()+"\n");
        System.out.println("ESTADO CIVIL: " + cl.getestadocivil()+"\n");
        System.out.println();
    }

    public void listarTodos() {
        ImplDao dao = new ImplDao();
        List <Cliente> list = dao.listarTodos();

        System.out.println("LISTAR TODOS\n");
        for (Cliente l: list) {
            System.out.println("Id: " + l.getId()+"\n");
            System.out.println("Nome: " + l.getNome()+"\n");
            System.out.println("Endereco: " + l.getEndereco()+"\n");
            System.out.println("Telefone: " + l.getTelefone()+"\n");
            System.out.println("Estado Civil: " + l.getestadocivil()+"\n");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Cliente c1 = new Cliente(1, "Danilo", "Rua: Ernesto", "(44)9944-6743", Estado_Civil.Casado);
        Cliente c2 = new Cliente(2, "Joelma", "Rua: Itajai", "(44)9798-3456", Estado_Civil.Divorciado);
        Cliente c3 = new Cliente(3, "Cebolina", "Av. Brasil", "(44)9875-8475", Estado_Civil.Emasiado);

        Main m = new Main();
        m.deleteTable();
        m.createTable();
        m.insert(c1);
        m.insert(c2);
        m.insert(c3);

        m.listarTodos();
        m.buscar(1);

        c1.setNome("Adão");
        c1.setEndereco("Rua: Morangueira");
        c1.setTelefone("(44)9933-6650");
        c1.setEstadoCivil(Estado_Civil.Casado);

        m.update(c1);
        m.delete(2);

        m.listarTodos();
    }
		

}
