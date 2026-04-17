import java.math.BigInteger;

public class DiffieHellman {

    static BigInteger p, g; // public values
    static BigInteger a, b; // private keys
    static BigInteger A, B; // public keys
    static BigInteger keyA, keyB; // shared keys

    public static void main(String[] args) {

        // Check arguments
        if (args.length < 4) {
            System.out.println("Usage: java DiffieHellman <p> <g> <a> <b>");
            return;
        }

        p = new BigInteger(args[0]);
        g = new BigInteger(args[1]);
        a = new BigInteger(args[2]);
        b = new BigInteger(args[3]);

        System.out.println("===== Diffie-Hellman Key Exchange =====");

        // Step 1
        System.out.println("\n--- Step 1: Public Values ---");
        System.out.println("p = " + p);
        System.out.println("g = " + g);

        // Step 2
        System.out.println("\n--- Step 2: Private Keys ---");
        System.out.println("Private key of A (a) = " + a);
        System.out.println("Private key of B (b) = " + b);

        // Step 3
        A = g.modPow(a, p);
        B = g.modPow(b, p);

        System.out.println("\n--- Step 3: Public Keys ---");
        System.out.println("A = g^a mod p = " + g + "^" + a + " mod " + p + " = " + A);
        System.out.println("B = g^b mod p = " + g + "^" + b + " mod " + p + " = " + B);

        // Step 4
        keyA = B.modPow(a, p);
        keyB = A.modPow(b, p);

        System.out.println("\n--- Step 4: Shared Secret Computation ---");
        System.out.println("Key computed by A = B^a mod p = " + B + "^" + a + " mod " + p + " = " + keyA);
        System.out.println("Key computed by B = A^b mod p = " + A + "^" + b + " mod " + p + " = " + keyB);

        // Final
        System.out.println("\n===== RESULT =====");
        if (keyA.equals(keyB)) {
            System.out.println("Shared Secret Key: " + keyA);
            System.out.println("Key Exchange Successful!");
        } else {
            System.out.println("Key mismatch! Something went wrong.");
        }
    }
}
