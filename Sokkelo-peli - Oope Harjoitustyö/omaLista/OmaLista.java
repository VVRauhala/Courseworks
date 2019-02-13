package omaLista;

import fi.uta.csjola.oope.lista.LinkitettyLista;
import osat.Sisalto;

/**
 * OmaLista-luokka, joka sis‰lt‰‰ omille listoille yhteiset piirteet sek‰
 * metodit lis‰yksille.
 * <p>
 * @author Vili-Veikko Rauhala (Rauhala.Vili.V@student.uta.fi) 
 * Opiskelijanumero: 421839
 * <p>
 * Tietojenk‰sittelytiede. OOPE Harjoiustyˆ
 */

public class OmaLista extends LinkitettyLista {

   /*
    * =====================================================================
    * Metodit
    */

   /**
    * Listan alkiot s‰ilytt‰v‰t kasvavan suuruusj‰rjestyksen, jos lis‰ys tehd‰‰n
    * t‰ll‰ operaatiolla
    *
    * @param lisattavaOlio olio joka lis‰t‰‰n listalle
    */
   @SuppressWarnings("unchecked") // Estet‰‰n k‰‰nt‰j‰n varoitus
   public void lisaa(Object lisattavaOlio) {

      // Jos lista on tyhj‰, lis‰t‰‰n alkio listalle suoraan
      if (onkoTyhja() == true) {
         lisaaLoppuun(lisattavaOlio);
      }

      // Jos listalla on jo alkioita, lis‰t‰‰n uusi alkio listalle
      // nousevassa j‰rjestyksess‰ energiansa mukaan compareTo-metodin avulla.
      else if (onkoTyhja() != true) {
         // for-lause jonka avulla k‰yd‰‰n listan kokoa l‰pi. Jos
         // compareTo-metodi
         // osuu kohdalle, suoritetaan t‰m‰ ja palataan kutsuvaan metodiin.
         for (int i = 0; i < koko; i++) {
            // Jos listassa oleva alkio on > kuin parametreina saatu alkio,
            // lis‰t‰‰n parametrina saatu
            // alkio listalle verratun alkio paikalle.
            if (((Sisalto) alkio(i)).compareTo((Sisalto) lisattavaOlio) == 1) {
               lisaa(i, lisattavaOlio);
               return;
            }
         }
         // Jos compareTo-metodi ei pid‰ miss‰‰n vaiheessa paikkaansa, on
         // lis‰tt‰v‰n
         // olio energia suurempi kuin mik‰‰n jo listalla olevan olion. T‰llˆin
         // lis‰t‰‰n
         // olio listan loppuun.
         lisaaLoppuun(lisattavaOlio);
      }
   }

   /**
    * Luo taulukon ja asettaa sen viitteet listan tietoalkioihin siten, ett‰ i.
    * alkio viittaa i. tietoalkioon. Operaatio on hyvin tehoton, koska
    * alkio-operaatio k‰y listaa l‰pi aina lista alusta alkaen. Tehokkaampaa
    * olisi k‰yd‰ lista l‰pi yhteen kertaa paa-viitteest‰ alkaen ja
    * seuraava-viitteit‰ seuraten, kunnes ollaan h‰nn‰ss‰.
    *
    * @return null, jos lista on tyhj‰. Paluuarvo on viite viitteet sis‰lt‰v‰‰n
    * taulukkoon, jos listalla on alkioita.
    */
   public Object[] taulukkoon() {
      // Listalla yksi tai useampi tietoalkio.
      if (koko > 0) {
         // Luodaan Object-tyyppisten viitteiden taulukko,
         // jossa on yht‰ monta alkiota kuin listalla on tietoalkioita.
         Object[] viitteet = new Object[koko];

         // K‰yd‰‰n lista l‰pi alusta loppuun silmukan avulla. Joka kierroksella
         // saadaan k‰yttˆˆn laskurin ja alkio-operaation avulla solmun
         // tietoalkion viite, jonka avulla vastaavassa paikassa olevasta
         // taulukon alkiosta voidaan asettaa viite tietoalkioon.
         for (int i = 0; i < koko; i++)
            // Liitet‰‰n nykyisest‰ taulukon alkiosta viite nykyiseen listan
            // tietoalkioon.
            viitteet[i] = alkio(i);

         // Palautetaan viite listan alkioihin liittyv‰t viitteet sis‰lt‰v‰‰n
         // taulukkoon.
         return viitteet;
      }
      // Lista on tyhj‰.
      else
         return null;
   }

   /**
    * Metodi joka tulostaa listan sis‰llˆn
    */
   public void tulostaLista() {
      // Pyydet‰‰n listalta taulukko, jossa on viitteet listan tietoalkioihin
      Object[] alkioviitteet = taulukkoon();

      // Tulostetaan taulukon sis‰ltˆ
      for (int i = 0; i < alkioviitteet.length; i++) {
         // kutsutaan taulukon paikan toString-metodia.
         // System.out.println(alkioviitteet[i].toString());
         System.out.println(alkio(i).toString());
      }
   }
}
