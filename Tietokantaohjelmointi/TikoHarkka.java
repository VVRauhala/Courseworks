import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * @author 28
 * @author Juho Haapala, Haapala.Juho.M@student.uta.fi
 * @author Topi Riikonen, Riikonen.Topi.M@student.uta.fi
 * @author Vili-Veikko Rauhala, Rauhala.Vili.V@student.uta.fi
 */

public class TikoHarkka {
   
   private static int vastauksiaOikein = 0;
   private static String etunimi;
   private static String sukunimi;
   private static String paaaine;
   private static int opiskelijanumero;
   private static int opiskelija_id;
   private static final String AJURI = "org.postgresql.Driver";
   private static final String PROTOKOLLA = "jdbc:postgresql:";
   private static final String PALVELIN = "dbstud2.sis.uta.fi";
   private static final int PORTTI = 5432;
   private static final String TIETOKANTA = "tiko2017db28";  // tähän oma käyttäjätunnus
   private static final String KAYTTAJA = "vr421839";  // tähän oma käyttäjätunnus
   private static final String SALASANA = "salasana";  // tähän tietokannan salasana
   
   public static void main(String args[]) {
      // Vaihe 1: tietokanta-ajurin lataaminen       
      try {
         Class.forName(AJURI);
      } catch (ClassNotFoundException poikkeus) {
         System.out.println("Ajurin lataaminen ei onnistunut. Lopetetaan ohjelman suoritus.");
         return;
      }
 
      // Vaihe 2: yhteyden ottaminen tietokantaan
      Connection con = null;
      Statement stmt = null;
      try {
         con = DriverManager.getConnection(PROTOKOLLA + "//" + PALVELIN + ":" + PORTTI + "/" + TIETOKANTA, KAYTTAJA, SALASANA);
         stmt = con.createStatement();
         stmt.execute("SET ROLE tiko2017db28");
         stmt.execute("SET search_path TO tiko");
         kirjautuminen(con);
      } catch (SQLException poikkeus) {
         // Vaihe 3.2: tähän toiminta mahdollisessa virhetilanteessa
         System.out.println("Tapahtui seuraava virhe: " + poikkeus.getMessage());     
      }
 
      // Vaihe 4: yhteyden sulkeminen 
      finally {
         try {
            if (stmt != null) {
               stmt.close();
            }
            if (con != null) {
               con.close();
            }
         }
         catch (SQLException e) {
            System.out.println("Tapahtui virhe: " + e.getMessage());
         }
      }
   }
   
   /*
    * Metodi joka käy läpi käyttäjän syötteet ja tallentaa tiedot tietokantaan.
    * Jos käyttäjän antamalla opiskelijanumerolla on jo tallennettu tietoa,
    * haetaan taulusta kyuseiselle opiskelijanumerolle vastaava o_id.
    *
    * Jos kyseistä opiskelijanumeroa ei ole vielä tallennettu tauluun, tallennetaan
    * uusi rivi tauluun käyttäjän antamilla syötteillä. Rivi saa o_ideeksi 
    * taulun rivien määrä+1.
    */
   
