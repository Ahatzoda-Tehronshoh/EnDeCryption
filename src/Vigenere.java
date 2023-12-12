import java.util.Vector;

public class Vigenere {
    public static void encrypt(Vector<Character> key, Vector<Character> text) {
        makeLengthEqual(key, text);

        for (int i = 0; i < text.size(); i++) {
            char plainChar = text.get(i);
            char keyChar = key.get(i);
            int encryptedChar = (plainChar + keyChar) % 65536;
            text.set(i, (char) encryptedChar);
        }
    }

    public static void decrypt(Vector<Character> key, Vector<Character> text) {
        makeLengthEqual(key, text);

        for (int i = 0; i < text.size(); i++) {
            char encryptedChar = text.get(i);
            char keyChar = key.get(i);
            int decryptedChar = (encryptedChar - keyChar + 65536) % 65536;
            text.set(i, (char) decryptedChar);
        }
    }

    private static void makeLengthEqual(Vector<Character> key, Vector<Character> text) {
        for(int i = 0, j = 0; i < text.size(); i++)
            if(i >= key.size())
                key.add(key.get(j++));
    }
}
