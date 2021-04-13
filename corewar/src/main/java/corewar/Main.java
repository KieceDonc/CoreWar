package corewar;

import corewar.ServerSide.Server;
import corewar.ClientSide.Client;
import corewar.ClientSide.Local;

public class Main{

    public static boolean DEBUG_VALUE = true;
    
    public static void main(String[] args){

        boolean exit = false;

        printLogo();
        do{
            switch(serverOrClient()){
                case 1:{ 
                    new Client();
                    break;
                }
                case 2:{
                    new Server();
                    break;
                }
                case 3:{
                    Local.PartieLocale();
                    break;
                }
                case 4:{
                    exit = true;
                    break;
                }
                case 5:{
                    Debug.mainDebug();
                    break;
                }
            };
        }while(!exit);
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
            System.out.println("3 - Lancer une partie en local");
            System.out.println("4 - Quitter");
            System.out.println("");
            System.out.print("Votre choix : ");
            choice = Read.i();
            System.out.println("");
            System.out.println("------------------------------------------------------------------------------------------");    
        }while(choice>5 || choice<1);
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