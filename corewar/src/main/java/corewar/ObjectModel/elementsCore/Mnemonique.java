package corewar.ObjectModel.elementsCore;

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

public enum Mnemonique {
    MOV,ADD,SUB,JMP,JMZ,JMG,JMN,DJN,DJZ,CMP,SEQ,SNE,SPL,DAT;

    // Retourne un regex faisant un OU logique de toutes les mnémoniques (insensible à la casse)
    public static Pattern getPattern(){
        return Pattern.compile("(MOV|ADD|SUB|JMP|JMZ|JMG|JMN|DJN|DJZ|CMP|SEQ|SNE|SPL|DAT)",Pattern.CASE_INSENSITIVE);
    }
}
