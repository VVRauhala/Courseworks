package tui;

import apulaiset.In;
import omaLista.OmaLista;
import osat.Kaytava;
import osat.Monkija;
import sokkelo.Sokkelo;

/**
 * Käyttöliittymä harjoitustyölle. Suorittaa ohjelman ja käyttäjän välisen
 * vuorovaikutuksen. Käyttäjän antamien syötteiden kautta suoritetaan eri
 * metodeja pelin pelaamiseen.
 * <p>
 * metodit lataukselle, tallennukselle, inventoinnille, tulostukselle,
 * katselemiselle, liikkumiselle ja odottamiselle.
 * <p>
 * @author Vili-Veikko Rauhala (Rauhala.Vili.V@student.uta.fi)
 * Opiskelijanumero: 421839.
 * <p>
 * Tietojenkäsittelytiede OOPE Harjoiustyö
 */
public class Kayttoliittyma {

   /*
    * =====================================================================
    * Attribuutti
    */

   /**
    * Arvo joka määrittää jatketaanko pelin läpikäyntiä. Jos false, peli loppuu.
    */
   private static boolean jatketaanko = true;

   /** Sokkeloa mallintava attribuutti */
   public static Sokkelo sokkelo = new Sokkelo();

   /*
    * =====================================================================
    * Aksessorit
    */

   /**
    * Metodi joka asettaa jatketaanko arvon määrättyyn arvoon.
    *
    * @param loppuuko joka sisältää tiedon siitä jatketaanko
    */
   public static void jatketaanko(boolean loppuuko) {
      jatketaanko = loppuuko;
   }

   /*
    * =====================================================================
    * Metodi
    */

