package morais.rh.Modelo;

public class Veiculo {
    
    String placa, cor, modelo, ramal;

    public Veiculo(String placa, String cor, String modelo, String ramal) {
        this.placa = placa.toUpperCase();
        this.cor = cor;
        this.modelo = modelo;
        this.ramal = ramal;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getRamal() {
        return ramal;
    }

    public void setRamal(String ramal) {
        this.ramal = ramal;
    } 

}
