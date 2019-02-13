package sokkelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import apulaiset.Automaatti;
import omaLista.OmaLista;
import osat.Esine;
import osat.Juuri;
import osat.Kaytava;
import osat.Monkija;
import osat.Robotti;
import osat.Seina;
import osat.Sisalto;

/**
 * Sokkelo-luokka, joka sis�lt�� sokkelolle yhteiset piirtee sek� metodit
 * lataukselle, tallennukselle, inventoinnille, katselemiselle, liikkumiselle,
 * odottamiselle ja muuntamiselle.
 * <p>
 * @author Vili-Veikko Rauhala (Rauhala.Vili.V@student.uta.fi) 
 * Opiskelijanumero: 421839
 * <p>
 * Tietojenk�sittelytiede OOPE Harjoiusty�
 */

public class Sokkelo {

   /*
    * =====================================================================
    * Attribuutit
    */

   /** Sokkelossa oleva m�nkij� */
   private Monkija monkkari;

   /** Sokkelon siemeluvun arvo */
   private static int siemenLuku;

   /** Sokkelon rivilukum��r� */
   private static int riviLkm;

   /** Sokkelon sarakelukum��r� */
   private static int sarakeLkm;

   /** Object-luokkainen sokkelo */
   public Object[][] sokkelo;

   /** Robottien tiedot sis�lt�v� lista */
   OmaLista robotit = new OmaLista();

   /*
    * =====================================================================
    * Aksessorit
    */
   /**
    * Monkijan palauttava metodi
    *
    * @return monkkari
    */
   public Monkija monkija() {
      return monkkari;
   }

   /**
    * SiemenLuvun palauttava metodi.
    *
    * @return siemenLuku.
    */
   public static int siemenLuku() {
      return siemenLuku;
   }

   /**
    * Rivilukum��r�n palauttava metodi.
    *
    * @return riviLkm.
    */
   public static int riviLkm() {
      return riviLkm;
   }

   /**
    * Sarakelukum��r�n palauttava metodi.
    *
    * @return sarakeLkm.
    */
   public static int sarakeLkm() {
      return sarakeLkm;
   }

   /*
    * =====================================================================
    * Metodit
    */

   /**
    * Sokkelo.txt tiedoston lataava metodi. K�y aluksi tekstitiedoston
    * ensimm�isen rivin l�pi, asettaen tekstitiedoston rivin tiedot
    * siemenlukuun, rivilkm sek� sarakelkm.
    * <p>
    * Ensimm�isen rivin j�lkeen k�yd��n seuraavia rivej� l�pi. Metodi etenee sen
    * mukaan mik� tiedoston ensimm�isen rivin arvo on. Asettaa indeksien arvojen
    * kohdalle tekstitiedoston mukaisen olion.
    * <p>
    * Suorittaa lopuksi tarkistaAlkutilanne-metodin, joka ker�� m�nkij�n kanssa
    * samassa paikassa olevat esineet pelin aluksi ja taistelee mahdollisesti
    * samassa paikassa olevien robottien kanssa.
    */
   public void lataa() {

      // Try-catch koska java vaatii virheiden v�ltt�miseksi.
      // Mahdollisia virheit� tiedoston puuttuminen tai lukuvirhe.
      try {

         // Luodaan syotevirta-olio sokkelo.txt tiedostosta
         FileInputStream syotevirta = new FileInputStream("sokkelo.txt");

         // Luodaan lukija-olio syotevirran lukemiseen
         InputStreamReader lukija = new InputStreamReader(syotevirta);

         // Luodaan tehokkaampi puskuroituLukija-olio sokkelo.txt tiedoston
         // k�sittelyyn.
         BufferedReader puskuroituLukija = new BufferedReader(lukija);

         /*
          * K�sitell��n tiedoston ensimm�inen rivi, josta saadaan siemen,
          * rivilkm ja sarakelkm. Rivilukum��r�st� ja sarakelukum��r�st� luodaan
          * sokkelo.
          */

         // Asetetaan tiedoston ensimm�inen rivi ekaRivi-atribuuttiin
         String ekaRivi = puskuroituLukija.readLine();

         // Erotetaan rivin osat toisistaan '|' merkin kohdalta
         // ja asetetaan ne ekanOsat-taulukon arvoiksi
         String[] ekanOsat = ekaRivi.split("[|]");

         // Poistetaan v�lily�nnit ekaRivin osista
         for (int i = 0; i < ekanOsat.length; i++)
            ekanOsat[i] = ekanOsat[i].trim();

         // Asetetaan ekaOsat-taulun arvot siemen, rivilkm sek� sarakelkm
         // attribuutteihin.
         siemenLuku = Integer.parseInt(ekanOsat[0]);
         riviLkm = Integer.parseInt(ekanOsat[1]);
         sarakeLkm = Integer.parseInt(ekanOsat[2]);

         // Luodaan rivilkm ja sarakelkm kokoinen sokkelo
         sokkelo = new Object[riviLkm][sarakeLkm];

         /*
          * K�sitell��n tiedoston loput riveist�
          */

         // Aluksi k�yd��n robotit-listaa l�pi. Jos lista ei ole tyhj�,
         // poistetaan kaikki t�m�n alkiot.
         while (robotit.onkoTyhja() == false) {
            robotit.poista(0);
         }

         // Suoritetaan niin kauvan kun BufferReaderin ready()-metodi == true
         while (puskuroituLukija.ready()) {

            // Asetetaan tiedoston puskuroituLukijan tuottama arvo
            // rivi-atribuuttiin
            String rivi = puskuroituLukija.readLine();

            // Erotetaan rivin osat toisistaan '|' merkin kohdalta
            // ja asetetaan ne osat-taulukon arvoiksi
            String[] osat = rivi.split("[|]");

            // Poistetaan v�lily�nnit rivien osista
            for (int i = 0; i < osat.length; i++) {
               osat[i] = osat[i].trim();
            }

            /*
             * Taulukon ensimm�isen sarake m��r��, mik� sokkelon osa on
             * kyseess�. Asetetaan sokkeloon kyseisen osan mukainen olio tokan
             * (rivi) ja kolmannen (sarake) sarakkeen arvojen kohdalle.
             *
             * Koska Sokkelo.txt tiedostossa on k�yt�v� paikka aina ennen
             * k�yt�v�ll� mahdollisesti olevaa esinett�, pit�� verrata onko
             * sokkelon kyseisell� k�yt�v�paikalla jo mahdollisesti sis�lt��.
             * Jos ei, tehd��n uusi kaytavaSisalto. Jos k�yt�v�ll� on jo
             * sis�lt��, asetetaan uusi solmu listan per��n.
             */

            // Jos osat taulun ensimm�inen sarake on "Seina", asetetaan
            // sokkeloon
            // Seina-olio osat-taulun osoittamien arvojen kohdalle.
            if (osat[0].equals("Seina")) {
               sokkelo[Integer.parseInt(osat[1])][Integer.parseInt(osat[2])] = new Seina(Integer.parseInt(osat[1]),
                     Integer.parseInt(osat[2]));
            }

            // Jos osat taulun ensimm�inen sarake on "Kaytava", asetetaan
            // sokkeloon
            // Kaytava-olio osat-taulun osoittamien arvojen kohdalle.
            else if (osat[0].equals("Kaytava")) {
               sokkelo[Integer.parseInt(osat[1])][Integer.parseInt(osat[2])] = new Kaytava(Integer.parseInt(osat[1]),
                     Integer.parseInt(osat[2]));
            }

            /*
             * Jos osat taulun ensimm�inen sarake on "Monkija", asetetaan
             * sokkeloon Monkija-olio osat-taulun osoittamien arvojen kohdalle
             * jos paikka on sallittu.
             */

            else if (osat[0].equals("Monkija")) {

               // Luodaan Monkija-olio jolle annetaan tekstitiedoston m��r��m�
               // paikka, energie sek� suunta
               monkkari = new Monkija(Integer.parseInt(osat[1]), Integer.parseInt(osat[2]), Integer.parseInt(osat[3]),
                     osat[4].charAt(0));

               // Jos paikka on sallittu, asetetaan m�nkij�-olio k�yt�v�paikan
               // listalle.
               // Jos paikka ei ole sallittu, tulostetaan virheilmoitus
               if (monkkari.sallittu()) {

                  // Lis�t��n Kaytava-olion omaan listaan monkkari.
                  ((Kaytava) sokkelo[Integer.parseInt(osat[1])][Integer.parseInt(osat[2])]).lisaa(monkkari);
               } else
                  System.out.println("Sokkelossa ilmeni virhe: monkkarille oli m��ritelty virheellinen paikka.");
            }

            /*
             * Jos osat taulun ensimm�inen sarake on "Robotti", asetetaan
             * sokkeloon Robotti-olio osat-taulun osoittamien arvojen kohdalle
             * jos paikka on sallittu.
             */

            else if (osat[0].equals("Robotti")) {

               // Luodaan Robotti-olio jolle annetaan tekstitiedoston m��r��m�
               // paikka, energia sek� suunta
               Robotti robo = new Robotti(Integer.parseInt(osat[1]), Integer.parseInt(osat[2]),
                     Integer.parseInt(osat[3]), osat[4].charAt(0));

               // Jos paikka on sallittu, asetetaan robotti-olio k�yt�v�paikan
               // listalle.
               // Jos paikka ei ole sallittu, tulostetaan virheilmoitus.
               if (robo.sallittu()) {

                  // Lis�t��n Kaytava-olion omaan listaan robo.
                  ((Kaytava) sokkelo[Integer.parseInt(osat[1])][Integer.parseInt(osat[2])]).lisaa(robo);

                  // Lis�t��n robotit-listalle robo. Robotteja lis�t��n
                  // listalle sit� mukaan kuin niit�
                  // sokkelosta ilmenee
                  robotit.lisaaLoppuun(robo);
                  
               } else
                  System.out.println("Sokkelossa ilmeni virhe: robotille oli m��ritelty virheellinen paikka.");
            }

            /*
             * Jos osat taulun ensimm�inen sarake on "Esine", asetetaan
             * k�yt�v�paikan listalle Esine-olio osat-taulun osoittamien arvojen
             * kohdalle.
             */

            else if (osat[0].equals("Esine")) {

               // Luodaan Esine-olio jolle annetaan tekstitiedoston m��r��m�
               // paikka sek� energia.
               Esine tuote = new Esine(Integer.parseInt(osat[1]), Integer.parseInt(osat[2]), Integer.parseInt(osat[3]));

               // Jos paikka on sallittu, asetetaan esine-olio sokkeloon.
               // Jos paikka ei ole sallittu, tulostetaan virheilmoitus.
               if (tuote.sallittu()) {

                  // Lis�t��n Kaytava-olion omaan listaan tuote
                  ((Kaytava) sokkelo[Integer.parseInt(osat[1])][Integer.parseInt(osat[2])]).lisaa(tuote);
               } else
                  System.out.println("Sokkelossa ilmeni virhe: esineelle oli m��ritelty virheellinen paikka.");
            }
         }
         puskuroituLukija.close();

         // Kutsutaan Autommaatti-luokan alusta-metodia
         Automaatti.alusta(siemenLuku);

         // Kutsutaan metodia joka tarkistaa pelin alussa k�yt�v�paikan arvot
         // jossa m�nki� sijaitsee. Jos samassa paikassa on esineit�, m�nkij�
         // ker��
         // esineet automaattisesti inventooriin. Jos paikassa on robotti,
         // syntyy
         // taistelu.
         tarkistaAlkutilanne();
      }

      // Virheilmoitus jos tiedostoa ei l�ydy
      catch (FileNotFoundException e) {
         System.out.println("Tiedosto josta sokkelo luetaan puuttuu.");
      }

      // Virheilmoitus lukuvirheelle
      catch (IOException e) {
         System.out.println("Virhe tekstitiedostossa.");
      }
   }