   public static void kirjautuminen(Connection con) {
      ResultSet rs = null;
      Statement stmt = null;
      try {
         stmt = con.createStatement();
         
         System.out.println("Tervetuloa!");
         Scanner scanner = new Scanner(System.in);
      
         // Etunimi
         int i = 0;
         while (i != 1) {
            System.out.println("Syötä etunimesi:");
            etunimi = scanner.nextLine();
            if (!etunimi.equals("")) {
               i = 1;
            }
            else
               System.out.println("Etunimikenttä ei voi olla tyhjä!");
         }
      
         //Sukunimi
         i = 0;
         while (i != 1) {
            System.out.println("Syötä sukunimesi:");
            sukunimi = scanner.nextLine();
            if (!sukunimi.equals("")) {
               i = 1;
            }
            else
               System.out.println("Sukunimikenttä ei voi olla tyhjä!");
         }
      
         //Pääaine
         i = 0;
         while (i != 1) {
            System.out.println("Syötä pääaineesi:");
            paaaine = scanner.nextLine();
            if (!paaaine.equals("")) {
               i = 1;
            }
            else
               System.out.println("Pääainekenttä ei voi olla tyhjä!");
         }
      
         //Opiskelijanumero
         i = 0;
         while (i != 1) {
            System.out.println("Syötä opiskelijanumerosi:");
            String onum = scanner.nextLine();
            if (!onum.equals("")) {
               try {
                  opiskelijanumero = Integer.parseInt(onum);
                  i = 1;
               }
               catch (NumberFormatException e) {
                  System.out.println("Virheellinen opiskelijanumero!");
               }
            }
            else {
               System.out.println("Opiskelijanumerokenttä ei voi olla tyhjä!");
            }
         }
         
         // Tarkistetaan onko kyseinen opnum jo tietokannassa. 
         // Jos on, jatketaan kyseisellä opiskelianumerolle tehdyllä o_idllä.
         // Jos ei, tehdään uusi o_id kyseiselle opiskelijanumerolle.
         rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM kayttaja WHERE o_numero = " + opiskelijanumero);
         int riveja = 0;
         while (rs.next()){
            riveja = rs.getInt("total");
         }

         // Jos opiskelijanumero on jo tallennettu tietokantaan
         if (riveja == 1) {
            rs = stmt.executeQuery("SELECT o_id FROM kayttaja WHERE o_numero = " + opiskelijanumero);
            while (rs.next()) {
               opiskelija_id = rs.getInt(1);
            }
         }
         // Jos opiskelijanumerolla ei olla tallennettu veilä tietokantaan tietoa
         else {
            rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM kayttaja");
            while (rs.next()) {
               riveja = rs.getInt("total");
            }
            riveja += 1;
            stmt.executeUpdate("INSERT INTO kayttaja " + "VALUES (" + opiskelijanumero + 
                    ", '" + etunimi +"', '" + sukunimi + "', '" + paaaine + "', " + riveja + ")");
            opiskelija_id = riveja;
         }
         sessio(con, opiskelija_id);
      } 
      catch (SQLException poikkeus) {
         System.out.println("Tapahtui seuraava virhe: " + poikkeus.getMessage());     
      }
      // Vaihe 4: yhteyden sulkeminen 
      finally {
         try {
            if (rs != null) {
               rs.close();
            }
            if (stmt != null) {
               stmt.close();
            }
         }
         catch (SQLException e) {
            System.out.println("Tapahtui virhe: " + e.getMessage());
         }
      }
   }
   
   /*
   * Metodi joka luo session. Sessioon tallennetaan avaimena sessio_id joka
   * saadaan tallennettujen sessiorivien määrä + 1. Sessioon tallennetaan myös
   * o_id joka saatiin käyttäjältä, sekä satunnaisesti valittu tehtävälista.
   */
   public static void sessio(Connection con, int o_id) {
      Statement stmt = null;
      ResultSet rs = null;
      int sessio_id = 0;
      int tl_id;
      try  {
         stmt = con.createStatement();
         
         // Haetaan uusi sessio_id. Tämä on sessiotaulun rivien määrä + 1.
         rs = stmt.executeQuery("SELECT COUNT(*) AS rows FROM sessio");
         while (rs.next()) {
            sessio_id = rs.getInt("rows");
         }
         sessio_id++;
         
         // Haetaan satunnanvaraisesti tehtävälistan id tehtävälistataulusta.
         int tehtavalistoja = 0;
         rs = stmt.executeQuery("SELECT COUNT (*) AS rows FROM tehtavalista");
         while (rs.next()) {
            tehtavalistoja = rs.getInt("rows");
         }
         
         Random rand = new Random();
         tl_id = rand.nextInt(tehtavalistoja) + 1;
         
         // Tallennetaan kyseinen sessio sessiotauluun
         stmt.executeUpdate("INSERT INTO sessio VALUES (" + tl_id + 
                    ", " + o_id + ", " + sessio_id +  ")");
         
         // haetaan tietyn tehtävälistan tehtävät.
         int t_id;
         rs = stmt.executeQuery("SELECT tehtava_id FROM kuuluu WHERE tl_id =  '" + tl_id + "'");
         
         for (int tehtavat = 0; tehtavat < 3; tehtavat++) {
            while (rs.next()) {
               t_id = rs.getInt("tehtava_id");
               // Kutsutaan tehtävien suorittavaa metodia. Annetaan parametreina
               //tehtävälistaan kuuluvat tehtävät
               tehtava(con, t_id, sessio_id);
            }
         }
        
         
         System.out.println("Sait tehtävistä oikein " + vastauksiaOikein + "/3.");
         System.out.println("Kiitos käynnistä, tervetuloa uudelleen!");
      
      }
      catch(SQLException poikkeus) {
         // Vaihe 3.2: tähän toiminta mahdollisessa virhetilanteessa
         System.out.println("Tapahtui seuraava virhe: " + poikkeus.getMessage());     
      }
      finally {
         try {
            if (rs != null) {
               rs.close();
            }
            if (stmt != null) {
               stmt.close();
            }
         }
         catch (SQLException e) {
            System.out.println("Tapahtui virhe: " + e.getMessage());
         }
      }
   }
   
