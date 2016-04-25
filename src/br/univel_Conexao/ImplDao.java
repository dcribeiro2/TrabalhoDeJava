package br.univel_Conexao;
import java.util.List;

public abstract class ImplDao implements Dao<Integer, Cliente> {

	public void Salvar(Cliente t) {
		System.out.println("Cliente salvo!");
	}

	@Override
	public Cliente buscar(Integer k) {
		System.out.println("Cliente buscado!");
		return null;
		
	}

	@Override
	public void atualizar(Cliente ){
		System.out.println("Dados atualizados!");
		
	}

	@Override
	public void excluir(Integer k) {
		System.out.println("Dados excluidos!");
	}

	@Override
	public java.awt.List listarTodos() {
		System.out.println("Listando dados...");
		return null;
	}


}
