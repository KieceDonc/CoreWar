package corewar.ObjectModel.elementsCore;

import java.io.Serializable;
import java.util.regex.Pattern;

// Direct : " " | Immédiat : "#" | Indirect : "@"
public enum Mode implements Serializable {
    DIRECT{ public String toString() { return "" ; } },
    IMMEDIAT{ public String toString() { return "#" ; } }, 
    INDIRECT{ public String toString() { return "@" ; } };


    // Retourne un regex faisant un OU logique de tous les modes sauf direct (puisqu'il correspond à aucun caractère)
    public static Pattern getPattern(){
        return Pattern.compile("(#|@)");
    }

    // Traduit le string en mode
    public static Mode toMode(String mode){
        Mode res = null;
        if(mode == null) res = Mode.DIRECT;
        else{
            if(mode.equals("#")) res = Mode.IMMEDIAT;
            if(mode.equals("@")) res = Mode.INDIRECT;
        }
        return res;
    }
}