   /*
   * Metodi joka käsittelee tehtävän. Kutsutaan alussa metodia joka tulostaa
   * esimerkkikantojen taulut. Tämän jälkeen näytetään käyttäjälle
   * parametrina saadun tehtävä_id:n kuvaus, ja odotetaan käyttäjältä vastausta.
   * Tarkistetaan onko käyttäjän antama vastaus päättynyt puolipisteeseen
   * ja että sulkeita on yhtä monta molempiin suuntiin.
   *
   * Tämän jälkeen verrataan annetun vastauksen tulosta esimerkkivastauksen
   * tuloksiin.
   */
   public static void tehtava(Connection con, int t_id, int s_id) {
      Statement stmt = null;
      Statement stmt2 = null;
      ResultSet rs = null;
      ResultSet rs2 = null;
      ResultSetMetaData rsmd;
      Scanner scanner = new Scanner(System.in);
      String esimerkkiVastausLauseke = null;
      String annettuVastaus = null;
      Timestamp loppu = null;
      Timestamp alku = null;
      boolean samat = true;
      
      try {
         stmt = con.createStatement();
         stmt2 = con.createStatement();
         tulostaTaulut(con);
         int yritys = 1;
         int i = 0;
         while (i < 3) {
            try {
               // Tulostetaan kysymys
               rs = stmt.executeQuery("SELECT kuvaus FROM Tehtava WHERE tehtava_id = " + t_id);
               if (rs.next())
                  System.out.println(rs.getString(1));
               // Tehtävän aloitushetki
               alku = new java.sql.Timestamp(new java.util.Date().getTime());

               boolean hyvaksytty = false;

               while (hyvaksytty == false) {
                  // Luetaan vastaus
                  annettuVastaus = scanner.nextLine();

                  // Tarkistetaan päättyykö vastaus puolipisteeseen
                  // ja että siinä on yhtä monta suljemerkkiä molempiin suuntiin
                  if (annettuVastaus.length() > 0) {
                     if (annettuVastaus.charAt(annettuVastaus.length()-1) == ';') {
                        int pituus = annettuVastaus.length();
                        int sulkeitaOikealle = 0;
                        int sulkeitaVasemmalle = 0;
                        for (int j = 0; j < pituus; j++) {
                           if (annettuVastaus.charAt(j) == '(') {
                              sulkeitaOikealle++;
                           }
                           if (annettuVastaus.charAt(j) == ')') {
                              sulkeitaVasemmalle++;
                           }
                        }
                        if (sulkeitaVasemmalle == sulkeitaOikealle) {
                           hyvaksytty = true;
                        }
                        else {
                           System.out.println("Vastauksessa tulee olla yhtä monta suljetta molempiin suuntiin!");
                        }
                     }
                     else {
                        System.out.println("Vastauksen tulee päättyä puolipisteeseen!");
                     }
                  }
                  else {
                     System.out.println("Yritä edes!");
                  }
               }

               // Tehtävän lopetushetki
               loppu = new java.sql.Timestamp(new java.util.Date().getTime());

               samat = true;

               String t4vastaus = "INSERT INTO opiskelijat VALUES (1234, 'Matti', 'VT');";
               String t5vastaus = "DELETE FROM opiskelijat WHERE nro = 1234;";

               String t4vastaus2 = "insert into opiskelijat values (1234, 'Matti', 'VT');";
               String t5vastaus2 = "delete from opiskelijat where nro = 1234;";

               // Verrataan annetun vastauksen tuottamaa tulosta
               // ja tehtävään tallennetun esimerkkivastauksen tuottamaa tulosta
               // Haetaan esimerkkivastaus

               if (t_id == 4) {
                  esimerkkiVastausLauseke = t4vastaus;
                  if (!t4vastaus.equals(annettuVastaus) && !t4vastaus2.equals(annettuVastaus)) {
                      samat = false;
                  }
               }
               else if (t_id == 5) {
                   esimerkkiVastausLauseke = t5vastaus;
                   if (!t5vastaus.equals(annettuVastaus) && !t5vastaus2.equals(annettuVastaus)) {
                      samat = false;
                   }
               }
               else {
                  String esimerkinHakuLauseke = "SELECT esim_vastaus FROM tehtava WHERE tehtava_id = " + t_id + ";";
                  rs = stmt.executeQuery(esimerkinHakuLauseke);
                  if (rs.next()) {
                     esimerkkiVastausLauseke = rs.getString(1);
                  }
                  rs = stmt.executeQuery(esimerkkiVastausLauseke);
                  rs2 = stmt2.executeQuery(annettuVastaus);


                  rsmd = rs.getMetaData();
                  int rsColumnCount = rsmd.getColumnCount();
                  rsmd = rs2.getMetaData();
                  int rs2ColumnCount = rsmd.getColumnCount();
                  int col = 1;

                  // Jos taulujen sarakkeiden määrä on eri samat = false
                  if (rsColumnCount != rs2ColumnCount) {
                     samat = false;
                  }
                  // Jos sarakkeiden määrä on sama, käydään rivit läpi
                  else {
                     while (rs.next() && rs2.next()) {
                        String rivi1 = rs.getString(col);
                        String rivi2 = rs2.getString(col);

                        // Vertaillaan rivin tietoja
                        if (!rivi1.equals(rivi2)) {
                           samat = false;
                           break;
                        }

                        if (col  < rsColumnCount) {
                           col++;
                        }
                     }
                  }
               }

               // pikku muokkaus
               String annettuVastaus2 = annettuVastaus.replace("'", "''");

               // Tietokantaan yrityksen tiedot.
               stmt.executeUpdate("INSERT INTO yritys " + "VALUES (" + t_id + 
                                  ", " + s_id + ", " + yritys + ", '" + alku + "', '" + loppu + 
                                  "', '" + annettuVastaus2 + "', " + samat + ")");
               
               // Tulostetaan käyttäjän tuottama taulu jos t_id != 4 ja 5
               if (t_id != 4 && t_id != 5) {
                  rs2 = stmt2.executeQuery(annettuVastaus);
                  rsmd = rs2.getMetaData();
                  int sarakkeita = rsmd.getColumnCount();
                     
                  // Tulostetaan käyttäjän tuottama taulu
                  System.out.println("");
                  while (rs2.next()) {
                     for (int f = 1; f <= sarakkeita; f++) {
                        if (f > 1) System.out.print(",  ");
                        String tieto = rs2.getString(f);
                        System.out.print(tieto);
                     }
                     System.out.println("");
                  }
                  System.out.println("");
               }
               
               // Jos vastaus oli oikea, onnitellaan ja pyydetään jatkamaan
               // seuraavaan tehtävään
               if (samat == true) {
                  i = 3;
                  vastauksiaOikein++;
                  System.out.println("Oikein!");
                  System.out.println("Paina enter - jatkaaksesi!");
                  scanner.nextLine();
               }
               
               // Jos vastaukset eivät olleet samat, tulostetaan tavoiteltu
               else {
                  i++;
                  yritys++;
                  System.out.print("Väärin! ");
                  if (t_id != 4 && t_id !=5) {
                     System.out.println("Tavoiteltava taulu näyttää: ");
                     System.out.println("");
                     rs = stmt.executeQuery(esimerkkiVastausLauseke);
                     rsmd = rs.getMetaData();
                     int sarakkeita = rsmd.getColumnCount();
                     
                     // Tulostetaan käyttäjän tuottama taulu
                     while (rs.next()) {
                        for (int f = 1; f <= sarakkeita; f++) {
                           if (f > 1) System.out.print(",  ");
                           String tieto = rs.getString(f);
                           System.out.print(tieto);
                        }
                        System.out.println("");
                     }
                     System.out.println("");
                  }
                  
                  System.out.println("Yrityksiä jäljellä " + (3-i) + ".");
                  if ((3-i) == 0) {
                     System.out.println("Oikea vastaus olisi ollut: " + esimerkkiVastausLauseke);
                  }
                  System.out.println("Paina enter - jatkaaksesi");
                  scanner.nextLine();
               }
            }
            catch(SQLException poikkeus) {
               samat = false;
               
               // pikku muokkaus
               String annettuVastaus2 = annettuVastaus.replace("'", "''");
               

               // Tietokantaan yrityksen tiedot.
               stmt.executeUpdate("INSERT INTO yritys " + "VALUES (" + t_id + 
                                  ", " + s_id + ", " + yritys + ", '" + alku + "', '" + loppu + 
                                  "', '" + annettuVastaus2 + "', " + samat + ")");
               i++;
               yritys++;
               System.out.println("Syötteessä ilmeni seuraava virhe: " + poikkeus.getMessage());
               System.out.println("Yrityksiä jäljellä " + (3-i) + ".");
               if ((3-i) == 0) {
                  System.out.println("Oikea vastaus olisi ollut: " + esimerkkiVastausLauseke);
               }
               System.out.println("Paina enter - jatkaaksesi");
               scanner.nextLine();
            }
         }
      }
      catch(SQLException poikkeus) {
         System.out.println("Tapahtui seuraava virhe: " + poikkeus.getMessage());
      }
      finally {
         try {
            if (rs != null) {
               rs.close();
            }
            if (rs2 != null) {
               rs2.close();
            }
            if (stmt != null) {
               stmt.close();
            }
            if (stmt2 != null) {
               stmt2.close();
            }
         }
         catch (SQLException e) {
            System.out.println("Tapahtui virhe: " + e.getMessage());
         }
      }
   }
   
