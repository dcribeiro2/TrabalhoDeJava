package br.univel_Conexao;
/*
 * Classe Cliente contendo os atributos id,
 * nome, endere�o, telefone e estado civil, sendo que estado civil � 
 * uma Enum que voc� deve criar. Ao armazenar esse campo no banco use seu valor ordinal.
 */
public class Cliente  {
	
	
	@Coluna(pk=true, nome="idclifor")
	private int id;
	@Coluna(nome = "nomecliente", tamanho = 150 )
	private String nome;
	@Coluna(nome = "endereco", tamanho = 200)
	private String endereco;
	@Coluna(nome = "fone", tamanho = 10)
	private String telefone;
	@Coluna(nome = "estadocivil")
	private Estado_Civil estadocivil;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}


	public void setEstadocivil(Estado_Civil estadocivil) {
		this.estadocivil = estadocivil;
	}
	public Cliente(int id, String nome, String endereco, String telefone, Estado_Civil estado_Civil) {
		super();
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		this.estadocivil = estadocivil;
	}

	 public Cliente() {
	}

	public Estado_Civil getestadocivil() {
	        return estadocivil;
	    }

	    public void setEstadoCivil(Estado_Civil estadoCivil) {
	        this.estadocivil = estadocivil;
	    }
}
