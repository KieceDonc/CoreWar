package corewar.ObjectModel.elementsCore;

import java.io.Serializable;

public class Operande implements Serializable {
   
    /**
     *
     */
    private static final long serialVersionUID = 7323085518904894591L;
    private Mode mode;
    private int adresse;

    public Mode getMode() { return this.mode; }
    public int getAdresse() { return this.adresse; }

    public void setMode(Mode mode) { this.mode = mode; }
    public void setAdresse(int adresse) { this.adresse = adresse; }

    public Operande(Mode mode, int adresse){
        this.setMode(mode);
        this.setAdresse(adresse);
    }

    //Le constructeur sans mode assume que le mode est direct (" ")
    public Operande(int adresse){
        this.setMode(Mode.DIRECT);
        this.setAdresse(adresse);
    }

    public String toString(){
        return this.mode.toString()+this.adresse;
    }
}
