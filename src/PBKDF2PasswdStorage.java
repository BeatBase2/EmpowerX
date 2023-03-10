import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
/*
* Description: Class is used to hash the passwords
* Retrieved from: https://github.com/1MansiS/JavaCrypto/blob/main/JavaCryptoModule/SecureJavaCrypto/src/main/java/com/secure/crypto/password_storage/PBKDF2PasswdStorage.java
* Date: January 12th 202
 */
public class PBKDF2PasswdStorage {
    /*
    This API, returns PBKDF2 version of plain text password ready for storage

    @param plainTextPasswd: Clear text password
    @param salt: Base64 encoded salt value

    @return : Base64 Encoded password to be stored
     */
    public static String generatePasswdForStorage(String plainTextPasswd, String salt) {
        // Strings are immutable, so there is no way to change/nullify/modify its content after use. So always, collect and store security sensitive information in a char array instead.
        char[] charEnteredPassword = plainTextPasswd.toCharArray() ;

        PBEKeySpec keySpec;
        keySpec = new PBEKeySpec(charEnteredPassword,
                Base64.getDecoder().decode(salt),
                100000, 20 * 6);

        SecretKeyFactory pbkdfKeyFactory = null ;

        try {
            pbkdfKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512") ;
        } catch (NullPointerException|NoSuchAlgorithmException e) {System.out.println("Specified algorithm " + "PBKDF2WithHmacSHA512" + "is not available by the provider " + pbkdfKeyFactory.getProvider().getName());System.out.println("Error : " + e.getMessage()); System.exit(0);}

        byte[] pbkdfHashedArray = null ;

        try {
            pbkdfHashedArray = pbkdfKeyFactory.generateSecret(keySpec).getEncoded() ;
        } catch (InvalidKeySpecException e) {System.out.println("Specified key specification is inappropriate"); System.out.println("Error : " + e.getMessage()); System.exit(0);}
        return Base64.getEncoder().encodeToString(pbkdfHashedArray);
    }
}
