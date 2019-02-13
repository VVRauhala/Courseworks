import tui.Kayttoliittyma;

/**
 * Ajoluokka, joka tulostaa logon sek� k�ynnist�� pelin.
 * <p>
 * @author Vili-Veikko Rauhala (Rauhala.Vili.V@student.uta.fi)
 * Opiskelijanumero: 421839
 * <p>
 * Tietojenk�sittelytiede OOPE Harjoiusty�
 */

public class Oope2016HT {

   /** Metodi logon tulostamiseen */
   public static void logo() {
      System.out.println("***********");
      System.out.println("* SOKKELO *");
      System.out.println("***********");
   }

   /**
    * Ajoluokka. Tulostaa aluksi logon ja t�m�n j�lkeen suorittaa
    * Kayttoliittyma-luokan pelaa-metodia.
    */
   public static void main(String[] args) {

      // Tulostetaan logo n�yt�lle
      logo();

      // K�ynnistet��n k�ytt�liittym�
      Kayttoliittyma.pelaa();
   }
}
