package corewar;

import corewar.ServerSide.Server;
import corewar.ClientSide.Client;

public class Main{

    private static boolean DEBUG_VALUE = true;
    
    public static void main(String[] args){
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

    public static int createParty(){
        int choice = 0;
        do{
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println("");
            System.out.println("Partie ID : 12345");
            System.out.println("");
            System.out.println("Liste des joueurs : (2)");
            System.out.println("");
            System.out.println("Joueur 1 : Ben ( Programme : Terminator )");
            System.out.println("Joueur 2 : Alan ( Programme : Turing )");
            System.out.println("");
            System.out.println("Options :");
            System.out.println("    1- Démarrer la partie");
            System.out.println("    2- Annuler la partie");
            System.out.println("");
            System.out.print("Votre choix : ");
            choice = Lire.i();
            System.out.println("");
            System.out.println("------------------------------------------------------------------------------------------");    
        }while(choice<1 || choice>2);
        return choice;
    }

    public static String choseName(){
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.print("Veuillez choisir un pseudo : ");
        String playerName = Lire.S();
        System.out.println("");
        System.out.println("------------------------------------------------------------------------------------------");
        return playerName;
    }

    public static int clientMainMenu(){
        int choice = 0;
        do{
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println("");
            System.out.println("1 - Crée une partie");
            System.out.println("2 - Rejoindre une partie");
            System.out.println("3 - Voir le classement");
            System.out.println("");
            System.out.print("Votre choix : ");
            choice = Lire.i();
            System.out.println("");
            System.out.println("------------------------------------------------------------------------------------------");    
        }while(choice>3 || choice<1);
        return choice;
    }

    public static int serverOrClient(){
        int choice = 0;
        do{
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println("");
            System.out.println("1 - Rejoindre en tant que joueur");
            System.out.println("2 - Créer un serveur");
            System.out.println("");
            System.out.print("Votre choix : ");
            choice = Lire.i();
            System.out.println("");
            System.out.println("------------------------------------------------------------------------------------------");    
        }while(choice>2 || choice<1);
        return choice;
    }

    public static void DebugMessage(String str){
        if(DEBUG_VALUE){
            System.out.println("DEBUG : "+str);
        }
    }

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

    public static void clearConsole(){
        try{
            String os = System.getProperty("os.name");

            if (os.contains("Windows")){
                Runtime.getRuntime().exec("cls");
            }else{
                System.out.print("\033\143");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
