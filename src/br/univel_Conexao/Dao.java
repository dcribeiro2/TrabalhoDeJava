package br.univel_Conexao;

import java.awt.List;

/*
 * publicvoid salvar(T t);
   public T buscar(K k);
   publicvoid atualizar(T t);
   publicvoid excluir(K k);
   public List<T> listarTodos();
 */
public interface Dao<K> {
	public <T> void salvar(T t);
	public <T> T buscar(K k);
	public <T> void atualizar(T t);
	public void excluir(K k);
	
	public <T> List listarTodos(); 
	

}
