package morais.rh.Modelo;

public class Usuario {

    String usuario, senha;
    int cod, atual, tema;

    public Usuario(String usuario, String senha, int cod, int atual, int tema) {
        this.usuario = usuario;
        this.senha = senha;
        this.cod = cod;
        this.atual = atual;
        this.tema = tema; 
    }
    

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getAtual() {
        return atual;
    }

    public void setAtual(int atual) {
        this.atual = atual;
    }


    public int getTema() {
        return tema;
    }


    public void setTema(int tema) {
        this.tema = tema;
    }

}
