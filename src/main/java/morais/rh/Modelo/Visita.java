package morais.rh.Modelo;

public class Visita {
    
    int cod;
    String motivo, entrada, saida, pesNome, VeiPlaca, Tipo;
    
    public Visita(int cod, String tipo, String motivo, String entrada, String saida, String pesNome, String veiPlaca) {
        this.cod = cod;
        this.Tipo = tipo;
        this.motivo = motivo;
        this.entrada = entrada;
        this.saida = saida;
        this.pesNome = pesNome;
        this.VeiPlaca = veiPlaca;
    }
    

    public String getPesNome() {
        return pesNome;
    }


    public void setPesNome(String pesNome) {
        this.pesNome = pesNome;
    }


    public String getVeiPlaca() {
        return VeiPlaca;
    }


    public void setVeiPlaca(String veiPlaca) {
        VeiPlaca = veiPlaca;
    }


    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        this.Tipo = tipo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getSaida() {
        return saida;
    }

    public void setSaida(String saida) {
        this.saida = saida;
    } 
}
