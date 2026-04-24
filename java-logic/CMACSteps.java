import java.util.Arrays;

public class CMACSteps {

    static final int BLOCK_SIZE = 16;

    // XOR
    static byte[] xor(byte[] a, byte[] b) {
        byte[] res = new byte[a.length];
        for (int i = 0; i < a.length; i++)
            res[i] = (byte) (a[i] ^ b[i]);
        return res;
    }

    // Left shift
    static byte[] leftShift(byte[] input) {
        byte[] res = new byte[input.length];
        int carry = 0;

        for (int i = input.length - 1; i >= 0; i--) {
            int val = (input[i] & 0xff) << 1;
            res[i] = (byte) ((val & 0xff) | carry);
            carry = (val >> 8) & 1;
        }
        return res;
    }

    // SIMPLE encryption (for demo)
    static byte[] encrypt(byte[] block, byte[] key) {
        return xor(block, key); // demo only
    }

    static void printHex(String label, byte[] data) {
        System.out.print(label + ": ");
        for (byte b : data)
            System.out.printf("%02x ", b);
        System.out.println();
    }

    public static void cmacSteps(String keyStr, String messageStr) {

        byte[] key = keyStr.getBytes();
        byte[] message = messageStr.getBytes();

        System.out.println("INPUT MESSAGE: " + messageStr);
        System.out.println("KEY: " + keyStr);

        // -------- SUBKEY GENERATION --------
        System.out.println("\nSTEP 1: Generate Subkeys");

        byte[] zero = new byte[BLOCK_SIZE];
        byte[] L = encrypt(zero, key);
        printHex("L", L);

        byte[] K1 = leftShift(L);
        if ((L[0] & 0x80) != 0)
            K1[BLOCK_SIZE - 1] ^= 0x87;

        printHex("K1", K1);

        byte[] K2 = leftShift(K1);
        if ((K1[0] & 0x80) != 0)
            K2[BLOCK_SIZE - 1] ^= 0x87;

        printHex("K2", K2);

        // -------- SPLIT BLOCKS --------
        int n = (message.length + BLOCK_SIZE - 1) / BLOCK_SIZE;
        boolean complete = (message.length % BLOCK_SIZE == 0);

        System.out.println("\nSTEP 2: Blocks = " + n);

        byte[] lastBlock = new byte[BLOCK_SIZE];

        if (complete) {
            System.arraycopy(message, (n - 1) * BLOCK_SIZE, lastBlock, 0, BLOCK_SIZE);
            lastBlock = xor(lastBlock, K1);
            System.out.println("Last block is complete → XOR with K1");
        } else {
            int len = message.length % BLOCK_SIZE;
            System.arraycopy(message, (n - 1) * BLOCK_SIZE, lastBlock, 0, len);
            lastBlock[len] = (byte) 0x80;
            lastBlock = xor(lastBlock, K2);
            System.out.println("Last block is NOT complete → Padding + XOR with K2");
        }

        printHex("Last Block", lastBlock);

        // -------- PROCESS --------
        System.out.println("\nSTEP 3: Processing");

        byte[] X = new byte[BLOCK_SIZE];
        byte[] Y;

        for (int i = 0; i < n - 1; i++) {
            byte[] block = Arrays.copyOfRange(message, i * BLOCK_SIZE, (i + 1) * BLOCK_SIZE);

            System.out.println("\nBLOCK " + i);
            printHex("Block", block);

            Y = xor(X, block);
            printHex("Y = X XOR Block", Y);

            X = encrypt(Y, key);
            printHex("X = Encrypt(Y)", X);
        }

        // Final block
        System.out.println("\nFINAL BLOCK PROCESSING");

        Y = xor(X, lastBlock);
        printHex("Y = X XOR LastBlock", Y);

        X = encrypt(Y, key);
        printHex("MAC", X);

        System.out.println("\nFINAL CMAC:");
        for (byte b : X)
            System.out.printf("%02x", b);
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java CMACSteps <key> <message>");
            return;
        }

        cmacSteps(args[0], args[1]);
    }
}
