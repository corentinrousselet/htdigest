import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Encrypt {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, IOException {
        //username:realm:password

        String username = args[1];
        String site = args[2];
        Scanner scanner = new Scanner(System.in);
        System.out.println("New password");
        String password = scanner.nextLine();

        var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        byte [] keyBytes = {0x00,0x01,0x02,0x02,0x03,0x04,0x05,0x07,0x08,0x09,0x10,0x11,0x12,0x13,0x14,0x15};
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

        byte [] data = (username +":"+ site +":"+ password).getBytes(StandardCharsets.UTF_8);

        cipher.init(Cipher.ENCRYPT_MODE,key);
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
