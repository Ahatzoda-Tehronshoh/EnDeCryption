import java.util.Vector;

public class Vertical {
    private static char filler = 'X';

    public static void encrypt(Vector<Character> key1, Vector<Character> text) {
        filler = 'X';
        Vector<Vector<Integer>> matrix = new Vector<>();

        Vector<Character> key = new Vector<>();
        key1.stream().distinct().forEach(key::add);

        if (text.contains(filler)) {
            System.out.println("Filler changed!");
            for (int i = 48; i < 65536; i++)
                if (!text.contains((char) i)) {
                    filler = (char) i;
                    break;
                }
        }

        while ((text.size() % key.size()) != 0)
            text.add(filler);

        int n = text.size() / key.size();
        for (int i = 0; i < n; i++) {
            matrix.add(new Vector<>());
            for (int j = 0; j < key.size(); j++)
                matrix.get(i).add((int) text.get(i * key.size() + j));
        }

        Vector<Integer> keyPrevIndex = new Vector<>();
        for (int i = 0; i < key.size(); i++)
            keyPrevIndex.add(i);

        for (int i = 0; i < key.size(); i++)
            for (int j = 0; j < key.size(); j++)
                if (key.get(i) < key.get(j)) {
                    Character ch = key.get(i);
                    key.set(i, key.get(j));
                    key.set(j, ch);

                    Integer ind = keyPrevIndex.get(i);
                    keyPrevIndex.set(i, keyPrevIndex.get(j));
                    keyPrevIndex.set(j, ind);
                }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i : keyPrevIndex)
            for (Vector<Integer> integers : matrix)
                stringBuilder.append((char) ((int) integers.get(i)));

        text.clear();
        for (char ch : stringBuilder.toString().toCharArray())
            text.add(ch);
    }

    public static void decrypt(Vector<Character> key1, Vector<Character> text) {
        Vector<Character> key = new Vector<>(key1);
        key.removeIf(c -> c == filler);

        while (text.size() % key.size() != 0) {
            text.add(filler);
        }

        Vector<Integer> keyPrevIndex = new Vector<>();
        for (int i = 0; i < key.size(); i++)
            keyPrevIndex.add(i);

        for (int i = 0; i < key.size(); i++)
            for (int j = 0; j < key.size(); j++)
                if (key.get(i) < key.get(j)) {
                    Character ch = key.get(i);
                    key.set(i, key.get(j));
                    key.set(j, ch);

                    Integer ind = keyPrevIndex.get(i);
                    keyPrevIndex.set(i, keyPrevIndex.get(j));
                    keyPrevIndex.set(j, ind);
                }

        int n = text.size() / key.size();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++)
            for (int j: keyPrevIndex)
                stringBuilder.append(text.get(j * n + i));

        text.clear();
        for (char ch : stringBuilder.toString().toCharArray())
            if (ch != filler)
                text.add(ch);
    }
}