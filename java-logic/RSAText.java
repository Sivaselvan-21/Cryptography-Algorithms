import java.math.BigInteger;


public class RSAText {

    static BigInteger p, q, n, phi, e, d;
    static int bitLength = 512;   // Large enough for text

    public static void generateKeys() {


        p =BigInteger.valueOf(11);
        q = BigInteger.valueOf(13);

        n = p.multiply(q);
        phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        e = BigInteger.valueOf(7); // Common public exponent

        if (!phi.gcd(e).equals(BigInteger.ONE)) {
            e = BigInteger.valueOf(3);
        }

        d = e.modInverse(phi);

        System.out.println("=========== RSA KEY GENERATION ===========");
        System.out.println("p           : " + p);
        System.out.println("q           : " + q);
        System.out.println("n           : " + n);
        System.out.println("phi(n)      : " + phi);
        System.out.println("Public key  : (" + e + ", " + n + ")");
        System.out.println("Private key : (" + d + ", " + n + ")");
        System.out.println("==========================================");
    }

    public static void encrypt(String message) {

        // Convert text → BigInteger
        BigInteger msgNumber = new BigInteger(message.getBytes());

        System.out.println("\n=========== ENCRYPTION ===========");
        System.out.println("Original Text      : " + message);
        System.out.println("As BigInteger      : " + msgNumber);

        BigInteger cipher = msgNumber.modPow(e, n);

        System.out.println("Ciphertext         : " + cipher);
        System.out.println("==================================");

        System.out.println("\nFinal Ciphertext:");
        System.out.println(cipher);
    }

    public static void decrypt(String cipherText) {

        BigInteger cipher = new BigInteger(cipherText);

        System.out.println("\n=========== DECRYPTION ===========");
        System.out.println("Ciphertext         : " + cipher);

        BigInteger decrypted = cipher.modPow(d, n);

        System.out.println("Decrypted Number   : " + decrypted);

        // Convert back to text
        String originalText = new String(decrypted.toByteArray());

        System.out.println("Decrypted Text     : " + originalText);
        System.out.println("==================================");

        System.out.println("\nFinal Decrypted Text:");
        System.out.println(originalText);
    }

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Usage:");
            System.out.println("Encrypt: java RSAText encrypt HELLO");
            System.out.println("Decrypt: java RSAText decrypt <ciphertext>");
            return;
        }

        generateKeys();

        if (args[0].equalsIgnoreCase("encrypt")) {
            encrypt(args[1]);
        } else if (args[0].equalsIgnoreCase("decrypt")) {
            decrypt(args[1]);
        } else {
            System.out.println("Invalid option.");
        }
    }
}
