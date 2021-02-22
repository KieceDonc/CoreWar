package corewars.elements;

/**
Une instruction est une ligne de code assembleur. Elle est composée de :
- (facultatif) un Label
- une mnémonique (opération à effectuer, comme MOV, SPL, etc...)
- deux opérandes A et B (#0, 5, etc...)
*/
public class Instruction {
    
    private Mnemonique mnemo;
    private Operande opeA;
    private Operande opeB; 
    
}
