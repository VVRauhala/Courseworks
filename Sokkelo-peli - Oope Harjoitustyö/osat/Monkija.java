package osat;

import apulaiset.Suunnallinen;
import omaLista.OmaLista;

/**
 * Luokka joka määrittää mönkijän tiedot. Toteuttaa Suunnallisen rajapinnan.
 * <p>
 * @author Vili-Veikko Rauhala (Rauhala.Vili.V@student.uta.fi).
 * Opiskelijanumero: 421839.
 * <p>
 * Tietojenkäsittelytiede. OOPE Harjoiustyö.
 */

public class Monkija extends Sisalto implements Suunnallinen {

   /*
    * =====================================================================
    * Attribuutit
    */

   /** Mönkijää symboloiva merkki sokkelossa. */
   private final static char MONKIJA = 'M';

   /** Mönkijälle tieto onko tämä elossa */
   private boolean onkoElossa = true;

   /** Mönkijän suuntaa vastaava merkki. */
   private char suunta;

   /**
    * Mönkijän OmaLista-luokkainen lista-arvo. Listalle lisätään mönkijän
    * keräämät esine-oliot.
    */
   private OmaLista inventoori = new OmaLista();

   /*
    * =====================================================================
    * Aksessorit
    */

   /**
    * Metodi joka palauttaa tiedon mönkijän tiedosta onko tämä elossa.
    *
    * @return onko mönkijä elossa.
    */
   public boolean onkoElossa() {
      return onkoElossa;
   }

   /**
    * Metodi joka asettaa mönkijälle tiedon tämän elossa olemisesta.
    *
    * @param tietoElosta tieto siitä onko mönkijä vielä elossa.
    */
   public void onkoElossa(boolean tietoElosta) {
      onkoElossa = tietoElosta;
   }

   /**
    * Metodi joka palauttaa mönkijän inventoorin.
    *
    * @return mönkijän inventoori.
    */
   public OmaLista inventoori() {
      return inventoori;
   }

   /**
    * Mönkijan merkin palauttava metodi.
    *
    * @return mönkijän merkki 'M'.
    */
   public static char merkki() {
      return MONKIJA;
   }

   /**
    * Mönkijän suunnan palauttava metodi.
    *
    * @return mönkijän suunta.
    */
   public char suunta() {
      return suunta;
   }

   /**
    * Metodi joka asettaa mönkijälle parametrin mukaisen ilmansuunnan.
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

   /**
    * Mönkijän inventoorin koon palauttava metodi
    *
    * @return mönkijän inventoorin koko
    */
   public int inventoorinKoko() {
      return inventoori.koko();
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
   public Monkija(int r, int s, int e, char i) {
      super(r, s, e);
      suunta(i);
   }

   /*
    * =====================================================================
    * Metodit
    */

   /**
    * Metodi joka lisää mönkijän inventooriin uuden esineen energian mukaan
    * nousevaan järjestykseen
    *
    * @param alkio joka on käytäväpaikan listaan lisättävä olio.
    */
   public void lisaa(Object alkio) {
      inventoori.lisaa(alkio);
   }

   /**
    * Mönkijän inventoorin tulostava metodi
    */
   public void tulostaInventoori() {
      // Kutsutaan listan tulostaLista-metodia
      inventoori.tulostaLista();
   }

   /**
    * Metodi joka tarkistaa onko mönkijän lista tyhjä.
    *
    * @return true jos lista on tyhjä.
    */
   public boolean onkoInventooriTyhja() {
      if (inventoori.onkoTyhja() == true) {
         return true;
      } else
         return false;
   }

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