   /**
    * Pelin käynnistävä metodi. Metodin do-while lause pyörittää peliä niin
    * kauvan kun jatketaanko == true. Mahdollinen false-arvo tulee komennolla
    * "lopeta", mönkijän häviöön tappelussa robotille tai kun mönkijä kerää
    * kaikki esineet.
    * <p>
    * Metodi lukee käyttäjältä komentoa, komennon mukaan suoritetaan eri
    * metodeja. Jos syötekomento on virheellinen, tulostetaan virheilmoitus
    * "Virhe!".
    * <p>
    * "lataa"-syöte lukee tekstitiedostosta sokkelon tiedot. Tämä suoritetaan
    * pelaamisen aloitettaessa heti automaattisesti, jotta sokkelon tiedot
    * saadaan ohjelmalle. "inventoi"-syöte tulostaa mönkijän ja tämän
    * inventoorin tiedot. "kartta"-syöte tulostaa sokkelon paikat ja näiden
    * tietojen kuvaavat merkit. "katso p||i||e||l"-syöte katsoo mönkijän
    * viereisen (ilmansuunnan mukaisen) paikan. Tulostaa paikan tiedot.
    * "ohita"-syöte ohittaa robootin liikkumisen, mutta suorittaa kuitenkin
    * sokkelossa roboottien liikkumisen. "liiku p||i||e||l"-syöte liikuttaa
    * mönkijää sokkelossa ilmansunnan mukaiseen paikkaan. Mönkijän liikkuessa
    * liikkuu myös robootit sokkelossa. "lopeta"-syöte asettaa attribuutin
    * jatketaanko false arvoiseksi. Tällöin ohjelma lopetetaan.
    */
   public static void pelaa() {

      // Ladataan tiedot tiedostosta keskusmuistiin.
      sokkelo.lataa();

      /*
       * Suoritetaan while lausetta kunnes käyttäjä antaa komennon "lopeta",
       * kaikki esineet on kerätty tai mönkijä häviää tappelun.
       */

      while (jatketaanko == true) {

         // Tulostetaan käyttäjälle kehotus
         System.out.println("Kirjoita komento:");

         // Luetaan käyttäjältä komento
         String valinta = In.readString();

         // Muunnetaan käyttäjän syöte taulukko rakenteeseen, jolloin syötteen
         // käskyt ja arvot saadaan eriteltyä toisistaan.
         // Erotetaan rivin osat toisistaan ' ' merkin kohdalta
         // ja asetetaan ne valinnanOsat-taulukon arvoiksi
         String[] valinnanOsat = valinta.split("[ ]");

         try {
            /*
             * ============================================================
             * LATAA
             *
             * Jos valinnanOsat-taulukon ensimmäinen paikan arvo on "lataa",
             * ladataan sokkelo kutsumalla Sokkelo-luokan lataa metodia.
             * Tarkistetaan myös syötteen virheellisyys tämän pituuden
             * tarkastamisella.
             */
            if (valinta.length() == 5 && valinnanOsat[0].equals("lataa")) {
               sokkelo.lataa();
            }

            /*
             * ============================================================
             * INVENTOI
             *
             * Jos valinnanOsat-taulukon ensimmäinen paikan arvo on "inventoi",
             * tulostetaan mönkijän inventoori kutsumalla Sokkelo-luokan
             * inventoi metodia. Tarkistetaan myös syötteen virheellisyys tämän
             * pituuden tarkastamisella.
             */
            else if (valinta.length() == 8 && valinnanOsat[0].equals("inventoi")) {
               sokkelo.inventoi();
            }

            /*
             * ============================================================
             * KARTTA
             *
             * Jos valinnanOsat-taulukon ensimmäinen paikan arvo on "kartta",
             * tulostetaan sokkelo kutsumalla Sokkelo-luokan tulostaSokkelo
             * metodia. Tarkistetaan myös syötteen virheellisyys tämän pituuden
             * tarkastamisella.
             */
            else if (valinta.length() == 6 && valinnanOsat[0].equals("kartta")) {
               sokkelo.tulostaSokkelo();
            }

            /*
             * ============================================================
             * ODOTA
             *
             * Jos valinnanOsat-taulukon ensimmäinen paikan arvo on "odota",
             * liikutetaan vain robootteja taulukossa Tarkistetaan myös syötteen
             * virheellisyys tämän pituuden tarkastamisella.
             */
            else if (valinta.length() == 5 && valinnanOsat[0].equals("odota")) {
               // Kutsutaan metodia joka suorittaa roboottien liikuttelun
               // sokkelossa
               // ja tulostaa sokkelon.
               sokkelo.odota();
            }

            /*
             * ============================================================
             * TALLENNA
             *
             * Jos valinnanOsat-taulukon ensimmäinen paikan arvo on "tallenna",
             * tallennetaan sokkelon tila tekstitiedostoon. Tarkistetaan myös
             * syötteen virheellisyys tämän pituuden tarkastamisella.
             */
            else if (valinta.length() == 8 && valinnanOsat[0].equals("tallenna")) {
               // Tallennetaan peli tekstitiedostoon
               sokkelo.tallenna();
            }

            /*
             * ============================================================
             * LOPETA
             *
             * Jos valinnanOsat-taulukon ensimmäinen paikan arvo on "lopeta",
             * asetetaan jatketaanko = false, jolloin pelaa- metodin suoritus
             * loppuu. Tarkistetaan myös syötteen virheellisyys tämän pituuden
             * tarkastamisella.
             */
            else if (valinta.length() == 6 && valinnanOsat[0].equals("lopeta")) {
               // Tulostetaan sokkelo
               sokkelo.tulostaSokkelo();
               // Lopetetaan while-silmukka.
               break;
            }

            /*
             * ============================================================
             * KATSO
             *
             * Jos valinnanOsat-taulukon ensimmäisen paikan arvo on "katso" ja
             * toisen paikan arvo on "p" || "i" || "e" || "l" kutsutaan
             * Sokkelo-luokan katsoPaikka metodia. Metodi tulostaa mönkijän
             * viereisen (ilmansuunnan mukaisen) paikan tiedot metodille annetun
             * suunta-parametrin mukaan. Tarkistetaan myös syötteen
             * virheellisyys tämän pituuden tarkastamisella.
             */
            else if (valinnanOsat[0].equals("katso") && valinta.length() == 7 && (valinnanOsat[1].equals("p")
                  || valinnanOsat[1].equals("i") || valinnanOsat[1].equals("e") || valinnanOsat[1].equals("l"))) {
               // Haetaan käyttäjän syötteestä suuntaa viittaava merkki joka
               // annetaan
               // Sokkelo-luokan katoPaikka-metodille parametrina.
               char suunta = valinnanOsat[1].charAt(0);
               sokkelo.katsoPaikka(suunta);
            }

            /*
             * ===============================================================
             * LIIKU
             *
             * Jos valinnanOsat-taulukon ensimmäisen paikan arvo on "liiku" ja
             * toisen paikan arvo on "p" || "i" || "e" || "l" kutsutaan
             * Sokkelo-luokan liiku metodia. Metodi siirtää sokkelossa mönkijää
             * yhden paikan valittuun suuntaan, jonka jälkeen myös robootit
             * liikkuvat sokkelossa. Tarkistetaan myös syötteen virheellisyys
             * tämän pituuden tarkastamisella.
             */
            else if (valinnanOsat[0].equals("liiku") && valinta.length() == 7 && (valinnanOsat[1].equals("p")
                  || valinnanOsat[1].equals("i") || valinnanOsat[1].equals("e") || valinnanOsat[1].equals("l"))) {
               // Haetaan käyttäjän syötteestä suunta johon mönkijää liikutetaan
               char suunta = valinnanOsat[1].charAt(0);
               sokkelo.liiku(suunta);
               // Lopuksi tulostetaan sokkelo
               sokkelo.tulostaSokkelo();
            }

            /*
             * ===============================================================
             * MUUNNA
             *
             * Jos valinnanOsat-taulukon ensimmäisen paikan arvo on "muunna",
             * toisen paikan arvo on >= mönkijän listalla olevien esineiden
             * lukumäärä ja koko syötteen mitta on valinnanOsat[0] +
             * valinnanOsat[1] + 1 (splitin myötä kadotettu välilyönti),
             * muunnetaan valitun lukumäärän verran esineita mönkijän energiaksi
             * Sokkelo-luokan muunna metodissa. Muunnetut esineet poistuvat
             * mönkijän inventoorista.
             */
            else if (valinnanOsat[0].equals("muunna") && valinnanOsat.length > 1
                  && valinta.length() == (valinnanOsat[0].length() + valinnanOsat[1].length() + 1)
                  && Integer.parseInt(valinnanOsat[1]) >= 0) {
               // Etsitään sokkelosta mönkijä, ja katsotaan kuinka monta
               // esinettä mönkijän
               // inventoorissa on. Jos käyttäjän syötteenä muunnettavien
               // esineiden lkm on >
               // kuin mönkijän inventoorin esineiden lukumäärä, tulostetaan
               // virheilmoitus.
               // Jos muunnettavien esineiden lkm <= mönkijän inventoorin koko,
               // muunnetaan
               // syötteen mukainen määrä esineitä.
               for (int i = 0; i < sokkelo.sokkelo.length; i++) {
                  for (int j = 0; j < sokkelo.sokkelo[i].length; j++) {
                     // Jos arvojen kohdalla sokkelossa on käytävä-olio
                     if (sokkelo.sokkelo[i][j].getClass() == Kaytava.class) {
                        // Tarkistetaan onko käytävä-olion lista ei-tyhjä
                        if (((Kaytava) sokkelo.sokkelo[i][j]).onkoTyhja() != true) {
                           // Luodaan apuLista jonka avulla käydään listan
                           // olioita läpi
                           OmaLista kaytavaPaikanLista = ((Kaytava) sokkelo.sokkelo[i][j]).lista();
                           // Alustetaan indeksi jonka avulla käydään listan
                           // alkioita läpi.
                           int indeksi = 0;
                           // Käydään listaa läpi kunnes solmun seuraava == null
                           while (kaytavaPaikanLista.alkio(indeksi) != null) {

                              // Jos listalta löytyvä alkio on mönkijä
                              if (kaytavaPaikanLista.alkio(indeksi).getClass() == Monkija.class) {
                                 // Haetaan mönkijän listan pituus
                                 int monkkarinListanPituus = sokkelo.monkija().inventoorinKoko();
                                 // Verrataan mönkijän listan pituutta
                                 // syötteessä annetun muunnettavien
                                 // esineiden määrään.
                                 if (monkkarinListanPituus >= Integer.parseInt(valinnanOsat[1])) {

                                    // Tehdään mönkijän inventoorista OmaLista
                                    // attribuutti.
                                    OmaLista monkijanInventoori = sokkelo.monkija().inventoori();

                                    // Kutsutaan Sokkelo-luokassa metodia
                                    // muunnaEsine, joka poistetaan
                                    // käyttäjän syötteenä antaman lukumäärän
                                    // verran esineitä mönkijän
                                    // inventoorin alusta, ja lisätää näiden
                                    // energian mönkijän
                                    // energiaan.
                                    sokkelo.muunnaEsine(Integer.parseInt(valinnanOsat[1]), monkijanInventoori);

                                 }
                                 // Jos muunnettavien esineiden määrä on
                                 // virheellinen, tulostetaan
                                 // virheilmoitus ja palataan kysymään
                                 // käyttäjältä komentoja.
                                 else {
                                    System.out.println("Virhe!");
                                    break;
                                 }
                              }
                              indeksi++;
                           }
                        }
                     }
                  }
               }
            }
            // Jos syöte on virheellinen, tulostetaan virheilmoitus.
            else {
               System.out.println("Virhe!");
            }
         }
         // Jos syötteitten kanssa ilmenee virheilmoituksia, napataan virhe
         // ja tulostetaan virheilmoitus.
         catch (Exception e) {
            System.out.println("Virhe!");
         }
         // Asetetaan onkoEsineita-metodin paluuarvo jatketaanko-attribuuttiin.
         jatketaanko = sokkelo.onkoEsineita();
         // Jos jatketaanko arvo on false sokkelon esineiden puutteen vuoksi,
         // lopetetaan while silmukan käynti
         if (jatketaanko == false)
            break;

         // Asetetaan onkoElossa-metodin paluuarvo jatketaanko-attribuuttiin.
         jatketaanko = sokkelo.onkoElossa();
      }
      // Tulostetaan tieto ohjelman lopetuksesta.
      System.out.println("Ohjelma lopetettu.");
   }
}