package tui;

import apulaiset.In;
import omaLista.OmaLista;
import osat.Kaytava;
import osat.Monkija;
import sokkelo.Sokkelo;

/**
 * K�ytt�liittym� harjoitusty�lle. Suorittaa ohjelman ja k�ytt�j�n v�lisen
 * vuorovaikutuksen. K�ytt�j�n antamien sy�tteiden kautta suoritetaan eri
 * metodeja pelin pelaamiseen.
 * <p>
 * metodit lataukselle, tallennukselle, inventoinnille, tulostukselle,
 * katselemiselle, liikkumiselle ja odottamiselle.
 * <p>
 * @author Vili-Veikko Rauhala (Rauhala.Vili.V@student.uta.fi)
 * Opiskelijanumero: 421839.
 * <p>
 * Tietojenk�sittelytiede OOPE Harjoiusty�
 */
public class Kayttoliittyma {

   /*
    * =====================================================================
    * Attribuutti
    */

   /**
    * Arvo joka m��ritt�� jatketaanko pelin l�pik�ynti�. Jos false, peli loppuu.
    */
   private static boolean jatketaanko = true;

   /** Sokkeloa mallintava attribuutti */
   public static Sokkelo sokkelo = new Sokkelo();

   /*
    * =====================================================================
    * Aksessorit
    */

   /**
    * Metodi joka asettaa jatketaanko arvon m��r�ttyyn arvoon.
    *
    * @param loppuuko joka sis�lt�� tiedon siit� jatketaanko
    */
   public static void jatketaanko(boolean loppuuko) {
      jatketaanko = loppuuko;
   }

   /*
    * =====================================================================
    * Metodi
    */

