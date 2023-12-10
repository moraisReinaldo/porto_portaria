package morais.rh.Modelo;

public class Usuario {

    String usuario, senha;
    int cod;

    public Usuario(String usuario, String senha, int cod) {
        this.usuario = usuario;
        this.senha = senha;
        this.cod = cod;
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

}
