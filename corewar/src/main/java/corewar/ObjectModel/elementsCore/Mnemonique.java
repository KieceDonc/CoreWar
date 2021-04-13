package corewar.ObjectModel.elementsCore;

import java.io.Serializable;
import java.util.regex.Pattern;

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
*/

public enum Mnemonique implements Serializable{
    MOV,ADD,SUB,JMP,JMZ,JMG,JMN,DJN,DJZ,CMP,SEQ,SNE,SPL,DAT;

    // Retourne un regex faisant un OU logique de toutes les mnémoniques (insensible à la casse)
    public static Pattern getPattern(){
        return Pattern.compile("(MOV|ADD|SUB|JMP|JMZ|JMG|JMN|DJN|DJZ|CMP|SEQ|SNE|SPL|DAT)",Pattern.CASE_INSENSITIVE);
    }
}
