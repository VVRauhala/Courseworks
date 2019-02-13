import tui.Kayttoliittyma;

/**
 * Ajoluokka, joka tulostaa logon sekä käynnistää pelin.
 * <p>
 * @author Vili-Veikko Rauhala (Rauhala.Vili.V@student.uta.fi)
 * Opiskelijanumero: 421839
 * <p>
 * Tietojenkäsittelytiede OOPE Harjoiustyö
 */

public class Oope2016HT {

   /** Metodi logon tulostamiseen */
   public static void logo() {
      System.out.println("***********");
      System.out.println("* SOKKELO *");
      System.out.println("***********");
   }

   /**
    * Ajoluokka. Tulostaa aluksi logon ja tämän jälkeen suorittaa
    * Kayttoliittyma-luokan pelaa-metodia.
    */
   public static void main(String[] args) {

      // Tulostetaan logo näytölle
      logo();

      // Käynnistetään käyttöliittymä
      Kayttoliittyma.pelaa();
   }
}
