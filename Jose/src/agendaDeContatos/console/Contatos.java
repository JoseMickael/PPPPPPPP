package agendaDeContatos.console;

public class Contatos {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private String estadoCivil;
    private String sexo;

    // Construtor com id (para atualizações e listagem)
    public Contatos(int id, String nome, String email, String telefone, String endereco, String estadoCivil, String sexo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.estadoCivil = estadoCivil;
        this.sexo = sexo;
    }

    // Construtor sem id (para inserção de novos contatos)
    public Contatos(String nome, String email, String telefone, String endereco, String estadoCivil, String sexo) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.estadoCivil = estadoCivil;
        this.sexo = sexo;
    }

    // Métodos getter e setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getEstadoCivil() { return estadoCivil; }
    public void setEstadoCivil(String estadoCivil) { this.estadoCivil = estadoCivil; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
}