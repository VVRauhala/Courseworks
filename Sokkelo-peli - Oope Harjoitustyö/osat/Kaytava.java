package osat;

import omaLista.OmaLista;

/**
 * Luokka joka määrittää käytävän tiedot. Perii juuri-luokan.
 * <p>
 * @author Vili-Veikko Rauhala (Rauhala.Vili.V@student.uta.fi).
 * Opiskelijanumero: 421839.
 * <p>
 * Tietojenkäsittelytiede. OOPE Harjoiustyö.
 */

public class Kaytava extends Juuri {

   /*
    * =====================================================================
    * Attribuutti
    */

   /** Käytävää symboloiva merkki sokkelossa. */
   private final char TYHJAMERKKI = ' ';

   /**
    * Käytävän OmaLista-luokkainen lista-arvo. Listalle lisätään käytäväpaikalla
    * olevat oliot
    */
   private OmaLista lista = new OmaLista();

   /*
    * =====================================================================
    * Aksessorit
    */

   /**
    * Käytävän merkin palauttava metodi.
    *
    * @return käytävän merkki ' '.
    */
   public char merkki() {
      return TYHJAMERKKI;
   }

   /**
    * Kaytavapaikan listan palattava metodi
    *
    * @return käytävän lista
    */
   public OmaLista lista() {
      return lista;
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
    */
   public Kaytava(int r, int s) {
      super(r, s);
   }

   /*
    * =====================================================================
    * Metodit
    */

   /**
    * Metodi joka tarkistaa onko käytäväpaikan lista tyhjä.
    *
    * @return true jos lista on tyhjä.
    */
   public boolean onkoTyhja() {
      if (lista.onkoTyhja() == true) {
         return true;
      } else
         return false;
   }

   /**
    * Käytävän listan tulostava metodi
    */
   public void tulostaKaytavanLista() {
      // Kutsutaan listan tulostaLista-metodia
      lista.tulostaLista();
   }

   /**
    * Metodi joka lisää käytävän listalle uuden olion energian mukaan nousevaan
    * järjestykseen
    *
    * @param alkio joka on käytäväpaikan listaan lisättävä olio.
    */
   public void lisaa(Object alkio) {
      lista.lisaa(alkio);
   }

   /**
    * Metodi jota kutsutaan käytäväpaikan listan läpikäydessä jos lista !=
    * tyhjä.
    * <p>
    * Metodi käy listaa läpi. Jos listalta löytyy mönkiä, paluuarvona mönkijän
    * merkki. Jos mönkijää ei löytynyt, käydään lista uudelleen läpi, ja
    * tarkistetaan löytyykö roboottia. Jos robootti löytyi, paluuarvona robootin
    * merkki. Jos listalla ei ollut mönkiää eikä roboottia, mutta
    * lista.onkoTyhja() != true, paluuarvona esineen merkki.
    * <p>
    * Koska mönkijä ja robootti taistelevat kohdatessaan saman tien, ei
    * tulostusmetodin tarvitse välittää näiden samanaikaisuudesta listalla.
    * Mönkijä myös kerää omaan listaansa käytäväpaikan esineen, jolloin mönkijää
    * ja esinettä ei voi olla samaan aikaan käytäväpaikalla tulostus- metodia
    * käytettäessä. Jos samalla paikalla on robootti ja esine, palautetaan
    * robootin merkki.
    *
    * @return mönkijän, robootin tai esineen merkki.
    */
   public char listanMerkki() {
      // Käydään listaa läpi. Jos listalta löytyy mönkijä, palautetaan tämän
      // merkki.
      // Samalla paikalla ei voi olla mönkijää ja esinettä, tai mönkijää ja
      // roboottia.
      for (int i = 0; i < lista.koko(); i++) {
         if (lista.alkio(i).getClass() == Monkija.class) {
            return Monkija.merkki();
         }
      }
      // Käydään listaa läpi. Jos listalta löytyy robootti, palautetaan tämän
      // merkki.
      // Jos samalla paikalla on robootti sekä esine, paluuarvona robootin
      // merkki.
      // Roboottia ja mönkijää ei voi olla samalla paikalla.
      for (int i = 0; i < lista.koko(); i++) {
         if (lista.alkio(i).getClass() == Robotti.class) {
            return Robotti.merkki();
         }
      }
      // Jos listalla ei ollut mönkijää eikä roboottia, on listalla oltava esine
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