   /**
    * Metodi joka tarkistaa sokkelon tilanteen. Jos sokkelossa on m�nkij�n
    * kanssa samassa paikkaa esineit�, m�nkij� ker�� esineet inventooriinsa. Jos
    * m�nkij�n kanssa on samassa paikassa robotti, syntyy tappelu. Lopuksi
    * metodi tarkistaa onko sokkelossa en��n esineit�. Jos ei, ohjelma
    * lopetetaan.
    */
   public void tarkistaAlkutilanne() {

      /*
       * K�yd��n listan arvoja l�pi. Jos listalta l�ytyy esine, siirret��n t�m�
       * m�nkij�n listalle. Jos listalta l�ytyy robotti, alkaa m�nkij�n ja
       * robotin v�linen taistelu.
       */
      for (int i = 0; i < sokkelo.length; i++) {
         for (int j = 0; j < sokkelo[i].length; j++) {
            // Jos arvojen kohdalla sokkelossa on k�yt�v�-olio
            if (sokkelo[i][j].getClass() == Kaytava.class) {
               // Tarkistetaan onko k�yt�v�-olion lista ei-tyhj�
               if (((Kaytava) sokkelo[i][j]).onkoTyhja() != true) {
                  // Luodaan kaytavanPaikanLista jonka avulla k�yd��n listan
                  // olioita l�pi
                  OmaLista kaytavaPaikanLista = ((Kaytava) sokkelo[i][j]).lista();
                  // Alustetaan indeksi jonka avulla k�yd��n kaytavanPaikanLista
                  // alkioita l�pi.
                  int indeksi = 0;
                  // K�yd��n apuListaa l�pi kunnes solmun seuraava == null
                  while (kaytavaPaikanLista.alkio(indeksi) != null) {
                     // Jos listalta l�ytyy alkio m�nkij�, k�yd��n kyseisen
                     // listan
                     // kaikki alkiot l�pi
                     if (kaytavaPaikanLista.alkio(indeksi).getClass() == Monkija.class) {

                        /*
                         * K�yd��n listan arvoja l�pi. Jos listalta l�ytyy
                         * esine, siirret��n t�m� m�nkij�n listalle. Jos
                         * listalta l�ytyy robotti, alkaa m�nkij�n ja robotin
                         * v�linen taistelu.
                         */
                        // Alustetaan listanIndeksi jonka avulla k�yd��n
                        // kaytavanPaikanLista
                        // alkioita l�pi uudelleen.
                        int listanIndeksi = 0;
                        // K�yd��n listaa l�pi kunnes solmun seuraava == null
                        while (kaytavaPaikanLista.alkio(listanIndeksi) != null) {

                           // Jos listalta l�ytyv� alkio on esine, siirret��n se
                           // m�nkij�n
                           // inventooriin
                           if (kaytavaPaikanLista.alkio(listanIndeksi).getClass() == Esine.class) {

                              // Suoritetaan m�nkij�n listalle lis�ysmetodi,
                              // joka saa parametrina
                              // k�yt�v�paikan listan poista-metodin paluuarvon.
                              // Poista-metodille
                              // annetaan parametrina listanIndeksi.
                              monkkari.lisaa(kaytavaPaikanLista.poista(listanIndeksi));

                              // Tarkistetaan poistettiinko k�yt�v�paikan
                              // listalta olio ennen m�nkij��.
                              // jos listaIndeksi < indeksi, poistetaan my�s
                              // indeksin pituudesta 1 jotta
                              // indeksin arvo pysyy m�nkij�n kohdalla.
                              if (listanIndeksi < indeksi) {
                                 indeksi--;
                              }
                              // Lopuksi listanIndeksi miinustetaan
                              // yhdell�, jolloin listanIndeksi ei p��se
                              // hypp��m��n yhden listan
                              // arvoista ylitse.
                              listanIndeksi--;
                           }

                           // Jos listalta l�ytyv� alkio on robotti, syttyy
                           // m�nkij�n
                           // ja robotin v�linen tappelu.
                           else if (kaytavaPaikanLista.alkio(listanIndeksi).getClass() == Robotti.class) {
                              // Kutsutaan metodia joka vertailee m�nkij�n ja
                              // robottien energioita
                              // compareTo-metodilla. Antaa paluuarvona true jos
                              // m�nkij� voittaa tappelun,
                              // falsen jos m�nkij� h�visi tappelun.
                              boolean monkijaElossa = tappelu();

                              // Jos m�nkij� selviytyy tappelusta, tarkistetaan
                              // poistettiinko k�yt�v�paikan
                              // listalta olio ennen m�nkij��. Jos listaIndeksi
                              // < indeksi, poistetaan my�s
                              // indeksin pituudesta 1 jotta indeksin arvo pysyy
                              // m�nkij�n kohdalla.
                              if (monkijaElossa == true) {
                                 if (listanIndeksi < indeksi) {
                                    indeksi--;
                                 }
                                 // Lopuksi listanIndeksi miinustetaan
                                 // yhdell�, jolloin listanIndeksi ei p��se
                                 // hypp��m��n yhden listan
                                 // arvoista ylitse.
                                 listanIndeksi--;
                              }

                              // Jos m�nkij� h�vi�� tappelun, lopetetaan metodin
                              // l�pik�ynti.
                              else if (monkijaElossa == false) {
                                 return;
                              }
                           }
                           listanIndeksi++;
                        }
                     }
                     indeksi++;
                  }
               }
            }
         }
      }
   }

