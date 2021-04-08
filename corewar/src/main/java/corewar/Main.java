package corewar;

import corewar.ServerSide.Server;
import corewar.ClientSide.Client;

public class Main{

    public static boolean DEBUG_VALUE = true;
    
    public static void main(String[] args){
        // PASSER EN COMMENTAIRE POUR TESTER SERVEUR
        test();

        printLogo();
        switch(serverOrClient()){
            case 1:{ 
                new Client();
                break;
            }
            case 2:{
                new Server();
            }
        };
    }

    public static void test(){
        Test.testTrucs();
    }

    /*
        Imprime le menu pour savoir si on souhaite lancer le serveur ou le client
        * return {int} 1 représente le choix de rejoindre en tant que joueur, 2 représente le choix de vouloir lancer le serveur
    */
    public static int serverOrClient(){
        int choice = 0;
        do{
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println("");
            System.out.println("1 - Rejoindre en tant que joueur");
            System.out.println("2 - Créer un serveur");
            System.out.println("");
            System.out.print("Votre choix : ");
            choice = Read.i();
            System.out.println("");
            System.out.println("------------------------------------------------------------------------------------------");    
        }while(choice>2 || choice<1);
        return choice;
    }

    /*
        Imprime le logo de core war 
    */
    public static void printLogo(){
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@&@@@@@@@@@@@@@@@%@@@@@@@@@@@@@@@@@@@&@@&@@@@@@@@@@@@@@@@@@");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@@@@@@@@@@@@@@@@@@@@%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%@@@@@@@@@@@@@");
        System.out.println("@@%@@@@&@@@@@@@@@&@@@@@#@@@@@@@@/@@@@@@@@@@@@@@@@@@@&&@@&%@@@@@@@@@@@&@@@@@@@@#@");
        System.out.println("@@@@@#@%@@@@@@&@@@@@@@@@%%@@@@@@@@&@@@@@@@@@@@@@@(/@@@@@@@@@@@@@%@@@@@@%@@@@@@@@");
        System.out.println("@@@@@@@@@@@@@@@@&@@@@@@@@@@@@@@&%@@@@@@@@@@@@@@@@@@@#%@@@@@@@@/@@@@@@@%@@@@@@&@@");
        System.out.println("@@@@@@@@@@@@@&#@@@@@&&@@@@@@@@@@@%(@@@%&@@&%@@@@@@@@@@@@@@@%&@@@(@@@@@@@@@@@@@&@");
        System.out.println("@@@@@@@@@@@@@@@&@@@@@@@@(%&&@@@@@@@@@@@@&&@@@(&@@@@@@@@@@@@@@@&@@@@@@@%@@@@@@@@@");
        System.out.println("@@@@@@&@&,         /@@@@@@#.         *@@@@.             .%@%                @@@@");
        System.out.println("@&@@&@  .&@@@@@@@@@#  (&  .@@@@@@@@@@%  .& *@@@@@@@@@@@@@    @@@@@@@@@@@@@% /@@&");
        System.out.println("@@@@%  @@@@@@/,(@@@@@*   @@@@@&*,(@@@@@&   *@@@@%   .@@@@@   @@@@@#         /@@@");
        System.out.println("@@@@. &@@@@, (@.        @@@@@  &@. #@@@@/  *@@@@&//(@@@@@@   @@@@@&%%%%%%%  @@@@");
        System.out.println("@@@@. @@@@@  &@&     .  @@@@@  @@( *@@@@#  *@@@@@@@@@@%.  *, @@@@@@@@@@@@@  @@@@");
        System.out.println("&@@@, /@@@@%     @@@@@  %@@@@#     @@@@@.  *@@@@%  &@@@@@  . @@@@@.         #@@@");
        System.out.println("@@@@@. .@@@@@@@@@@@@&    *@@@@@@@@@@@@@  * *@@@@%   *@@@@@.  @@@@@@@@@@@@@@ .@@@");
        System.out.println("@@@@@@@,   /%&@@%/   (@@@   .(&@@@%*   ,@& ,####( ((  ###%%* ############## .@@@");
        System.out.println("@@@@@@@@@@@%&@@@@@@@%#%%#%&%&#%##%#&/#%#@/@@@@%@@@@@@@@@*/@@@@@%%@%@@@@@@@@@@@@@");
        System.out.println("@@@@@@@@@( .,,,,    ,,,,,.   .,,,,  &&  ,,,,,,  (@@@  ,,,,,,,,,,.   *@&@@@@@@@@@");
        System.out.println("@@@&#@@@@&  @@@@&  ,@@@@@@   @@@@@  &  @@@@@@@@  (&@  @@@@@@@@@@@@@@  %@@@@@@%@@");
        System.out.println("@@@@@@@&@@, (@@@@. @@@@@@@@ *@@@@, (  @@@@@@@@@@  @@  @@@@@    (@@@@/ %@@@@@@*@@");
        System.out.println("@@@@@@@@@@@  @@@@&&@@@(&@@@(@@@@@    %@@@@  @@@@&  @  @@@@@@@@@@@@@/  @@&@@@@@@@");
        System.out.println("@@@@@@@@@@@* *@@@@@@@@  @@@@@@@@.   (@@@@/  ,@@@@%    @@@@@.#@@@@@  ,@@@@@@@@@@@");
        System.out.println("@@@@@@@@@@@@  @@@@@@@   ,@@@@@@&   *@@@@@@@@@@@@@@#   @@@@@   @@@@@%  @@@@@@@@@@");
        System.out.println("@@@@@@@@@@@@( ,@@@@@/ *. %@@@@@   .@@@@@      &@@@@/  @@@@@ .  #@@@@@  #@&@@@@@@");
        System.out.println("@@&@&@@%@@@@@.        @@        .        %@@@               (@/        (@@@&@@@@");
        System.out.println("@@@@@@#&@@@@@@@@&@@@@@@@@%@@@@@//@@@&#@@%%&#&@%&%@%&(&@@@@@@@@(@@@@@@@@@@@@@@@@@");
        System.out.println("@@@@@@@@@@@@@&(@@@%@@@@@@@@@@@@@%(/@@@@@@@@%@@@@@%%@@@@@@@@@@@@@(@@@@@@@@@@@@@@@");
        System.out.println("&@@@@@@@@@@@@@@##@@@@@@@&#@@@@@&%&%@#&%&%%&(&%@@&@@@@@@@@@@@@&#@@@@@@@@@@@@@@@@@");
        System.out.println("@@@@@@@&@@@@@@@@@@@@@@@@@@&&@@@@@@@@@@@@@@@%@&&&&@@@@@@@@@&@@@@@%@@@@@@@@%@@@@@@");
        System.out.println("@@@@@@@@%@@@@@@&@@@@%@@@@@@@@%@@@@@@@@@@@@@@@@@@@@@&&@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@@@@@@@@@@%@@@@@@@@@@@@@@@@&@@@@@@@@(@@@@@@@@@@@@@@@@@@@(@&@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@#@@@@&@#@@@&@@#@@@@&@@@&@@&@@@&@@@@@@/@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("------------------------------------------------------------------------------------------");
    }

}