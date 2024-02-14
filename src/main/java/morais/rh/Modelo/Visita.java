package morais.rh.Modelo;

public class Visita {
    
    int cod;
    String motivo, entrada, saida, pesNome, VeiPlaca, Tipo, ramal, port;
    
    public Visita(int cod, String motivo, String entrada, String saida, String pesNome, String veiPlaca, String tipo,
            String ramal,String port) {
        this.cod = cod;
        this.motivo = motivo;
        this.entrada = entrada;
        this.saida = saida;
        this.pesNome = pesNome;
        VeiPlaca = veiPlaca;
        Tipo = tipo;
        this.ramal = ramal;
        this.port = port;
    }

    public String getPesNome() {
        return pesNome;
    }


    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
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


    @Override
    public String toString() {
        return "Visita [cod=" + cod + ", motivo=" + motivo + ", entrada=" + entrada + ", saida=" + saida + ", pesNome="
                + pesNome + ", VeiPlaca=" + VeiPlaca + ", Tipo=" + Tipo + "]";
    }



    public String getRamal() {
        return ramal;
    }



    public void setRamal(String ramal) {
        this.ramal = ramal;
    } 
}