   /**
    * Metodi joka tarkistaa onko m�nkij� viel� elossa. Katsoo m�nkij�n tiedoista
    * m�nkij�n tiedon t�m�n el�misen tilasta.
    *
    * @return onko m�nkij� elossa.
    */
   public boolean onkoElossa() {
      return monkkari.onkoElossa();

   }

   /**
    * Metodi joka k�y sokkeloa l�pi kunnes l�yt�� sokkelosta k�yt�v�paikan,
    * jonka listalla on m�nkij�. Metodi kutsuu m�nkij�n metodia, joka tulostaa
    * m�nkij�n inventoorin tiedot.
    */
   public void inventoi() {
      // K�yd��n l�pi sokkeloa kunnes l�ytyy k�yt�v�-paikka.
      for (int i = 0; i < sokkelo.length; i++) {
         for (int j = 0; j < sokkelo[i].length; j++) {
            // Jos arvojen kohdalla sokkelossa on k�yt�v�-olio
            if (sokkelo[i][j].getClass() == Kaytava.class) {
               // Tarkistetaan onko k�yt�v�-olion lista ei-tyhj�
               if (((Kaytava) sokkelo[i][j]).onkoTyhja() != true) {
                  // Luodaan apuLista jonka avulla k�yd��n listan olioita l�pi
                  OmaLista apuLista = ((Kaytava) sokkelo[i][j]).lista();

                  // Alustetaan indeksi jonka avulla k�yd��n listan alkioita
                  // l�pi.
                  int indeksi = 0;
                  // K�yd��n listaa l�pi kunnes solmun seuraava == null
                  while (apuLista.alkio(indeksi) != null) {
                     // Jos listalta l�ytyy m�nkij�, kutsutaan m�nkij�n
                     // toString- metodia ja m�nkij�n listan tulostavaa metodia.
                     if (apuLista.alkio(indeksi).getClass() == Monkija.class) {

                        // Tulostetaan m�nkij�n toString()-metodin paluuarvo
                        System.out.println(monkkari.toString());

                        // Jos m�nkij�n inventoori on ep�tyhj�, tulostetaan
                        // inventoorin sis�lt�
                        if (monkkari.onkoInventooriTyhja() != true) {
                           // Kutsutaan m�nkij�n tulostaInventoori()-metodia.
                           monkkari.tulostaInventoori();
                           return;
                        }
                     }
                     indeksi++;
                  }
               }
            }
         }
      }
   }

   /**
    * Metodi joka tulostaa sokkelon. K�y sokkelon jokaisen paikan l�pi. Jos
    * sokkelon osa on sein�, tulostetaan sein�� kuvaava symbooli. Jos sokkelon
    * osa on k�yt�v�, ja k�yt�v�paikka on tyhj�, tulostetaan k�yt�v�n symboli.
    * Jos k�yt�v�paikka ei ole tyhj�, kutsutaan apuKaytavan kautta
    * Kaytava-luokan listanMerkki()-metodia, jonka avulla saadaan tulostetuksi
    * oikea merkki sokkeloon.
    */
   public void tulostaSokkelo() {
      try {
         // K�yd��n sokkelon rivej� l�pi
         for (Object[] rivi : sokkelo) {
            // K�yd��n rivin sarakkeita l�pi
            for (Object alkio : rivi) {

               /*
                * Jos alkion luokka == Seina-luokka. Tulostetaan Seinan merkki.
                */
               if (alkio.getClass() == Seina.class) {

                  // Asetetaan apuSeina-olioon Seina-luokaksi muutettu
                  // Object-luokkainen alkio.
                  Seina apuSeina = (Seina) alkio;
                  System.out.print(apuSeina.merkki());
               }

               /*
                * Jos alkion luokka == Kaytava-luokka. Jos kaytavapaikka on
                * tyhj�, tulostetaan kaytavan oma merkki. Jos k�yt�v�paikalla on
                * jokin olio listalla, tulostetaan t�m�n merkki
                */
               else if (alkio.getClass() == Kaytava.class) {

                  // Asetetaan apuKaytava-olioon Kaytava-luokaksi muutettu
                  // Object-luokkainen alkio.
                  Kaytava apuKaytava = (Kaytava) alkio;

                  /*
                   * Jos kaytavapaikka on tyhj�, tulostetaan kaytavan oma
                   * merkki. Jos k�yt�v�paikalla on jokin olio listalla,
                   * tulostetaan t�m�n merkki
                   */
                  if (apuKaytava.onkoTyhja() == true) {
                     System.out.print(apuKaytava.merkki());
                  }

                  /*
                   * Jos paikan lista ei ole tyhj�, tarkistetaan mit� listalla
                   * on ja tulostetaan joko m�nki�n, robotin tai esineen
                   * merkki.
                   */
                  else {
                     System.out.print(apuKaytava.listanMerkki());
                  }
               }
            }
            // Kun yksi riveist� on k�yty l�pi, vaihdetaan rivi�
            System.out.println("");
         }
      } catch (NullPointerException e) {
         System.out.println("Taulukon kaikkia paikkoja ei ole luotu.");
      } catch (Exception e) {
         System.out.println("Taulukon tulostamisessa ilmeni ongelma.");
      }
   }

