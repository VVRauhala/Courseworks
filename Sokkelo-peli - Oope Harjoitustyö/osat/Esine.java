package osat;

/**
 * Luokka joka määrittää esineen tiedot.
 * <p>
 * @author Vili-Veikko Rauhala (Rauhala.Vili.V@student.uta.fi)
 * Opiskelijanumero: 421839.
 * <p>
 * Tietojenkäsittelytiede OOPE. Harjoiustyö.
 */

public class Esine extends Sisalto {

   /*
    * =====================================================================
    * Attribuutit
    */

   /** Esinettä symboloiva merkki sokkelossa. */
   private final static char ESINE = 'E';

   /*
    * =====================================================================
    * Aksessorit
    */

   /**
    * Mönkijan merkin palauttava metodi.
    *
    * @return mönkijän merkki 'M'.
    */
   public static char merkki() {
      return ESINE;
   }

   /*
    * =====================================================================
    * Rakentaja
    */

   /**
    * Rakentaja joka kutsuu ylemmän hierarkiatason rakentajaa antamalla tälle
    * parametreina saadut arvot.
    *
    * @param r {@inheritDoc}
    * @param s {@inheritDoc}
    * @param e {@inheritDoc}
    */
   public Esine(int r, int s, int e) {
      super(r, s, e);
   }

   /*
    * =====================================================================
    * Metodit
    */

   /**
    * toString-metodin korvaus. Koska esineellä ei ole muuta tietoa kuin rivi-
    * indeksi, sarakeindeksi sekä energia, saadaan tämä suoraan kutsumalla
    * yliluokan (Sisalto) toString-metodia.
    *
    * @return {@inheritDoc}
    */
   @Override
   public String toString() {
      return super.toString();
   }
}
