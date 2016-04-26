package br.univel_Conexao;
import java.util.List;

public abstract class ImplDao<K, T> implements Dao {

	public void Salvar(T t) {
		System.out.println("Cliente salvo!");
	}

	@Override
	public void T buscar(K k) {
		System.out.println("Cliente buscado!");
		return null;
		
		
	}

	@Override
	public void atualizar(T t){
		System.out.println("Dados atualizados!");
		
	}

	@Override
	public void excluir(K k) {
		System.out.println("Dados excluidos!");
	}

	@Override
	public java.awt.List listarTodos() {
		System.out.println("Listando dados...");
		return null;
	}


}