   /**
    * Metodi joka tulostaa m�nkij�n viereisen paikan tiedot. Viereinen paikka
    * joka tulostetaan m��r�ytyy k�ytt�j�n antaman sy�teen mukaan.
    *
    * @param suunta k�ytt�j�n antama suunta.
    */
   public void katsoPaikka(char suunta) {
      // Arvot joiden avulla navigoidaan m�nkij�n paikka, ja n�iden avulla
      // haetaan oikeaa sokkelon paikkaa sokkelosta joka tulostetaan.
      int apuRivi = 0;
      int apuSarake = 0;

      /*
       * K�yd��n aluksi sokkeloa l�pi ja etsit��n sokkelosta k�yt�v�paikkaa
       * jossa m�nkij� sijaitsee. Jos k�yt�v�paikan lista != tyhj�, k�yd��n
       * listan arvoja l�pi. Jos listalla on m�nkij�, tulostetaan m�nkij�n
       * suunnan mukainen viereinen paikka.
       */
      for (int i = 0; i < sokkelo.length; i++) {
         for (int j = 0; j < sokkelo[i].length; j++) {
            // Jos arvojen kohdalla sokkelossa on k�yt�v�-olio
            if (sokkelo[i][j].getClass() == Kaytava.class) {
               // Tarkistetaan onko k�yt�v�-olion lista ei-tyhj�
               if (((Kaytava) sokkelo[i][j]).onkoTyhja() != true) {
                  // Luodaan apuLista jonka avulla k�yd��n listan olioita l�pi
                  OmaLista apuLista = ((Kaytava) sokkelo[i][j]).lista();
                  // Alustetaan indeksi jonka avulla k�yd��n listan alkioita
                  // l�pi.
                  int indeksi = 0;
                  // K�yd��n listaa l�pi kunnes solmun seuraava == null
                  while (apuLista.alkio(indeksi) != null) {
                     // Jos listalta l�ytyy m�nkij�, asetetaan m�nkij�n paikkan
                     // rivin ja sarakkeen arvo apuRiviin ja apuSarakkeeseen
                     if (apuLista.alkio(indeksi).getClass() == Monkija.class) {
                        apuRivi = ((Juuri) apuLista.alkio(indeksi)).rivi();
                        apuSarake = ((Juuri) apuLista.alkio(indeksi)).sarake();

                        /*
                         * apuRivin ja apuSarakkeen avulla navigoidaan
                         * sokkelossa sen mukaan, mik� suunnan arvo on. Jos
                         * suunta == 'p', tulostetaan m�nkij�n yl�puolella
                         * olevan sokkelon paikan tiedot. Jos suunta == 'i',
                         * tulostetaan m�nkij�n oikeallapuolella olevan sokkelon
                         * paikan tiedot. Jos suunta == 'e', tulostetaan
                         * m�nkij�n alapuolella olevan sokkelon paikan tiedot.
                         * Jos suunta == 'l', tulostetaan m�nkij�n
                         * vasemmallapuolella olevan sokkelon paikan tiedot.
                         */

                        // Jos suunta on 'p', apuRivi - 1.
                        if (suunta == 'p') {
                           // Kutsutaan sokkelopaikan olion toString-metodia
                           System.out.println(sokkelo[apuRivi - 1][apuSarake].toString());
                           // Jos sokkelopaikka on k�yt�v�, k�yd��n
                           // k�yt�v�paikan listaa l�pi
                           if (sokkelo[apuRivi - 1][apuSarake].getClass() == Kaytava.class) {
                              if (((Kaytava) sokkelo[apuRivi - 1][apuSarake]).onkoTyhja() != true) {
                                 // Kutsutaan kaytavan tulostaKaytavanLista
                                 // metodia joka tulostaa kaikki
                                 // listan arvot
                                 ((Kaytava) sokkelo[apuRivi - 1][apuSarake]).tulostaKaytavanLista();
                              }
                           }
                        }

                        // Jos suunta on 'i', apuSarake + 1.
                        else if (suunta == 'i') {
                           // Kutsutaan sokkelopaikan olion toString-metodia
                           System.out.println(sokkelo[apuRivi][apuSarake + 1].toString());
                           // Jos sokkelopaikka on k�yt�v�, k�yd��n
                           // k�yt�v�paikan listaa l�pi
                           if (sokkelo[apuRivi][apuSarake + 1].getClass() == Kaytava.class) {
                              if (((Kaytava) sokkelo[apuRivi][apuSarake + 1]).onkoTyhja() != true) {
                                 // Kutsutaan kaytavan tulostaKaytavanLista
                                 // metodia joka tulostaa kaikki
                                 // listan arvot
                                 ((Kaytava) sokkelo[apuRivi][apuSarake + 1]).tulostaKaytavanLista();
                              }
                           }
                        }

                        // Jos suunta on 'e', apurivi + 1.
                        else if (suunta == 'e') {
                           // Kutsutaan sokkelopaikan olion toString-metodia
                           System.out.println(sokkelo[apuRivi + 1][apuSarake].toString());
                           // Jos sokkelopaikka on k�yt�v�, k�yd��n
                           // k�yt�v�paikan listaa l�pi
                           if (sokkelo[apuRivi + 1][apuSarake].getClass() == Kaytava.class) {
                              if (((Kaytava) sokkelo[apuRivi + 1][apuSarake]).onkoTyhja() != true) {
                                 // Kutsutaan kaytavan tulostaKaytavanLista
                                 // metodia joka tulostaa kaikki
                                 // listan arvot
                                 ((Kaytava) sokkelo[apuRivi + 1][apuSarake]).tulostaKaytavanLista();
                              }
                           }
                        }

                        // Jos suunta on l, apuSarake -1.
                        else if (suunta == 'l') {
                           // Kutsutaan sokkelopaikan olion toString-metodia
                           System.out.println(sokkelo[apuRivi][apuSarake - 1].toString());
                           // Jos sokkelopaikka on k�yt�v�, k�yd��n
                           // k�yt�v�paikan listaa l�pi
                           if (sokkelo[apuRivi][apuSarake - 1].getClass() == Kaytava.class) {
                              if (((Kaytava) sokkelo[apuRivi][apuSarake - 1]).onkoTyhja() != true) {
                                 // Kutsutaan kaytavan tulostaKaytavanLista
                                 // metodia joka tulostaa kaikki
                                 // listan arvot
                                 ((Kaytava) sokkelo[apuRivi][apuSarake - 1]).tulostaKaytavanLista();
                              }
                           }
                        }
                        return;
                     }
                     indeksi++;
                  }
               }
            }
         }
      }
   }

   /**
    * Metodi joka muuntaa m�nkij�n listassa olevia esineit� parametrina saadun
    * lukum��r�n verran energiaksi m�nkij�lle. M�nkij� saa esineen sis�lt�m�n
    * energian verran lis�� energiaa.
    *
    * @param muunnettavaLkm sis�lt�� muunnettavien esineiden lukum��r�, k�yt�v�paikan lista
    * jossa m�nkij� sijaitsee, indeksi jossa m�nkij� sijaitsee kaytavanPaikanListalla sek� 
    * m�nkij�n inventoori.
    * @param monkijanInventoori lista jossa m�nkij�n esineet
    */
   public void muunnaEsine(int muunnettavaLkm, OmaLista monkijanInventoori) {
      // Siirret��n esineiden energioita m�nkij�lle muunnettavaLkm verran.
      for (int i = 0; i < muunnettavaLkm; i++) {
         // Haetaan muunnettava esine m�nkij�n inventoorin
         // ensimm�iselt� paikalta. Poistetaan samalla esine inventoorista.
         Esine muunnettavaTuote = (Esine) monkijanInventoori.poistaAlusta();

         // Haetaan muunnettavan esineen energia
         int energia = muunnettavaTuote.energia();

         // Lis�t��n muunnettavan tuotteen energia m�nkij�n energiaan.
         monkkari.lisaaEnergiaa(energia);
      }
   }

   /**
    * Metodi joka hakee sokkelosta k�yt�v�paikan, jossa sis�lt�� m�nkij�.
    * Suhteellisen turha metodi t�ll� hetkell� koska useimmissa metodeissa
    * tarvitaan my�s listan indeksi�, jossa m�nkij� oli. Hy�dynnet��n kuitenkin
    * joissain tilanteissa jossa tarvitaan vain sokkelon listaa jossa on
    * m�nkij�.
    *
    * @return m�nkij�n sis�lt�v� lista.
    */
   public OmaLista monkijallinenLista() {
      for (int i = 0; i < sokkelo.length; i++) {
         for (int j = 0; j < sokkelo[i].length; j++) {
            // Jos arvojen kohdalla sokkelossa on k�yt�v�-olio
            if (sokkelo[i][j].getClass() == Kaytava.class) {
               // Tarkistetaan onko k�yt�v�-olion lista ei-tyhj�
               if (((Kaytava) sokkelo[i][j]).onkoTyhja() != true) {
                  // Luodaan apuLista jonka avulla k�yd��n listan olioita l�pi
                  OmaLista apuLista = ((Kaytava) sokkelo[i][j]).lista();
                  // Alustetaan indeksi jonka avulla k�yd��n listan alkioita
                  // l�pi.
                  int indeksi = 0;
                  // K�yd��n listaa l�pi kunnes solmun seuraava == null
                  while (apuLista.alkio(indeksi) != null) {
                     // Jos listalta l�ytyy m�nkij�, palautetaan kyseinen lista.
                     if (apuLista.alkio(indeksi).getClass() == Monkija.class) {
                        return apuLista;
                     }
                     indeksi++;
                  }
               }
            }
         }
      }
      // Jos sokkelon milt��n k�yt�v�lt� ei l�ydy m�nkij��,
      // paluuarvona null.
      return null;
   }

