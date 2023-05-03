/*
 * Fichero: PasswordHasher.java
 * Autor: Jose Manuel Cejudo Gausi (jmc@jmcejudo.com)
 * 
 * Ã‰ste programa es software libre: usted tiene derecho a redistribuirlo y/o modificarlo bajo los
 * tÃ©rminos de la Licencia EUPL European Public License publicada por el organismo IDABC de la
 * ComisiÃ³n Europea, en su versiÃ³n 1.0. o posteriores.
 * 
 * Ã‰ste programa se distribuye de buena fe, pero SIN NINGUNA GARANTÃ�A, incluso sin las presuntas
 * garantÃ­as implÃ­citas de USABILIDAD o ADECUACIÃ“N A PROPÃ“SITO CONCRETO. Para mas informaciÃ³n
 * consulte la Licencia EUPL European Public License.
 * 
 * Usted recibe una copia de la Licencia EUPL European Public License junto con este programa,
 * si por algÃºn motivo no le es posible visualizarla, puede consultarla en la siguiente
 * URL: http://ec.europa.eu/idabc/servlets/Doc?id=31099
 * 
 * You should have received a copy of the EUPL European Public License along with this program.
 * If not, see http://ec.europa.eu/idabc/servlets/Doc?id=31096
 * 
 * Vous devez avoir reÃ§u une copie de la EUPL European Public License avec ce programme.
 * Si non, voir http://ec.europa.eu/idabc/servlets/Doc?id=31205
 * 
 * Sie sollten eine Kopie der EUPL European Public License zusammen mit diesem Programm.
 * Wenn nicht, finden Sie da http://ec.europa.eu/idabc/servlets/Doc?id=29919
 * 
 */

package es.jahernandez.utilidades;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * ImplementaciÃ³n simple de mÃ©todos de utilidad para generar cÃ³digos <code>hash</code> con
 * <code>salt</code>.
 * <p>
 * Esta clase utiliza los siguientes parÃ¡metros por defecto:
 * <ul>
 * <li>Algoritmo de hash: HmacSHA512</li>
 * <li>Longitud del <code>salt</code>: 64 bytes</li>
 * <li>Iteraciones: 1000</li>
 * <li>Conjunto de caracteres para codificaciÃ³n de cadenas: UTF-8</li>
 * </ul>
 * <p>
 * Ejemplo:
 * 
 * <pre>
 * // Crea el hash de &lt;code&gt;valor&lt;/code&gt;
 * PasswordHash ph = PasswordHasher.hash(valor);
 * // Comprueba si un &lt;code&gt;valor&lt;/code&gt; concuerda con un &lt;code&gt;hash&lt;/code&gt;
 * boolean valido = PasswordHasher.isValid(valor, ph.getHash(), ph.getSalt());
 * </pre>
 * <p>
 * NOTA: esta clase es un ejemplo de implementaciÃ³n, aunque puede usarse en un entorno de
 * producciÃ³n. Para simplificar su uso todas las excepciones chequeadas se capturan y se relanzan
 * como {@link RuntimeException}. En una implementaciÃ³n mÃ¡s completa podrÃ­a ser conveniente definir
 * excepciones especÃ­ficas.
 * 
 * @author Jose Manuel Cejudo Gausi
 */
public final class PasswordHasher {

    /**
     * Nombre del algoritmo de hash.
     */
    public static final String ALGORITHM = "HmacSHA512";

    /**
     * Longitud del <code>salt</code>. Igual al tamaÃ±o del resultado del algoritmo.
     */
    public static final int SALT_LENGTH = 64;

    /**
     * NÃºmero de iteraciones a realizar.
     */
    public static final int ITERATIONS = 1000;

    /**
     * Nombre del conjunto de caracteres para codificar cadenas de texto.
     */
    public static final String CHARSET_NAME = "UTF-8";

    /**
     * Representa el resultado de una operaciÃ³n de <code>hash</code> con <code>salt</code>.
     * 
     * @author Jose Manuel Cejudo Gausi
     */
    public static class PasswordHash {

        /**
         * Contiene el valor de <code>hash</code>.
         */
        private final byte[] hash;

