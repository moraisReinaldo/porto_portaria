package morais.rh.Modelo;

public class Tipo {
    
    int cod;
    String desc;

    public Tipo(int cod, String desc) {
        this.cod = cod;
        this.desc = desc;
    }
    public int getCod() {
        return cod;
    }
    public void setCod(int cod) {
        this.cod = cod;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    
}
