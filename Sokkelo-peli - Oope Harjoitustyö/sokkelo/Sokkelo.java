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
 * Sokkelo-luokka, joka sisältää sokkelolle yhteiset piirtee sekä metodit
 * lataukselle, tallennukselle, inventoinnille, katselemiselle, liikkumiselle,
 * odottamiselle ja muuntamiselle.
 * <p>
 * @author Vili-Veikko Rauhala (Rauhala.Vili.V@student.uta.fi) 
 * Opiskelijanumero: 421839
 * <p>
 * Tietojenkäsittelytiede OOPE Harjoiustyö
 */

public class Sokkelo {

   /*
    * =====================================================================
    * Attribuutit
    */

   /** Sokkelossa oleva mönkijä */
   private Monkija monkkari;

   /** Sokkelon siemeluvun arvo */
   private static int siemenLuku;

   /** Sokkelon rivilukumäärä */
   private static int riviLkm;

   /** Sokkelon sarakelukumäärä */
   private static int sarakeLkm;

   /** Object-luokkainen sokkelo */
   public Object[][] sokkelo;

   /** Robottien tiedot sisältävä lista */
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
    * Rivilukumäärän palauttava metodi.
    *
    * @return riviLkm.
    */
   public static int riviLkm() {
      return riviLkm;
   }

   /**
    * Sarakelukumäärän palauttava metodi.
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
    * Sokkelo.txt tiedoston lataava metodi. Käy aluksi tekstitiedoston
    * ensimmäisen rivin läpi, asettaen tekstitiedoston rivin tiedot
    * siemenlukuun, rivilkm sekä sarakelkm.
    * <p>
    * Ensimmäisen rivin jälkeen käydään seuraavia rivejä läpi. Metodi etenee sen
    * mukaan mikä tiedoston ensimmäisen rivin arvo on. Asettaa indeksien arvojen
    * kohdalle tekstitiedoston mukaisen olion.
    * <p>
    * Suorittaa lopuksi tarkistaAlkutilanne-metodin, joka kerää mönkijän kanssa
    * samassa paikassa olevat esineet pelin aluksi ja taistelee mahdollisesti
    * samassa paikassa olevien robottien kanssa.
    */
   public void lataa() {

      // Try-catch koska java vaatii virheiden välttämiseksi.
      // Mahdollisia virheitä tiedoston puuttuminen tai lukuvirhe.
      try {

         // Luodaan syotevirta-olio sokkelo.txt tiedostosta
         FileInputStream syotevirta = new FileInputStream("sokkelo.txt");

         // Luodaan lukija-olio syotevirran lukemiseen
         InputStreamReader lukija = new InputStreamReader(syotevirta);

         // Luodaan tehokkaampi puskuroituLukija-olio sokkelo.txt tiedoston
         // käsittelyyn.
         BufferedReader puskuroituLukija = new BufferedReader(lukija);

         /*
          * Käsitellään tiedoston ensimmäinen rivi, josta saadaan siemen,
          * rivilkm ja sarakelkm. Rivilukumäärästä ja sarakelukumäärästä luodaan
          * sokkelo.
          */

         // Asetetaan tiedoston ensimmäinen rivi ekaRivi-atribuuttiin
         String ekaRivi = puskuroituLukija.readLine();

         // Erotetaan rivin osat toisistaan '|' merkin kohdalta
         // ja asetetaan ne ekanOsat-taulukon arvoiksi
         String[] ekanOsat = ekaRivi.split("[|]");

         // Poistetaan välilyönnit ekaRivin osista
         for (int i = 0; i < ekanOsat.length; i++)
            ekanOsat[i] = ekanOsat[i].trim();

         // Asetetaan ekaOsat-taulun arvot siemen, rivilkm sekä sarakelkm
         // attribuutteihin.
         siemenLuku = Integer.parseInt(ekanOsat[0]);
         riviLkm = Integer.parseInt(ekanOsat[1]);
         sarakeLkm = Integer.parseInt(ekanOsat[2]);

         // Luodaan rivilkm ja sarakelkm kokoinen sokkelo
         sokkelo = new Object[riviLkm][sarakeLkm];

         /*
          * Käsitellään tiedoston loput riveistä
          */

         // Aluksi käydään robotit-listaa läpi. Jos lista ei ole tyhjä,
         // poistetaan kaikki tämän alkiot.
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

            // Poistetaan välilyönnit rivien osista
            for (int i = 0; i < osat.length; i++) {
               osat[i] = osat[i].trim();
            }

            /*
             * Taulukon ensimmäisen sarake määrää, mikä sokkelon osa on
             * kyseessä. Asetetaan sokkeloon kyseisen osan mukainen olio tokan
             * (rivi) ja kolmannen (sarake) sarakkeen arvojen kohdalle.
             *
             * Koska Sokkelo.txt tiedostossa on käytävä paikka aina ennen
             * käytävällä mahdollisesti olevaa esinettä, pitää verrata onko
             * sokkelon kyseisellä käytäväpaikalla jo mahdollisesti sisältöä.
             * Jos ei, tehdään uusi kaytavaSisalto. Jos käytävällä on jo
             * sisältöä, asetetaan uusi solmu listan perään.
             */

            // Jos osat taulun ensimmäinen sarake on "Seina", asetetaan
            // sokkeloon
            // Seina-olio osat-taulun osoittamien arvojen kohdalle.
            if (osat[0].equals("Seina")) {
               sokkelo[Integer.parseInt(osat[1])][Integer.parseInt(osat[2])] = new Seina(Integer.parseInt(osat[1]),
                     Integer.parseInt(osat[2]));
            }

            // Jos osat taulun ensimmäinen sarake on "Kaytava", asetetaan
            // sokkeloon
            // Kaytava-olio osat-taulun osoittamien arvojen kohdalle.
            else if (osat[0].equals("Kaytava")) {
               sokkelo[Integer.parseInt(osat[1])][Integer.parseInt(osat[2])] = new Kaytava(Integer.parseInt(osat[1]),
                     Integer.parseInt(osat[2]));
            }

            /*
             * Jos osat taulun ensimmäinen sarake on "Monkija", asetetaan
             * sokkeloon Monkija-olio osat-taulun osoittamien arvojen kohdalle
             * jos paikka on sallittu.
             */

            else if (osat[0].equals("Monkija")) {

               // Luodaan Monkija-olio jolle annetaan tekstitiedoston määräämä
               // paikka, energie sekä suunta
               monkkari = new Monkija(Integer.parseInt(osat[1]), Integer.parseInt(osat[2]), Integer.parseInt(osat[3]),
                     osat[4].charAt(0));

               // Jos paikka on sallittu, asetetaan mönkijä-olio käytäväpaikan
               // listalle.
               // Jos paikka ei ole sallittu, tulostetaan virheilmoitus
               if (monkkari.sallittu()) {

                  // Lisätään Kaytava-olion omaan listaan monkkari.
                  ((Kaytava) sokkelo[Integer.parseInt(osat[1])][Integer.parseInt(osat[2])]).lisaa(monkkari);
               } else
                  System.out.println("Sokkelossa ilmeni virhe: monkkarille oli määritelty virheellinen paikka.");
            }

            /*
             * Jos osat taulun ensimmäinen sarake on "Robotti", asetetaan
             * sokkeloon Robotti-olio osat-taulun osoittamien arvojen kohdalle
             * jos paikka on sallittu.
             */

            else if (osat[0].equals("Robotti")) {

               // Luodaan Robotti-olio jolle annetaan tekstitiedoston määräämä
               // paikka, energia sekä suunta
               Robotti robo = new Robotti(Integer.parseInt(osat[1]), Integer.parseInt(osat[2]),
                     Integer.parseInt(osat[3]), osat[4].charAt(0));

               // Jos paikka on sallittu, asetetaan robotti-olio käytäväpaikan
               // listalle.
               // Jos paikka ei ole sallittu, tulostetaan virheilmoitus.
               if (robo.sallittu()) {

                  // Lisätään Kaytava-olion omaan listaan robo.
                  ((Kaytava) sokkelo[Integer.parseInt(osat[1])][Integer.parseInt(osat[2])]).lisaa(robo);

                  // Lisätään robotit-listalle robo. Robotteja lisätään
                  // listalle sitä mukaan kuin niitä
                  // sokkelosta ilmenee
                  robotit.lisaaLoppuun(robo);
                  
               } else
                  System.out.println("Sokkelossa ilmeni virhe: robotille oli määritelty virheellinen paikka.");
            }

            /*
             * Jos osat taulun ensimmäinen sarake on "Esine", asetetaan
             * käytäväpaikan listalle Esine-olio osat-taulun osoittamien arvojen
             * kohdalle.
             */

            else if (osat[0].equals("Esine")) {

               // Luodaan Esine-olio jolle annetaan tekstitiedoston määräämä
               // paikka sekä energia.
               Esine tuote = new Esine(Integer.parseInt(osat[1]), Integer.parseInt(osat[2]), Integer.parseInt(osat[3]));

               // Jos paikka on sallittu, asetetaan esine-olio sokkeloon.
               // Jos paikka ei ole sallittu, tulostetaan virheilmoitus.
               if (tuote.sallittu()) {

                  // Lisätään Kaytava-olion omaan listaan tuote
                  ((Kaytava) sokkelo[Integer.parseInt(osat[1])][Integer.parseInt(osat[2])]).lisaa(tuote);
               } else
                  System.out.println("Sokkelossa ilmeni virhe: esineelle oli määritelty virheellinen paikka.");
            }
         }
         puskuroituLukija.close();

         // Kutsutaan Autommaatti-luokan alusta-metodia
         Automaatti.alusta(siemenLuku);

         // Kutsutaan metodia joka tarkistaa pelin alussa käytäväpaikan arvot
         // jossa mönkiä sijaitsee. Jos samassa paikassa on esineitä, mönkijä
         // kerää
         // esineet automaattisesti inventooriin. Jos paikassa on robotti,
         // syntyy
         // taistelu.
         tarkistaAlkutilanne();
      }

