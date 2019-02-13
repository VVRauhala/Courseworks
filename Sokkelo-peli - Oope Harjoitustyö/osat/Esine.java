package osat;

/**
 * Luokka joka m��ritt�� esineen tiedot.
 * <p>
 * @author Vili-Veikko Rauhala (Rauhala.Vili.V@student.uta.fi)
 * Opiskelijanumero: 421839.
 * <p>
 * Tietojenk�sittelytiede OOPE. Harjoiusty�.
 */

public class Esine extends Sisalto {

   /*
    * =====================================================================
    * Attribuutit
    */

   /** Esinett� symboloiva merkki sokkelossa. */
   private final static char ESINE = 'E';

   /*
    * =====================================================================
    * Aksessorit
    */

   /**
    * M�nkijan merkin palauttava metodi.
    *
    * @return m�nkij�n merkki 'M'.
    */
   public static char merkki() {
      return ESINE;
   }

   /*
    * =====================================================================
    * Rakentaja
    */

   /**
    * Rakentaja joka kutsuu ylemm�n hierarkiatason rakentajaa antamalla t�lle
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
    * toString-metodin korvaus. Koska esineell� ei ole muuta tietoa kuin rivi-
    * indeksi, sarakeindeksi sek� energia, saadaan t�m� suoraan kutsumalla
    * yliluokan (Sisalto) toString-metodia.
    *
    * @return {@inheritDoc}
    */
   @Override
   public String toString() {
      return super.toString();
   }
}
