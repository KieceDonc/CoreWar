package corewar.Local;

/*
DAT -- data (kills the process)
MOV -- move (copies data from one address to another)
ADD -- add (adds one number to another)
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

public enum Mnemonique {
    DAT, MOV, ADD, SUB, MUL, DIV, MOD, JMP, 
    JMZ, JMN, DJN, SPL, CMP, SEQ, SNE, SLT,
    LDP, STP, NOP;
}
