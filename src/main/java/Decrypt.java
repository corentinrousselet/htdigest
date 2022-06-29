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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Decrypt {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, DecoderException, IllegalBlockSizeException, BadPaddingException, IOException {
        var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        byte [] iv = {0,0,0,0,0,0,-120,125,124,95,25,1,-15,14,2,34};
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        byte [] keyBytes = {0x00,0x01,0x02,0x02,0x03,0x04,0x05,0x07,0x08,0x09,0x10,0x11,0x12,0x13,0x14,0x15};
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

        cipher.init(Cipher.DECRYPT_MODE,key,ivspec);
        byte [] cipherText= Hex.decodeHex(readFromFile(args));

        String decryptDataString = new String(cipher.doFinal(cipherText),StandardCharsets.UTF_8);
        System.out.println(decryptDataString);

    }

    private static String readFromFile(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        String line = reader.readLine();
        System.out.println("Line from file : "+ args[0]+", "+line);
        return line;
    }


}
