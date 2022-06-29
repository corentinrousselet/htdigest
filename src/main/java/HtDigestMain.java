import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import org.apache.commons.codec.binary.Hex;


public class HtDigestMain {
    public static void main(String[] args) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            String username = args[1];
            String site = args[2];
            Scanner scanner = new Scanner(System.in);
            System.out.println("New password");
            String password = scanner.nextLine();

            String digestString = getDigestString(messageDigest, username, site, password);
            writeInFile(args, username+":"+site+":"+digestString);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static String getDigestString(MessageDigest messageDigest, String username, String site, String password) {
        byte [] digest = messageDigest.digest((username +":"+ site +":"+ password).getBytes(StandardCharsets.UTF_8));
        String digestString = Hex.encodeHexString(digest);
        System.out.println(digestString);
        return digestString;
    }
    private static void writeInFile(String[] args, String digestString) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(args[0]));
        writer.write(digestString);
        writer.close();
        System.out.println("Digest write in file : "+ args[0]);
    }


}
