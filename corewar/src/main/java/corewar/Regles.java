package corewar;

public class Regles {

    public static void printRegles() {
        final int big = 1000;
        final int med = 500;
        final int smol = 250;
        final int megasmol = 100;
        Utils.clear();
        System.out.println("Core War est un jeu de programmation conçu par D. G. Jones and A. K. Dewdney\nCe programme est un simulateur MARS, permettant d'interpréter des programmes en langage Redcode, et d'héberger leur combat au sein d'une mémoire virtuelle.");
        System.out.println("Le langage Redcode est un langage d'assemblage.\nIl s'agit d'une suite d'instructions, lignes par lignes, constituées de différents éléments, voici un exemple d'instruction Redcode : ");
        Utils.sleep(med);
        System.out.println("\n\tMOV\t#3,\t5\n");
        Utils.sleep(med);
        System.out.println("Et un exemple de programme Redcode :");
        Utils.sleep(med);
        System.out.println("\nstart\tADD\t#4,\tbomb");
        Utils.sleep(smol);
        System.out.println("\tMOV\t2,\t@2");
        Utils.sleep(smol);
        System.out.println("\tJMP\tstart");
        Utils.sleep(smol);
        System.out.println("bomb\tDAT\t#0\n");
        Utils.sleep(med);
        System.out.println("Les Mnemoniques (ou \"op-code\") comme MOV, ou JMP sont des commandes qui permettent d'effectuer différentes actions simples,\ncomme \"Copie cette instruction 2 cases memoire plus loin\" ou \"Lit l'instruction 3 cases memoire plus loin seulement si ce nombre est egal a zero\"");
        System.out.println("On utilises les opérandes pour indiquer où effectuer ces actions, comme @2, 5 ou #-4. Elles sont constituées d'une adresse (le nombre) et d'un mode d'adressage (le symbole devant le nombre)\nLa première opérandes d'une instruction est nommée Operande A / Op A, la seconde Operande B / Op B");
        System.out.println("Eventuellement, on peut nommer des instructions avec des labels, \"start\" et \"bomb\" dans l'exemple, pour pouvoir se repérer dans le code.");
        Utils.sleep(big);
        System.out.println("\n\n\n----------- LA LISTE DES OPERANDES");
        System.out.println("DAT - \"Data\" : Donnée. Indique que l'Op A contient un nombre et non pas une adresse. Si le programme tente d'executer une instruction DAT, il meurt."); Utils.sleep(megasmol);
        System.out.println("SPL - \"Split\" : Crée un nouveau processus à l'adresse indiquée par Op A. Chaque processus est joué a tour de rôle au tour du joueur.");Utils.sleep(megasmol);
        System.out.println("SNE - \"Skip if Not Equal\" : Saute la prochaine instruction si les données pointées par Op A et Op B sont différentes");Utils.sleep(megasmol);
        System.out.println("MOV - \"Move\" : copie la donnée de Op A vers Op B");Utils.sleep(megasmol);
        System.out.println("ADD - \"Add\" : Aditionne la donnée de Op A à celle de Op B");Utils.sleep(megasmol);
        System.out.println("SUB - \"Substract\" : Soustrait la donnée de Op A à celle de Op B");Utils.sleep(megasmol);
        System.out.println("JMP - \"Jump\" : Continue l'execution du programme à l'adresse indiquée par Op A");Utils.sleep(megasmol);
        System.out.println("JMZ - \"Jump if Zero\" : Continue l'execution du programme à l'adresse indiquée par Op A seulement si Op B vaut 0");Utils.sleep(megasmol);
        System.out.println("JMG - \"Jump if greter than Zero\" : Continue l'execution du programme à l'adresse indiquée par Op A seulement si Op B est supérieur 0");Utils.sleep(megasmol);
        System.out.println("JMN - \"Jump if not Zero\" : Continue l'execution du programme à l'adresse indiquée par Op A seulement si Op B est différent de 0");Utils.sleep(megasmol);
        System.out.println("DJZ - \"Decrement and jump if Zero\" : Comme JMZ mais décrémente Op B de 1 avant de faire la comparaison a 0");Utils.sleep(megasmol);
        System.out.println("DJN - \"Decrement and jump if not Zero\" : Comme JMN mais décrémente Op B de 1 avant de faire la comparaison a 0");Utils.sleep(megasmol);
        System.out.println("SEQ - \"Skip if Equal\" : Aussi appelée \"CMP - 'Compare' saute la prochaine instruction si les données pointées par Op A et Op B sont égales");Utils.sleep(megasmol);
        System.out.println("SNE - \"Skip if Not Equal\" : Saute la prochaine instruction si les données pointées par Op A et Op B sont différentes");
        Utils.sleep(med);
        System.out.println("\n\n----------- LA LISTE DES MODES D'ADRESSAGE");Utils.sleep(med);
        System.out.println("DIRECT - \"N\" ou\"$N\" : Pointe vers l'adresse située à N cases mémoire de distance de celle ci.");Utils.sleep(megasmol);
        System.out.println("INDIRECT - \"@N\" : Pointe vers l'adresse pointée par la donnée située à N cases mémoire de distance de celle ci.");Utils.sleep(megasmol);
        System.out.println("IMMEDIAT - \"#N\" : Indique une donnée brute, le nombre N.");
        Utils.sleep(big);
        System.out.println("\n\n\n----------- COMPTAGE DES POINTS");Utils.sleep(med);
        System.out.println("A la fin d'une partie, un score est calculé pour chaque Warrior (et ce score est attribué au joueur ayant joué ce warrior)");Utils.sleep(megasmol);
        System.out.println("Score de vie - 500 points sont partagés entre tous les warriors possédant un processus encore en vie à la fin de la partie.");Utils.sleep(megasmol);
        System.out.println("Score de possession - de 0 à 400 points sont attribués aux warriors en fonction de l'espace mémoire occupé. 0 s'ils ne contrôlent aucune case mémoire, et 400 s'ils en contrôlent la totalité.");Utils.sleep(megasmol);
        System.out.println("Bonus processus - de 0 à 100 points sont attribués aux warriors en fonction du nombre de processus encore en vie à la fin de la partie. +10 par processus jusqu'à un maximum de 100.");Utils.sleep(megasmol);
        System.out.println("TOTAL - La somme des 3 scores correspond au score total attribué à l'entité dont on calcule le score.");

        Utils.sleep(3*big);

        
        
        
        // MOV -- move (copies data from one address to another)
        // ADD -- add (adds one number to another)
        // SUB -- subtract (subtracts one number from another)
        // JMP -- jump (continues execution from another address)
        // JMZ -- jump if zero (tests a number and jumps to an address if it's 0)
        // JMG -- jump if greater than zero
        // JMN -- jump if not zero (tests a number and jumps if it isn't 0)
        // DJN -- decrement and jump if not zero (decrements a number by one, and jumps unless the result is 0)
        // DJZ -- decrement and jump if zero ((decrements a number by one, and jumps if the result is zero)
        // CMP -- compare (same as SEQ)
        // SPL -- split (creates new process)
        // DAT -- data (kills the process)





    }
    
}
