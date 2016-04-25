package br.univel_Conexao;

public @interface Coluna {

	String nome();
	int tamanho() default -1;

	boolean pk() default false;

}
