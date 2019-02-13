/*
 * Tietorakenteet 2016
 * Harjoitustyˆ
 * Vili-Veikko Rauhala
 * Rauhala.Vili.V@Student.uta.fi
 */

public class BinaryTree {
   
   /* Bin‰‰ripuun attribuutit */
   private Node root;
   private Node last;
   private boolean contains;
   private int size = 0;
   
   /*
    * BinaryTree
    * 
    * Luodaan bin‰‰ripuu johon ei ole viel‰ asetettu solmuja 
    */
   public BinaryTree() {
      root = null;
      last = null;
   }
   
   /*
    * getSize
    * 
    * Koon palauttava metodi.
    * 
    * Paluuarvona puun koko.
    */
   public int getSize() {
      return size;
   }
   
   /*
    *  insertItem
    *  
    *  Bin‰‰ripuuhun arvojen lis‰ys. Parametrein‰ avain sek‰ string merkkijono.
    *  
    *  Parametrein‰ lis‰tt‰v‰n solmun avain ja data.
    */
   public void insertItem(int key, String mj) {
      
      // Lis‰tt‰v‰ solmu
      Node newNode = new Node(key, mj);
      
      // Tarkastetaan onko puu tyhj‰, jos on, lis‰t‰‰n uusi solmu juureksi.
      if (isEmpty()) {
         root = last = newNode;
         size++;
      }
      
      // Jos puuhun ei ole lis‰tty muuta kuin juureen solmu, lis‰t‰‰n uusi
      // solmu t‰m‰n vasempaan lapseen.
      else if (last == root) {
         root.setLeftChild(newNode);
         newNode.setParent(last);
         last = newNode;
         size++;
         
         // Kutsutaan metodia joka kuplittaa solmun oikealle paikalleen.
         percolateUp(last);
      }
      
      // Jos viimeisin lis‰tty solmu ei ole juuri, etsit‰‰n uudelle solmulle
      // sopiva paikka.
      else {
         
         // Jos viimeeksi lis‰tty solmu on vanhempansa vasen solmu, lis‰t‰‰n uusi
         // solmu t‰m‰n viimeeksi lis‰tyn solmun vanhemman oikeaksi lapseksi.
         if (last.getParent().getLeftChild() == last) {
            last.getParent().setRightChild(newNode);
            newNode.setParent(last.getParent());
            last = newNode;
            size++;
            
            // Kutsutaan metodia joka kuplittaa solmun oikealle paikalleen.
            percolateUp(last);
         }
         
         
         // Jos viimeeksi lis‰tty solmu on vanhempansa oikea lapsi, t‰ytyy 
         // etsi‰ paikka puusta johon uusi solmu lis‰t‰‰n. Mahdollisuuksia on viimeeksi
         // liks‰tyn solmun oikeanpuoleinen paikka, jonka parent solmu on silti eri.
         // Toinen mahdollinen paikka on uuden tason ensimm‰inen paikka.
         else {
            // K‰yd‰‰n solmuja l‰pi puussa ylˆsp‰in kunnes last on joko itse root tai 
            // oman vanhempansa vasen lapsi
            while (last != root) {
               if (last != last.getParent().getLeftChild()) {
                  last = last.getParent();
               }
               else
                  break;
            }
            // Jos last == root, asetetaan uusi solmu puun vasemmanpuoleisimpaan tyhj‰‰n
            // lehteen uudelle tasolle
            if (last == root) {
               while (last.getLeftChild() != null) {
                  last = last.getLeftChild();
               }
               
               last.setLeftChild(newNode);
               newNode.setParent(last);
               last = newNode;
               size++;
               
               // Kutsutaan metodia joka kuplittaa solmun oikealle paikalleen.
               percolateUp(last);
            }
            
            // Jos last solmu on vanhempansa vasen lapsi, lis‰t‰‰n uusi solmu
            // lastin vanhemman oikeanpuoleisen lapsen vasemmanpuoleisimpaan vapaaseen lehteen
            else if (last == last.getParent().getLeftChild()) {
               last = last.getParent().getRightChild();
               // Edet‰‰n puussa alasp‰in kunnes p‰‰st‰‰n lehden kohdalle.
               while (last.getLeftChild() != null) {
                  last = last.getLeftChild();
               }
               
               last.setLeftChild(newNode);
               newNode.setParent(last);
               last = newNode;
               size++;
               
               // Kutsutaan metodia joka kuplittaa solmun oikealle paikalleen.
               percolateUp(last);
               
            }
         }
      }
   }
   
