import kotlin.Pair;

import java.util.Vector;

public class Vertical {
    private static char filler = '¡';

    public static void encrypt(Vector<Character> key1, Vector<Character> text) {
        filler = '¡';
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

        System.out.println("Key= " + key);

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
        Vector<Character> key = new Vector<>();
        key1.stream().distinct().forEach(key::add);

        Vector<Vector<Integer>> keyPrevIndex = new Vector<>();
        for (int i = 0; i < key.size(); i++) {
            Vector<Integer> item = new Vector<>();
            item.add(i);
            item.add(i);
            keyPrevIndex.add(item);
        }

        System.out.println("Key= " + key);

        for (int i = 0; i < key.size(); i++)
            for (int j = 0; j < key.size(); j++)
                if (key.get(i) < key.get(j)) {
                    Character ch = key.get(i);
                    key.set(i, key.get(j));
                    key.set(j, ch);

                    Integer ind = keyPrevIndex.get(i).get(0);
                    keyPrevIndex.get(i).set(0, keyPrevIndex.get(j).get(0));
                    keyPrevIndex.get(j).set(0, ind);
                }

        for (int i = 0; i < key.size(); i++)
            for (int j = 0; j < key.size(); j++)
                if (keyPrevIndex.get(i).get(0) < keyPrevIndex.get(j).get(0)) {
                    Vector<Integer> item = keyPrevIndex.get(i);
                    keyPrevIndex.set(i, keyPrevIndex.get(j));
                    keyPrevIndex.set(j, item);
                }

        System.out.println(keyPrevIndex);

        int n = text.size() / key.size();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++)
            for (Vector<Integer> j: keyPrevIndex)
                stringBuilder.append(text.get(j.get(1) * n + i));

        text.clear();
        for (char ch : stringBuilder.toString().toCharArray())
            if (ch != filler)
                text.add(ch);
    }
}