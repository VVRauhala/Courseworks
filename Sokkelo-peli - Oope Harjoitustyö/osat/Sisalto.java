package osat;

/**
 * Luokka joka perii juuren ja josta periytyy mönkijä, robootti ja esine.
 * Sisältää energian tiedon. Metodeina toString-metodin korvaus ja
 * compareTo-metodin toteutus.
 * <p>
 * @author Vili-Veikko Rauhala (Rauhala.Vili.V@student.uta.fi).
 * Opiskelijanumero: 421839.
 * <p>
 * Tietojenkäsittelytiede. OOPE Harjoiustyö.
 */

public class Sisalto extends Juuri implements Comparable<Sisalto> {

   /*
    * =====================================================================
    * Attribuutit
    */

   /** Olion energia. */
   private int energia;

   /*
    * =====================================================================
    * Aksessorit
    */

   /**
    * Metodi joka palauttaa olion energian.
    *
    * @return energia
    */
   public int energia() {
      return energia;
   }

   /**
    * Metodi joka asettaa olion energiaksi parametrina saadun arvon e. Jos
    * parametrina saatu arvo e on pienempi kuin 0, tulostetaan virheilmoitus.
    *
    * @param e olion energia.
    */
   public void energia(int e) {
      if (e >= 0)
         energia = e;
      else
         System.out.println("Virhe!");
   }

   /**
    * Metodi joka lisää energiaan parametrina annetun arvon.
    *
    * @param e lisättävä energia
    */
   public void lisaaEnergiaa(int e) {
      if (e >= 0)
         energia = energia + e;
      else
         System.out.println("Virhe!");
   }

   /**
    * Metodi joka poistaa energiaa mönkijältä parametrina annetun arvon verran.
    *
    * @param e miinustettava energia
    */
   public void poistaEnergiaa(int e) {
      if (e >= 0)
         energia = energia - e;
      else
         System.out.println("Virhe!");
   }

   /*
    * =====================================================================
    * Rakentaja
    */

   /**
    * Rakentaja joka kutsuu ylemmän hierarkiatason rakentajaa antamalla tälle
    * parametreina r ja s. Tämän jälkeen kutsutaan energian aksessoria antaen
    * tälle energia parametrina.
    *
    * @param r {@inheritDoc}
    * @param s {@inheritDoc}
    * @param e energia-aksessoriin annettava tieto.
    */
   public Sisalto(int r, int s, int e) {
      super(r, s);
      energia(e);
   }

   /*
    * =====================================================================
    * Metodit
    */

   /**
    * toString-metodin korvaus.
    *
    * @return paluuarvona luokan-nimi |riviIndeksi |sarakeIndeksi |energia |
    */
   @Override
   public String toString() {
      // Asetetaan energia String-tyyppiseen apuEnergia
      String apuEnergia = Integer.toString(energia);
      // Jos apuEnergia.length() on < 4, lisätään sen perään välilyönti.
      while (apuEnergia.length() < 4)
         apuEnergia += " ";
      return super.toString() + apuEnergia + EROTIN;
   }

   /**
    * Comparable-rajapinnan metodin toteutus.
    *
    * @return -1 jos energia on pienempi kuin parametrina saadun olion energia,
    * 0 jos energiat ovat samat, tai 1jos energia on suurempi kuin
    * parametrina saadun olion energia.
    */
   public int compareTo(Sisalto e) {
      // Tämä olio < parametrina saatu olio.
      if (energia < e.energia())
         return -1;
      // Tämä olio == parametrina saatu olio.
      else if (energia == e.energia())
         return 0;
      // Tämä olio > parametrina saatu olio.
      else
         return 1;
   }
}