   /**
    * Pelin k�ynnist�v� metodi. Metodin do-while lause py�ritt�� peli� niin
    * kauvan kun jatketaanko == true. Mahdollinen false-arvo tulee komennolla
    * "lopeta", m�nkij�n h�vi��n tappelussa robotille tai kun m�nkij� ker��
    * kaikki esineet.
    * <p>
    * Metodi lukee k�ytt�j�lt� komentoa, komennon mukaan suoritetaan eri
    * metodeja. Jos sy�tekomento on virheellinen, tulostetaan virheilmoitus
    * "Virhe!".
    * <p>
    * "lataa"-sy�te lukee tekstitiedostosta sokkelon tiedot. T�m� suoritetaan
    * pelaamisen aloitettaessa heti automaattisesti, jotta sokkelon tiedot
    * saadaan ohjelmalle. "inventoi"-sy�te tulostaa m�nkij�n ja t�m�n
    * inventoorin tiedot. "kartta"-sy�te tulostaa sokkelon paikat ja n�iden
    * tietojen kuvaavat merkit. "katso p||i||e||l"-sy�te katsoo m�nkij�n
    * viereisen (ilmansuunnan mukaisen) paikan. Tulostaa paikan tiedot.
    * "ohita"-sy�te ohittaa robootin liikkumisen, mutta suorittaa kuitenkin
    * sokkelossa roboottien liikkumisen. "liiku p||i||e||l"-sy�te liikuttaa
    * m�nkij�� sokkelossa ilmansunnan mukaiseen paikkaan. M�nkij�n liikkuessa
    * liikkuu my�s robootit sokkelossa. "lopeta"-sy�te asettaa attribuutin
    * jatketaanko false arvoiseksi. T�ll�in ohjelma lopetetaan.
    */
   public static void pelaa() {

      // Ladataan tiedot tiedostosta keskusmuistiin.
      sokkelo.lataa();

      /*
       * Suoritetaan while lausetta kunnes k�ytt�j� antaa komennon "lopeta",
       * kaikki esineet on ker�tty tai m�nkij� h�vi�� tappelun.
       */

      while (jatketaanko == true) {

         // Tulostetaan k�ytt�j�lle kehotus
         System.out.println("Kirjoita komento:");

         // Luetaan k�ytt�j�lt� komento
         String valinta = In.readString();

         // Muunnetaan k�ytt�j�n sy�te taulukko rakenteeseen, jolloin sy�tteen
         // k�skyt ja arvot saadaan eritelty� toisistaan.
         // Erotetaan rivin osat toisistaan ' ' merkin kohdalta
         // ja asetetaan ne valinnanOsat-taulukon arvoiksi
         String[] valinnanOsat = valinta.split("[ ]");

         try {
            /*
             * ============================================================
             * LATAA
             *
             * Jos valinnanOsat-taulukon ensimm�inen paikan arvo on "lataa",
             * ladataan sokkelo kutsumalla Sokkelo-luokan lataa metodia.
             * Tarkistetaan my�s sy�tteen virheellisyys t�m�n pituuden
             * tarkastamisella.
             */
            if (valinta.length() == 5 && valinnanOsat[0].equals("lataa")) {
               sokkelo.lataa();
            }

            /*
             * ============================================================
             * INVENTOI
             *
             * Jos valinnanOsat-taulukon ensimm�inen paikan arvo on "inventoi",
             * tulostetaan m�nkij�n inventoori kutsumalla Sokkelo-luokan
             * inventoi metodia. Tarkistetaan my�s sy�tteen virheellisyys t�m�n
             * pituuden tarkastamisella.
             */
            else if (valinta.length() == 8 && valinnanOsat[0].equals("inventoi")) {
               sokkelo.inventoi();
            }

            /*
             * ============================================================
             * KARTTA
             *
             * Jos valinnanOsat-taulukon ensimm�inen paikan arvo on "kartta",
             * tulostetaan sokkelo kutsumalla Sokkelo-luokan tulostaSokkelo
             * metodia. Tarkistetaan my�s sy�tteen virheellisyys t�m�n pituuden
             * tarkastamisella.
             */
            else if (valinta.length() == 6 && valinnanOsat[0].equals("kartta")) {
               sokkelo.tulostaSokkelo();
            }

            /*
             * ============================================================
             * ODOTA
             *
             * Jos valinnanOsat-taulukon ensimm�inen paikan arvo on "odota",
             * liikutetaan vain robootteja taulukossa Tarkistetaan my�s sy�tteen
             * virheellisyys t�m�n pituuden tarkastamisella.
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
             * Jos valinnanOsat-taulukon ensimm�inen paikan arvo on "tallenna",
             * tallennetaan sokkelon tila tekstitiedostoon. Tarkistetaan my�s
             * sy�tteen virheellisyys t�m�n pituuden tarkastamisella.
             */
            else if (valinta.length() == 8 && valinnanOsat[0].equals("tallenna")) {
               // Tallennetaan peli tekstitiedostoon
               sokkelo.tallenna();
            }

            /*
             * ============================================================
             * LOPETA
             *
             * Jos valinnanOsat-taulukon ensimm�inen paikan arvo on "lopeta",
             * asetetaan jatketaanko = false, jolloin pelaa- metodin suoritus
             * loppuu. Tarkistetaan my�s sy�tteen virheellisyys t�m�n pituuden
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
             * Jos valinnanOsat-taulukon ensimm�isen paikan arvo on "katso" ja
             * toisen paikan arvo on "p" || "i" || "e" || "l" kutsutaan
             * Sokkelo-luokan katsoPaikka metodia. Metodi tulostaa m�nkij�n
             * viereisen (ilmansuunnan mukaisen) paikan tiedot metodille annetun
             * suunta-parametrin mukaan. Tarkistetaan my�s sy�tteen
             * virheellisyys t�m�n pituuden tarkastamisella.
             */
            else if (valinnanOsat[0].equals("katso") && valinta.length() == 7 && (valinnanOsat[1].equals("p")
                  || valinnanOsat[1].equals("i") || valinnanOsat[1].equals("e") || valinnanOsat[1].equals("l"))) {
               // Haetaan k�ytt�j�n sy�tteest� suuntaa viittaava merkki joka
               // annetaan
               // Sokkelo-luokan katoPaikka-metodille parametrina.
               char suunta = valinnanOsat[1].charAt(0);
               sokkelo.katsoPaikka(suunta);
            }

            /*
             * ===============================================================
             * LIIKU
             *
             * Jos valinnanOsat-taulukon ensimm�isen paikan arvo on "liiku" ja
             * toisen paikan arvo on "p" || "i" || "e" || "l" kutsutaan
             * Sokkelo-luokan liiku metodia. Metodi siirt�� sokkelossa m�nkij��
             * yhden paikan valittuun suuntaan, jonka j�lkeen my�s robootit
             * liikkuvat sokkelossa. Tarkistetaan my�s sy�tteen virheellisyys
             * t�m�n pituuden tarkastamisella.
             */
            else if (valinnanOsat[0].equals("liiku") && valinta.length() == 7 && (valinnanOsat[1].equals("p")
                  || valinnanOsat[1].equals("i") || valinnanOsat[1].equals("e") || valinnanOsat[1].equals("l"))) {
               // Haetaan k�ytt�j�n sy�tteest� suunta johon m�nkij�� liikutetaan
               char suunta = valinnanOsat[1].charAt(0);
               sokkelo.liiku(suunta);
               // Lopuksi tulostetaan sokkelo
               sokkelo.tulostaSokkelo();
            }

            /*
             * ===============================================================
             * MUUNNA
             *
             * Jos valinnanOsat-taulukon ensimm�isen paikan arvo on "muunna",
             * toisen paikan arvo on >= m�nkij�n listalla olevien esineiden
             * lukum��r� ja koko sy�tteen mitta on valinnanOsat[0] +
             * valinnanOsat[1] + 1 (splitin my�t� kadotettu v�lily�nti),
             * muunnetaan valitun lukum��r�n verran esineita m�nkij�n energiaksi
             * Sokkelo-luokan muunna metodissa. Muunnetut esineet poistuvat
             * m�nkij�n inventoorista.
             */
            else if (valinnanOsat[0].equals("muunna") && valinnanOsat.length > 1
                  && valinta.length() == (valinnanOsat[0].length() + valinnanOsat[1].length() + 1)
                  && Integer.parseInt(valinnanOsat[1]) >= 0) {
               // Etsit��n sokkelosta m�nkij�, ja katsotaan kuinka monta
               // esinett� m�nkij�n
               // inventoorissa on. Jos k�ytt�j�n sy�tteen� muunnettavien
               // esineiden lkm on >
               // kuin m�nkij�n inventoorin esineiden lukum��r�, tulostetaan
               // virheilmoitus.
               // Jos muunnettavien esineiden lkm <= m�nkij�n inventoorin koko,
               // muunnetaan
               // sy�tteen mukainen m��r� esineit�.
               for (int i = 0; i < sokkelo.sokkelo.length; i++) {
                  for (int j = 0; j < sokkelo.sokkelo[i].length; j++) {
                     // Jos arvojen kohdalla sokkelossa on k�yt�v�-olio
                     if (sokkelo.sokkelo[i][j].getClass() == Kaytava.class) {
                        // Tarkistetaan onko k�yt�v�-olion lista ei-tyhj�
                        if (((Kaytava) sokkelo.sokkelo[i][j]).onkoTyhja() != true) {
                           // Luodaan apuLista jonka avulla k�yd��n listan
                           // olioita l�pi
                           OmaLista kaytavaPaikanLista = ((Kaytava) sokkelo.sokkelo[i][j]).lista();
                           // Alustetaan indeksi jonka avulla k�yd��n listan
                           // alkioita l�pi.
                           int indeksi = 0;
                           // K�yd��n listaa l�pi kunnes solmun seuraava == null
                           while (kaytavaPaikanLista.alkio(indeksi) != null) {

                              // Jos listalta l�ytyv� alkio on m�nkij�
                              if (kaytavaPaikanLista.alkio(indeksi).getClass() == Monkija.class) {
                                 // Haetaan m�nkij�n listan pituus
                                 int monkkarinListanPituus = sokkelo.monkija().inventoorinKoko();
                                 // Verrataan m�nkij�n listan pituutta
                                 // sy�tteess� annetun muunnettavien
                                 // esineiden m��r��n.
                                 if (monkkarinListanPituus >= Integer.parseInt(valinnanOsat[1])) {

                                    // Tehd��n m�nkij�n inventoorista OmaLista
                                    // attribuutti.
                                    OmaLista monkijanInventoori = sokkelo.monkija().inventoori();

                                    // Kutsutaan Sokkelo-luokassa metodia
                                    // muunnaEsine, joka poistetaan
                                    // k�ytt�j�n sy�tteen� antaman lukum��r�n
                                    // verran esineit� m�nkij�n
                                    // inventoorin alusta, ja lis�t�� n�iden
                                    // energian m�nkij�n
                                    // energiaan.
                                    sokkelo.muunnaEsine(Integer.parseInt(valinnanOsat[1]), monkijanInventoori);

                                 }
                                 // Jos muunnettavien esineiden m��r� on
                                 // virheellinen, tulostetaan
                                 // virheilmoitus ja palataan kysym��n
                                 // k�ytt�j�lt� komentoja.
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
            // Jos sy�te on virheellinen, tulostetaan virheilmoitus.
            else {
               System.out.println("Virhe!");
            }
         }
         // Jos sy�tteitten kanssa ilmenee virheilmoituksia, napataan virhe
         // ja tulostetaan virheilmoitus.
         catch (Exception e) {
            System.out.println("Virhe!");
         }
         // Asetetaan onkoEsineita-metodin paluuarvo jatketaanko-attribuuttiin.
         jatketaanko = sokkelo.onkoEsineita();
         // Jos jatketaanko arvo on false sokkelon esineiden puutteen vuoksi,
         // lopetetaan while silmukan k�ynti
         if (jatketaanko == false)
            break;

         // Asetetaan onkoElossa-metodin paluuarvo jatketaanko-attribuuttiin.
         jatketaanko = sokkelo.onkoElossa();
      }
      // Tulostetaan tieto ohjelman lopetuksesta.
      System.out.println("Ohjelma lopetettu.");
   }
}