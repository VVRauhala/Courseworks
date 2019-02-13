package osat;

import apulaiset.Paikallinen;
import tui.Kayttoliittyma;

/**
 * Luokka joka toimii esi-is�n� eri sokkelon osille. Sis�lt�� teidot rivi- sek�
 * sarakeindeksist�, sek� erottimen merkin. Metodeina sallittu-operaatio sek�
 * my�s toString-metodin korvaus.
 * <p>
 * @author Vili-Veikko Rauhala (Rauhala.Vili.V@student.uta.fi).
 * Opiskelijanumero: 421839.
 * <p>
 * Tietojenk�sittelytiede. OOPE Harjoiusty�.
 */

public class Juuri implements Paikallinen {

   /*
    * =====================================================================
    * Attribuutit
    */

   /** Erotinta symboloiva merkki. */
   protected final char EROTIN = '|';

   /** Olion paikkaa vastaava riviIndeksi sokkelossa. */
   private int riviIndeksi;

   /** Olion paikkaa vastaava sokkeloIndeksi sokkelossa. */
   private int sarakeIndeksi;

   /*
    * =====================================================================
    * Aksessorit
    */

   /**
    * {@inheritDoc}
    *
    * @return riviIndeksi
    */
   public int rivi() {
      return riviIndeksi;
   }

   /**
    * {@inheritDoc}
    *
    * @param i {@inheritDoc}
    * @throws IllegalArgumentException {@inheritDoc}
    */
   public void rivi(int i) throws IllegalArgumentException {
      if (i >= 0)
         riviIndeksi = i;
      else
         throw new IllegalArgumentException();
   }

   /**
    * {@inheritDoc}
    *
    * @return sarakeIndeksi.
    */
   public int sarake() {
      return sarakeIndeksi;
   }

   /**
    * {@inheritDoc}
    *
    * @param j {@inheritDoc}
    * @throws IllegalArgumentException {@inheritDoc}
    */
   public void sarake(int j) throws IllegalArgumentException {
      if (j >= 0)
         sarakeIndeksi = j;
      else
         throw new IllegalArgumentException();
   }

   /*
    * =====================================================================
    * Rakentajat
    */

   /**
    * Rakentaja joka asettaa parametreina saadut r ja s arvot riviIndeksiksi ja
    * sarakeIndeksiksi aksessorien kautta.
    *
    * @param r rivi-aksessoriin annettava tieto .
    * @param s sarake-aksessoriin annettava tieto.
    */
   public Juuri(int r, int s) {
      rivi(r);
      sarake(s);
   }

   /*
    * =====================================================================
    * Metodit
    */

   /**
    * {@inheritDoc}
    *
    * @return {@inheritDoc}
    */
   public boolean sallittu() {
      if (Kayttoliittyma.sokkelo.sokkelo[riviIndeksi][sarakeIndeksi].getClass() == Kaytava.class) {
         return true;
      } else
         return false;
   }

   /**
    * Object-luokan toString-metodin korvaus.
    *
    * @return paluuarvona luokan-nimi |riviIndeksi |sarakeIndeksi |
    */
   @Override
   public String toString() {
      String luokanNim = getClass().getSimpleName();
      // Jos luokanNim.length() on < 9, lis�t��n sen per��n v�lily�nti.
      while (luokanNim.length() < 9) {
         luokanNim += " ";
      }

      // Asetetaan riviIndeksi sek� sarakeIndeksi apumuuttujiin, joissa ne
      // muutetaan String tyyppisiksi.
      // Jos apuRivi.length() tai apuSarake.length() on < 4, lis�t��n sen per��n
      // v�lily�nti.
      String apuRivi = Integer.toString(riviIndeksi);
      String apuSarake = Integer.toString(sarakeIndeksi);

      // Lis�t��n apuRiviin v�lily�ntej� jotta t�m�n pituus on 4 merkki�.
      while (apuRivi.length() < 4)
         apuRivi += " ";

      // Lis�t��n apuSarakkeeseen v�lily�ntej� jotta t�m�n pituus on 4 merkki�.
      while (apuSarake.length() < 4)
         apuSarake += " ";

      // Paluuarvona luokan nimi, riviIndeksi sek� sarakeIndeksi.
      return luokanNim + EROTIN + apuRivi + EROTIN + apuSarake + EROTIN;
   }
}