   /*
    * preoderPrint
    * 
    * Puun avaintein tulostus v‰lilyˆnnill‰ sisent‰en esij‰rjerstyksess‰
    * k‰ytet‰‰n apuna erillist‰ print metodia joka suorittaa tulostuksen
    * rekursiivisesti kaikille puun tasoille.
    */
   
   public void preorderPrint() {
      if (root != null) {
         String indent = "";
         print(indent, root);
      }
   } 
   
   /*
    * print
    * 
    * Metodi jota preoderPrint kutsuu. K‰y puun tasoja rekursiivisesti
    * ja tulostaa n‰iden sis‰llˆn komentoriville.
    * 
    * Parametreina sisennys ja solmu.
    */
   void print(String indent, Node node) {
      System.out.println(indent + node.getKey());
      if (node.getLeftChild() != null) {
         print(indent + "   ", node.getLeftChild());
      }
      if (node.getRightChild() != null) {
         print(indent + "   ", node.getRightChild());
      }
   }
   
   /*
    * removeMinElement
    * 
    * poistetaan puun pienimm‰n avaimen ovaama solvu, joka on juuressa.
    * Paluuarvona itse solmu.
    * 
    * Asetetaan juuressa oleva solmu temp. Vaihdetaan last solmu juureen,
    * kutsutaan persolateDown ja etsit‰‰n puusta uusi viimeisin solmu.
    *
    * Paluuarvona pienin solmu.
    */
   public Node removeMinElement() {
      Node minElement = new Node(); 
      Node oldLast = new Node();
      oldLast.setParent(last.getParent());
      minElement = root;
      
      // Jos root == last, palautetaan suoraan minElement
      if (root == last) {
         root = null;
         last = null;
         size--;
         return minElement;
      }
      
      // Etsit‰‰n uusi lastin paikka
      Node newLast = findNewLast();
      
      // Asetetaan last juureen ja viittaukset kuntoon.
      root = last;
      
      if (minElement.getLeftChild() != null) {
         root.setLeftChild(minElement.getLeftChild());
         minElement.getLeftChild().setParent(root);
      }
      if (minElement.getRightChild() != null) {
         root.setRightChild(minElement.getRightChild());
         minElement.getRightChild().setParent(root);
      }
      if (root.getLeftChild() == root) {
         root.setLeftChild(null);
      }
      root.setParent(minElement.getParent());
      
      
      // Poistetaan vanhan lastin vanhemman viittais kyseiseen solmuun
      if (oldLast.getParent().getLeftChild() == last && oldLast.getParent() == minElement) {
         root.setLeftChild(null);
      }
      else if (oldLast.getParent().getRightChild() == last && oldLast.getParent() == minElement) {
         root.setRightChild(null);
      }
      else if (oldLast.getParent().getLeftChild() == last) {
         oldLast.getParent().setLeftChild(null);
      }
      else if (oldLast.getParent().getRightChild() == last) {
         oldLast.getParent().setRightChild(null);
      }
      
      // asetetaan uusi paikka lastille
      last = newLast;
      
      size--;
      
      // Tarkistetaan puun j‰rjestys
      percolateDown(root);
         
      // Paluuarvona itse pienimm‰n arvon omaava solmu,
      // eli alkuper‰inen juuri
      return minElement;
   }
   
