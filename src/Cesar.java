import java.util.Vector;

public class Cesar {
    public static Vector<Character> encrypt(int n, Vector<Character> encryptedText) {
        for(int i = 0; i < encryptedText.size(); i++)
            encryptedText.set(i, (char) (((int) encryptedText.get(i) + n) % 65536));

        return encryptedText;
    }

    public static Vector<Character> decrypt(int n, Vector<Character> encryptedText) {
        for(int i = 0; i < encryptedText.size(); i++)
            encryptedText.set(i, (char) (((int) encryptedText.get(i) - n) % 65536));

        return encryptedText;
    }
}