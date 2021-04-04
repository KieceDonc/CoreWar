package corewar.Local.elementsCore;
import corewar.Local.Partie.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Deque;

/*
Le core est la mémoire dans laquelle les programmes s'affrontent. 
Il s'agit d'un tableau contenant des InstructionIDs.
*/

public class Core {

    private InstructionID[] memoire;
    private Warriors warriors;
    private ArrayDeque<Warrior> ordre;

    public InstructionID[] getMemoire() { return this.memoire; }
    public Warriors getWarriors() { return this.warriors; }
    public ArrayDeque<Warrior> getOrdre() { return this.ordre; }

    public void setMemoire(InstructionID[] memoire) { this.memoire = memoire; }
    public void setWarriors(Warriors warriors) { this.warriors = warriors; }
    public void setOrdre(ArrayDeque<Warrior> ordre) {this.ordre = ordre; }

    public Core(InstructionID[] memoire, Warriors warriors, ArrayDeque<Warrior> ordre){
        this.setMemoire(memoire);
        this.setWarriors(warriors);
        this.setOrdre(ordre);
    }

    public Core(int length){
        this(new InstructionID[length],null,null);
    }

    public Core(){
        this(8000);
    }

    public int length(){
        return this.getMemoire().length;
    }

    public void write(int adresse, InstructionID InstructionID){
        this.getMemoire()[mod(adresse)] = InstructionID;
    }

    
    public InstructionID read(int adresse){
        return this.getMemoire()[mod(adresse)];
    }

    public int mod(int i){
        return Math.floorMod(i,this.length());
    }

    public void add(Warrior w, int i){
        w.initPointeur(i);
        for(InstructionID ins : w.getInstructions()){
           write(i++,ins);
        }
    }

    public void addRandom(Warrior warrior){
        int DIST_MIN = 5;
        int length = warrior.getInstructions().size();
        int intervalle = DIST_MIN + length;
        int[] temp = new int[length()];

        int incr = 0;
        
        for(int i = 0 ; i < length() ; i++)
            if(this.read(i) != null )
                for(int j = 0 ; j <= 2*intervalle ; j++)
                    if(temp[Math.floorMod(i-intervalle+j,length())] == 0){
                        temp[Math.floorMod(i-intervalle+j,length())] = 1;
                        incr++;
                    }

        int rand = (int) Math.floor(Math.random()*(length()-incr));
        int i = 0;
        int j = 0;
        while(j < rand){
            if(temp[i] == 0)
                j++;
            i++;
        }

        add(warrior,i);
    }

    public void load(){
        Arrays.fill(getMemoire(),null);
        for(int i = 0 ; i < getWarriors().getWarriors().size() ; i++){
            if(i == 0) add(getWarriors().get(i),0); 
            else addRandom(getWarriors().get(i));
        }

    }

    // toString renvoyant x cases par lignes
    public String toString(int x){
        String res = "";
        int len = this.length();
        int i = 0;
        String ANSI_RESET = "\u001B[0m";
        HashMap<Integer,Warrior> pointeurs = getWarriors().mapPointeurs();

        while(i < len){
            if(this.read(i) == null){
                if(pointeurs.containsKey(i)) res+=pointeurs.get(i).couleurAnsi()+"[-]"+ANSI_RESET;
                else res+= "[-]";
                    
            }
            else{
                if(pointeurs.containsKey(i)) res+=pointeurs.get(i).couleurAnsi()+"["+String.valueOf(this.read(i).getId())+"]"+ANSI_RESET;
                else res+="["+this.read(i).getId()+"]";
                //EN GRAS : res+="[\033[1m"+String.valueOf(this.read(i).getId())+"\033[0m]";
            }
            i++;
            if(i%x == 0)
                res+="\n";
        }

        return res;
    }

    public String testString(int x){
        String res = "";
        int len = this.length();
        int i = 0;

        while(i < len){
            if(this.read(i) == null){
                res+= "[-]";     
            }
            else{
                res+="["+this.read(i).getId()+"]";
                //EN GRAS : res+="[\033[1m"+String.valueOf(this.read(i).getId())+"\033[0m]";
            }
            i++;
            if(i%x == 0)
                res+="\n";
        }

        return res;

    }