   /*
   * Metodi joka tulostaa näytölle esimerkkikantojen taulut.
   * Ensimmäisellä rivillä taulun nimi, toisella rivillä sarakkeiden
   * otsikot ja tästä eteenpäin taulun rivien tiedot.
   *
   */
   public static void tulostaTaulut(Connection con) {
      Statement stmt = null;
      ResultSet rs = null;
      ResultSetMetaData rsmd;
      int columnsNumber;
      try {
         /*
         * Tulostetaan opiskelija-taulu 
         */ 
         System.out.println("");
         System.out.println("opiskelijat - taulu");
         stmt = con.createStatement();
         rs = stmt.executeQuery("SELECT * from opiskelijat");
         rsmd = rs.getMetaData();
         columnsNumber = rsmd.getColumnCount();
         
         // Tulostetaan taulun sarakkeiden otsikot
         for (int i = 1; i <= columnsNumber; i++) {
            String name = rsmd.getColumnName(i);
            if (i > 1)
               System.out.print(", ");
            System.out.print(name);
         }
         System.out.println("");
         //Tulostetaan taulun rivien tiedot
         while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
               if (i > 1) System.out.print(",  ");
               String columnValue = rs.getString(i);
               System.out.print(columnValue);
            }
            System.out.println("");
         }
         System.out.println("");
         
