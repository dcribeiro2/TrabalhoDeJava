package br.univel_Conexao;

import java.awt.List;
import java.sql.Connection;

public class Main{

	public Main() {

    }

    public void createTable() {
        Conexao a1 = new Conexao();
        SQL_P con = SQL_P.getInstance();
        a1.getCreateTable(con, new Cliente);
    }

    public void deleteTable() {
        Conexao a1 = new Conexao();
        Connection con = (Connection) SQL_P.getInstance();
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

    public void search(Integer id) {
    	ImplDao dao = new ImplDao();
        Cliente cl = dao.buscar(id);

        System.out.println("ID: " + cl.getId()+"\n");
        System.out.println("NOME: " + cl.getNome()+"\n");
        System.out.println("ENDERECO: " + cl.getEndereco()+"\n");
        System.out.println("TELEFONE: " + cl.getTelefone()+"\n");
        System.out.println("ESTADO CIVIL: " + cl.getestadocivil()+"\n");
        System.out.println();
    }

    public void listAll() {
        ImplDao dao = new ImplDao();
        List<Cliente> list = dao.listAll();

        System.out.println("LISTAR TODOS\n");
        for (Cliente l: list) {
            System.out.println("ID: " + l.getId()+"\n");
            System.out.println("NOME: " + l.getNome()+"\n");
            System.out.println("ENDERECO: " + l.getEndereco()+"\n");
            System.out.println("TELEFONE: " + l.getTelefone()+"\n");
            System.out.println("ESTADO CIVIL: " + l.getestadocivil()+"\n");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Cliente c1 = new Cliente(1, "Danilo", "Rua: Ernesto", "(44)9944-6743", Estado_Civil.Casado);
        Cliente c2 = new Cliente(2, "Joelma", "Rua: Itajai", "(44)9798-3456", Estado_Civil.Divorciado);
        Cliente c3 = new Cliente(3, "Cebolina", "Av. Brasil", "(44)9875-8475", Estado_Civil.Emasiado);

        Main main = new Main();
        main.deleteTable();
        main.createTable();
        main.insert(c1);
        main.insert(c2);
        main.insert(c3);

        main.listAll();
        main.search(1);

        c1.setNome("Adão");
        c1.setEndereco("Rua: Morangueira");
        c1.setTelefone("(44)9933-6650");
        c1.setEstadoCivil(Estado_Civil.Divorciado);

        main.update(c1);
        main.delete(2);

        main.listAll();
    }
		
	

	public <T> Object listarTodos () {
		return null;
	}

}
