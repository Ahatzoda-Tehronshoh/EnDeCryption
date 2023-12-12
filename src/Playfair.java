import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class Playfair {
    private static char filler = 'X';

    private static char[][] createMatrix(Vector<Character> key) {
        char[][] matrix = new char[256][256];
        Set<Character> used = new HashSet<>();
        int x = 0, y = 0;

        for (Character c : key)
            if (!used.contains(c)) {
                matrix[x][y] = c;
                used.add(c);
                y++;
                if (y == 256) {
                    y = 0;
                    x++;
                }
            }

        for (int ch = 0; ch < 65536; ch++)
            if (!used.contains((char)ch)) {
                matrix[x][y] = (char)ch;
                y++;
                if (y == 256) {
                    y = 0;
                    x++;
                }
            }

        return matrix;
    }

    private static void encryptPair(char[][] matrix, char a, char b, StringBuilder encrypted) {
        int a_row = 0, a_col = 0, b_row = 0, b_col = 0;

        for (int x = 0; x < 256; x++)
            for (int y = 0; y < 256; y++) {
                if (matrix[x][y] == a) {
                    a_row = x;
                    a_col = y;
                } else if (matrix[x][y] == b) {
                    b_row = x;
                    b_col = y;
                }
            }

        if (a_row == b_row) {
            // Та же строка - след элемент в той же строки
            encrypted.append(matrix[a_row][(a_col + 1) % 256]);
            encrypted.append(matrix[b_row][(b_col + 1) % 256]);
        } else if (a_col == b_col) {
            // Тот же столбец - след элемент в том же столбце
            encrypted.append(matrix[(a_row + 1) % 256][a_col]);
            encrypted.append(matrix[(b_row + 1) % 256][b_col]);
        } else {
            // Разные строки и столбцы - углы прямоугольника
            encrypted.append(matrix[a_row][b_col]);
            encrypted.append(matrix[b_row][a_col]);
        }
    }

    public static void encrypt(Vector<Character> key, Vector<Character> text) {
        filler = 'X';
        char[][] keyMatrix = createMatrix(key);

        if(text.contains(filler)) {
            System.out.println("Filler changed!");
            for(int i = 48; i < 65536; i++)
                if(!text.contains((char)i)) {
                    filler = (char)i;
                    break;
                }
        }

        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < text.size(); i += 2) {
            char a = text.get(i);
            char b = filler;
            if((i + 1) < text.size())
                b = text.get(i + 1);
            else
                i--;

            if (a == b) {
                b = filler;
                i--;
            }

            encryptPair(keyMatrix, a, b, encrypted);
        }

        text.clear();
        for (char c : encrypted.toString().toCharArray())
            text.add(c);

        try(BufferedWriter bf = new BufferedWriter(new FileWriter("text.txt"))){
            for(char ch: text)
                bf.write(ch);
        } catch(IOException ee) {
            ee.printStackTrace();
        }

        System.out.println(filler);
    }

    public static void decrypt(Vector<Character> key, Vector<Character> text) {
        char[][] matrix = createMatrix(key);
        StringBuilder decrypted = new StringBuilder();

        for (int i = 0; i < text.size(); i += 2) {
            char a = text.get(i);
            char b = (i + 1 < text.size()) ? text.get(i + 1) : filler;

            int a_row = 0, a_col = 0, b_row = 0, b_col = 0;

            for (int x = 0; x < 256; x++)
                for (int y = 0; y < 256; y++) {
                    if (matrix[x][y] == a) {
                        a_row = x;
                        a_col = y;
                    } else if (matrix[x][y] == b) {
                        b_row = x;
                        b_col = y;
                    }
                }

            if (a_row == b_row) {
                // Та же строка - предыдущий элемент в той же строке
                decrypted.append(matrix[a_row][(a_col + 255) % 256]);
                decrypted.append(matrix[b_row][(b_col + 255) % 256]);
            } else if (a_col == b_col) {
                // Тот же столбец - предыдущий элемент в том же столбце
                decrypted.append(matrix[(a_row + 255) % 256][a_col]);
                decrypted.append(matrix[(b_row + 255) % 256][b_col]);
            } else {
                // Разные строки и столбцы - углы прямоугольника
                decrypted.append(matrix[a_row][b_col]);
                decrypted.append(matrix[b_row][a_col]);
            }
        }

        text.clear();
        for (char c : decrypted.toString().toCharArray())
            if(c != filler)
                text.add(c);
        System.out.println(filler);
    }
}