         /* 
         * Tulostetaan kurssit-taulu 
         */
         System.out.println("kurssit - taulu");
         stmt = con.createStatement();
         rs = stmt.executeQuery("SELECT * from kurssit");
         rsmd = rs.getMetaData();
         columnsNumber = rsmd.getColumnCount();
         // Tulostetaan taulun sarakkeiden otsikot
         for (int i = 1; i <= columnsNumber; i++) {
            String name = rsmd.getColumnName(i);
            if (i > 1)
               System.out.print(", ");
            System.out.print(name);
         }
         System.out.println("");
         //Tulostetaan taulun rivien tiedot
         while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
               if (i > 1) System.out.print(",  ");
               String columnValue = rs.getString(i);
               System.out.print(columnValue);
            }
            System.out.println("");
         }
         System.out.println("");
         
         /*
         *Tulostetaan suoritukset-taulu
         */
         System.out.println("suoritukset - taulu");
         stmt = con.createStatement();
         rs = stmt.executeQuery("SELECT * from suoritukset");
         rsmd = rs.getMetaData();
         columnsNumber = rsmd.getColumnCount();
         // Tulostetaan taulun sarakkeiden otsikot
         for (int i = 1; i <= columnsNumber; i++) {
            String name = rsmd.getColumnName(i);
            if (i > 1)
               System.out.print(", ");
            System.out.print(name);
         }
         System.out.println("");
         //Tulostetaan taulun rivien tiedot
         while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
               if (i > 1) System.out.print(",  ");
               String columnValue = rs.getString(i);
               System.out.print(columnValue);
            }
            System.out.println("");
         }
         System.out.println("");
      }
      catch(SQLException poikkeus) {
         System.out.println("Tapahtui seuraava virhe: " + poikkeus.getMessage());
      }
      
      finally {
         try {
            if (rs != null) {
               rs.close();
            }
            if (stmt != null) {
               stmt.close();
            }
         }
         catch (SQLException e) {
            System.out.println("Tapahtui virhe: " + e.getMessage());
         }
      }
   }
}