package corewar.Local;
import java.util.ArrayList;

/*
Le core est la mémoire dans laquelle les programmes s'affrontent. 
Il s'agit d'un tableau contenant des InstructionIDs.
*/

public class Core {

    private InstructionID[] memoire;

    public InstructionID[] getMemoire() { return this.memoire; }
    public void setMemoire(InstructionID[] memoire) { this.memoire = memoire; }

    public Core(){
        this.setMemoire(new InstructionID[8000]);
    }

    public Core(int length){
        this.setMemoire(new InstructionID[length]);
    }

    public int length(){
        return this.getMemoire().length;
    }

    public void write(int adresse, InstructionID InstructionID){
        this.getMemoire()[adresse%this.length()] = InstructionID;
    }

    public InstructionID read(int adresse){
        return this.getMemoire()[adresse];
    }

    public String toString(int x){
        String res = "";
        int len = this.length();
        int i = 0;

        while(i < len){
            if(this.read(i) == null)
                res+="[-]";
            else
                res+="["+this.read(i).getId()+"]";
            i++;
            if(i%x == 0)
                res+="\n";
        }

        return res;
    }

    public String toString(){
        return (toString(bestLength(this.length())));
    }
    

    // Cette fonction sert à calculer la taille d'une ligne pour que toutes les lignes fassent la même taille et que le toString soit le plus carré possible.
    public static int bestLength(int length){
        int square = (int) Math.ceil(Math.sqrt(length));
        int res = 0;

        for(int i = 1 ; i <= square ; i++){
            if(length%i == 0)
                res = i;
        }


        System.out.println(res);

        return (length/res);
    }
    
    




    
}
