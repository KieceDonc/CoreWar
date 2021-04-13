package corewar.ClientSide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import corewar.Read;
import corewar.Regles;
import corewar.Utils;
import corewar.Network.SocketCommunication;
import corewar.ObjectModel.Player;
import corewar.ObjectModel.Rankings;
import corewar.ObjectModel.Warrior;
import corewar.ServerSide.ClientPrinterGameList;

/*
  Gère le client de façon générale
  Appeler "new Client()" pour intialiser un nouveau client
*/
public class Client {

  private Player currentPlayer;

  public Client() {
    initPlayer();
    mainMenu();
  }

  /*
    Initialise le joueur en lui choissisant un pseudo valide ( valide = pas déjà prit )
  */
  private void initPlayer() {
    String playerName = "";
    try {
      boolean isPlayerNameTaken = false;
      boolean incorrectPlayerName = false;
      do {
        if (isPlayerNameTaken) {
          System.out.println("Pseudo déjà prit, dommage :(\nVeuillez en prendre un nouveau");
        } else if (incorrectPlayerName) {
          System.out.println("Pseudo incorrect :(\nVeuillez en prendre un nouveau");
        }
        System.out .println("------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.print("Veuillez choisir un pseudo : ");
        playerName = Read.S();
        System.out.println("");
        System.out.println("------------------------------------------------------------------------------------------");
        incorrectPlayerName = playerName.length() <= 0;
        if (!incorrectPlayerName) {
          isPlayerNameTaken = isPlayerNameTaken(playerName);
        }
      } while (isPlayerNameTaken || incorrectPlayerName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    currentPlayer = new Player(playerName);
  }

  private boolean isPlayerNameTaken(String playerName) {
    try {
      Connexion connexion = new Connexion(new SocketCommunication(SocketCommunication.IS_PLAYER_NAME_TAKEN, playerName));
      connexion.start();
      connexion.join();
      return (boolean) connexion.getReceivedObject();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Erreur fatal dans la fonction choseName()");
    System.exit(0);
    return true;
  }

  private void mainMenu() {
    int choice = 0;
    int maxChoice = 8;
    do {
      System.out.println("------------------------------------------------------------------------------------------");
      System.out.println("");
      System.out.println("1 - Créer une partie");
      System.out.println("2 - Rejoindre une partie");
      System.out.println("3 - Voir le classement des joueurs");
      System.out.println("4 - Voir le classement des warriors");
      System.out.println("5 - Ajouter un Warrior");
      System.out.println("6 - Voir le Warrior");
      System.out.println("7 - Voir les règles");
      System.out.println("8 - Fermer");
      System.out.println("");
      System.out.print("Votre choix : ");
      choice = Read.i();
      System.out.println("");
      System.out.println("------------------------------------------------------------------------------------------");
    } while (choice > maxChoice || choice < 1);

    switch (choice) {
      case 1: {
        if(currentPlayer.getWarrior()!=null && currentPlayer.getWarrior().isReady()){
          createGame();
        }else{
          invalidProgram();
        }
        break;
      }
      case 2: {
        if(currentPlayer.getWarrior()!=null && currentPlayer.getWarrior().isReady()){
          joinGame(); 
        }else{
          invalidProgram();
        }
        break;
      }
      case 3: {
        //getRanking().print();
        System.out.println(getRankings().toStringPlayers());
        break;
      }
      case 4: {
        // getWarriorsRanking().print();
        System.out.println(getRankings().toStringWarriors());
        break;
      }
      case 5:{
        addWarrior();
        break;
      }
      case 6:{
        afficheWarrior();
        break;
      }
      case 7:{
        Regles.printRegles();
        break;
      }
      case 8 :{
        System.exit(0);
        break;
      }
      default: {
        System.out.println("wtf, unhandled choice, max choice =" + maxChoice + ", current choice = " + choice);
        System.out.println("Normally it happens when you don't increment maxChoice in clientMainMenu()");
        System.exit(0);
      }
    };

    mainMenu();
  }

  private void afficheWarrior() {
    Warrior w = currentPlayer.getWarrior();
    if(w == null)
      System.out.println("Veuillez d'abord ajouter un Warrior (option 5)");
    else{
      System.out.println(w.toStringFull());
    }
  }

  // Méthode ajoutant un programme à un joueur, seulement s'il est valide.
  //"corewar/src/main/java/corewar/Warriors/"
  private void addWarrior() {
    String[] pathnames;
    File f;

    // Le joueur rentre le répertoire de ses warriors
    System.out.println("Rentrez le chemin absolu vers le REPERTOIRE contenant vos warriors, sans / à la fin. EXEMPLE : C:/Users/Yuumi/Desktop/Warriors\nRentrez 0 si ils sont rangés dans un dossier nommé Warriors dans le même répertoire que ce .jar");
    String source = Read.S();
    if(source.equals("-1")) source = "corewar/src/main/java/corewar/Warriors/";
    if(source.equals("0")) source = "Warriors/";
    boolean valide = false;

    // Si le répertoire existe on peut continuer, sinon retour au menu
    f = new File(source);
    if(!f.isDirectory()){
      System.out.println("Le répertoire n'a pas été trouvé. Veuillez reessayer");
      return;
    } 
    

    // On commence par montrer tous les warriors afin que le joueur puisse choisir celui de son choix
    pathnames = f.list();
    int index = 1;
    for (String pathname : pathnames) {
        System.out.println(index+" || "+pathname);
        index++;
    }
    System.out.println("Rentrez le numero correspondant au fichier de votre choix, ou 0 pour quitter ce menu.");
    int choix = -1;
    // Si le joueur rentre 0, alors on sort sans rien faire. Sinon on peut le traiter, et s'il n'y a pas d'erreur, on peut le donner au joueur.
    do{
      choix = Read.i();
    }while(choix <0 || choix > pathnames.length);

    // On peut donc traiter le programme choisi si le nombre rentré est supérieur à 0 ( et correct )
    if(choix >0){

      choix--;
      Warrior w = Warrior.makeWarrior(source+pathnames[choix]);
      
      if(w != null){
        this.currentPlayer.setWarrior(w);
        valide = true;
        playerAddedWarrior(w);
      }
    }

    if(valide)
      System.out.println("Warrior correctement ajouté!");
    else
      System.out.println("Annulation du choix du Warrior... Retour au menu.");

    Utils.sleep(1000);
    return;
  }

  private void invalidProgram(){
    System.out.println("------------------------------------------------------------------------------------------");
    System.out.println("");
    System.out.println("Vous devez choisir un programme avant ");
    System.out.println("");
    System.out.println("------------------------------------------------------------------------------------------");
  }

  private void createGame() {
    try {
      Game game = Game.create(currentPlayer);
      game.start();
      game.join();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void joinGame() {
    ClientPrinterGameList gameList = getGameList();
    if (gameList.getSize() == 0) {
      System.out.println("------------------------------------------------------------------------------------------");
      System.out.println("");
      System.out.println("Aucune partie disponible");
      System.out.println("");
      System.out.println("------------------------------------------------------------------------------------------");
    } else {
      boolean firstSetup = true;
      int gameID = 0;
      do {
        if (!firstSetup) {
          gameList = getGameList();
        } else {
          firstSetup = false;
        }
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("");
        gameList.print();
        System.out.println("");
        System.out.println("Entrer -10 pour annuler votre action");
        System.out.println("");
        System.out.print("Veuillez écrire l'id de la partie que vous souhaitez rejoindre : ");
        gameID = Read.i();
        System.out.println("");
        System.out.println("");
        System.out.println("------------------------------------------------------------------------------------------");
      } while (gameID!=-10 && gameList.getByID(gameID) == -1);
      
      if(gameID!=-10){
        try {
          Game game = Game.join(currentPlayer, gameID);
          if(game!=null){
            game.start();
            game.join();
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
   }

  private Rankings getRankings(){
    try {
      Connexion connexion = new Connexion(new SocketCommunication(SocketCommunication.GET_RANKINGS, null));
      connexion.start();
      connexion.join();
      return (Rankings)connexion.getReceivedObject();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  private void playerAddedWarrior(Warrior warrior){
    System.out.println("------------------------------------------------------------------------------------------");
    System.out.println("");
    System.out.println("Envoi du warrior en cours ...");
    System.out.println("");
    System.out.println("------------------------------------------------------------------------------------------");
    try {
      Connexion connexion = new Connexion(new SocketCommunication(SocketCommunication.PLAYER_ADDED_WARRIOR, warrior));
      connexion.start();
      connexion.join();
      System.out.println("------------------------------------------------------------------------------------------");
      System.out.println("");
      System.out.println("Warrior reçu!");
      System.out.println("");
      System.out.println("------------------------------------------------------------------------------------------");
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  private ClientPrinterGameList getGameList(){
    try {
      Connexion connexion = new Connexion(new SocketCommunication(SocketCommunication.GET_GAME_LIST_PRINTER, null));
      connexion.start();
      connexion.join();
      return (ClientPrinterGameList)connexion.getReceivedObject();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    return null;
  }


}