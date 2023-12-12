import java.util.Collections;
import java.util.Vector;

public class Atbash {
    public static void unicodeEncrypt(Vector<Character> encryptedText) {
        for (int i = 0; i < encryptedText.size(); i++)
            encryptedText.set(i, (char) ((65535 - (int) encryptedText.get(i))));
    }

    public static void wordEncrypt(Vector<Character> encryptedText) {
        StringBuilder currentWord = new StringBuilder();
        Vector<Character> result = new Vector<>();

        for (char c : encryptedText) {
            if (Character.isWhitespace(c)) {
                if (currentWord.length() > 0) {
                    currentWord.reverse();
                    result.addAll(currentWord.chars().mapToObj(e -> (char) e).toList());
                    result.add(' ');
                    currentWord = new StringBuilder();
                }
            } else {
                currentWord.append(c);
            }
        }

        if (currentWord.length() > 0) {
            currentWord.reverse();
            result.addAll(currentWord.chars().mapToObj(e -> (char) e).toList());
        }

        encryptedText.clear();
        encryptedText.addAll(result);
    }

    public static void sentenceEncrypt(Vector<Character> encryptedText) {
        StringBuilder currentSentence = new StringBuilder();
        Vector<Character> result = new Vector<>();

        for (char c : encryptedText) {
            if (c == '.' || c == '!' || c == '?') {
                currentSentence.reverse();
                result.addAll(currentSentence.chars().mapToObj(e -> (char) e).toList());
                result.add(c);
                currentSentence = new StringBuilder();
            } else {
                currentSentence.append(c);
            }
        }

        if (currentSentence.length() > 0) {
            currentSentence.reverse();
            result.addAll(currentSentence.chars().mapToObj(e -> (char) e).toList());
        }

        encryptedText.clear();
        encryptedText.addAll(result);
    }

    public static void textEncrypt(Vector<Character> encryptedText) {
        Collections.reverse(encryptedText);
    }
}
