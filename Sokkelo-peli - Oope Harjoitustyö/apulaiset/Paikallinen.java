package apulaiset;

/**
 * Rajapinta paikkansa tunteville olioille. Paikka määritellään rivi- ja
 * sarakeindeksien avulla. Indeksiarvon tulee olla positiivinen (suurempi tai
 * yhtä suuri kuin nolla).
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2016.
 * <p>
 *
 * @author Jorma Laurikkala (jorma.laurikkala@uta.fi), Informaatiotieteiden
 *         yksikkö, Tampereen yliopisto.
 *
 */

public interface Paikallinen {

   /**
    * Paikan rivi-indeksin palauttava metodi.
    *
    * @return paikan rivi-indeksi.
    */
   public abstract int rivi();

   /**
    * Paikan rivi-indeksin asettava metodi.
    *
    * @param ind
    *           rivi-indeksi.
    * @throws IllegalArgumentException
    *            jos indeksi on negatiivinen.
    */
   public abstract void rivi(int ind);

   /**
    * Paikan sarakeindeksin palauttava metodi.
    *
    * @return paikan sarakeindeksi.
    */
   public abstract int sarake();

   /**
    * Paikan sarakeindeksin asettava metodi.
    *
    * @param ind
    *           sarakeindeksi.
    * @throws IllegalArgumentException
    *            jos indeksi on negatiivinen.
    */
   public abstract void sarake(int ind);

   /**
    * Kertoo onko paikkaan sallittua asettaa sisältöä (mönkijä, robotti tai
    * esine). Paikka on käytettävissä, jos siinä on käytävää.
    *
    * @return true, jos paikka on käytettävissä.
    */
   public abstract boolean sallittu();
}