   /**
    * Metodi joka liikuttaa m�nkij�� sokkelossa k�ytt�j�n sy�tteen mukaiseen
    * suuntaan. Jos suunnassa johon m�nkij�� siirret��n on sein�, tulostetaan
    * "kops". Jos suunnassa johon siirryt��n on k�yt�v�, siirtyy m�nkij�
    * kyseiselle sokkelon paikalle.
    * <p>
    * Jos paikalla johon siirryttiin on esine, siirret��n se m�nkij�n
    * inventooriin. Jos paikalla on robotti, syntyy m�nkij�n ja robotin
    * v�linen tappelu. Liikkumisen j�lkeen suoritetaan metodi joka liikuttaa
    * robotteja sokkelossa.
    *
    * @param suunta johon m�nkij��n siirret�n.
    */
   public void liiku(char suunta) {
      // M�nkij�n paikan rivi- ja sarakeindeksi
      int riviIndeksi = monkkari.rivi();
      int sarakeIndeksi = monkkari.sarake();

      /*
       * ==================================================================
       * LIIKUTAAN POHJOISEEN
       *
       * Jos parametrina saatu suunta on 'p'.
       */
      if (suunta == 'p') {
         // Tarkistetaan onko liikuttavassa suunnassa sallittu paikka, eli
         // k�yt�v�.
         // Jos suunta on 'p', riviIndeksi-1 jotta p��st��n m�nkij�n yl�puolella
         // olevaan sokkelon paikkaan k�siksi.
         if (((Juuri) sokkelo[riviIndeksi - 1][sarakeIndeksi]).sallittu() == true) {

            // Lis�t��n m�nkij� sokkelossa pohjoiseen
            ((Kaytava) sokkelo[riviIndeksi - 1][sarakeIndeksi]).lisaa(monkkari);

            // Annetaan m�nkij�lle uuden paikan indeksit ja uusi suunta
            monkkari.rivi(riviIndeksi - 1);
            monkkari.sarake(sarakeIndeksi);
            monkkari.suunta(suunta);

            // Haetaan sokkelosta lista jossa sijaitsee vanhan m�nkij�n olio
            OmaLista vanhaMonkkari = ((Kaytava) sokkelo[riviIndeksi][sarakeIndeksi]).lista();

            // Alustetaan indeksi jonka avulla k�yd��n listan alkioita l�pi.
            int indeksi = 0;
            // K�yd��n listaa l�pi kunnes solmun seuraava == null
            while (vanhaMonkkari.alkio(indeksi) != null) {
               // Jos listalta l�ytyy m�nkij�, poistetaan m�nkij�.
               if (vanhaMonkkari.alkio(indeksi).getClass() == Monkija.class) {
                  vanhaMonkkari.poista(indeksi);
               }
            }

            // Suoritetaan tarkistaAlkutilanne-metodi joka katsoo onko m�nkij�n
            // uudessa paikassa
            // esineit� jotka ker�t��n, tai robotteja joiden kanssa tapellaan.
            tarkistaAlkutilanne();

            // P�ivitet��n m�nkij�n esineiden paikkojen tiedot m�nkij�n paikan
            // tiedoiksi
            for (int i = 0; i < monkkari.inventoori().koko(); i++) {
               ((Juuri) monkkari.inventoori().alkio(i)).rivi(riviIndeksi - 1);
               ((Juuri) monkkari.inventoori().alkio(i)).sarake(sarakeIndeksi);
            }

            // Tarkistetaan onko sokkelossa viel� esineit�.
            // Jos onkoEsineita-metodin paluuarvo on false, lopetetaan
            // liiku-metodin l�pik�ynti koska peli on jo l�p�isty.
            if (onkoEsineita() == false)
               return;

            // Liikutetaan robotteja m�nkij�n liikuttelun j�lkeen.
            liikutaRobotteja(robotit, sokkelo);
         } else {
            // Jos liikuttavassa suunnassa ei ole k�yt�v�-paikkaa, tulostetaan
            // "kops!" ja liikutetaan robotteja ja vaihdetaan m�nkij�n suuntaa.
            monkkari.suunta(suunta);
            System.out.println("Kops!");
            liikutaRobotteja(robotit, sokkelo);
         }
      }

      /*
       * ==================================================================
       * LIIKUTAAN IT��N
       *
       * Jos parametrina saatu suunta on 'i'.
       */
      else if (suunta == 'i') {
         // Tarkistetaan onko liikuttavassa suunnassa sallittu paikka, eli
         // k�yt�v�.
         // Jos suunta on 'i', sarakeIndeksi+1 jotta p��st��n oikealla puolella
         // olevaan sokkelon paikkaan k�siksi.
         if (((Juuri) sokkelo[riviIndeksi][sarakeIndeksi + 1]).sallittu() == true) {

            // Lis�t��n m�nkij� sokkelossa it��n
            ((Kaytava) sokkelo[riviIndeksi][sarakeIndeksi + 1]).lisaa(monkkari);

            // Annetaan m�nkij�lle uuden paikan indeksit ja uusi suunta
            monkkari.rivi(riviIndeksi);
            monkkari.sarake(sarakeIndeksi + 1);
            monkkari.suunta(suunta);

            // Haetaan sokkelosta lista jossa sijaitsee vanhan m�nkij�n olio
            OmaLista vanhaMonkkari = ((Kaytava) sokkelo[riviIndeksi][sarakeIndeksi]).lista();

            // Alustetaan indeksi jonka avulla k�yd��n listan alkioita l�pi.
            int indeksi = 0;
            // K�yd��n listaa l�pi kunnes solmun seuraava == null
            while (vanhaMonkkari.alkio(indeksi) != null) {
               // Jos listalta l�ytyy m�nkij�, poistetaan m�nkij�.
               if (vanhaMonkkari.alkio(indeksi).getClass() == Monkija.class) {
                  vanhaMonkkari.poista(indeksi);
               }
            }

            // Suoritetaan tarkistaAlkutilanne-metodi joka katsoo onko m�nkij�n
            // uudessa paikassa
            // esineit� jotka ker�t��n, tai robotteja joiden kanssa tapellaan.
            tarkistaAlkutilanne();

            // P�ivitet��n m�nkij�n esineiden paikkojen tiedot m�nkij�n paikan
            // tiedoiksi
            for (int i = 0; i < monkkari.inventoori().koko(); i++) {
               ((Juuri) monkkari.inventoori().alkio(i)).rivi(riviIndeksi);
               ((Juuri) monkkari.inventoori().alkio(i)).sarake(sarakeIndeksi + 1);
            }

            // Tarkistetaan onko sokkelossa viel� esineit�.
            // Jos onkoEsineita-metodin paluuarvo on false, lopetetaan
            // liiku-metodin l�pik�ynti koska peli on jo l�p�isty.
            if (onkoEsineita() == false)
               return;

            // Liikutetaan robotteja m�nkij�n liikuttelun j�lkeen.
            liikutaRobotteja(robotit, sokkelo);
         } else {
            // Jos liikuttavassa suunnassa ei ole k�yt�v�-paikkaa, tulostetaan
            // "kops!" ja liikutetaan robotteja ja vaihdetaan m�nkij�n suuntaa.
            monkkari.suunta(suunta);
            System.out.println("Kops!");
            liikutaRobotteja(robotit, sokkelo);
         }
      }

      /*
       * ==================================================================
       * LIIKUTAAN L�NTEEN
       *
       * Jos parametrina saatu suunta on 'l'.
       */
      else if (suunta == 'l') {
         // Tarkistetaan onko liikuttavassa suunnassa sallittu paikka, eli
         // k�yt�v�.
         // Jos suunta on 'l', sarakeIndeksi-1 jotta p��st��n vasemmalle
         // puolella
         // olevaan sokkelon paikkaan k�siksi.
         if (((Juuri) sokkelo[riviIndeksi][sarakeIndeksi - 1]).sallittu() == true) {

            // Lis�t��n m�nkij� sokkelossa it��n
            ((Kaytava) sokkelo[riviIndeksi][sarakeIndeksi - 1]).lisaa(monkkari);

            // Annetaan m�nkij�lle uuden paikan indeksit ja uusi suunta
            monkkari.rivi(riviIndeksi);
            monkkari.sarake(sarakeIndeksi - 1);
            monkkari.suunta(suunta);

            // Haetaan sokkelosta lista jossa sijaitsee vanhan m�nkij�n olio
            OmaLista vanhaMonkkari = ((Kaytava) sokkelo[riviIndeksi][sarakeIndeksi]).lista();

            // Alustetaan indeksi jonka avulla k�yd��n listan alkioita l�pi.
            int indeksi = 0;
            // K�yd��n listaa l�pi kunnes solmun seuraava == null
            while (vanhaMonkkari.alkio(indeksi) != null) {
               // Jos listalta l�ytyy m�nkij�, poistetaan m�nkij�.
               if (vanhaMonkkari.alkio(indeksi).getClass() == Monkija.class) {
                  vanhaMonkkari.poista(indeksi);
               }
            }

            // Suoritetaan tarkistaAlkutilanne-metodi joka katsoo onko m�nkij�n
            // uudessa paikassa
            // esineit� jotka ker�t��n, tai robotteja joiden kanssa tapellaan.
            tarkistaAlkutilanne();

            // P�ivitet��n m�nkij�n esineiden paikkojen tiedot m�nkij�n paikan
            // tiedoiksi
            for (int i = 0; i < monkkari.inventoori().koko(); i++) {
               ((Juuri) monkkari.inventoori().alkio(i)).rivi(riviIndeksi);
               ((Juuri) monkkari.inventoori().alkio(i)).sarake(sarakeIndeksi - 1);
            }

            // Tarkistetaan onko sokkelossa viel� esineit�.
            // Jos onkoEsineita-metodin paluuarvo on false, lopetetaan
            // liiku-metodin l�pik�ynti koska peli on jo l�p�isty.
            if (onkoEsineita() == false)
               return;

            // Liikutetaan robotteja m�nkij�n liikuttelun j�lkeen.
            liikutaRobotteja(robotit, sokkelo);
         } else {
            // Jos liikuttavassa suunnassa ei ole k�yt�v�-paikkaa, tulostetaan
            // "kops!" ja liikutetaan robotteja ja vaihdetaan m�nkij�n suuntaa.
            monkkari.suunta(suunta);
            System.out.println("Kops!");
            liikutaRobotteja(robotit, sokkelo);
         }
      }

      /*
       * ==================================================================
       * LIIKUTAAN ETEL��N
       *
       * Jos parametrina saatu suunta on 'e'
       */
      else if (suunta == 'e') {
         // Tarkistetaan onko liikuttavassa suunnassa sallittu paikka, eli
         // k�yt�v�.
         // Jos suunta on 'e', riviIndeksi+1 jotta p��st��n m�nkij�n alapuolella
         // olevaan sokkelon paikkaan k�siksi.
         if (((Juuri) sokkelo[riviIndeksi + 1][sarakeIndeksi]).sallittu() == true) {

            // Lis�t��n m�nkij� sokkelossa pohjoiseen
            ((Kaytava) sokkelo[riviIndeksi + 1][sarakeIndeksi]).lisaa(monkkari);

            // Annetaan m�nkij�lle uuden paikan indeksit ja uusi suunta
            monkkari.rivi(riviIndeksi + 1);
            monkkari.sarake(sarakeIndeksi);
            monkkari.suunta(suunta);

            // Haetaan sokkelosta lista jossa sijaitsee vanhan m�nkij�n olio
            OmaLista vanhaMonkkari = ((Kaytava) sokkelo[riviIndeksi][sarakeIndeksi]).lista();

            // Alustetaan indeksi jonka avulla k�yd��n listan alkioita l�pi.
            int indeksi = 0;
            // K�yd��n listaa l�pi kunnes solmun seuraava == null
            while (vanhaMonkkari.alkio(indeksi) != null) {
               // Jos listalta l�ytyy m�nkij�, poistetaan m�nkij�.
               if (vanhaMonkkari.alkio(indeksi).getClass() == Monkija.class) {
                  vanhaMonkkari.poista(indeksi);
               }
            }

            // Suoritetaan tarkistaAlkutilanne-metodi joka katsoo onko m�nkij�n
            // uudessa paikassa
            // esineit� jotka ker�t��n, tai robotteja joiden kanssa tapellaan.
            tarkistaAlkutilanne();

            // P�ivitet��n m�nkij�n esineiden paikkojen tiedot m�nkij�n paikan
            // tiedoiksi
            for (int i = 0; i < monkkari.inventoori().koko(); i++) {
               ((Juuri) monkkari.inventoori().alkio(i)).rivi(riviIndeksi + 1);
               ((Juuri) monkkari.inventoori().alkio(i)).sarake(sarakeIndeksi);
            }

            // Tarkistetaan onko sokkelossa viel� esineit�.
            // Jos onkoEsineita-metodin paluuarvo on false, lopetetaan
            // liiku-metodin l�pik�ynti koska peli on jo l�p�isty.
            if (onkoEsineita() == false)
               return;

            // Liikutetaan robotteja m�nkij�n liikuttelun j�lkeen.
            liikutaRobotteja(robotit, sokkelo);
         } else {
            // Jos liikuttavassa suunnassa ei ole k�yt�v�-paikkaa, tulostetaan
            // "kops!" ja liikutetaan robotteja ja vaihdetaan m�nkij�n suuntaa.
            monkkari.suunta(suunta);
            System.out.println("Kops!");
            liikutaRobotteja(robotit, sokkelo);
         }
      }
   }

