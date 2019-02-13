import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Integer;
import java.io.File;

/*
 * Tietorakenteet 2016
 * Harjoitustyö
 * Vili-Veikko Rauhala
 * Rauhala.Vili.V@Student.uta.fi
 */

public class Tira2016 {
   
   public static void main(String[] args) {
      
      
      File input = new File(args[0]);
      
      File output = new File(args[1]);
      
      // Luodaan puu
      BinaryTree puu = new BinaryTree();
      
      readInput(puu, input, output);
   } 
   
   /*
    * readInput
    * 
    * Metodi lukee input.txt tiedostosta syötteet ja kirjoitetaan 
    * palaute output.txt tiedostoo.
    * 
    * parametrina puu.
    */
   private static void readInput(BinaryTree puu, File input, File output) {
      String line;
      try {
         BufferedReader br = new BufferedReader( new FileReader(input));
         BufferedWriter bw = new BufferedWriter (new FileWriter(output));
         while ((line = br.readLine()) != null) {
            String[] values = line.split(" ");
            int valuesAmount = values.length;
            
            // Palauttaa puun koon
            if (values[0].equals("s")) {
               if (valuesAmount == 1) {
                  if (puu.isEmpty() == true) {
                     bw.write("Jono on tyhjä.");
                     bw.newLine();
                  }
                  else {
                     int puunKoko = puu.getSize();
                     bw.write(Integer.toString(puunKoko));
                     bw.newLine();
                  }
               }
               else {
                  bw.write("Virheellinen syöte.");
                  bw.newLine();
               }
            }
            
            // Lisää puun alkion.
            else if (values[0].equals("i")) {
               if (valuesAmount == 3) {
                  if (isInteger(values[1])) {
                     // Muutetaan String -> int
                     int key = Integer.parseInt(values[1]);
                     
                     if (key <= 0) {
                        bw.write("Virheellinen syöte.");
                        bw.newLine();
                     }
                     else {
                        if (puu.doesContain(key, values[2]) == true) {
                           bw.write("Avain " + key + " on jo jonossa.");
                           bw.newLine();
                        }
                        else {
                           puu.insertItem(key, values[2]);
                           bw.write("(" + key + "," + values[2] + ") lis.");
                           bw.newLine();
                        }
                     }
                  }
                  else {
                     bw.write("Virheellinen syöte.");
                     bw.newLine();
                  }
               }
               else {
                  bw.write("Virheellinen syöte.");
                  bw.newLine();
               }
            }
            
            // Pienimmän alkion (juuren) poisto.
            else if (values[0].equals("r")) {
               if (valuesAmount == 1) {
                  if (puu.isEmpty() == true) {
                     bw.write("Jono on tyhjä.");
                     bw.newLine();
                  }
                  else {
                     Node minElement = puu.removeMinElement();
                     bw.write("(" + minElement.getKey() + "," + minElement.getData() + ") poistettu.");
                     bw.newLine();
                  }
               }
               else {
                  bw.write("Virheellinen syöte.");
                  bw.newLine();
               }
            }
            
            // Pienimmän alkion (juuren) palautus. 
            else if (values[0].equals("m")) {
               if (valuesAmount == 1) {
                  if (puu.isEmpty() == true) {
                     bw.write("Jono on tyhjä");
                     bw.newLine();
                  }
                  else {
                     bw.write("Pienin alkio on (" + puu.minKey() + "," + puu.minElement() + ")");
                     bw.newLine();
                  }
               }
               else {
                  bw.write("Virheellinen syöte.");
                  bw.newLine();
               }
            }
            
            // Puun tulostus
            // Metodi ei kirjoita puun tulostetta Output.txt tiedostoon.
            // Tästä syystä tyhjän puun printtaaminen ei myöskään kirjoita
            // ilmoitusta tästä Output.txt tiedostoon. Molemmissa tapauksissa
            // metodi antaa tuloksen komentoriville.
            else if (values[0].equals("p")) {
               if (valuesAmount == 1) {
                  if (puu.isEmpty() == true) {
                     // tulostetaan vain komentoriville
                     System.out.println("Jono on tyhjä.");
                  }
                  else {
                     // tulostetaan vain komentoriville
                     puu.preorderPrint();
                  }
               }
               else {
                  bw.write("Virheellinen syöte.");
                  bw.newLine();
               }
            }
            
            // Quit
            else if (values[0].equals("q")) {
               if (valuesAmount == 1) {
                  bw.write("Ohjelma lopetettu.");
                  bw.newLine();
                  bw.close();
                  br.close();
                  break;
               }
               else {
                  bw.write("Virheellinen syöte.");
                  bw.newLine();
               }
            }
            
            else {
               bw.write("Virheellinen syöte.");
               bw.newLine();
            }
         }
         bw.close();
         br.close();
      }
      catch(IOException e) {
         System.out.println("File not found.");
      }
   }
   
   /*
    * isInteger
    * 
    * Metodi joka tarkastaa onko syöteenä annettu avain mahdollista 
    * muuttaa int-muotoon.
    * Tarkastaa myös onko kysymyksessä negatiivininen luku.
    * 
    * Parametrina str(mahdollinen avain).
    * Paluuarvona totuusarvo.
    */
   public static boolean isInteger(String str) {
      // Jos string on null
      if (str == null) {
         return false;
      }
      int length = str.length();
      // Jos string on 0 merkkiä pitkä
      if (length == 0) {
         return false;
      }
      int i = 0;
      // Jos str sisältää '-', mutta on vain 1 merkin mittainen
      if (str.charAt(0) == '-') {
         if (length == 1) {
            return false;
         }
         i = 1;
      }
      for (; i < length; i++) {
         char c = str.charAt(i);
         if (c < '0' || c > '9') {
            return false;
         }
      }
      return true;
   }
}
