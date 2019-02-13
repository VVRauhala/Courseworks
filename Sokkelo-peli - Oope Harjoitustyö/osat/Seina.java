package osat;

/**
 * Luokka joka m‰‰ritt‰‰ sein‰n tiedot. Perii juuri-luokan.
 * <p>
 * @author Vili-Veikko Rauhala (Rauhala.Vili.V@student.uta.fi).
 * Opiskelijanumero: 421839.
 * <p>
 * Tietojenk‰sittelytiede. OOPE Harjoiustyˆ.
 */

public class Seina extends Juuri {

   /*
    * ====================================================================
    * Attribuutit
    */

   /** K‰yt‰v‰‰ symboloiva merkki sokkelossa. */
   private final char SEINAMERKKI = '.';

   /*
    * =====================================================================
    * Aksessori
    */

   /**
    * Sein‰n merkin palauttava metodi
    *
    * @return sein‰n merkki '.'.
    */
   public char merkki() {
      return SEINAMERKKI;
   }

   /*
    * =====================================================================
    * Rakentaja
    */

   /**
    * Rakentaja joka kutsuu ylemm‰n hierarkiatason rakentajaa antamalla t‰lle
    * parametreina saadut arvot.
    *
    * @param r {@inheritDoc}
    * @param s {@inheritDoc}
    */
   public Seina(int r, int s) {
      super(r, s);
   }

   /*
    * =====================================================================
    * Metodit
    */

   /**
    * toString-metodin korvaus.
    *
    * @return {@inheritDoc}
    */
   @Override
   public String toString() {
      return super.toString();
   }
}