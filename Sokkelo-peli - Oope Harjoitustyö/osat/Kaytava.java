package osat;

import omaLista.OmaLista;

/**
 * Luokka joka m��ritt�� k�yt�v�n tiedot. Perii juuri-luokan.
 * <p>
 * @author Vili-Veikko Rauhala (Rauhala.Vili.V@student.uta.fi).
 * Opiskelijanumero: 421839.
 * <p>
 * Tietojenk�sittelytiede. OOPE Harjoiusty�.
 */

public class Kaytava extends Juuri {

   /*
    * =====================================================================
    * Attribuutti
    */

   /** K�yt�v�� symboloiva merkki sokkelossa. */
   private final char TYHJAMERKKI = ' ';

   /**
    * K�yt�v�n OmaLista-luokkainen lista-arvo. Listalle lis�t��n k�yt�v�paikalla
    * olevat oliot
    */
   private OmaLista lista = new OmaLista();

   /*
    * =====================================================================
    * Aksessorit
    */

   /**
    * K�yt�v�n merkin palauttava metodi.
    *
    * @return k�yt�v�n merkki ' '.
    */
   public char merkki() {
      return TYHJAMERKKI;
   }

   /**
    * Kaytavapaikan listan palattava metodi
    *
    * @return k�yt�v�n lista
    */
   public OmaLista lista() {
      return lista;
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
    */
   public Kaytava(int r, int s) {
      super(r, s);
   }

   /*
    * =====================================================================
    * Metodit
    */

   /**
    * Metodi joka tarkistaa onko k�yt�v�paikan lista tyhj�.
    *
    * @return true jos lista on tyhj�.
    */
   public boolean onkoTyhja() {
      if (lista.onkoTyhja() == true) {
         return true;
      } else
         return false;
   }

   /**
    * K�yt�v�n listan tulostava metodi
    */
   public void tulostaKaytavanLista() {
      // Kutsutaan listan tulostaLista-metodia
      lista.tulostaLista();
   }

   /**
    * Metodi joka lis�� k�yt�v�n listalle uuden olion energian mukaan nousevaan
    * j�rjestykseen
    *
    * @param alkio joka on k�yt�v�paikan listaan lis�tt�v� olio.
    */
   public void lisaa(Object alkio) {
      lista.lisaa(alkio);
   }

   /**
    * Metodi jota kutsutaan k�yt�v�paikan listan l�pik�ydess� jos lista !=
    * tyhj�.
    * <p>
    * Metodi k�y listaa l�pi. Jos listalta l�ytyy m�nki�, paluuarvona m�nkij�n
    * merkki. Jos m�nkij�� ei l�ytynyt, k�yd��n lista uudelleen l�pi, ja
    * tarkistetaan l�ytyyk� roboottia. Jos robootti l�ytyi, paluuarvona robootin
    * merkki. Jos listalla ei ollut m�nki�� eik� roboottia, mutta
    * lista.onkoTyhja() != true, paluuarvona esineen merkki.
    * <p>
    * Koska m�nkij� ja robootti taistelevat kohdatessaan saman tien, ei
    * tulostusmetodin tarvitse v�litt�� n�iden samanaikaisuudesta listalla.
    * M�nkij� my�s ker�� omaan listaansa k�yt�v�paikan esineen, jolloin m�nkij��
    * ja esinett� ei voi olla samaan aikaan k�yt�v�paikalla tulostus- metodia
    * k�ytett�ess�. Jos samalla paikalla on robootti ja esine, palautetaan
    * robootin merkki.
    *
    * @return m�nkij�n, robootin tai esineen merkki.
    */
   public char listanMerkki() {
      // K�yd��n listaa l�pi. Jos listalta l�ytyy m�nkij�, palautetaan t�m�n
      // merkki.
      // Samalla paikalla ei voi olla m�nkij�� ja esinett�, tai m�nkij�� ja
      // roboottia.
      for (int i = 0; i < lista.koko(); i++) {
         if (lista.alkio(i).getClass() == Monkija.class) {
            return Monkija.merkki();
         }
      }
      // K�yd��n listaa l�pi. Jos listalta l�ytyy robootti, palautetaan t�m�n
      // merkki.
      // Jos samalla paikalla on robootti sek� esine, paluuarvona robootin
      // merkki.
      // Roboottia ja m�nkij�� ei voi olla samalla paikalla.
      for (int i = 0; i < lista.koko(); i++) {
         if (lista.alkio(i).getClass() == Robotti.class) {
            return Robotti.merkki();
         }
      }
      // Jos listalla ei ollut m�nkij�� eik� roboottia, on listalla oltava esine
      return Esine.merkki();
   }

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