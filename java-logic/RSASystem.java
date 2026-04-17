import java.math.BigInteger;
import java.security.SecureRandom;

public class RSASystem {

    // ---------- Key Generation ----------
    static BigInteger p, q, n, phi, e, d;

    static void generateKeys() {

        System.out.println("========== RSA KEY GENERATION ==========");

        SecureRandom random = new SecureRandom();

        // small primes for demo (not secure)
        p = BigInteger.probablePrime(8, random);
        q = BigInteger.probablePrime(8, random);

        System.out.println("Prime p        : " + p);
        System.out.println("Prime q        : " + q);

        n = p.multiply(q);
        System.out.println("n = p*q        : " + n);

        phi = (p.subtract(BigInteger.ONE))
                .multiply(q.subtract(BigInteger.ONE));
        System.out.println("phi(n)         : " + phi);

        e = BigInteger.valueOf(3);
        while (!phi.gcd(e).equals(BigInteger.ONE)) {
            e = e.add(BigInteger.TWO);
        }

        System.out.println("Public key e   : " + e);

        d = e.modInverse(phi);
        System.out.println("Private key d  : " + d);

        System.out.println("========================================\n");
    }

    // ---------- Encryption ----------
    static BigInteger encrypt(BigInteger message) {

        System.out.println("=========== ENCRYPTION ===========");
        System.out.println("Message (M)     : " + message);

        BigInteger cipher = message.modPow(e, n);

        System.out.println("Cipher (C=M^e mod n): " + cipher);
        System.out.println("==================================\n");

        return cipher;
    }

    // ---------- Decryption ----------
    static BigInteger decrypt(BigInteger cipher) {

        System.out.println("=========== DECRYPTION ===========");
        System.out.println("Cipher (C)      : " + cipher);

        BigInteger plain = cipher.modPow(d, n);

        System.out.println("Plain (M=C^d mod n): " + plain);
        System.out.println("==================================\n");

        return plain;
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {

        String mode = args[0];
        BigInteger input = new BigInteger(args[1]);

        generateKeys();

        if (mode.equals("encrypt")) {
            BigInteger cipher = encrypt(input);
            System.out.println("Final Ciphertext: " + cipher);
        } else {
            BigInteger plain = decrypt(input);
            System.out.println("Final Plaintext: " + plain);
        }
    }
}
