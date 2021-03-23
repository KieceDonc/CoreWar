package corewar.Local;

import java.util.regex.Pattern;

// Direct : " " | Immédiat : "#" | Indirect : "@"
public enum Mode {
    DIRECT{ public String toString() { return "" ; } },
    IMMEDIAT{ public String toString() { return "#" ; } }, 
    INDIRECT{ public String toString() { return "@" ; } };

    public static Pattern getPattern(){
        String res = "(#|@)";
        return Pattern.compile(res);
    }

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
