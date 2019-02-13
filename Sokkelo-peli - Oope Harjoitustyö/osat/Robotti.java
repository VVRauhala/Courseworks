package osat;

import apulaiset.Suunnallinen;

/**
 * Luokka joka määrittää robotin tiedot. Toteuttaa Suunnallisen rajapinnan.
 * <p>
 * @author Vili-Veikko Rauhala (Rauhala.Vili.V@student.uta.fi).
 * Opiskelijanumero: 421839.
 * <p>
 * Tietojenkäsittelytiede. OOPE Harjoiustyö.
 */
public class Robotti extends Sisalto implements Suunnallinen {

   /*
    * =====================================================================
    * Attribuutit
    */

   /** Robottia symboloiva merkki sokkelossa. */
   private final static char ROBOTTI = 'R';

   /** Robotin suuntaa vastaava merkki. */
   private char suunta;

   /*
    * =====================================================================
    * Aksessorit
    */

   /**
    * Robotin merkin palauttava metodi.
    *
    * @return Robotin merkki 'R'.
    */
   public static char merkki() {
      return ROBOTTI;
   }

   /**
    * Robotin suunnan palauttava metodi.
    *
    * @return robotin suunta.
    */
   public char suunta() {
      return suunta;
   }

   /**
    * Metodi joka asettaa robotille parametrin mukaisen ilmansuunnan.
    *
    * @param ilmansuunta, joko 'p', 'i', 'e' tai 'l'.
    * @throws IllegalArgumentException jos ilmansuunta ei ole yllä mainituista.
    */
   public void suunta(char ilmansuunta) throws IllegalArgumentException {
      if (ilmansuunta == POHJOINEN || ilmansuunta == ITA || ilmansuunta == ETELA || ilmansuunta == LANSI)
         suunta = ilmansuunta;
      else
         throw new IllegalArgumentException("Virhe!");
   }

   /*
    * =====================================================================
    * Rakentaja
    */

   /**
    * Rakentaja joka kutsuu ylemmän hierarkiatason rakentajaa antamalla tälle
    * parametreina r, s ja e. Tämän jälkeen kutsutaan suunnan aksessoria antaen
    * tälle ilmansuunta parametrina.
    *
    * @param r {@inheritDoc}
    * @param s {@inheritDoc}
    * @param e {@inheritDoc}
    * @param i suunta-aksessoriin annettava tieto.
    */
   public Robotti(int r, int s, int e, char i) {
      super(r, s, e);
      suunta(i);
   }

   /*
    * =====================================================================
    * Metodit
    */

   /**
    * toString-metodin korvaus.
    *
    * @return paluuarvona luokan-nimi |riviIndeksi |sarakeIndeksi |energia |suunta |
    */
   @Override
   public String toString() {
      // Asetetaan suunta String-tyyppiseen apuSuunta
      String apuSuunta = Character.toString(suunta);
      // Jos apuSuunta.length() on < 4, lisätään sen perään välilyönti.
      while (apuSuunta.length() < 4)
         apuSuunta += " ";
      return super.toString() + apuSuunta + EROTIN;
   }
}