        /**
         * Contiene el valor de <code>salt</code>.
         */
        private final byte[] salt;

        /**
         * Construye una nueva instancia con los valores indicados.
         * 
         * @param hash
         *            el valor de <code>hash</code>.
         * @param salt
         *            el valor de <code>salt</code>.
         */
        public PasswordHash(final byte[] hash, final byte[] salt) {

            this.hash = hash;
            this.salt = salt;

        }

        /**
         * Devuelve el valor de <code>hash</code>.
         * 
         * @return el valor.
         */
        public byte[] getHash() {

            return hash;

        }

        /**
         * Devuelve el valor de <code>salt</code>.
         * 
         * @return el valor.
         */
        public byte[] getSalt() {

            return salt;

        }

    }

    /**
     * Constructor privado para impedir la instanciaciÃ³n de esta clase de utilidad.
     */
    private PasswordHasher() {

        super();

    }

    /**
     * Calcula el <code>hash</code> del valor indicado.
     * <p>
     * Este mÃ©todo genera un valor de <code>salt</code> aleatorio y lo utiliza para calcular
     * repetitivamente tantas operaciones de <code>hash</code> como indique {@link #ITERATIONS}.
     * <p>
     * El objeto {@link PasswordHash} devuelto contiene el <code>hash</code> calculado y el
     * <code>salt</code> generado.
     * 
     * @param value
     *            el valor para el que calcular el <code>hash</code>.
     * @return el resultado del cÃ¡lculo <code>hash</code>.
     */
    public static PasswordHash hash(final String value) {

        final byte[] salt = new byte[SALT_LENGTH];
        final SecureRandom rnd = new SecureRandom();
        rnd.nextBytes(salt);

        return hash(value, salt);

    }

    /**
     * Calcula el <code>hash</code> del valor indicado usando el <code>salt</code> indicado.
     * <p>
     * El objeto {@link PasswordHash} devuelto contiene el <code>hash</code> calculado y el
     * <code>salt</code> generado.
     * 
     * @param value
     *            el valor para el que calcular el <code>hash</code>.
     * @param salt
     *            el valor de <code>salt</code> a utilizar.
     * @return el resultado del cÃ¡lculo <code>hash</code>.
     */
    public static PasswordHash hash(final String value, final byte[] salt) {

        try {
            byte[] retVal;
            final byte[] valueBytes = value.getBytes(CHARSET_NAME);
            final Mac mac = Mac.getInstance(ALGORITHM);
            final Key key = new SecretKeySpec(salt, ALGORITHM);
            mac.init(key);
            retVal = mac.doFinal(valueBytes);
            for (int i = 1; i < ITERATIONS; i++) {
                retVal = mac.doFinal(retVal);
            }
            return new PasswordHash(retVal, salt);

        } catch (final NoSuchAlgorithmException cause) {
            throw new RuntimeException(cause);
        } catch (final InvalidKeyException cause) {
            throw new RuntimeException(cause);
        } catch (final UnsupportedEncodingException cause) {
            throw new RuntimeException(cause);
        }

    }

    /**
     * Comprueba si el <code>hash</code> calculado para el valor y el <code>salt</code> indicados es
     * igual al <code>hash</code> correcto indicado.
     * <p>
     * Este mÃ©todo permite comprobar si un valor es integro con respecto al <code>hash</code>.
     * 
     * @param value
     *            el valor a comprobar.
     * @param correctHash
     *            el <code>hash</code> correcto.
     * @param salt
     *            el <code>salt</code> a utilizar para la generaciÃ³n del <code>hash</code> de
     *            <code>value</code>. Debe ser el mismo que el utilizado para calcular
     *            <code>hash</code>.
     * @return <code>true</code> si el <code>hash</code> de <code>value</code> usando
     *         <code>salt</code> es igual a <code>correctHash</code>.
     */
    public static boolean isValid(final String value, final byte[] correctHash, final byte[] salt) {

        final PasswordHash ph = hash(value, salt);
        return Arrays.equals(ph.getHash(), correctHash);

    }

}