   /**
    * Metodi joka p�ivitt�� robotit-listan tiedot Automaatti-luokan
    * paivitaPaikat metodissa. Lopuksi p�ivitet��n robottien uudet tiedot
    * sokkeloon ja poistetaan vanhat.
    *
    * @param robotit sis�lt�v� OmaLista
    * @param sokkelo jossa robotteja liikutetaan
    */
   public void liikutaRobotteja(OmaLista robotit, Object[][] sokkelo) {

      // Luodaan apuRoboLista, johon asetetaan robotit listan tiedot
      OmaLista apuRoboLista = robotit;

      // Kutsutaan Automaatti-luokan paivitaPaikat metodia
      Automaatti.paivitaPaikat(robotit, sokkelo);

      /*
       * P�ivitet��n sokkelon paikkoja. K�yd��n jokainen robotit-listan alkio
       * l�pi.
       */
      // Alustetaan indeksi = 0 ja t�m�n avulla k�yd��n listaa l�pi
      int indeksi = 0;
      while (indeksi < robotit.koko()) {

         // Alustetaan indeksin mukainen robotti robon.
         Robotti robo = (Robotti) robotit.alkio(indeksi);

         // Haetaan robon rivi ja saraketieto.
         int apuRivi = robo.rivi();
         int apuSarake = robo.sarake();

         // Lis�t��n sokkeloon robo apuRivin ja apuSarakkeen
         // osoittamille paikoille.
         ((Kaytava) sokkelo[apuRivi][apuSarake]).lisaa(robo);

         /*
          * Poistetaan vanha robon paikka sokkelosta. Koska robotti liikuu aina
          * johonkin suuntaan, voidaan robon suunnan mukaisella arvolla katsoa
          * mist� sokkelon paikasta t�m� on uudelle paikalleen tullut.
          */

         /*
          * ===============================================================
          * POHJOINEN
          *
          * Jos robon suunta on 'p', on robo tullut sokkelosta [rivi+1][sarake]
          * paikalta
          */
         if (robo.suunta() == 'p') {
            // Haetaan sokkelosta apuRivin + 1 ja apuSarakkeen osoittaman
            // k�yt�v�paikan lista
            OmaLista apuLista = ((Kaytava) sokkelo[apuRivi + 1][apuSarake]).lista();
            // Alustetaan paikka = 0 ja t�m�n avulla k�yd��n apuListaa l�pi ja
            // etsit��n
            // vanha robotti
            int paikka = 0;
            // K�yd��n apuListan alkioita l�pi kunnes l�ytyy robotti joka
            // vastaa
            // apuRoboListan indeksin vastaavaa robottia.
            while (paikka < apuLista.koko()) {
               // Jos listan paikalla oleva robotti == apuRoboListan robotti,
               // poistetaan t�m�
               if (apuLista.alkio(paikka) == apuRoboLista.alkio(indeksi)) {
                  // Poistetaan vanha robotti sokkelosta
                  apuLista.poista(paikka);
               }
               paikka++;
            }
         }

         /*
          * =============================================================== IT�
          *
          * Jos robon suunta on 'i', on robo tullut sokkelosta [rivi][sarake-1]
          * paikalta
          */
         else if (robo.suunta() == 'i') {
            // Haetaan sokkelosta apuRivin ja apuSarakkeen - 1 osoittaman
            // k�yt�v�paikan lista
            OmaLista apuLista = ((Kaytava) sokkelo[apuRivi][apuSarake - 1]).lista();
            // Alustetaan paikka = 0 ja t�m�n avulla k�yd��n apuListaa l�pi ja
            // etsit��n
            // vanha robotti
            int paikka = 0;
            // K�yd��n apuListan alkioita l�pi kunnes l�ytyy robotti joka
            // vastaa
            // apuRoboListan indeksin vastaavaa robottia.
            while (paikka < apuLista.koko()) {
               // Jos listan paikalla oleva robotti == apuRoboListan robotti,
               // poistetaan t�m�
               if (apuLista.alkio(paikka) == apuRoboLista.alkio(indeksi)) {
                  // Poistetaan vanha robotti sokkelosta
                  apuLista.poista(paikka);
               }
               paikka++;
            }
         }

         /*
          * ===============================================================
          * L�NSI
          *
          * Jos robon suunta on 'l', on robo tullut sokkelosta [rivi][sarake+1]
          * paikalta
          */
         else if (robo.suunta() == 'l') {
            // Haetaan sokkelosta apuRivin ja apuSarakkeen + 1 osoittaman
            // k�yt�v�paikan lista
            OmaLista apuLista = ((Kaytava) sokkelo[apuRivi][apuSarake + 1]).lista();
            // Alustetaan paikka = 0 ja t�m�n avulla k�yd��n apuListaa l�pi ja
            // etsit��n
            // vanha robotti
            int paikka = 0;
            // K�yd��n apuListan alkioita l�pi kunnes l�ytyy robotti joka
            // vastaa
            // apuRoboListan indeksin vastaavaa robottia.
            while (paikka < apuLista.koko()) {
               // Jos listan paikalla oleva robotti == apuRoboListan robotti,
               // poistetaan t�m�
               if (apuLista.alkio(paikka) == apuRoboLista.alkio(indeksi)) {
                  // Poistetaan vanha robotti sokkelosta
                  apuLista.poista(paikka);
               }
               paikka++;
            }
         }

         /*
          * ===============================================================
          * ETEL�
          *
          * Jos robon suunta on 'e', on robo tullut sokkelosta [rivi+1][sarake]
          * paikalta
          */
         else if (robo.suunta() == 'e') {
            // Haetaan sokkelosta apuRivin - 1 ja apuSarakkeen osoittaman
            // k�yt�v�paikan lista
            OmaLista apuLista = ((Kaytava) sokkelo[apuRivi - 1][apuSarake]).lista();
            // Alustetaan paikka = 0 ja t�m�n avulla k�yd��n apuListaa l�pi ja
            // etsit��n
            // vanha robotti
            int paikka = 0;
            // K�yd��n apuListan alkioita l�pi kunnes l�ytyy robotti joka
            // vastaa
            // apuRoboListan indeksin vastaavaa robottia.
            while (paikka < apuLista.koko()) {
               // Jos listan paikalla oleva robotti == apuRoboListan robotti,
               // poistetaan t�m�
               if (apuLista.alkio(paikka) == apuRoboLista.alkio(indeksi)) {
                  // Poistetaan vanha robotti sokkelosta
                  apuLista.poista(paikka);
               }
               paikka++;
            }
         }
         indeksi++;
      }
      tarkistaAlkutilanne();
   }

