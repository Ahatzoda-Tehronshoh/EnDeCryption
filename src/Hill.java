import java.math.BigInteger;
import java.util.Vector;

public class Hill {
    private static Character filler = 'X';

    private static Vector<Vector<Integer>> keyToMatrix(Vector<Character> key) {
        int x = (int) Math.sqrt(key.size());
        if ((x * x) != key.size()) {
            x++;
            for (int i = key.size(), y = 0; i < x * x; i++)
                key.add(key.get(y++));
        }

        Vector<Vector<Integer>> keyMatrix = new Vector<>();

        for (int i = 0; i < x; i++) {
            Vector<Integer> vec = new Vector<>();
            for (int j = 0; j < x; j++)
                vec.add((int) key.get(i * x + j));

            keyMatrix.add(vec);
        }

        return keyMatrix;
    }

    public static void encrypt(Vector<Character> key, Vector<Character> text) {
        filler = 'X';
        Vector<Vector<Integer>> keyMatrix = keyToMatrix(key);

        int det = (int) determinant(keyMatrix);
        digitInverse(det, 65536);

        if (text.contains(filler)) {
            System.out.println("Filler changed!");
            for (int i = 48; i < 65536; i++)
                if (!text.contains((char) i)) {
                    filler = (char) i;
                    break;
                }
        }

        while ((text.size() % keyMatrix.size()) != 0)
            text.add(filler);

        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < text.size(); i += keyMatrix.size())
            for (int j = 0; j < keyMatrix.size(); ++j) {
                int encryptedChar = 0;
                for (int k = 0; k < keyMatrix.size(); ++k)
                    encryptedChar += keyMatrix.get(j).get(k) * text.get(i + k);

                encrypted.append((char) (encryptedChar % 65536));
            }

        text.clear();
        for (char c : encrypted.toString().toCharArray()) {
            //System.out.print((int)c + " ");
            text.add(c);
        }
//        System.out.println();
    }

    public static void decrypt(Vector<Character> key, Vector<Character> text) {
        Vector<Vector<Integer>> keyMatrix = keyToMatrix(key);
        System.out.println(keyMatrix.toString());

        Vector<Vector<Integer>> inverseKeyMatrix = invertMatrix(keyMatrix);

        System.out.println(inverseKeyMatrix.toString());

        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < text.size(); i += keyMatrix.size()) {
            for (int j = 0; j < keyMatrix.size(); ++j) {
                int decryptedChar = 0;
                for (int k = 0; k < keyMatrix.size(); ++k)
                    decryptedChar += inverseKeyMatrix.get(j).get(k) * text.get(i + k);

                decrypted.append((char) (decryptedChar % 65536));
            }
        }

        text.clear();
        for (char c : decrypted.toString().toCharArray())
            if (c != filler)
                text.add(c);
    }

    private static Vector<Vector<Integer>> invertMatrix(Vector<Vector<Integer>> matrix) {
        Vector<Vector<Integer>> result = new Vector<>();
        Vector<Vector<Integer>> algebraicComplementsMatrix = algebraicComplements(matrix);

        System.out.println("Dop = " + algebraicComplementsMatrix);

        int det = (int) determinant(matrix);
        int dInv = digitInverse(det, 65536);

        System.out.println(det + " ; DInv = " + dInv);

        for (int i = 0; i < matrix.size(); i++) {
            result.add(new Vector<>());
            for (int j = 0; j < matrix.size(); j++)
                result.get(i).add(algebraicComplementsMatrix.get(j).get(i) * dInv);
        }

        for (int i = 0; i < matrix.size(); i++)
            for (int j = 0; j < matrix.size(); j++)
                if (result.get(i).get(j) >= 0)
                    result.get(i).set(j, result.get(i).get(j) % 65536);
                else
                    for (int x = 0; ; x++)
                        if ((x * 65536 + result.get(i).get(j)) > 0) {
                            result.get(i).set(j, x * 65536 + result.get(i).get(j));
                            break;
                        }
        return result;
    }

    public static int digitInverse(int a, int module) {
        System.out.println(a + " " + module);
        BigInteger bigA = BigInteger.valueOf(a);
        BigInteger bigM = BigInteger.valueOf(module);

        // Вычисляем обратный элемент
        return bigA.modInverse(bigM).intValue();
    }

    public static double determinant(Vector<Vector<Integer>> matrix) {
        double result = 0;

        if (matrix.size() == 1) {
            result = matrix.get(0).get(0);
            return result;
        }

        for (int i = 0; i < matrix.get(0).size(); i++) {
            Vector<Vector<Integer>> temp = new Vector<>();

            for (int j = 1; j < matrix.size(); j++) {
                temp.add(new Vector<>());

                for (int k = 0; k < matrix.get(0).size(); k++) {
                    temp.get(j - 1).add(0);

                    if (k < i)
                        temp.get(j - 1).set(k, matrix.get(j).get(k));
                    else if (k > i)
                        temp.get(j - 1).set(k - 1, matrix.get(j).get(k));
                }
            }

            result += matrix.get(0).get(i) * Math.pow(-1, i) * determinant(temp);
        }

        return result;
    }

    public static Vector<Vector<Integer>> algebraicComplements(Vector<Vector<Integer>> matrix) {
        Vector<Vector<Integer>> algebraicComplements = new Vector<>();

        for (int i = 0; i < matrix.size(); i++) {
            algebraicComplements.add(new Vector<>());
            for (int t = 0; t < matrix.size(); t++)
                algebraicComplements.get(i).add(0);

            for (int j = 0; j < matrix.get(i).size(); j++) {
                Vector<Vector<Integer>> temp = new Vector<>();

                for (int k = 0; k < matrix.size() - 1; k++) {
                    temp.add(new Vector<>());
                    for (int l = 0; l < matrix.size() - 1; l++)
                        temp.get(k).add(0);
                }

                for (int k = 0; k < matrix.size(); k++) {
                    for (int l = 0; l < matrix.get(k).size(); l++) {
                        if (k != i && l != j) {
                            int rowIndex = k < i ? k : k - 1;
                            int colIndex = l < j ? l : l - 1;
                            temp.get(rowIndex).set(colIndex, matrix.get(k).get(l));
                        }
                    }
                }

                algebraicComplements.get(i).set(j, (int) (Math.pow(-1, i + j) * determinant(temp)));
            }
        }

        return algebraicComplements;
    }
}