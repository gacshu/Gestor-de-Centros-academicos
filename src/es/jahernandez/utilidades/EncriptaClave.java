package es.jahernandez.utilidades;

import org.apache.commons.codec.digest.DigestUtils;

public class EncriptaClave 
{
    /**
     * Sole entry point to the class and application used for testing the
     * String Encrypter class.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        //testUsingSecretKey();
        //testUsingPassPhrase();
        String texto="23181654";
        String encriptMD5=DigestUtils.md5Hex(texto);
        System.out.println("md5:"+encriptMD5);
    }

}
