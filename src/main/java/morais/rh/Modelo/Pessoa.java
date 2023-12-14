package morais.rh.Modelo;

public class Pessoa {

    int codigo;
    String nome, documento, telefone, ramal, tipo;

    public Pessoa(int codigo, String nome, String documento, String telefone, String ramal, String tipo) {
        this.codigo = codigo;
        this.nome = nome;
        this.documento = documento;
        this.telefone = telefone;
        this.ramal = ramal;
        this.tipo = tipo;
    }

    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDocumento() {
        return documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getRamal() {
        return ramal;
    }
    public void setRamal(String ramal) {
        this.ramal = ramal;
    }

    

}
