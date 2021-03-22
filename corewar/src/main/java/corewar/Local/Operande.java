package corewar.Local;

public class Operande {
   
    private Mode mode;
    private Adresse adresse;

    public Mode getMode() { return this.mode; }
    public Adresse getAdresse() { return this.adresse; }

    public void setMode(Mode mode) { this.mode = mode; }
    public void setAdresse(Adresse adresse) { this.adresse = adresse; }

    public Operande(Mode mode, Adresse adresse){
        this.setMode(mode);
        this.setAdresse(adresse);
    }

    //Le constructeur sans mode assume que le mode est direct (" ")
    public Operande(Adresse adresse){
        this.setMode(Mode.DIRECT);
        this.setAdresse(adresse);
    }

    public String toString(){
        return this.mode.toString()+this.adresse.toString();
    }
}
