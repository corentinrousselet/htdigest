import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Scanner;

public class Encrypt {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, IOException, KeyStoreException, UnrecoverableKeyException, CertificateException {
        //username:realm:password

        String username = args[1];
        String site = args[2];
        Scanner scanner = new Scanner(System.in);
        System.out.println("New password");
        String password = scanner.nextLine();

        var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        InputStream readStream = new FileInputStream("keystore.p12");
        keyStore.load(readStream,"password".toCharArray());
        Key keyInStore = keyStore.getKey("training","password".toCharArray());

        byte [] data = (username +":"+ site +":"+ password).getBytes(StandardCharsets.UTF_8);

        cipher.init(Cipher.ENCRYPT_MODE,keyInStore);
        byte [] iv = cipher.getIV();
        byte [] cipherText = cipher.doFinal(data);

        String cipherTextString = Hex.encodeHexString(cipherText);
        String ivString = Hex.encodeHexString(iv);
        System.out.println(cipherTextString);
        writeInFile(args,cipherTextString,ivString);
    }

    private static void writeInFile(String[] args, String digestString,String ivString) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(args[0]));
        writer.write(digestString+":"+ivString);
        writer.close();
        System.out.println("Digest write in file : "+ args[0]);
    }
}