   /**
    * Metodi joka suorittaa m�nkij�n ja robottien v�lisen tappelun. Vertaillaan
    * m�nkij�n ja robottien energioita compareTo metodilla. Jos m�nkij�n
    * energia suurempi tai yht�suuri kuin robotin energia, tulostetaan "Voitto!", 
    * poistetaan robotti sokkelosta ja poistetaan m�nkij�n energiasta robotin energian erotus.
    * Paluuarvona m�nkij�n selviytymisest� true. Jos m�nkij�n energia pienempi kuin 
    * robotin energia, tulostetaan "Tappio!" ja paluuarvona m�nkij�n selviytymisest�
    * false.
    *
    * @return totuusarvo siit� s�ilyyk� m�nkij� elossa.
    */
   public boolean tappelu() {
      // Haetaan sokkelossa m�nkij�n sis�lt�v� k�yt�v�paikan lista
      OmaLista tappeluKaytava = monkijallinenLista();

      // K�yd��n tappeluKaytava-listaa l�pi. Haetaan tappeluKaytava-listalta
      // robotti,
      // joka asetetaan vastustaja-muuttujaan. Muutetaan robotin tyyppi
      // Sisalloksi,
      // jotta pystyt��n vertailemaan energioita CompareTo-metodilla.
      for (int i = 0; i < tappeluKaytava.koko(); i++) {
         if (tappeluKaytava.alkio(i).getClass() == Robotti.class) {
            Sisalto vastustaja = (Robotti) tappeluKaytava.alkio(i);

            /*
             * Vertaillaan m�nkij�n ja robotin energioita
             */

            // Jos m�nkij�ll� on suurempi energia kuin robotilla, m�nkij�
            // voittaa tappelun. Tulostetaan voitosta ilmoittaminen,
            // poistetaan robotin energian m��r� m�nkij�n energiasta.
            // Lopuksi poistetaan robotti k�yt�v�paikan sek�
            // robotit listalta.
            if (monkkari.compareTo(vastustaja) == 1) {

               // Tulostetaan voiton parahdus
               System.out.println("Voitto!");
               // Poistetaan m�nkij�lt� robotin energia
               monkkari.poistaEnergiaa(vastustaja.energia());
               // Poistetaan k�yt�v�paikan listalta kukistettu robotti
               tappeluKaytava.poista(i);
               // Poistetaan robotit-listalta vastustaja-robottia vastaava robotti
               int apuIndeksi = 0;
               while (apuIndeksi < robotit.koko()) {
                  // Jos vastustaja on sama kuin robotit listan apuIndeksin mukainen
                  // alkio
                  if (vastustaja == robotit.alkio(apuIndeksi)) {
                     // Poistetaan robotti
                     robotit.poista(apuIndeksi);
                     // Lopetetaan while-lauseen k�ynti ettei poisteta vahingossa toista
                     // robottia jolla on mahdollisesti samat tiedot.
                     break;
                  }
                  apuIndeksi++;
               }
               // Paluuarvona m�nkij�n selviytyminen, eli true.
               return true;
            }
            // Jos m�nkij�n energia on == kuin robotin enerkija, katsotaan
            // m�nkij�n voittaneen tappelussa. Tulostetaan voitosta
            // ilmoittaminen,
            // poistetaan robotin m��r�m� energia m�nkij�lt�.
            // Lopuksi poistetaan robotti k�yt�v�paikan listalta.
            else if (monkkari.compareTo(vastustaja) == 0) {
               System.out.println("Voitto!");
               monkkari.poistaEnergiaa(vastustaja.energia());
               // Poistetaan k�yt�v�paikan listalta kukistettu robotti
               tappeluKaytava.poista(i);
               // Poistetaan robotit-listalta vastustaja-robottia vastaava robotti
               int apuIndeksi = 0;
               while (apuIndeksi < robotit.koko()) {
                  // Jos vastustaja on sama kuin robotit listan apuIndeksin mukainen
                  // alkio
                  if (vastustaja == robotit.alkio(apuIndeksi)) {
                     // Poistetaan robotti
                     robotit.poista(apuIndeksi);
                     // Lopetetaan while-lauseen k�ynti ettei poisteta vahingossa toista
                     // robottia jolla on mahdollisesti samat tiedot.
                     break;
                  }
                  apuIndeksi++;
               }
               
               // Paluuarvona m�nkij�n selviytyminen, eli true.
               return true;
            }

            // Jos m�nkij�ll� on pienempi energia kuin robotilla, m�nkij�
            // h�vi�� tapelun ja t�ten koko pelin. Energian muunnoksia ei
            // tarvitse tehd�, mutta poistetaan m�nkij� k�yt�v�paikan
            // listalta. Tulostetaan "Tappio!" ja paluuarvona false.
            else if (monkkari.compareTo(vastustaja) == -1) {
               System.out.println("Tappio!");
               // Asetetaan m�nkij�n elossa oleminen falseksi.
               monkkari.onkoElossa(false);
               // Haetaan m�nkij�n paikka listalta
               for (int j = 0; j < tappeluKaytava.koko(); j++) {
                  if (tappeluKaytava.alkio(j).getClass() == Monkija.class) {
                     // Poistetaan m�nkij� listalta.
                     tappeluKaytava.poista(j);
                  }
               }
               // Paluuarvona m�nkij�n h�vi�, eli false.
               return false;
            }
         }
      }
      // Jos listalta ei kuitenkaan jostain syyst� l�ydy robotteja, ei synny
      // my�sk��n
      // tappeluja. T�ll�in m�nkij� s�ilyy oletettavasti elossa.
      return true;
   }

