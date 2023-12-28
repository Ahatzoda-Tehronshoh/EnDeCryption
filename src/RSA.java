import java.math.BigInteger;
import java.util.Vector;

class RSA {
    public static BigInteger[] generateED(int p, int q) {
        int fiN = (p - 1) * (q - 1);

        BigInteger eBI = null, dBI = null;
        for (int e = fiN - 1; e >= 2; --e)
            if (gcd(e, fiN) == 1) {
                eBI = new BigInteger(e + "");
                dBI = eBI.modInverse(new BigInteger(fiN + ""));

                if (eBI.compareTo(dBI) != 0)
                    break;
            }

        return new BigInteger[]{eBI, dBI};
    }

    public static void encrypt(int p, int q, BigInteger e, Vector<Character> text) {
        BigInteger n = new BigInteger((p * q) + "");

        StringBuilder str = new StringBuilder();
        for (char symbol : text) {
            BigInteger symbolBI = new BigInteger(((int)symbol) + "");
            str.append((char) (symbolBI.modPow(e, n).intValue()));
        }

        text.clear();
        for (char ch : str.toString().toCharArray())
            text.add(ch);
    }

    public static void decrypt(int p, int q, BigInteger d, Vector<Character> text) {
        BigInteger n = new BigInteger((p * q) + "");

        StringBuilder str = new StringBuilder();
        for (char symbol : text) {
            BigInteger symbolBI = new BigInteger(((int) symbol) + "");
            str.append((char) (symbolBI.modPow(d, n).intValue()));
        }

        text.clear();
        for (char ch : str.toString().toCharArray())
            text.add(ch);
    }

    private static int gcd(int e, int z) {
        if (e == 0)
            return z;

        return gcd(z % e, e);
    }
}