   /*
    * findNewLast
    * 
    * Metodi joka etsii last-solmua edellisen lis‰yspaikan.
    * 
    * Paluuarvona uusi viimeisin solmu puussa.
    */
   public Node findNewLast() {
      Node newLast = new Node();
      
      // Jos viimeeksi lis‰tty on juuri, uutta lastia ei ole.
      if (last == root) {
         newLast = null;
      }
      
      // Jos lastin vanhempi on itse juuri, sek‰ on itse
      // juuren vasen lapsi, lis‰t‰‰n juuri newLast
      else if (last.getParent() == root && last == last.getParent().getLeftChild()) {
            newLast = last;
      }
      
      // Jos last on vanhempansa vasen lapsi, t‰ytyy puusta
      // etsi‰ hankalammin edellinen lis‰yspaikka.
      // Mahdollisia lis‰yspaikkoja on edellisen tason kaikista
      // oikeanpuoleisin solmu, tai lastin vasemmalla puolella sijaitseva
      // solmu.
      else  {
         // K‰yd‰‰n solmuja l‰pi puussa ylˆsp‰in kunnes last on joko itse root tai 
         // oman vanhempansa oikea lapsi
          newLast = last;
          while (newLast != root) {
             if (newLast != newLast.getParent().getRightChild())
                newLast = newLast.getParent();
             else 
                break;
          }
             
          // Jos newLast == root, haetaan taulukon kaikista 
          // oikeanpuoleisin solmu joka on uusi last.
          if (newLast == root) {
             while (newLast.getRightChild() != null) {
                newLast = newLast.getRightChild();
             }
          }
          
          // Jos newLast on vanhempansa oikea lapsi, uusi last on vanhemman 
          // vasemman lapsen oikeanpuoleisin lapsenlapsi
          else if (newLast == newLast.getParent().getRightChild()) {
             newLast = newLast.getParent().getLeftChild();
             while (newLast.getRightChild() != null) {
                newLast = newLast.getRightChild();
             }
          }
      }
      
      return newLast;
   }
   
   /*
    * percolateDown
    * 
    * Vertaa kahta currentin lasta. Jos se kumman avain on pienempi
    * && pienempi kuin parentin, vaihdetaan kyseisten solmujen paikkoja.
    * Kuplitetaan puun loppuun asti.
    * 
    * Parametrein‰ solmu jota verrataan lapsiinsa.
    */
   
   public void percolateDown (Node curr) {
      if (curr.getLeftChild() != null && curr.getRightChild() != null) {
         if (curr.getLeftChild().getKey() < curr.getRightChild().getKey() &&
               curr.getLeftChild().getKey() < curr.getKey()) {
            swap(curr.getLeftChild(), curr);
            
            percolateDown(curr);
         }
         
         else if (curr.getRightChild().getKey() < curr.getLeftChild().getKey() &&
               curr.getRightChild().getKey() < curr.getKey()) {
            
            // Jos kyseess‰ last vaihdetaan
            if (curr.getRightChild() == last) {
               last = curr;
            }
            
            // Kutsutaan swap metodia joka vaihtaa kahden solmun paikkoja.
            swap(curr.getRightChild(), curr);
            
            percolateDown(curr);
         }
      }
      
      else if (curr.getLeftChild() != null) {
         if (curr.getLeftChild().getKey() < curr.getKey()) {
            
            // Jos kyseess‰ last vaihdetaan
            if (curr.getLeftChild() == last) {
               last = curr;
            }
            swap(curr.getLeftChild(), curr);
            
            percolateDown(curr);
         }
      }
   }
   
   /*
    * percolateUp
    * 
    * Ylˆsp‰in kupliva j‰rjestyksen muuttaja. Vaihtaa kahden solmun paikkoja
    * jos curr avain < kuin currentin parentin avain.
    * 
    * Parametrina solmu jota verrataan vanhempaansa.
    */
   
   public void percolateUp (Node curr) {
      if (curr != root && curr.getKey() < curr.getParent().getKey()) {
         if (curr == last) {
            last = curr.getParent();
         }
         // Kutsutaan swap metodia joka vaihtaa solmut p‰ikseen
         swap(curr, curr.getParent());
         
         // Kutsutaan percolateUp rekursiivisesti jos muutoksia tehtiin
         percolateUp(curr);
      }
   }
   
