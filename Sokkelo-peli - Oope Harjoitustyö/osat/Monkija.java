package osat;

import apulaiset.Suunnallinen;
import omaLista.OmaLista;

/**
 * Luokka joka m��ritt�� m�nkij�n tiedot. Toteuttaa Suunnallisen rajapinnan.
 * <p>
 * @author Vili-Veikko Rauhala (Rauhala.Vili.V@student.uta.fi).
 * Opiskelijanumero: 421839.
 * <p>
 * Tietojenk�sittelytiede. OOPE Harjoiusty�.
 */

public class Monkija extends Sisalto implements Suunnallinen {

   /*
    * =====================================================================
    * Attribuutit
    */

   /** M�nkij�� symboloiva merkki sokkelossa. */
   private final static char MONKIJA = 'M';

   /** M�nkij�lle tieto onko t�m� elossa */
   private boolean onkoElossa = true;

   /** M�nkij�n suuntaa vastaava merkki. */
   private char suunta;

   /**
    * M�nkij�n OmaLista-luokkainen lista-arvo. Listalle lis�t��n m�nkij�n
    * ker��m�t esine-oliot.
    */
   private OmaLista inventoori = new OmaLista();

   /*
    * =====================================================================
    * Aksessorit
    */

   /**
    * Metodi joka palauttaa tiedon m�nkij�n tiedosta onko t�m� elossa.
    *
    * @return onko m�nkij� elossa.
    */
   public boolean onkoElossa() {
      return onkoElossa;
   }

   /**
    * Metodi joka asettaa m�nkij�lle tiedon t�m�n elossa olemisesta.
    *
    * @param tietoElosta tieto siit� onko m�nkij� viel� elossa.
    */
   public void onkoElossa(boolean tietoElosta) {
      onkoElossa = tietoElosta;
   }

   /**
    * Metodi joka palauttaa m�nkij�n inventoorin.
    *
    * @return m�nkij�n inventoori.
    */
   public OmaLista inventoori() {
      return inventoori;
   }

   /**
    * M�nkijan merkin palauttava metodi.
    *
    * @return m�nkij�n merkki 'M'.
    */
   public static char merkki() {
      return MONKIJA;
   }

   /**
    * M�nkij�n suunnan palauttava metodi.
    *
    * @return m�nkij�n suunta.
    */
   public char suunta() {
      return suunta;
   }

   /**
    * Metodi joka asettaa m�nkij�lle parametrin mukaisen ilmansuunnan.
    *
    * @param ilmansuunta, joko 'p', 'i', 'e' tai 'l'.
    * @throws IllegalArgumentException jos ilmansuunta ei ole yll� mainituista.
    */
   public void suunta(char ilmansuunta) throws IllegalArgumentException {
      if (ilmansuunta == POHJOINEN || ilmansuunta == ITA || ilmansuunta == ETELA || ilmansuunta == LANSI)
         suunta = ilmansuunta;
      else
         throw new IllegalArgumentException("Virhe!");
   }

   /**
    * M�nkij�n inventoorin koon palauttava metodi
    *
    * @return m�nkij�n inventoorin koko
    */
   public int inventoorinKoko() {
      return inventoori.koko();
   }

   /*
    * =====================================================================
    * Rakentaja
    */

   /**
    * Rakentaja joka kutsuu ylemm�n hierarkiatason rakentajaa antamalla t�lle
    * parametreina r, s ja e. T�m�n j�lkeen kutsutaan suunnan aksessoria antaen
    * t�lle ilmansuunta parametrina.
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
    * Metodi joka lis�� m�nkij�n inventooriin uuden esineen energian mukaan
    * nousevaan j�rjestykseen
    *
    * @param alkio joka on k�yt�v�paikan listaan lis�tt�v� olio.
    */
   public void lisaa(Object alkio) {
      inventoori.lisaa(alkio);
   }

   /**
    * M�nkij�n inventoorin tulostava metodi
    */
   public void tulostaInventoori() {
      // Kutsutaan listan tulostaLista-metodia
      inventoori.tulostaLista();
   }

   /**
    * Metodi joka tarkistaa onko m�nkij�n lista tyhj�.
    *
    * @return true jos lista on tyhj�.
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
      // Jos apuSuunta.length() on < 4, lis�t��n sen per��n v�lily�nti.
      while (apuSuunta.length() < 4)
         apuSuunta += " ";
      return super.toString() + apuSuunta + EROTIN;
   }
}
