package corewar;

import java.io.*;

/*
      Les méthodes de lire bloque le programme jusqu'à une entrée valide
*/
public class Lire{

      public static String S(){ // Lire un String
            boolean error = false;
            String tmp = "";
            char C='\0';
            do{
                  try{
                        while ((C=(char) System.in.read()) !='\n'){
                        if (C != '\r')  tmp = tmp+C;
                        
                        }
                        error=false;
                  }catch (IOException e){
                        System.out.println("Erreur");
                        System.out.println("Veuillez s'il vous plaît rentrer une chaîne de caractère :");
                        error=true;
                  } 
            }while(error); 
            return tmp;
      } // fin de S()

      public static byte b(){  // Lire un entier byte
            byte x=0;
            boolean error = false;
            do{
                  try{
                        x=Byte.parseByte(S());
                        error=false;
                  }catch (NumberFormatException e){
                        System.out.println("Erreur");
                        System.out.println("Veuillez s'il vous plaît rentrer un entier byte :");
                        error=true;
                  }     
            }while(error);
            return x;
      }        
            
      public static short s(){  // Lire un entier short
            short x=0;
            boolean error=false;
            do{
                  try{
                        x=Short.parseShort(S());
                        error=false;
                  }catch(NumberFormatException e){
                        System.out.println("Erreur");
                        System.out.println("Veuillez s'il vous plaît rentrer un entier short :");
                        error=true;
                  }     
            }while(error);
            return x;
      }        
          
      public static int i(){  // Lire un entier
            int x=0;
            boolean error=true;
            do{
                  try{
                        x=Integer.parseInt(S());
                        error=false;
                  }catch (NumberFormatException e) {
                        System.out.println("Erreur");
                        System.out.println("Veuillez s'il vous plaît rentrer un entier :");
                        error=true;
                  }     
            }while(error);
            return x;
      }        
            
      public static long l(){  // Lire un entier long
            long x=0;
            boolean error=false;
            do{
                  try{
                        x=Integer.parseInt(S());
                        error=false;
                  }catch(NumberFormatException e){
                        System.out.println("Erreur");
                        System.out.println("Veuillez s'il vous plaît rentrer un entier long");
                        error=true;
                  }     
            }while(error);
            return x;
      }        
          
            
      public  static double d(){  // Lire un double
            double x=0.0;
            boolean error=false;
            do{
                  try{
                        x=Double.valueOf(S()).doubleValue();
                        error=false;
                  }catch (NumberFormatException e) {
                        System.out.println("Erreur");
                        System.out.println("Veuillez s'il vous plaît rentrer un double");
                        error=true;
                  }     
                  return x ;
            }while(error);
      }        
            
      public  static float f(){  // Lire un float
            float x=0.0f;
            boolean error=false;
            do{
                  try{
                        x=Double.valueOf(S()).floatValue();
                        error=false;
                  }catch (NumberFormatException e){
                        System.out.println("Erreur");
                        System.out.println("Veuillez s'il vous plaît rentrer un float");
                        error=true;
                  }
            }while(error);     
            return x;
      }        
          
            
      public  static char c(){// Lire un caractere
            String tmp=S();
            if(tmp.length()==0){
                  return '\n';
            }else{
                  return tmp.charAt(0);
            }
      }        
}         
