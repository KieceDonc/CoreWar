package corewar;

import java.util.concurrent.TimeUnit;

public class Utils {

    // Appeler pour effacer l'écran 
    public static void clear(){
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    // Endort le programme un certain temps. int milliseconds -> temps en millisecondes (1000 = 1s)
    public static void sleep(int milliseconds){
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            ;
        }

    }

    // Fait une animation avec 3 petits points qui cyclent. int loop -> nombre de frame d'animation 1 frame = 0.75s, String text -> texte affiché avec l'animation
    public static void animation(int loop, String text){
        String s3 = " _        _        _\n/\\_\\     /\\_\\     /\\_\\\n\\/_/     \\/_/     \\/_/\n";
        String s2 = " _        _\n/\\_\\     /\\_\\\n\\/_/     \\/_/\n";
        String s1 = " _\n/\\_\\\n\\/_/\n";
        String ligne = "------------------------------------------------------------------------------------------\n";
        System.out.println(ligne+"\n"+text+"\n\n\n\n\n"+ligne);
        for(int i = 1 ; i < loop ; i++){
            sleep(750);
            if(i%4 == 0) System.out.println(ligne+"\n"+text+"\n\n\n\n\n"+ligne);
            else if(i%4 == 1) System.out.println(ligne+"\n"+text+"\n"+s1+"\n"+ligne);
            else if(i%4 == 2) System.out.println(ligne+"\n"+text+"\n"+s2+"\n"+ligne);
            else if(i%4 == 3) System.out.println(ligne+"\n"+text+"\n"+s3+"\n"+ligne);
        }
    }
}
