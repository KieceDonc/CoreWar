package corewar.Local;

public class Adresse {
    private int adresse;

    public int getAdresse() { return adresse; }
    public void setAdresse(int adresse) { this.adresse = adresse; }

    public Adresse(int adresse){
        this.setAdresse(adresse);
    }

    public Adresse(int adresse, int mod){
        this.setAdresse(adresse%mod);
    }

    public void mod(int mod){
        this.setAdresse(this.getAdresse()%mod);
    }

    public void plus(Adresse ad2){
        this.setAdresse(this.getAdresse()+ad2.getAdresse());
    }

    public void moins(Adresse ad2){
        this.setAdresse(this.getAdresse()-ad2.getAdresse());
    }

    public String toString(){
        return String.valueOf(this.getAdresse());
    }
}