   /* 
    * swap
    * 
    * Metodi joka vaihtaa kahden solmun paikkoja
    * 
    * Parametreina solmut a ja b.
    */
   public void swap (Node a, Node b) {
      if (b == root) {
         root = a;
      }
      
      // Asetetaan a:n viittaukset temp.
      Node temp = new Node();
      temp.setParent(a.getParent());
      temp.setLeftChild(a.getLeftChild());
      temp.setRightChild(a.getRightChild());
      
      // Vaihdetaan A:lle B:n parent. Tarkistetaan onko null.
      if (b.getParent() != null) {
         a.setParent(b.getParent());
         // Muutetaan parentin lapsi oikeaksi
         if (a.getParent().getLeftChild() == b) {
            a.getParent().setLeftChild(a);
         }
         else {
            a.getParent().setRightChild(a);
         }
      }
      else {
         a.setParent(null);
      }
      
      /*
       *  Jos vaihdettavat arvot ovat parent ja vasen lapsi
       */
      
      if (a == b.getLeftChild()) {
         
         // Vaihdetaan muut A:n arvot
         a.setRightChild(b.getRightChild());
         // Varmistetaan ettei viitata null arvoon
         if (a.getRightChild() != null) {
            a.getRightChild().setParent(a);
         }
         a.setLeftChild(b);
         
         // Vaihdetaan B:n arvot
         b.setParent(a);
         b.setLeftChild(temp.getLeftChild());
         b.setRightChild(temp.getRightChild());
         
         // Varmistetaan ettei viitata null arvoon
         if (b.getLeftChild() != null) {
            b.getLeftChild().setParent(b);
         }
         if (b.getRightChild() != null) {
            b.getRightChild().setParent(b);
         }
      }
      
      /*
       *  Jos vaihdettavat arvot parent ja oikea lapsi
       */
      
      else {
         
         // Vaihdetaan loput A:n arvoista
         a.setLeftChild(b.getLeftChild());
         // Varmistetaan ettei viitata null arvoon
         if (a.getLeftChild() != null) {
            a.getLeftChild().setParent(a);
         }
         a.setRightChild(b);
         
         // Vaihdetaan B:n arvot
         b.setParent(a);
         b.setLeftChild(temp.getLeftChild());
         b.setRightChild(temp.getRightChild());
         // Varmsitetaan ettei viitata null arvoon
         if (b.getLeftChild() != null) {
            b.getLeftChild().setParent(b);
         }
         // Varmistetaan ettei viitata null arvoon
         if (b.getRightChild() != null) {
            b.getRightChild().setParent(b);
         }
      }
   } 
   
   /* 
    * doesCointain
    * 
    * Metodi joka recursiveCheckIfContains metodia apuna k‰ytt‰en katsoo
    * onko puussa jo lis‰tt‰v‰ksi m‰‰r‰tty solmu.
    * 
    * parametreina avain sek‰ data.
    * paluuarvona totuusarvo.
    */
   public boolean doesContain(int key, String mj) {
      contains = false;
      // Lis‰tt‰v‰ solmu
      Node newNode = new Node(key, mj);
      
      // Jos puu ei ole tyhj‰, k‰yd‰‰n solmut l‰pi.
      if (isEmpty() == false) {
         recursiveCheckIfContains(newNode, root);
      }
      
      return contains;
   }
   /*
    * recursiveCheckIfContains
    * 
    * Metodi jota doesContain kutsuu. K‰y rekursiivisesti puun solmut l‰pi ja katsoo
    * onko parametrina annettu solmu jo puussa.
    * 
    * parametrein‰ uusi solmu sek‰ verrattava solmu.
    */
   public void recursiveCheckIfContains(Node newNode, Node curr) {
      if (curr.getKey() == newNode.getKey()) {
         contains = true;
         return;
      }
      if (curr.getLeftChild() != null) {
         recursiveCheckIfContains(newNode, curr.getLeftChild());
      }
      if (curr.getRightChild() != null) {
         recursiveCheckIfContains(newNode, curr.getRightChild());
      }
   }
   
   /*
    * isEmpty
    * 
    * Metodi joka katsoo onko puu tyhj‰.
    * 
    * Paluuarvona totuusarvo.
    */
   public boolean isEmpty() {
      if (root == null)
         return true;
      else
         return false;
   }
   
   /*
    * minKey
    * 
    * Metodi joka palauttaa pienimm‰n alkion avaimen poistamatta sit‰.
    * 
    * Paluuarvona pienimm‰n solmun (juuren) avain.
    */
   public int minKey() {
      return root.getKey();
   }
   
   /*
    * minElement
    * 
    * Metodi joka palauttaa pienimm‰n alkion datan poistamatta sit‰.
    * 
    * Paluuarvona pienimm‰n solmun (juuren) data.
    */
   public String minElement() {
      return root.getData();
   }
}
