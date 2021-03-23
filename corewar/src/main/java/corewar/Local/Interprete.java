package corewar.Local;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.HashMap;

public class Interprete {

    //Cette fonction convertit le fichier RedCode dont l'emplacement est passé en paramètre en Liste de string. Une ligne de code = Une ligne du tableau. 
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

    // Fonction récupérant tous les labels dans une HashMap clé->label, valeur->position dans code
    // Retire ensuite tous les labels en début de code.
    public static HashMap<String,Integer> getLabels(ArrayList<String> code){

        HashMap<String,Integer> labels = new HashMap<String,Integer>();
        
        for(int i = 0 ; i < code.size() ; i++){
            Scanner scan = new Scanner(code.get(i));
            Pattern cmp = toPattern(Mnemonique.values(),true); // On récupère toutes les mnémoniques

            if(!scan.hasNext(cmp)){ // Si la ligne suivante ne commence pas par un mnémonique, c'est un label.
                labels.put(scan.next(), i);
            }

            code.set(i, parseSpaces(scan.nextLine()));
        }

        return labels;
    }



    // sandbox
    public static void sandbox1(){
        ArrayList<String> code;
        try {
            code = toList("CoreWars/corewar/src/main/java/corewar/Warriors/dwarf.redcode");
            HashMap<String,Integer> lab = getLabels(code);
            print(code);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }


    // Cette fonction retire tous les espaces et virgules
    public static String parseSpaces(String str){
        str = str.replace(",","");
        return str.trim().replaceAll(" +", " ");
    }

    // Transforme un tableau en String de type (tab[0] | tab[1] | tab[2] ...)
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

    // Print une arraylist (test)
    public static void print(ArrayList<String> liste){
        String res = "";
        for(int i = 0 ; i < liste.size() ; i++){
            res+=liste.get(i)+"\n";
        }
        System.out.println(res);
    }

}
