package apulaiset;

/**
 * Rajapinta suunnallisille olioille. Olio tuntee p‰‰ilmansuunnat, joiden
 * symbolit on annettu vakioina.
 * <p>
 * Harjoitustyˆ, Olio-ohjelmoinnin perusteet, kev‰t 2016.
 * <p>
 *
 * @author Jorma Laurikkala (jorma.laurikkala@uta.fi), Informaatiotieteiden
 *         yksikkˆ, Tampereen yliopisto.
 *
 */

public interface Suunnallinen {

   /** Pohjoista symboloiva merkki. */
   public static char POHJOINEN = 'p';

   /** It‰‰ symboloiva merkki. */
   public static char ITA = 'i';

   /** Etel‰‰ symboloiva merkki. */
   public static char ETELA = 'e';

   /** L‰ntt‰ symboloiva merkki. */
   public static char LANSI = 'l';

   /**
    * Olion suunnan palauttava metodi.
    *
    * @return jokin yll‰ m‰‰ritellyist‰ nelj‰st‰ p‰‰ilmansuunnan symbolista.
    */
   public abstract char suunta();

   /**
    * Olion suunnan asettava metodi.
    *
    * @param ilmansuunta
    *           uusi suunta, joka on jokin nelj‰st‰ p‰‰ilmansuunnasta.
    * @throws IllegalArgumentException
    *            jos parametri ei ollut jokin yll‰ m‰‰ritellyist‰
    *            p‰‰ilmansuunnan symboleista.
    */
   public abstract void suunta(char ilmansuunta) throws IllegalArgumentException;
}
