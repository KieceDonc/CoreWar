package corewar.ClientSide;
import corewar.ObjectModel.elementsCore.*;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.HashMap;

public class Interpreteur {

    //Cette fonction convertit le fichier texte dont l'emplacement est passé en paramètre en Liste de string.
    //Elle renvoie une ArrayList<String> telle que : Une ligne de code = Une ligne du tableau. 
    public static ArrayList<String> toList(String chemin) throws FileNotFoundException{

        ArrayList<String> code = new ArrayList<String>();

        Scanner scan = new Scanner(new FileReader(chemin));
        
        while(scan.hasNextLine()){
            String ligne = scan.nextLine();

            if(!ligne.startsWith(";")){ // Si le commentaire est une ligne, on la passe
                ligne = ligne.split(";")[0]; // Si le commentaire est après une instruction, on le retire. 
                ligne = parseSpaces(ligne);
                code.add(ligne);
            }
        }

        scan.close();
        return code;
    }

    // Fonction prenant en paramètre une liste de String contenant des instructions REDCODE
    // Elle retourne une liste de String contenant les mêmes instructions mais en remplaçant les labels par des adresses.
    public static ArrayList<String> treatLabels(ArrayList<String> code){

        // HashMap reliant les label à leur position absolue dans le code.
        HashMap<String,Integer> labels = new HashMap<String,Integer>();
        
        // On associe labels à leur position absolue dans la hashmap d'abord
        int i = 0;
        for(String ligne : code){
            Scanner scan = new Scanner(ligne);
            Pattern cmp = Mnemonique.getPattern(); // On récupère toutes les mnémoniques

            if(!scan.hasNext(cmp)){ // Si la ligne suivante ne commence pas par un mnémonique, c'est un label.
                labels.put(scan.next(), i);
            }

            code.set(i, parseSpaces(scan.nextLine()));
            scan.close();
            i++;
        }

        // On remplace tous les labels par la distance de leur instruction à l'adresse du label original
        i = 0;
        for(String ligne : code){
            for(String key : labels.keySet()){
                if(ligne.contains(key)){
                    ligne = ligne.replace(key,String.valueOf(labels.get(key)-i));
                }
            }
            code.set(i,ligne);
            i++;
        }

        return code;
    }

    public static ArrayList<Instruction> interpreter(ArrayList<String> code){

        ArrayList<Instruction> warrior = new ArrayList<Instruction>();

        for(String ligne : code){
            Scanner scan = new Scanner(ligne);
            Instruction instruction;
    
            // On récupère la première mnémonique
            Mnemonique mnq = Mnemonique.valueOf(scan.next().toUpperCase());
    
            // On récupère le mode de la première opérande
            String modeStr1 = scan.findWithinHorizon(Mode.getPattern(), 2);
            Mode m1 = Mode.toMode(modeStr1);
    
            // On récupère l'adresse de la première opérande
            String adStr1 = scan.next();
            Integer ad1 = Integer.parseInt(adStr1);
            
            if(scan.hasNext()){
                // On récupère le mode de la seconde opérande;
                String modeStr2 = scan.findWithinHorizon(Mode.getPattern(), 2);
                Mode m2 = Mode.toMode(modeStr2);
        
                // On récupère l'adresse de la seconde opérande
                String adStr2 = scan.next();
                Integer ad2 = null;
                if(adStr2 != null) ad2 = Integer.parseInt(adStr2);

                instruction = new Instruction(mnq,m1,ad1,m2,ad2);
            }
            else
                instruction = new Instruction(mnq,m1,ad1);
            
                

            warrior.add(instruction);
            scan.close();
        }   
        return warrior;
    }



    public static ArrayList<Instruction> interpreter(String chemin){
        ArrayList<Instruction> warrior = null;
        try{ 
                ArrayList<String> code = toList(chemin);
                code = treatLabels(code);
                warrior = interpreter(code);
        }
        catch (FileNotFoundException e) {
            System.out.println("Fichier non trouvé.");
        }
        catch(Exception e){
            System.out.println("Erreur lors de l'interprétation du fichier... Vérifiez qu'il respecte bien toutes les conventions de REDCODE!");
            return null;
        }
        return warrior;
    }



    // Cette fonction retire tous les espaces et virgules
    public static String parseSpaces(String str){
        str = str.replace(",","");
        return str.trim().replaceAll(" +", " ");
    }

    // Transforme un tableau en Pattern de type (tab[0] | tab[1] | tab[2] ...)
    // Si sensiCasse est à false, alors rajoute (?i) devant
    public static Pattern toPattern(Object[] tab, boolean sensiCasse){
        String res = "";
        if(sensiCasse = false) res+="(?i)";
        res+="(";
        for(int i = 0 ; i <(tab.length-1) ; i++){
            res+=tab[i].toString()+"|";
        }
        res+=tab[tab.length-1]+")";

        return Pattern.compile(res);
    }

    // Affiche une arraylist de string (pour test)
    public static void print(ArrayList<String> liste){
        String res = "";
        for(int i = 0 ; i < liste.size() ; i++){
            res+=liste.get(i)+"\n";
        }
        System.out.println(res);
    }

    // Affiche une arraylist d'instructions (pour test)
    public static void printI(ArrayList<Instruction> liste){
        String res = "";
        for(int i = 0 ; i < liste.size() ; i++){
            res+=liste.get(i).toString()+"\n";
        }
        System.out.println(res);
    }
}