   /**
    * Metodi joka tarkistaa onko sokkelossa esineit�. Jos sokkelo on tyhj�,
    * paluuarvona true. Jos sokkelosta l�ytyy esineit�, paluuarvo on false
    *
    * @return true jos sokkelossa on esineit�
    * @return false jos sokkelossa ei ole esineit�
    */
   public boolean onkoEsineita() {

      // Alustetaan onkoEsineita aluksi false. Jos sokkelosta l�ytyy esineit�,
      // asetetaan arvoksi true.
      boolean onkoEsineita = false;

      // K�yd��n kaikki sokkelon paikat l�pi.
      for (int i = 0; i < sokkelo.length; i++) {
         for (int j = 0; j < sokkelo[i].length; j++) {
            if (sokkelo[i][j].getClass() == Kaytava.class) {
               // Tarkistetaan onko k�yt�v�-olion lista ei-tyhj�
               if (((Kaytava) sokkelo[i][j]).onkoTyhja() != true) {
                  // Luodaan apuLista jonka avulla k�yd��n listan olioita l�pi
                  OmaLista apuLista = ((Kaytava) sokkelo[i][j]).lista();
                  // Alustetaan indeksi jonka avulla k�yd��n listan alkioita
                  // l�pi.
                  int indeksi = 0;
                  // K�yd��n listaa l�pi kunnes solmun seuraava == null
                  while (apuLista.alkio(indeksi) != null) {
                     // Jos listalta l�ytyy esine, asetetaan onkoEsineita
                     // arvoksi true.
                     if (apuLista.alkio(indeksi).getClass() == Esine.class) {
                        onkoEsineita = true;
                     }
                     indeksi++;
                  }
               }
            }
         }
      }
      return onkoEsineita;
   }

   /**
    * Metodi jolla m�nkij�n ei tarvitse liikkua sokkelossa, mutta joka liikuttaa
    * robotteja sokkelossa ja tulostaa lopuksi sokkelon.
    */
   public void odota() {
      // Liikutetaan robotteja
      liikutaRobotteja(robotit, sokkelo);
      // Tulostetaan sokkelo
      tulostaSokkelo();

   }

   /**
    * Metodi joka tallentaa sokkelon tiedot tekstitiedostoon.
    * Koittaa napata IOExceptionin jos tiedoston k�sittelyss� virheit�.
    */
   public void tallenna() {

      // asetetaan tiedoston nimi
      final String TIEDNIMI = "sokkelo.txt";

      try {
         // Luodaan tiedosto-olio
         File tiedosto = new File(TIEDNIMI);

         // Luodaan tulostusvirta ja liitet��n se tiedostoon
         FileOutputStream tulostusvirta = new FileOutputStream(tiedosto);

         // Luodaan virtaan kirjoittaja
         PrintWriter kirjoittaja = new PrintWriter(tulostusvirta, true);

         /*
          * Kirjoitetaan tiedoston alkuun siemen, rivien ja sarakkeiden
          * lukum��r�
          */

         // Luodaan siemenest�, rivi- ja sarakelukum��r�st� String tyyppinen
         // apumuuttujat.
         // Tarkistetaan my�s ovatko n�iden mitat < 4. Jos on, lis�t��n n�iden
         // per��n
         // tyhji� merkkej�.
         String apuSiemen = Integer.toString(siemenLuku);
         while (apuSiemen.length() < 4) {
            apuSiemen += " ";
         }
         String apuRivi = Integer.toString(riviLkm);
         while (apuRivi.length() < 4) {
            apuRivi += " ";
         }
         String apuSarake = Integer.toString(sarakeLkm);
         while (apuSarake.length() < 4) {
            apuSarake += " ";
         }

         // Kirjoitetaan apuSiemen, apuRivi ja apuSarake tiedoston alkuun
         // erottaen tiedot
         // erottimella
         kirjoittaja.println(apuSiemen + "|" + apuRivi + "|" + apuSarake + "|");

         /*
          * K�yd��n sokkeloa l�pi ja kirjoitetaan sokkelon tiedot tiedostoon
          */
         for (int i = 0; i < sokkelo.length; i++) {
            for (int j = 0; j < sokkelo[i].length; j++) {

               // Jos sokkelon osa on sein�, kutsutaan t�m�n toString
               if (sokkelo[i][j].getClass() == Seina.class) {
                  // Kirjoitetaan sein�n tieto
                  kirjoittaja.println(sokkelo[i][j].toString());
               }

               // Jos sokkelon osa on k�yt�v�, kutsutaan t�m�n toString
               // ja katsotaan onko k�yt�v�ll� esineit�. Jos on, tulostetaan
               // n�iden toString.
               else if (sokkelo[i][j].getClass() == Kaytava.class) {
                  // Kirjoitetaan k�yt�v�paikan tieto
                  kirjoittaja.println(sokkelo[i][j].toString());

                  // Tarkistetaan onko k�yt�v�-olion lista ei-tyhj�
                  if (((Kaytava) sokkelo[i][j]).onkoTyhja() != true) {
                     // Luodaan apuLista jonka avulla k�yd��n listan olioita
                     // l�pi
                     OmaLista apuLista = ((Kaytava) sokkelo[i][j]).lista();
                     // Alustetaan indeksi jonka avulla k�yd��n listan alkioita
                     // l�pi.
                     int indeksi = 0;
                     // K�yd��n listaa l�pi kunnes solmun seuraava == null
                     while (apuLista.alkio(indeksi) != null) {
                        // Kirjoitetaan k�yt�v�paikalla olevan olion tiedot
                        kirjoittaja.println(apuLista.alkio(indeksi).toString());

                        // Jos k�yt�v�paikan olio on m�nkij�, tarkistetaan onko
                        // t�ll�
                        // inventoorissa esineit�. Jos on, tulostetaan my�s
                        // t�m�n
                        // ker��mien esineiden tiedot
                        if (apuLista.alkio(indeksi).getClass() == Monkija.class) {
                           // Tarkistetaan onko m�nkij�ll� esineit�
                           // inventoorissa
                           if (monkkari.onkoInventooriTyhja() != true) {

                              // Alustetaan inventooriIndeksi jonka avulla
                              // k�yd��n inventoorin
                              // alkioita l�pi
                              int inventooriIndeksi = 0;
                              // luodaan apuInventoori jonka avulla k�yd��n
                              // inventoorin esineit� l�pi
                              OmaLista apuInventoori = monkkari.inventoori();
                              // K�yd��n inventooria l�pi kunnes solmun seuraava
                              // == null
                              while (apuInventoori.alkio(inventooriIndeksi) != null) {
                                 // Kirjoitetaan m�nkij�n inventoorissa olevan
                                 // esineen toString tiedostoon
                                 kirjoittaja.println(apuInventoori.alkio(inventooriIndeksi).toString());
                                 inventooriIndeksi++;
                              }
                           }
                        }
                        indeksi++;
                     }
                  }
               }
            }
         }
         // Suljetaan tiedosto
         kirjoittaja.close();
      } catch (IOException e) {
         System.out.println(e);
      }
   }
}