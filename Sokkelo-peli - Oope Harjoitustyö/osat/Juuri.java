package osat;

import apulaiset.Paikallinen;
import tui.Kayttoliittyma;

/**
 * Luokka joka toimii esi-isänä eri sokkelon osille. Sisältää teidot rivi- sekä
 * sarakeindeksistä, sekä erottimen merkin. Metodeina sallittu-operaatio sekä
 * myös toString-metodin korvaus.
 * <p>
 * @author Vili-Veikko Rauhala (Rauhala.Vili.V@student.uta.fi).
 * Opiskelijanumero: 421839.
 * <p>
 * Tietojenkäsittelytiede. OOPE Harjoiustyö.
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
      // Jos luokanNim.length() on < 9, lisätään sen perään välilyönti.
      while (luokanNim.length() < 9) {
         luokanNim += " ";
      }

      // Asetetaan riviIndeksi sekä sarakeIndeksi apumuuttujiin, joissa ne
      // muutetaan String tyyppisiksi.
      // Jos apuRivi.length() tai apuSarake.length() on < 4, lisätään sen perään
      // välilyönti.
      String apuRivi = Integer.toString(riviIndeksi);
      String apuSarake = Integer.toString(sarakeIndeksi);

      // Lisätään apuRiviin välilyöntejä jotta tämän pituus on 4 merkkiä.
      while (apuRivi.length() < 4)
         apuRivi += " ";

      // Lisätään apuSarakkeeseen välilyöntejä jotta tämän pituus on 4 merkkiä.
      while (apuSarake.length() < 4)
         apuSarake += " ";

      // Paluuarvona luokan nimi, riviIndeksi sekä sarakeIndeksi.
      return luokanNim + EROTIN + apuRivi + EROTIN + apuSarake + EROTIN;
   }
}