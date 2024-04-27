package morais.rh.Modelo;

public class EntradaRapida {
    private int entCod;
    private String pesNome;
    private String entTipo;
    private String entPlaca;
    private String entRamal;

    public EntradaRapida(int entCod, String pesNome, String entTipo, String entPlaca, String entRamal) {
        this.entCod = entCod;
        this.pesNome = pesNome;
        this.entTipo = entTipo;
        this.entPlaca = entPlaca;
        this.entRamal = entRamal;
    }

    public int getEntCod() {
        return entCod;
    }

    public String getPesNome() {
        return pesNome;
    }

    public String getEntTipo() {
        return entTipo;
    }

    public String getEntPlaca() {
        return entPlaca;
    }

    public String getEntRamal() {
        return entRamal;
    }
}