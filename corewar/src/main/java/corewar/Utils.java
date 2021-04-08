package corewar;

import java.util.concurrent.TimeUnit;

public class Utils {
    public static void clear(){
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    public static void sleep(int milliseconds){
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            ;
        }

    }

    public static void animation(int loop, String text){
        String s3 = " _        _        _\n/\\_\\     /\\_\\     /\\_\\\n\\/_/     \\/_/     \\/_/\n";
        String s2 = " _        _\n/\\_\\     /\\_\\\n\\/_/     \\/_/\n";
        String s1 = " _\n/\\_\\\n\\/_/\n";
        String ligne = "------------------------------------------------------------------------------------------\n";
        clear();
        System.out.println(ligne+"\n"+text+"\n\n\n\n\n"+ligne);
        for(int i = 1 ; i < loop ; i++){
            sleep(750);
            clear();
            if(i%4 == 0) System.out.println(ligne+"\n"+text+"\n\n\n\n\n"+ligne);
            else if(i%4 == 1) System.out.println(ligne+"\n"+text+"\n"+s1+"\n"+ligne);
            else if(i%4 == 2) System.out.println(ligne+"\n"+text+"\n"+s2+"\n"+ligne);
            else if(i%4 == 3) System.out.println(ligne+"\n"+text+"\n"+s3+"\n"+ligne);
        }
        clear();
    }
}