    public String toString(){
        return (toString(bestLength(this.length())));
    }
     public String testString(){
         String res = testString(bestLength(this.length()))+"\n";
         int i = 0;
         for(InstructionID ins : getMemoire()){
            if(ins != null){
                res+=i+":\t"+ins.toString()+"\n";
            }
            i++;
         }
         return res;
     }

    // Cette fonction sert à calculer la taille d'une ligne pour que toutes les lignes fassent la même taille et que le toString soit le plus carré possible.
    // Soit len la taille du core, la taille d'une ligne est l'entier le plus proche de sqrt(len) qui est diviseur de len
    public static int bestLength(int length){
        
        // On calcule la racine carrée de la longueue (length^(x) avec x = 0.5) mais si on le souhaite, on peut changer x pour afficher plus comme un rectancle.
        double ratio = 0.5; 
        int square = (int) Math.ceil(Math.pow(length,ratio));

        int res = 0;
        int MAX = 60;
        
        for(int i = 1 ; (i <= square) ; i++){
            if(length%i == 0)
                res = i;
        }
        if(length/res >MAX) return MAX;
        return (length/res);
    }
    
    // GESTION TOUR DE JEU
    public void initOrdre(){
        setOrdre(new ArrayDeque<Warrior>());
        for(Warrior w : getWarriors().getWarriors())
            ordre.offerLast(w);
    }

    public Warrior firstWarrior(){
        return getOrdre().peekFirst();
    }

    public void cycle(){
        getOrdre().peekFirst().cycle();
        getOrdre().offerLast(getOrdre().pollFirst());
    }

    public String ordreString(){
        String res = "> ";
        if(!getOrdre().isEmpty()){
    
        for(Warrior w : getOrdre()){
            res+=w.toString()+"\n";
        }}
        return res;

    }

    // Evalue l'operande (OP.A ou OP.B) de l'adresse passées en paramètres
    public Integer evalAdresse(OP op, int adresseIn){
        try{
            InstructionID ins = read(adresseIn);
            int adresse = 0;
            Mode mode = Mode.IMMEDIAT;
            switch(op){
                case A :{
                    adresse = ins.getOp1().getAdresse();
                    mode = ins.getOp1().getMode();
                    break;
                }
                case B : {
                    adresse = ins.getOp2().getAdresse();
                    mode = ins.getOp2().getMode();
                    break;
                }
            }

            switch(mode){
                case IMMEDIAT : { //#
                    return 0;
                }
                case DIRECT : { // $
                    return mod(adresseIn+adresse);
                }
                case INDIRECT : { //@
                    if(read(adresseIn+adresse).getMnq() == Mnemonique.DAT)
                        return mod((read(adresseIn+adresse).getOp1().getAdresse())+adresseIn+adresse);
                }
                default :{
                    return 0;
                }
            }
        }catch(NullPointerException e) { return null; } 
    }

    // Rend la valeur pointée par l'opérande indiquée, si elle existe. Sinon renvoie null.
    public Integer evalData(OP op, int adresseIn){
        try{
            InstructionID ins = read(adresseIn);
            
            switch(op){
                case A :{
                    if(ins.getOp1().getMode() == Mode.IMMEDIAT)
                        return ins.getOp1().getAdresse();
                    else
                        if( read(evalAdresse(OP.A,adresseIn)).getMnq() == Mnemonique.DAT)
                            return read(evalAdresse(OP.A,adresseIn)).getOp1().getAdresse();
                    return null;
                }
                case B : {
                    if(ins.getOp2().getMode() == Mode.IMMEDIAT)
                        return ins.getOp2().getAdresse();
                    else{
                        if(read(evalAdresse(OP.B,adresseIn)).getMnq() == Mnemonique.DAT)
                            return read(evalAdresse(OP.B,adresseIn)).getOp1().getAdresse();
                    }
                    return null;
                }
            }
            return null;
        } catch(NullPointerException e) { return null; }  
    }


