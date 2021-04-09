package corewar.ObjectModel.elementsCore;

import corewar.ObjectModel.*;

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
        
        int pointeur = w.firstPointeur();
        boolean flag = true;
        while(flag){
            if(this.read(mod(pointeur)).getMnq() == Mnemonique.DAT)
                pointeur++;
            else
                flag = false;
        }
        w.initPointeur(pointeur);
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
        String ANSI_RESET = "\033[0m";
        String ANSI_BOLD = "\033[1m";
        HashMap<Integer,Warrior> pointeurs = getWarriors().mapPointeurs();
        HashMap<Character,String> id = getWarriors().mapId();

        while(i < len){
            if(this.read(i) == null){
                if(pointeurs.containsKey(i)) res+=pointeurs.get(i).couleurAnsi()+"[-]"+ANSI_RESET;
                else res+= "[-]";
                    
            }
            else{
                if(pointeurs.containsKey(i)) res+=pointeurs.get(i).couleurAnsi()+ANSI_BOLD+String.valueOf(this.read(i).getId())+"]"+ANSI_RESET;
                else res+=id.get(read(i).getId())+"["+this.read(i).getId()+"]"+ANSI_RESET;
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

    // Initialise Ordre dans l'ordre de la liste Warriors
    public void initOrdre(){
        setOrdre(new ArrayDeque<Warrior>());
        for(Warrior w : getWarriors().getWarriors())
            ordre.offerLast(w);
    }

    // Retourne le premier warrior à jouer
    public Warrior firstWarrior(){
        return getOrdre().peekFirst();
    }

    // Retourne le prochain pointeur à être executé
    public int firstPointeur(){
        return firstWarrior().firstPointeur();
    }


    // Si le joueur est encore en vie, le met à la fin de la file Ordre, sinon le retire (= mort)
    public void cycle(Integer i){
        boolean alive = getOrdre().peekFirst().cycle(i);
        if(alive)
            getOrdre().offerLast(getOrdre().pollFirst());
        else
            getOrdre().removeFirst();
    }

    // Retourne un string contenant l'ordre des joueurs et de leurs pointeurs
    public String ordreString(){
        String res = "> ";
        if(!getOrdre().isEmpty())
            for(Warrior w : getOrdre())
                res+=w.toString()+"\n";
            
        return res;
    }

    // Retourne le vainqueur s'il est unique, sinon null
    public Warrior isWinner(){
        if(getOrdre().size() <= 1)
            return getOrdre().getFirst();
        else
            return null;
    }

    // Evalue l'operande (OP.A ou OP.B) de l'adresse passées en paramètres. Rend son adresse et la valeur.
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
                    return null;
                }
                case DIRECT : { // $
                    return mod(adresseIn+adresse);
                }
                case INDIRECT : { //@
                    InstructionID insInd = read(adresseIn+adresse);
                    if(insInd != null && insInd.getMnq() == Mnemonique.DAT)
                        return mod((read(adresseIn+adresse).getOp1().getAdresse())+adresseIn+adresse);
                }
            }
        }catch(NullPointerException e){return null;}
        return null;
    }

    // Rend la valeur pointée par l'opérande indiquée, si elle existe. Sinon renvoie null.
    public Integer evalData(OP op, int adresseIn){
        
            InstructionID ins = read(adresseIn);
            if(ins == null) return null;
            
            try{
                switch(op){
                    case A :{
                        if(ins.getOp1().getMode() == Mode.IMMEDIAT) // Si mode immediat, on retourne l'adresse contenue dans l'opérande
                            return ins.getOp1().getAdresse();
                        Instruction read = read(evalAdresse(OP.A,adresseIn));
                        if(read != null && read.getMnq() == Mnemonique.DAT) return read.getOp1().getAdresse();  // Sinon c'est une instruction DAT, et on rend l'adresse de l'op 1
                        return null;
                    }
                    case B : { // Idem operande B
                        if(ins.getOp2().getMode() == Mode.IMMEDIAT)
                            return ins.getOp1().getAdresse();
                        Instruction read = read(evalAdresse(OP.B,adresseIn));
                        if(read != null && read.getMnq() == Mnemonique.DAT) return read.getOp1().getAdresse();
                        return null;
                    }
                }
            }catch(NullPointerException e){return null;}
            return null;
        
    }


    // Retourne la prochaine position du pointeur. Si null, le processus doit mourir. (INTEGER)
    public Integer executer(int adresse){

        try{
            InstructionID ins = read(adresse);
            if(ins == null) return null;

            // On récupère tous les éléments de l'instruction pour que ce soit plus simple à traiter
            Mnemonique mnq = ins.getMnq();
            Operande opA = ins.getOp1(), opB = ins.getOp2();
            int adA = opA.getAdresse(), adB = opB.getAdresse();
            Mode modeA = opA.getMode(), modeB = opB.getMode();
            Integer evalA = evalAdresse(OP.A, adresse), evalB = evalAdresse(OP.B, adresse);
            Integer valA = evalData(OP.A,adresse), valB = evalData(OP.B,adresse);// On évalue forcément les opérandes, même si le processus sera tué.
            char id = ins.getId();
            if(evalA != null) valA = evalData(OP.A,adresse);
            if(evalB != null) valB = evalData(OP.B,adresse);

            //pr(evalA+" A | B "+evalB);
            //pr(valA+" valA | valB "+valB);

            switch(mnq){

                // DAT -- data (kills the process) - OK
                case DAT : {
                    return null;
                }

                // MOV -- move (copies data from one address to another) - OK
                case MOV : {
                    if(opA.getMode() == Mode.IMMEDIAT){ 
                        write(evalB,new InstructionID(adA,id));
                        return mod(adresse+1);
                    }
                    Instruction read = read(evalA);
                    if(read != null){
                        try{write(evalB,new InstructionID(read(adresse),id));}
                        catch(NullPointerException e) {return null;}
                        return mod(adresse+1);
                    } 
                    return null;
                }

                // ADD -- add (adds one number to another)
                case ADD : {
                    Instruction readIns = read(evalB);
                    if(readIns == null) return null;
                    if(valA!=null && readIns!=null && readIns.getMnq() == Mnemonique.DAT){
                            readIns.getOp1().setAdresse(mod(readIns.getOp1().getAdresse()+valA));
                            return mod(adresse+1);
                    }
                    else
                        return null;
                }

                //SUB -- subtract (subtracts one number from another)
                case SUB : {
                    valA = -valA;
                    Instruction readIns = read(evalB);
                    if(valA!=null && readIns!=null && readIns.getMnq() == Mnemonique.DAT){
                        readIns.getOp1().setAdresse(mod(readIns.getOp1().getAdresse()+valA));
                        return mod(adresse+1);
                    }
                    return null;    
                }

                //JMP -- jump (continues execution from another address)
                case JMP : {
                    if(modeA == Mode.IMMEDIAT)
                        return null;
                    return evalA;
                }

                //JMZ -- jump if zero (tests a number and jumps to an address if it's 0)
                case JMZ : {
                    if(evalB == null || valB == null)
                        return null;
                    if(valB == 0) return evalA;
                    return mod(adresse+1);
                }

                //JMG -- jump if greater than zero
                case JMG : {
                    if(evalB == null || valB == null)
                        return null;
                    if(valB > 0) return evalA;
                    return mod(adresse+1);
                }

                //JMN -- jump if not zero (tests a number and jumps if it isn't 0)
                case JMN : {
                    if(evalB == null || valB == null)
                        return null;
                    if(valB != 0) return evalA;
                    return mod(adresse+1);
                }

                //DJN -- decrement and jump if not zero (decrements a number by one, and jumps unless the result is 0)
                case DJN : {
                    if(evalB == null || valB == null || read(evalB).getMnq() != Mnemonique.DAT)
                        return null;
                    valB = valB-1;
                    read(evalB).getOp1().setAdresse(valB);
                    if(valB != 0) return evalA;
                    return mod(adresse+1);
                }

                //DJZ -- decrement and jump if zero ((decrements a number by one, and jumps if the result is zero)
                case DJZ : {
                    if(evalB == null || valB == null || read(evalB).getMnq() != Mnemonique.DAT)
                        return null;
                    valB = valB-1;
                    read(evalB).getOp1().setAdresse(valB);
                    if(valB == 0) return evalA;
                    return mod(adresse+1);
                }

                //CMP -- compare, SKIP IS EQUAL
                case CMP :
                case SEQ : {
                    if(valB == null || valA == null)
                        return null;
                    if(valB == valA) return mod(adresse+2);
                    return mod(adresse+1);
                }

                //SNQ -- compare, SKIP IF NOT EQUAL
                case SNE : {
                    if(valB == null || valA == null)
                        return null;
                    if(valB != valA) return mod(adresse+2);
                    return mod(adresse+1);
                }

                // SPL -- split (creates new process)
                case SPL : {
                    if(evalA == null)
                        return null;
                    firstWarrior().addPointeur(evalA);
                    return mod(adresse+1);
                }
            }

            return null;
        }catch(NullPointerException e) {e.printStackTrace(); return null; }


        /*



/*
MOV -- move (copies data from one address to another)
ADD -- add (adds one number to another)
SUB -- subtract (subtracts one number from another)
JMP -- jump (continues execution from another address)
JMZ -- jump if zero (tests a number and jumps to an address if it's 0)
JMG -- jump if greater than zero
JMN -- jump if not zero (tests a number and jumps if it isn't 0)
DJN -- decrement and jump if not zero (decrements a number by one, and jumps unless the result is 0)
DJZ -- decrement and jump if zero ((decrements a number by one, and jumps if the result is zero)
CMP -- compare (same as SEQ)
SPL -- split (creates new process)
DAT -- data (kills the process)

// REDCODE ETENDU :
MUL -- multiply (multiplies one number with another)
DIV -- divide (divides one number with another)
MOD -- modulus (divides one number with another and gives the remainder)
SEQ -- skip if equal (compares two instructions, and skips the next instruction if they are equal)
SNE -- skip if not equal (compares two instructions, and skips the next instruction if they aren't equal)
SLT -- skip if lower than (compares two values, and skips the next instruction if the first is lower than the second)
LDP -- load from p-space (loads a number from private storage space)
STP -- save to p-space (saves a number to private storage space)
NOP -- no operation (does nothing)
*/
    }



    // Print plus court
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
                InstructionID ins5 = new InstructionID(-10,'X');
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

            case JMP : {
                InstructionID ins1 = new InstructionID(5, 'X');
                InstructionID ins2 = new InstructionID(12, 'X');
                InstructionID ins3 = new InstructionID(Mnemonique.JMP,Mode.DIRECT,-20,Mode.IMMEDIAT,0,'X');
                InstructionID ins4 = new InstructionID(Mnemonique.JMP,Mode.INDIRECT,-10,Mode.IMMEDIAT,0,'X');
                c.write(6,ins1);
                c.write(12,ins2);
                c.write(25,ins3);
                c.write(15,ins4);
                pr(c.testString());
                pr("-------------------\nExecution 15 et 25 :");
                pr(c.executer(15)+"  ||  "+c.executer(25)+"\n");
                pr(c.testString());
            }

            case DJN : {
                InstructionID ins1 = new InstructionID(3, 'X');
                InstructionID ins2 = new InstructionID(1, 'X');
                InstructionID ins3 = new InstructionID(-10, 'X');
                InstructionID ins4 = new InstructionID(Mnemonique.DJN,Mode.DIRECT,10,Mode.DIRECT,-15,'X');
                InstructionID ins5 = new InstructionID(Mnemonique.DJN,Mode.DIRECT,15,Mode.DIRECT,-20,'X');
                c.write(5,ins1);
                c.write(10,ins2);
                c.write(15,ins3);
                c.write(20,ins4);
                c.write(30,ins5);
                pr(c.testString());
                pr("-------------------\nExecution 20 et 30 :");
                pr(c.executer(20)+"  ||  "+c.executer(30)+"\n");
                pr(c.testString());
            }

            case DJZ : {
                InstructionID ins1 = new InstructionID(3, 'X');
                InstructionID ins2 = new InstructionID(1, 'X');
                InstructionID ins3 = new InstructionID(-10, 'X');
                InstructionID ins4 = new InstructionID(Mnemonique.DJZ,Mode.DIRECT,10,Mode.DIRECT,-15,'X');
                InstructionID ins5 = new InstructionID(Mnemonique.DJZ,Mode.DIRECT,15,Mode.DIRECT,-20,'X');
                c.write(5,ins1);
                c.write(10,ins2);
                c.write(15,ins3);
                c.write(20,ins4);
                c.write(30,ins5);
                pr(c.testString());
                pr("-------------------\nExecution 20 et 30 :");
                pr(c.executer(20)+"  ||  "+c.executer(30)+"\n");
                pr(c.testString());
            }
        }
    }
}