      // Virheilmoitus jos tiedostoa ei löydy
      catch (FileNotFoundException e) {
         System.out.println("Tiedosto josta sokkelo luetaan puuttuu.");
      }

      // Virheilmoitus lukuvirheelle
      catch (IOException e) {
         System.out.println("Virhe tekstitiedostossa.");
      }
   }

   /**
    * Metodi joka tarkistaa sokkelon tilanteen. Jos sokkelossa on mönkijän
    * kanssa samassa paikkaa esineitä, mönkijä kerää esineet inventooriinsa. Jos
    * mönkijän kanssa on samassa paikassa robotti, syntyy tappelu. Lopuksi
    * metodi tarkistaa onko sokkelossa enään esineitä. Jos ei, ohjelma
    * lopetetaan.
    */
   public void tarkistaAlkutilanne() {

      /*
       * Käydään listan arvoja läpi. Jos listalta löytyy esine, siirretään tämä
       * mönkijän listalle. Jos listalta löytyy robotti, alkaa mönkijän ja
       * robotin välinen taistelu.
       */
      for (int i = 0; i < sokkelo.length; i++) {
         for (int j = 0; j < sokkelo[i].length; j++) {
            // Jos arvojen kohdalla sokkelossa on käytävä-olio
            if (sokkelo[i][j].getClass() == Kaytava.class) {
               // Tarkistetaan onko käytävä-olion lista ei-tyhjä
               if (((Kaytava) sokkelo[i][j]).onkoTyhja() != true) {
                  // Luodaan kaytavanPaikanLista jonka avulla käydään listan
                  // olioita läpi
                  OmaLista kaytavaPaikanLista = ((Kaytava) sokkelo[i][j]).lista();
                  // Alustetaan indeksi jonka avulla käydään kaytavanPaikanLista
                  // alkioita läpi.
                  int indeksi = 0;
                  // Käydään apuListaa läpi kunnes solmun seuraava == null
                  while (kaytavaPaikanLista.alkio(indeksi) != null) {
                     // Jos listalta löytyy alkio mönkijä, käydään kyseisen
                     // listan
                     // kaikki alkiot läpi
                     if (kaytavaPaikanLista.alkio(indeksi).getClass() == Monkija.class) {

                        /*
                         * Käydään listan arvoja läpi. Jos listalta löytyy
                         * esine, siirretään tämä mönkijän listalle. Jos
                         * listalta löytyy robotti, alkaa mönkijän ja robotin
                         * välinen taistelu.
                         */
                        // Alustetaan listanIndeksi jonka avulla käydään
                        // kaytavanPaikanLista
                        // alkioita läpi uudelleen.
                        int listanIndeksi = 0;
                        // Käydään listaa läpi kunnes solmun seuraava == null
                        while (kaytavaPaikanLista.alkio(listanIndeksi) != null) {

                           // Jos listalta löytyvä alkio on esine, siirretään se
                           // mönkijän
                           // inventooriin
                           if (kaytavaPaikanLista.alkio(listanIndeksi).getClass() == Esine.class) {

                              // Suoritetaan mönkijän listalle lisäysmetodi,
                              // joka saa parametrina
                              // käytäväpaikan listan poista-metodin paluuarvon.
                              // Poista-metodille
                              // annetaan parametrina listanIndeksi.
                              monkkari.lisaa(kaytavaPaikanLista.poista(listanIndeksi));

                              // Tarkistetaan poistettiinko käytäväpaikan
                              // listalta olio ennen mönkijää.
                              // jos listaIndeksi < indeksi, poistetaan myös
                              // indeksin pituudesta 1 jotta
                              // indeksin arvo pysyy mönkijän kohdalla.
                              if (listanIndeksi < indeksi) {
                                 indeksi--;
                              }
                              // Lopuksi listanIndeksi miinustetaan
                              // yhdellä, jolloin listanIndeksi ei pääse
                              // hyppäämään yhden listan
                              // arvoista ylitse.
                              listanIndeksi--;
                           }

                           // Jos listalta löytyvä alkio on robotti, syttyy
                           // mönkijän
                           // ja robotin välinen tappelu.
                           else if (kaytavaPaikanLista.alkio(listanIndeksi).getClass() == Robotti.class) {
                              // Kutsutaan metodia joka vertailee mönkijän ja
                              // robottien energioita
                              // compareTo-metodilla. Antaa paluuarvona true jos
                              // mönkijä voittaa tappelun,
                              // falsen jos mönkijä hävisi tappelun.
                              boolean monkijaElossa = tappelu();

                              // Jos mönkijä selviytyy tappelusta, tarkistetaan
                              // poistettiinko käytäväpaikan
                              // listalta olio ennen mönkijää. Jos listaIndeksi
                              // < indeksi, poistetaan myös
                              // indeksin pituudesta 1 jotta indeksin arvo pysyy
                              // mönkijän kohdalla.
                              if (monkijaElossa == true) {
                                 if (listanIndeksi < indeksi) {
                                    indeksi--;
                                 }
                                 // Lopuksi listanIndeksi miinustetaan
                                 // yhdellä, jolloin listanIndeksi ei pääse
                                 // hyppäämään yhden listan
                                 // arvoista ylitse.
                                 listanIndeksi--;
                              }

                              // Jos mönkijä häviää tappelun, lopetetaan metodin
                              // läpikäynti.
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
    * Metodi joka tarkistaa onko mönkijä vielä elossa. Katsoo mönkijän tiedoista
    * mönkijän tiedon tämän elämisen tilasta.
    *
    * @return onko mönkijä elossa.
    */
   public boolean onkoElossa() {
      return monkkari.onkoElossa();

   }

   /**
    * Metodi joka käy sokkeloa läpi kunnes löytää sokkelosta käytäväpaikan,
    * jonka listalla on mönkijä. Metodi kutsuu mönkijän metodia, joka tulostaa
    * mönkijän inventoorin tiedot.
    */
   public void inventoi() {
      // Käydään läpi sokkeloa kunnes löytyy käytävä-paikka.
      for (int i = 0; i < sokkelo.length; i++) {
         for (int j = 0; j < sokkelo[i].length; j++) {
            // Jos arvojen kohdalla sokkelossa on käytävä-olio
            if (sokkelo[i][j].getClass() == Kaytava.class) {
               // Tarkistetaan onko käytävä-olion lista ei-tyhjä
               if (((Kaytava) sokkelo[i][j]).onkoTyhja() != true) {
                  // Luodaan apuLista jonka avulla käydään listan olioita läpi
                  OmaLista apuLista = ((Kaytava) sokkelo[i][j]).lista();

                  // Alustetaan indeksi jonka avulla käydään listan alkioita
                  // läpi.
                  int indeksi = 0;
                  // Käydään listaa läpi kunnes solmun seuraava == null
                  while (apuLista.alkio(indeksi) != null) {
                     // Jos listalta löytyy mönkijä, kutsutaan mönkijän
                     // toString- metodia ja mönkijän listan tulostavaa metodia.
                     if (apuLista.alkio(indeksi).getClass() == Monkija.class) {

                        // Tulostetaan mönkijän toString()-metodin paluuarvo
                        System.out.println(monkkari.toString());

                        // Jos mönkijän inventoori on epätyhjä, tulostetaan
                        // inventoorin sisältö
                        if (monkkari.onkoInventooriTyhja() != true) {
                           // Kutsutaan mönkijän tulostaInventoori()-metodia.
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
    * Metodi joka tulostaa sokkelon. Käy sokkelon jokaisen paikan läpi. Jos
    * sokkelon osa on seinä, tulostetaan seinää kuvaava symbooli. Jos sokkelon
    * osa on käytävä, ja käytäväpaikka on tyhjä, tulostetaan käytävän symboli.
    * Jos käytäväpaikka ei ole tyhjä, kutsutaan apuKaytavan kautta
    * Kaytava-luokan listanMerkki()-metodia, jonka avulla saadaan tulostetuksi
    * oikea merkki sokkeloon.
    */
   public void tulostaSokkelo() {
      try {
         // Käydään sokkelon rivejä läpi
         for (Object[] rivi : sokkelo) {
            // Käydään rivin sarakkeita läpi
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
                * tyhjä, tulostetaan kaytavan oma merkki. Jos käytäväpaikalla on
                * jokin olio listalla, tulostetaan tämän merkki
                */
               else if (alkio.getClass() == Kaytava.class) {

                  // Asetetaan apuKaytava-olioon Kaytava-luokaksi muutettu
                  // Object-luokkainen alkio.
                  Kaytava apuKaytava = (Kaytava) alkio;

                  /*
                   * Jos kaytavapaikka on tyhjä, tulostetaan kaytavan oma
                   * merkki. Jos käytäväpaikalla on jokin olio listalla,
                   * tulostetaan tämän merkki
                   */
                  if (apuKaytava.onkoTyhja() == true) {
                     System.out.print(apuKaytava.merkki());
                  }

                  /*
                   * Jos paikan lista ei ole tyhjä, tarkistetaan mitä listalla
                   * on ja tulostetaan joko mönkiän, robotin tai esineen
                   * merkki.
                   */
                  else {
                     System.out.print(apuKaytava.listanMerkki());
                  }
               }
            }
            // Kun yksi riveistä on käyty läpi, vaihdetaan riviä
            System.out.println("");
         }
      } catch (NullPointerException e) {
         System.out.println("Taulukon kaikkia paikkoja ei ole luotu.");
      } catch (Exception e) {
         System.out.println("Taulukon tulostamisessa ilmeni ongelma.");
      }
   }

   /**
    * Metodi joka tulostaa mönkijän viereisen paikan tiedot. Viereinen paikka
    * joka tulostetaan määräytyy käyttäjän antaman syöteen mukaan.
    *
    * @param suunta käyttäjän antama suunta.
    */
   public void katsoPaikka(char suunta) {
      // Arvot joiden avulla navigoidaan mönkijän paikka, ja näiden avulla
      // haetaan oikeaa sokkelon paikkaa sokkelosta joka tulostetaan.
      int apuRivi = 0;
      int apuSarake = 0;

      /*
       * Käydään aluksi sokkeloa läpi ja etsitään sokkelosta käytäväpaikkaa
       * jossa mönkijä sijaitsee. Jos käytäväpaikan lista != tyhjä, käydään
       * listan arvoja läpi. Jos listalla on mönkijä, tulostetaan mönkijän
       * suunnan mukainen viereinen paikka.
       */
      for (int i = 0; i < sokkelo.length; i++) {
         for (int j = 0; j < sokkelo[i].length; j++) {
            // Jos arvojen kohdalla sokkelossa on käytävä-olio
            if (sokkelo[i][j].getClass() == Kaytava.class) {
               // Tarkistetaan onko käytävä-olion lista ei-tyhjä
               if (((Kaytava) sokkelo[i][j]).onkoTyhja() != true) {
                  // Luodaan apuLista jonka avulla käydään listan olioita läpi
                  OmaLista apuLista = ((Kaytava) sokkelo[i][j]).lista();
                  // Alustetaan indeksi jonka avulla käydään listan alkioita
                  // läpi.
                  int indeksi = 0;
                  // Käydään listaa läpi kunnes solmun seuraava == null
                  while (apuLista.alkio(indeksi) != null) {
                     // Jos listalta löytyy mönkijä, asetetaan mönkijän paikkan
                     // rivin ja sarakkeen arvo apuRiviin ja apuSarakkeeseen
                     if (apuLista.alkio(indeksi).getClass() == Monkija.class) {
                        apuRivi = ((Juuri) apuLista.alkio(indeksi)).rivi();
                        apuSarake = ((Juuri) apuLista.alkio(indeksi)).sarake();

                        /*
                         * apuRivin ja apuSarakkeen avulla navigoidaan
                         * sokkelossa sen mukaan, mikä suunnan arvo on. Jos
                         * suunta == 'p', tulostetaan mönkijän yläpuolella
                         * olevan sokkelon paikan tiedot. Jos suunta == 'i',
                         * tulostetaan mönkijän oikeallapuolella olevan sokkelon
                         * paikan tiedot. Jos suunta == 'e', tulostetaan
                         * mönkijän alapuolella olevan sokkelon paikan tiedot.
                         * Jos suunta == 'l', tulostetaan mönkijän
                         * vasemmallapuolella olevan sokkelon paikan tiedot.
                         */

                        // Jos suunta on 'p', apuRivi - 1.
                        if (suunta == 'p') {
                           // Kutsutaan sokkelopaikan olion toString-metodia
                           System.out.println(sokkelo[apuRivi - 1][apuSarake].toString());
                           // Jos sokkelopaikka on käytävä, käydään
                           // käytäväpaikan listaa läpi
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
                           // Jos sokkelopaikka on käytävä, käydään
                           // käytäväpaikan listaa läpi
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
                           // Jos sokkelopaikka on käytävä, käydään
                           // käytäväpaikan listaa läpi
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
                           // Jos sokkelopaikka on käytävä, käydään
                           // käytäväpaikan listaa läpi
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
    * Metodi joka muuntaa mönkijän listassa olevia esineitä parametrina saadun
    * lukumäärän verran energiaksi mönkijälle. Mönkijä saa esineen sisältämän
    * energian verran lisää energiaa.
    *
    * @param muunnettavaLkm sisältää muunnettavien esineiden lukumäärä, käytäväpaikan lista
    * jossa mönkijä sijaitsee, indeksi jossa mönkijä sijaitsee kaytavanPaikanListalla sekä 
    * mönkijän inventoori.
    * @param monkijanInventoori lista jossa mönkijän esineet
    */
   public void muunnaEsine(int muunnettavaLkm, OmaLista monkijanInventoori) {
      // Siirretään esineiden energioita mönkijälle muunnettavaLkm verran.
      for (int i = 0; i < muunnettavaLkm; i++) {
         // Haetaan muunnettava esine mönkijän inventoorin
         // ensimmäiseltä paikalta. Poistetaan samalla esine inventoorista.
         Esine muunnettavaTuote = (Esine) monkijanInventoori.poistaAlusta();

         // Haetaan muunnettavan esineen energia
         int energia = muunnettavaTuote.energia();

         // Lisätään muunnettavan tuotteen energia mönkijän energiaan.
         monkkari.lisaaEnergiaa(energia);
      }
   }

   /**
    * Metodi joka hakee sokkelosta käytäväpaikan, jossa sisältää mönkijä.
    * Suhteellisen turha metodi tällä hetkellä koska useimmissa metodeissa
    * tarvitaan myös listan indeksiä, jossa mönkijä oli. Hyödynnetään kuitenkin
    * joissain tilanteissa jossa tarvitaan vain sokkelon listaa jossa on
    * mönkijä.
    *
    * @return mönkijän sisältävä lista.
    */
   public OmaLista monkijallinenLista() {
      for (int i = 0; i < sokkelo.length; i++) {
         for (int j = 0; j < sokkelo[i].length; j++) {
            // Jos arvojen kohdalla sokkelossa on käytävä-olio
            if (sokkelo[i][j].getClass() == Kaytava.class) {
               // Tarkistetaan onko käytävä-olion lista ei-tyhjä
               if (((Kaytava) sokkelo[i][j]).onkoTyhja() != true) {
                  // Luodaan apuLista jonka avulla käydään listan olioita läpi
                  OmaLista apuLista = ((Kaytava) sokkelo[i][j]).lista();
                  // Alustetaan indeksi jonka avulla käydään listan alkioita
                  // läpi.
                  int indeksi = 0;
                  // Käydään listaa läpi kunnes solmun seuraava == null
                  while (apuLista.alkio(indeksi) != null) {
                     // Jos listalta löytyy mönkijä, palautetaan kyseinen lista.
                     if (apuLista.alkio(indeksi).getClass() == Monkija.class) {
                        return apuLista;
                     }
                     indeksi++;
                  }
               }
            }
         }
      }
      // Jos sokkelon miltään käytävältä ei löydy mönkijää,
      // paluuarvona null.
      return null;
   }

   /**
    * Metodi joka liikuttaa mönkijää sokkelossa käyttäjän syötteen mukaiseen
    * suuntaan. Jos suunnassa johon mönkijää siirretään on seinä, tulostetaan
    * "kops". Jos suunnassa johon siirrytään on käytävä, siirtyy mönkijä
    * kyseiselle sokkelon paikalle.
    * <p>
    * Jos paikalla johon siirryttiin on esine, siirretään se mönkijän
    * inventooriin. Jos paikalla on robotti, syntyy mönkijän ja robotin
    * välinen tappelu. Liikkumisen jälkeen suoritetaan metodi joka liikuttaa
    * robotteja sokkelossa.
    *
    * @param suunta johon mönkijään siirretän.
    */
   public void liiku(char suunta) {
      // Mönkijän paikan rivi- ja sarakeindeksi
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
         // käytävä.
         // Jos suunta on 'p', riviIndeksi-1 jotta päästään mönkijän yläpuolella
         // olevaan sokkelon paikkaan käsiksi.
         if (((Juuri) sokkelo[riviIndeksi - 1][sarakeIndeksi]).sallittu() == true) {

            // Lisätään mönkijä sokkelossa pohjoiseen
            ((Kaytava) sokkelo[riviIndeksi - 1][sarakeIndeksi]).lisaa(monkkari);

            // Annetaan mönkijälle uuden paikan indeksit ja uusi suunta
            monkkari.rivi(riviIndeksi - 1);
            monkkari.sarake(sarakeIndeksi);
            monkkari.suunta(suunta);

            // Haetaan sokkelosta lista jossa sijaitsee vanhan mönkijän olio
            OmaLista vanhaMonkkari = ((Kaytava) sokkelo[riviIndeksi][sarakeIndeksi]).lista();

            // Alustetaan indeksi jonka avulla käydään listan alkioita läpi.
            int indeksi = 0;
            // Käydään listaa läpi kunnes solmun seuraava == null
            while (vanhaMonkkari.alkio(indeksi) != null) {
               // Jos listalta löytyy mönkijä, poistetaan mönkijä.
               if (vanhaMonkkari.alkio(indeksi).getClass() == Monkija.class) {
                  vanhaMonkkari.poista(indeksi);
               }
            }

            // Suoritetaan tarkistaAlkutilanne-metodi joka katsoo onko mönkijän
            // uudessa paikassa
            // esineitä jotka kerätään, tai robotteja joiden kanssa tapellaan.
            tarkistaAlkutilanne();

            // Päivitetään mönkijän esineiden paikkojen tiedot mönkijän paikan
            // tiedoiksi
            for (int i = 0; i < monkkari.inventoori().koko(); i++) {
               ((Juuri) monkkari.inventoori().alkio(i)).rivi(riviIndeksi - 1);
               ((Juuri) monkkari.inventoori().alkio(i)).sarake(sarakeIndeksi);
            }

            // Tarkistetaan onko sokkelossa vielä esineitä.
            // Jos onkoEsineita-metodin paluuarvo on false, lopetetaan
            // liiku-metodin läpikäynti koska peli on jo läpäisty.
            if (onkoEsineita() == false)
               return;

            // Liikutetaan robotteja mönkijän liikuttelun jälkeen.
            liikutaRobotteja(robotit, sokkelo);
         } else {
            // Jos liikuttavassa suunnassa ei ole käytävä-paikkaa, tulostetaan
            // "kops!" ja liikutetaan robotteja ja vaihdetaan mönkijän suuntaa.
            monkkari.suunta(suunta);
            System.out.println("Kops!");
            liikutaRobotteja(robotit, sokkelo);
         }
      }

      /*
       * ==================================================================
       * LIIKUTAAN ITÄÄN
       *
       * Jos parametrina saatu suunta on 'i'.
       */
      else if (suunta == 'i') {
         // Tarkistetaan onko liikuttavassa suunnassa sallittu paikka, eli
         // käytävä.
         // Jos suunta on 'i', sarakeIndeksi+1 jotta päästään oikealla puolella
         // olevaan sokkelon paikkaan käsiksi.
         if (((Juuri) sokkelo[riviIndeksi][sarakeIndeksi + 1]).sallittu() == true) {

            // Lisätään mönkijä sokkelossa itään
            ((Kaytava) sokkelo[riviIndeksi][sarakeIndeksi + 1]).lisaa(monkkari);

            // Annetaan mönkijälle uuden paikan indeksit ja uusi suunta
            monkkari.rivi(riviIndeksi);
            monkkari.sarake(sarakeIndeksi + 1);
            monkkari.suunta(suunta);

            // Haetaan sokkelosta lista jossa sijaitsee vanhan mönkijän olio
            OmaLista vanhaMonkkari = ((Kaytava) sokkelo[riviIndeksi][sarakeIndeksi]).lista();

            // Alustetaan indeksi jonka avulla käydään listan alkioita läpi.
            int indeksi = 0;
            // Käydään listaa läpi kunnes solmun seuraava == null
            while (vanhaMonkkari.alkio(indeksi) != null) {
               // Jos listalta löytyy mönkijä, poistetaan mönkijä.
               if (vanhaMonkkari.alkio(indeksi).getClass() == Monkija.class) {
                  vanhaMonkkari.poista(indeksi);
               }
            }

            // Suoritetaan tarkistaAlkutilanne-metodi joka katsoo onko mönkijän
            // uudessa paikassa
            // esineitä jotka kerätään, tai robotteja joiden kanssa tapellaan.
            tarkistaAlkutilanne();

            // Päivitetään mönkijän esineiden paikkojen tiedot mönkijän paikan
            // tiedoiksi
            for (int i = 0; i < monkkari.inventoori().koko(); i++) {
               ((Juuri) monkkari.inventoori().alkio(i)).rivi(riviIndeksi);
               ((Juuri) monkkari.inventoori().alkio(i)).sarake(sarakeIndeksi + 1);
            }

            // Tarkistetaan onko sokkelossa vielä esineitä.
            // Jos onkoEsineita-metodin paluuarvo on false, lopetetaan
            // liiku-metodin läpikäynti koska peli on jo läpäisty.
            if (onkoEsineita() == false)
               return;

            // Liikutetaan robotteja mönkijän liikuttelun jälkeen.
            liikutaRobotteja(robotit, sokkelo);
         } else {
            // Jos liikuttavassa suunnassa ei ole käytävä-paikkaa, tulostetaan
            // "kops!" ja liikutetaan robotteja ja vaihdetaan mönkijän suuntaa.
            monkkari.suunta(suunta);
            System.out.println("Kops!");
            liikutaRobotteja(robotit, sokkelo);
         }
      }

      /*
       * ==================================================================
       * LIIKUTAAN LÄNTEEN
       *
       * Jos parametrina saatu suunta on 'l'.
       */
      else if (suunta == 'l') {
         // Tarkistetaan onko liikuttavassa suunnassa sallittu paikka, eli
         // käytävä.
         // Jos suunta on 'l', sarakeIndeksi-1 jotta päästään vasemmalle
         // puolella
         // olevaan sokkelon paikkaan käsiksi.
         if (((Juuri) sokkelo[riviIndeksi][sarakeIndeksi - 1]).sallittu() == true) {

            // Lisätään mönkijä sokkelossa itään
            ((Kaytava) sokkelo[riviIndeksi][sarakeIndeksi - 1]).lisaa(monkkari);

            // Annetaan mönkijälle uuden paikan indeksit ja uusi suunta
            monkkari.rivi(riviIndeksi);
            monkkari.sarake(sarakeIndeksi - 1);
            monkkari.suunta(suunta);

            // Haetaan sokkelosta lista jossa sijaitsee vanhan mönkijän olio
            OmaLista vanhaMonkkari = ((Kaytava) sokkelo[riviIndeksi][sarakeIndeksi]).lista();

            // Alustetaan indeksi jonka avulla käydään listan alkioita läpi.
            int indeksi = 0;
            // Käydään listaa läpi kunnes solmun seuraava == null
            while (vanhaMonkkari.alkio(indeksi) != null) {
               // Jos listalta löytyy mönkijä, poistetaan mönkijä.
               if (vanhaMonkkari.alkio(indeksi).getClass() == Monkija.class) {
                  vanhaMonkkari.poista(indeksi);
               }
            }

            // Suoritetaan tarkistaAlkutilanne-metodi joka katsoo onko mönkijän
            // uudessa paikassa
            // esineitä jotka kerätään, tai robotteja joiden kanssa tapellaan.
            tarkistaAlkutilanne();

            // Päivitetään mönkijän esineiden paikkojen tiedot mönkijän paikan
            // tiedoiksi
            for (int i = 0; i < monkkari.inventoori().koko(); i++) {
               ((Juuri) monkkari.inventoori().alkio(i)).rivi(riviIndeksi);
               ((Juuri) monkkari.inventoori().alkio(i)).sarake(sarakeIndeksi - 1);
            }

            // Tarkistetaan onko sokkelossa vielä esineitä.
            // Jos onkoEsineita-metodin paluuarvo on false, lopetetaan
            // liiku-metodin läpikäynti koska peli on jo läpäisty.
            if (onkoEsineita() == false)
               return;

            // Liikutetaan robotteja mönkijän liikuttelun jälkeen.
            liikutaRobotteja(robotit, sokkelo);
         } else {
            // Jos liikuttavassa suunnassa ei ole käytävä-paikkaa, tulostetaan
            // "kops!" ja liikutetaan robotteja ja vaihdetaan mönkijän suuntaa.
            monkkari.suunta(suunta);
            System.out.println("Kops!");
            liikutaRobotteja(robotit, sokkelo);
         }
      }

      /*
       * ==================================================================
       * LIIKUTAAN ETELÄÄN
       *
       * Jos parametrina saatu suunta on 'e'
       */
      else if (suunta == 'e') {
         // Tarkistetaan onko liikuttavassa suunnassa sallittu paikka, eli
         // käytävä.
         // Jos suunta on 'e', riviIndeksi+1 jotta päästään mönkijän alapuolella
         // olevaan sokkelon paikkaan käsiksi.
         if (((Juuri) sokkelo[riviIndeksi + 1][sarakeIndeksi]).sallittu() == true) {

            // Lisätään mönkijä sokkelossa pohjoiseen
            ((Kaytava) sokkelo[riviIndeksi + 1][sarakeIndeksi]).lisaa(monkkari);

            // Annetaan mönkijälle uuden paikan indeksit ja uusi suunta
            monkkari.rivi(riviIndeksi + 1);
            monkkari.sarake(sarakeIndeksi);
            monkkari.suunta(suunta);

            // Haetaan sokkelosta lista jossa sijaitsee vanhan mönkijän olio
            OmaLista vanhaMonkkari = ((Kaytava) sokkelo[riviIndeksi][sarakeIndeksi]).lista();

            // Alustetaan indeksi jonka avulla käydään listan alkioita läpi.
            int indeksi = 0;
            // Käydään listaa läpi kunnes solmun seuraava == null
            while (vanhaMonkkari.alkio(indeksi) != null) {
               // Jos listalta löytyy mönkijä, poistetaan mönkijä.
               if (vanhaMonkkari.alkio(indeksi).getClass() == Monkija.class) {
                  vanhaMonkkari.poista(indeksi);
               }
            }

            // Suoritetaan tarkistaAlkutilanne-metodi joka katsoo onko mönkijän
            // uudessa paikassa
            // esineitä jotka kerätään, tai robotteja joiden kanssa tapellaan.
            tarkistaAlkutilanne();

            // Päivitetään mönkijän esineiden paikkojen tiedot mönkijän paikan
            // tiedoiksi
            for (int i = 0; i < monkkari.inventoori().koko(); i++) {
               ((Juuri) monkkari.inventoori().alkio(i)).rivi(riviIndeksi + 1);
               ((Juuri) monkkari.inventoori().alkio(i)).sarake(sarakeIndeksi);
            }

            // Tarkistetaan onko sokkelossa vielä esineitä.
            // Jos onkoEsineita-metodin paluuarvo on false, lopetetaan
            // liiku-metodin läpikäynti koska peli on jo läpäisty.
            if (onkoEsineita() == false)
               return;

            // Liikutetaan robotteja mönkijän liikuttelun jälkeen.
            liikutaRobotteja(robotit, sokkelo);
         } else {
            // Jos liikuttavassa suunnassa ei ole käytävä-paikkaa, tulostetaan
            // "kops!" ja liikutetaan robotteja ja vaihdetaan mönkijän suuntaa.
            monkkari.suunta(suunta);
            System.out.println("Kops!");
            liikutaRobotteja(robotit, sokkelo);
         }
      }
   }

   /**
    * Metodi joka päivittää robotit-listan tiedot Automaatti-luokan
    * paivitaPaikat metodissa. Lopuksi päivitetään robottien uudet tiedot
    * sokkeloon ja poistetaan vanhat.
    *
    * @param robotit sisältävä OmaLista
    * @param sokkelo jossa robotteja liikutetaan
    */
   public void liikutaRobotteja(OmaLista robotit, Object[][] sokkelo) {

      // Luodaan apuRoboLista, johon asetetaan robotit listan tiedot
      OmaLista apuRoboLista = robotit;

      // Kutsutaan Automaatti-luokan paivitaPaikat metodia
      Automaatti.paivitaPaikat(robotit, sokkelo);

      /*
       * Päivitetään sokkelon paikkoja. Käydään jokainen robotit-listan alkio
       * läpi.
       */
      // Alustetaan indeksi = 0 ja tämän avulla käydään listaa läpi
      int indeksi = 0;
      while (indeksi < robotit.koko()) {

         // Alustetaan indeksin mukainen robotti robon.
         Robotti robo = (Robotti) robotit.alkio(indeksi);

         // Haetaan robon rivi ja saraketieto.
         int apuRivi = robo.rivi();
         int apuSarake = robo.sarake();

         // Lisätään sokkeloon robo apuRivin ja apuSarakkeen
         // osoittamille paikoille.
         ((Kaytava) sokkelo[apuRivi][apuSarake]).lisaa(robo);

         /*
          * Poistetaan vanha robon paikka sokkelosta. Koska robotti liikuu aina
          * johonkin suuntaan, voidaan robon suunnan mukaisella arvolla katsoa
          * mistä sokkelon paikasta tämä on uudelle paikalleen tullut.
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
            // käytäväpaikan lista
            OmaLista apuLista = ((Kaytava) sokkelo[apuRivi + 1][apuSarake]).lista();
            // Alustetaan paikka = 0 ja tämän avulla käydään apuListaa läpi ja
            // etsitään
            // vanha robotti
            int paikka = 0;
            // Käydään apuListan alkioita läpi kunnes löytyy robotti joka
            // vastaa
            // apuRoboListan indeksin vastaavaa robottia.
            while (paikka < apuLista.koko()) {
               // Jos listan paikalla oleva robotti == apuRoboListan robotti,
               // poistetaan tämä
               if (apuLista.alkio(paikka) == apuRoboLista.alkio(indeksi)) {
                  // Poistetaan vanha robotti sokkelosta
                  apuLista.poista(paikka);
               }
               paikka++;
            }
         }

         /*
          * =============================================================== ITÄ
          *
          * Jos robon suunta on 'i', on robo tullut sokkelosta [rivi][sarake-1]
          * paikalta
          */
         else if (robo.suunta() == 'i') {
            // Haetaan sokkelosta apuRivin ja apuSarakkeen - 1 osoittaman
            // käytäväpaikan lista
            OmaLista apuLista = ((Kaytava) sokkelo[apuRivi][apuSarake - 1]).lista();
            // Alustetaan paikka = 0 ja tämän avulla käydään apuListaa läpi ja
            // etsitään
            // vanha robotti
            int paikka = 0;
            // Käydään apuListan alkioita läpi kunnes löytyy robotti joka
            // vastaa
            // apuRoboListan indeksin vastaavaa robottia.
            while (paikka < apuLista.koko()) {
               // Jos listan paikalla oleva robotti == apuRoboListan robotti,
               // poistetaan tämä
               if (apuLista.alkio(paikka) == apuRoboLista.alkio(indeksi)) {
                  // Poistetaan vanha robotti sokkelosta
                  apuLista.poista(paikka);
               }
               paikka++;
            }
         }

         /*
          * ===============================================================
          * LÄNSI
          *
          * Jos robon suunta on 'l', on robo tullut sokkelosta [rivi][sarake+1]
          * paikalta
          */
         else if (robo.suunta() == 'l') {
            // Haetaan sokkelosta apuRivin ja apuSarakkeen + 1 osoittaman
            // käytäväpaikan lista
            OmaLista apuLista = ((Kaytava) sokkelo[apuRivi][apuSarake + 1]).lista();
            // Alustetaan paikka = 0 ja tämän avulla käydään apuListaa läpi ja
            // etsitään
            // vanha robotti
            int paikka = 0;
            // Käydään apuListan alkioita läpi kunnes löytyy robotti joka
            // vastaa
            // apuRoboListan indeksin vastaavaa robottia.
            while (paikka < apuLista.koko()) {
               // Jos listan paikalla oleva robotti == apuRoboListan robotti,
               // poistetaan tämä
               if (apuLista.alkio(paikka) == apuRoboLista.alkio(indeksi)) {
                  // Poistetaan vanha robotti sokkelosta
                  apuLista.poista(paikka);
               }
               paikka++;
            }
         }

         /*
          * ===============================================================
          * ETELÄ
          *
          * Jos robon suunta on 'e', on robo tullut sokkelosta [rivi+1][sarake]
          * paikalta
          */
         else if (robo.suunta() == 'e') {
            // Haetaan sokkelosta apuRivin - 1 ja apuSarakkeen osoittaman
            // käytäväpaikan lista
            OmaLista apuLista = ((Kaytava) sokkelo[apuRivi - 1][apuSarake]).lista();
            // Alustetaan paikka = 0 ja tämän avulla käydään apuListaa läpi ja
            // etsitään
            // vanha robotti
            int paikka = 0;
            // Käydään apuListan alkioita läpi kunnes löytyy robotti joka
            // vastaa
            // apuRoboListan indeksin vastaavaa robottia.
            while (paikka < apuLista.koko()) {
               // Jos listan paikalla oleva robotti == apuRoboListan robotti,
               // poistetaan tämä
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
    * Metodi joka suorittaa mönkijän ja robottien välisen tappelun. Vertaillaan
    * mönkijän ja robottien energioita compareTo metodilla. Jos mönkijän
    * energia suurempi tai yhtäsuuri kuin robotin energia, tulostetaan "Voitto!", 
    * poistetaan robotti sokkelosta ja poistetaan mönkijän energiasta robotin energian erotus.
    * Paluuarvona mönkijän selviytymisestä true. Jos mönkijän energia pienempi kuin 
    * robotin energia, tulostetaan "Tappio!" ja paluuarvona mönkijän selviytymisestä
    * false.
    *
    * @return totuusarvo siitä säilyykö mönkijä elossa.
    */
   public boolean tappelu() {
      // Haetaan sokkelossa mönkijän sisältävä käytäväpaikan lista
      OmaLista tappeluKaytava = monkijallinenLista();

      // Käydään tappeluKaytava-listaa läpi. Haetaan tappeluKaytava-listalta
      // robotti,
      // joka asetetaan vastustaja-muuttujaan. Muutetaan robotin tyyppi
      // Sisalloksi,
      // jotta pystytään vertailemaan energioita CompareTo-metodilla.
      for (int i = 0; i < tappeluKaytava.koko(); i++) {
         if (tappeluKaytava.alkio(i).getClass() == Robotti.class) {
            Sisalto vastustaja = (Robotti) tappeluKaytava.alkio(i);

            /*
             * Vertaillaan mönkijän ja robotin energioita
             */

            // Jos mönkijällä on suurempi energia kuin robotilla, mönkijä
            // voittaa tappelun. Tulostetaan voitosta ilmoittaminen,
            // poistetaan robotin energian määrä mönkijän energiasta.
            // Lopuksi poistetaan robotti käytäväpaikan sekä
            // robotit listalta.
            if (monkkari.compareTo(vastustaja) == 1) {

               // Tulostetaan voiton parahdus
               System.out.println("Voitto!");
               // Poistetaan mönkijältä robotin energia
               monkkari.poistaEnergiaa(vastustaja.energia());
               // Poistetaan käytäväpaikan listalta kukistettu robotti
               tappeluKaytava.poista(i);
               // Poistetaan robotit-listalta vastustaja-robottia vastaava robotti
               int apuIndeksi = 0;
               while (apuIndeksi < robotit.koko()) {
                  // Jos vastustaja on sama kuin robotit listan apuIndeksin mukainen
                  // alkio
                  if (vastustaja == robotit.alkio(apuIndeksi)) {
                     // Poistetaan robotti
                     robotit.poista(apuIndeksi);
                     // Lopetetaan while-lauseen käynti ettei poisteta vahingossa toista
                     // robottia jolla on mahdollisesti samat tiedot.
                     break;
                  }
                  apuIndeksi++;
               }
               // Paluuarvona mönkijän selviytyminen, eli true.
               return true;
            }
            // Jos mönkijän energia on == kuin robotin enerkija, katsotaan
            // mönkijän voittaneen tappelussa. Tulostetaan voitosta
            // ilmoittaminen,
            // poistetaan robotin määrämä energia mönkijältä.
            // Lopuksi poistetaan robotti käytäväpaikan listalta.
            else if (monkkari.compareTo(vastustaja) == 0) {
               System.out.println("Voitto!");
               monkkari.poistaEnergiaa(vastustaja.energia());
               // Poistetaan käytäväpaikan listalta kukistettu robotti
               tappeluKaytava.poista(i);
               // Poistetaan robotit-listalta vastustaja-robottia vastaava robotti
               int apuIndeksi = 0;
               while (apuIndeksi < robotit.koko()) {
                  // Jos vastustaja on sama kuin robotit listan apuIndeksin mukainen
                  // alkio
                  if (vastustaja == robotit.alkio(apuIndeksi)) {
                     // Poistetaan robotti
                     robotit.poista(apuIndeksi);
                     // Lopetetaan while-lauseen käynti ettei poisteta vahingossa toista
                     // robottia jolla on mahdollisesti samat tiedot.
                     break;
                  }
                  apuIndeksi++;
               }
               
               // Paluuarvona mönkijän selviytyminen, eli true.
               return true;
            }

            // Jos mönkijällä on pienempi energia kuin robotilla, mönkijä
            // häviää tapelun ja täten koko pelin. Energian muunnoksia ei
            // tarvitse tehdä, mutta poistetaan mönkijä käytäväpaikan
            // listalta. Tulostetaan "Tappio!" ja paluuarvona false.
            else if (monkkari.compareTo(vastustaja) == -1) {
               System.out.println("Tappio!");
               // Asetetaan mönkijän elossa oleminen falseksi.
               monkkari.onkoElossa(false);
               // Haetaan mönkijän paikka listalta
               for (int j = 0; j < tappeluKaytava.koko(); j++) {
                  if (tappeluKaytava.alkio(j).getClass() == Monkija.class) {
                     // Poistetaan mönkijä listalta.
                     tappeluKaytava.poista(j);
                  }
               }
               // Paluuarvona mönkijän häviö, eli false.
               return false;
            }
         }
      }
      // Jos listalta ei kuitenkaan jostain syystä löydy robotteja, ei synny
      // myöskään
      // tappeluja. Tällöin mönkijä säilyy oletettavasti elossa.
      return true;
   }

   /**
    * Metodi joka tarkistaa onko sokkelossa esineitä. Jos sokkelo on tyhjä,
    * paluuarvona true. Jos sokkelosta löytyy esineitä, paluuarvo on false
    *
    * @return true jos sokkelossa on esineitä
    * @return false jos sokkelossa ei ole esineitä
    */
   public boolean onkoEsineita() {

      // Alustetaan onkoEsineita aluksi false. Jos sokkelosta löytyy esineitä,
      // asetetaan arvoksi true.
      boolean onkoEsineita = false;

      // Käydään kaikki sokkelon paikat läpi.
      for (int i = 0; i < sokkelo.length; i++) {
         for (int j = 0; j < sokkelo[i].length; j++) {
            if (sokkelo[i][j].getClass() == Kaytava.class) {
               // Tarkistetaan onko käytävä-olion lista ei-tyhjä
               if (((Kaytava) sokkelo[i][j]).onkoTyhja() != true) {
                  // Luodaan apuLista jonka avulla käydään listan olioita läpi
                  OmaLista apuLista = ((Kaytava) sokkelo[i][j]).lista();
                  // Alustetaan indeksi jonka avulla käydään listan alkioita
                  // läpi.
                  int indeksi = 0;
                  // Käydään listaa läpi kunnes solmun seuraava == null
                  while (apuLista.alkio(indeksi) != null) {
                     // Jos listalta löytyy esine, asetetaan onkoEsineita
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
    * Metodi jolla mönkijän ei tarvitse liikkua sokkelossa, mutta joka liikuttaa
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
    * Koittaa napata IOExceptionin jos tiedoston käsittelyssä virheitä.
    */
   public void tallenna() {

      // asetetaan tiedoston nimi
      final String TIEDNIMI = "sokkelo.txt";

      try {
         // Luodaan tiedosto-olio
         File tiedosto = new File(TIEDNIMI);

         // Luodaan tulostusvirta ja liitetään se tiedostoon
         FileOutputStream tulostusvirta = new FileOutputStream(tiedosto);

         // Luodaan virtaan kirjoittaja
         PrintWriter kirjoittaja = new PrintWriter(tulostusvirta, true);

         /*
          * Kirjoitetaan tiedoston alkuun siemen, rivien ja sarakkeiden
          * lukumäärä
          */

         // Luodaan siemenestä, rivi- ja sarakelukumäärästä String tyyppinen
         // apumuuttujat.
         // Tarkistetaan myös ovatko näiden mitat < 4. Jos on, lisätään näiden
         // perään
         // tyhjiä merkkejä.
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
          * Käydään sokkeloa läpi ja kirjoitetaan sokkelon tiedot tiedostoon
          */
         for (int i = 0; i < sokkelo.length; i++) {
            for (int j = 0; j < sokkelo[i].length; j++) {

               // Jos sokkelon osa on seinä, kutsutaan tämän toString
               if (sokkelo[i][j].getClass() == Seina.class) {
                  // Kirjoitetaan seinän tieto
                  kirjoittaja.println(sokkelo[i][j].toString());
               }

               // Jos sokkelon osa on käytävä, kutsutaan tämän toString
               // ja katsotaan onko käytävällä esineitä. Jos on, tulostetaan
               // näiden toString.
               else if (sokkelo[i][j].getClass() == Kaytava.class) {
                  // Kirjoitetaan käytäväpaikan tieto
                  kirjoittaja.println(sokkelo[i][j].toString());

                  // Tarkistetaan onko käytävä-olion lista ei-tyhjä
                  if (((Kaytava) sokkelo[i][j]).onkoTyhja() != true) {
                     // Luodaan apuLista jonka avulla käydään listan olioita
                     // läpi
                     OmaLista apuLista = ((Kaytava) sokkelo[i][j]).lista();
                     // Alustetaan indeksi jonka avulla käydään listan alkioita
                     // läpi.
                     int indeksi = 0;
                     // Käydään listaa läpi kunnes solmun seuraava == null
                     while (apuLista.alkio(indeksi) != null) {
                        // Kirjoitetaan käytäväpaikalla olevan olion tiedot
                        kirjoittaja.println(apuLista.alkio(indeksi).toString());

                        // Jos käytäväpaikan olio on mönkijä, tarkistetaan onko
                        // tällä
                        // inventoorissa esineitä. Jos on, tulostetaan myös
                        // tämän
                        // keräämien esineiden tiedot
                        if (apuLista.alkio(indeksi).getClass() == Monkija.class) {
                           // Tarkistetaan onko mönkijällä esineitä
                           // inventoorissa
                           if (monkkari.onkoInventooriTyhja() != true) {

                              // Alustetaan inventooriIndeksi jonka avulla
                              // käydään inventoorin
                              // alkioita läpi
                              int inventooriIndeksi = 0;
                              // luodaan apuInventoori jonka avulla käydään
                              // inventoorin esineitä läpi
                              OmaLista apuInventoori = monkkari.inventoori();
                              // Käydään inventooria läpi kunnes solmun seuraava
                              // == null
                              while (apuInventoori.alkio(inventooriIndeksi) != null) {
                                 // Kirjoitetaan mönkijän inventoorissa olevan
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