    // Return la prochaine position du pointeur. Si null, le processus doit mourir. (INTEGER)
    public Integer executer(int adresse){
        InstructionID ins = read(adresse);
        Mnemonique mnq = ins.getMnq();

        // On récupère tous les éléments de l'instruction pour que ce soit plus simple à traiter
        Operande opA = ins.getOp1();
        Operande opB = ins.getOp2();
        int adA = opA.getAdresse();
        int adB = opB.getAdresse();
        Mode modeA = opA.getMode();
        Mode modeB = opB.getMode();
        char id = ins.getId();

        switch(mnq){

            // DAT -- data (kills the process) - OK
            case DAT : {
                return null;
            }

            // MOV -- move (copies data from one address to another) - OK
            case MOV : {
                if(opA.getMode() == Mode.IMMEDIAT){ 
                    write(evalAdresse(OP.B,adresse),new InstructionID(adA,id));
                    return adresse+1;
                }
                else if(read(evalAdresse(OP.A,adresse)) != null){
                    write(evalAdresse(OP.B,adresse),new InstructionID(read(adresse),id));
                    return adresse+1;
                } else return null;
            }

            // ADD -- add (adds one number to another)
            case ADD : {
                Integer valA = null;
                if(evalData(OP.A,adresse) != null){
                    valA = evalData(OP.A,adresse);
                    return null;
                }
                Instruction readIns = read(evalAdresse(OP.B,adresse));
                if(valA!=null && readIns.getMnq() == Mnemonique.DAT){

                        readIns.getOp1().setAdresse(mod(readIns.getOp1().getAdresse()+valA));
                        return adresse+1;

                }
                else return null;
            }

            case 
        }

        return null;


        /*



SUB -- subtract (subtracts one number from another)
MUL -- multiply (multiplies one number with another)
DIV -- divide (divides one number with another)
MOD -- modulus (divides one number with another and gives the remainder)
JMP -- jump (continues execution from another address)
JMZ -- jump if zero (tests a number and jumps to an address if it's 0)
JMN -- jump if not zero (tests a number and jumps if it isn't 0)
DJN -- decrement and jump if not zero (decrements a number by one, and jumps unless the result is 0)
SPL -- split (starts a second process at another address)
CMP -- compare (same as SEQ)
SEQ -- skip if equal (compares two instructions, and skips the next instruction if they are equal)
SNE -- skip if not equal (compares two instructions, and skips the next instruction if they aren't equal)
SLT -- skip if lower than (compares two values, and skips the next instruction if the first is lower than the second)
LDP -- load from p-space (loads a number from private storage space)
STP -- save to p-space (saves a number to private storage space)
NOP -- no operation (does nothing)
*/
    }



    // Print plus rapide et court
    public static void pr(Object o){
        System.out.println(o);
    }

    // Enumeration pour l'evaluation des adresses
    public enum OP {
        A,B;
    }

    // Teste les mnémoniques indiquées
    public void testInstruction(Mnemonique mnq){
        Core c = new Core (10*10);
        pr("TEST DE LA MNEMONIQUE "+mnq.toString());
        switch(mnq){
            
            case DAT : {
                InstructionID ins1 = new InstructionID(5,'X');
                c.write(12,ins1);
                pr(c.testString());
                pr("-------------------\nExecution 12 :");
                pr(c.executer(12)+"\n");
                pr(c.testString());
                break;
            }

            case MOV : {
                InstructionID ins1 = new InstructionID(20,'X');
                InstructionID ins2 = new InstructionID(Mnemonique.MOV,Mode.IMMEDIAT,99,Mode.DIRECT,-5,'X');
                c.write(12,ins1);
                c.write(0,ins2);
                pr(c.testString());
                pr("-------------------\nExecution 0 :");
                pr(c.executer(0)+"\n");
                pr(c.testString());
                break;
            }

            case ADD : {
                InstructionID ins1 = new InstructionID(10,'X');
                InstructionID ins3 = new InstructionID(-5,'X');
                InstructionID ins4 = new InstructionID(18,'X');
                InstructionID ins5 = new InstructionID(-9,'X');
                InstructionID ins2 = new InstructionID(Mnemonique.ADD,Mode.INDIRECT,25,Mode.INDIRECT,10,'X');
                c.write(20,ins1);
                c.write(15,ins2);
                c.write(25,ins3);
                c.write(30,ins4);
                c.write(40,ins5);
                pr(c.testString());
                pr("-------------------\nExecution 15 :");
                pr(c.executer(15)+"\n");
                pr(c.testString());
                break;
            }
        }
    }
}
