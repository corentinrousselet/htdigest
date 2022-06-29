import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;

public class Decrypt {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, DecoderException, IllegalBlockSizeException, BadPaddingException, IOException, CertificateException, UnrecoverableKeyException, KeyStoreException {
        var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        InputStream readStream = new FileInputStream("keystore.p12");
        keyStore.load(readStream,"password".toCharArray());
        Key keyInStore = keyStore.getKey("training","password".toCharArray());

        String lineFromFile = readFromFile(args);

        IvParameterSpec ivSpec = new IvParameterSpec(Hex.decodeHex(lineFromFile.split(":")[1]));
        cipher.init(Cipher.DECRYPT_MODE, keyInStore, ivSpec);

        byte[] cipherText = Hex.decodeHex(lineFromFile.split(":")[0]);

        String decryptDataString = new String(cipher.doFinal(cipherText), StandardCharsets.UTF_8);
        System.out.println("decrypt message : " + decryptDataString);
    }

    private static String readFromFile(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        String line = reader.readLine();
        System.out.println("Line from file " + args[0] + " : " + line);
        return line;
    }


}
