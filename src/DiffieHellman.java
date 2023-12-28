import java.math.BigInteger;
import java.util.HashSet;
import java.util.Vector;

public class DiffieHellman {
    public static BigInteger primitiveRoot(BigInteger a) {
        BigInteger q = new BigInteger("0");
        for(BigInteger i = a.subtract(BigInteger.ONE); i.compareTo(BigInteger.ZERO) > 0; i = i.subtract(BigInteger.ONE)) {
            HashSet<String> set = new HashSet<>();
            for(BigInteger j = BigInteger.ONE; j.compareTo(a) < 0; j = j.add(BigInteger.ONE)) {
                String mod = a.modPow(j, i).toString();
                if(set.contains(mod))
                    break;
                else
                    set.add(mod);
            }

            if(i.compareTo(new BigInteger(String.valueOf(set.size() + 1))) == 0) {
                q = i;
                break;
            }
        }

        return q;
    }

    public static void encrypt(int a, Vector<Character> text) {

    }

    public static void decrypt(Vector<Character> key, Vector<Character> text) {
    }
}