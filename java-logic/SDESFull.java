public class SDESFull {

    static int[] P10 = {3,5,2,7,4,10,1,9,8,6};
    static int[] P8  = {6,3,7,4,8,5,10,9};
    static int[] IP  = {2,6,3,1,4,8,5,7};
    static int[] IP_INV = {4,1,3,5,7,2,8,6};
    static int[] EP  = {4,1,2,3,2,3,4,1};
    static int[] P4  = {2,4,3,1};

    static int[][] S0 = {
        {1,0,3,2},
        {3,2,1,0},
        {0,2,1,3},
        {3,1,3,2}
    };

    static int[][] S1 = {
        {0,1,2,3},
        {2,0,1,3},
        {3,0,1,0},
        {2,1,0,3}
    };

    static String permute(String input, int[] table) {
        StringBuilder sb = new StringBuilder();
        for (int i : table)
            sb.append(input.charAt(i - 1));
        return sb.toString();
    }

    static String leftShift(String input, int n) {
        return input.substring(n) + input.substring(0, n);
    }

    static String xor(String a, String b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length(); i++)
            sb.append(a.charAt(i) == b.charAt(i) ? '0' : '1');
        return sb.toString();
    }

    static String sbox(String input, int[][] box) {
        int row = Integer.parseInt("" + input.charAt(0) + input.charAt(3), 2);
        int col = Integer.parseInt("" + input.charAt(1) + input.charAt(2), 2);
        int val = box[row][col];
        return String.format("%2s", Integer.toBinaryString(val)).replace(' ', '0');
    }

    

    static String[] generateKeys(String key) {

        System.out.println("========== KEY GENERATION ==========");

        String p10 = permute(key, P10);
        System.out.println("After P10      : " + p10);

        String left = p10.substring(0,5);
        String right = p10.substring(5);

        System.out.println("Left 5 bits    : " + left);
        System.out.println("Right 5 bits   : " + right);

        left = leftShift(left,1);
        right = leftShift(right,1);

        System.out.println("After LS-1 L   : " + left);
        System.out.println("After LS-1 R   : " + right);

        String k1 = permute(left + right, P8);
        System.out.println("K1             : " + k1);

        left = leftShift(left,2);
        right = leftShift(right,2);

        System.out.println("After LS-2 L   : " + left);
        System.out.println("After LS-2 R   : " + right);

        String k2 = permute(left + right, P8);
        System.out.println("K2             : " + k2);

        System.out.println("====================================\n");

        return new String[]{k1, k2};
    }

   

    static String[] fk(String left, String right, String key, int round) {

        System.out.println("----- ROUND " + round + " -----");
        System.out.println("L" + (round-1) + " : " + left);
        System.out.println("R" + (round-1) + " : " + right);

        String ep = permute(right, EP);
        System.out.println("After EP        : " + ep);

        String xorResult = xor(ep, key);
        System.out.println("After XOR       : " + xorResult);

        String left4 = xorResult.substring(0,4);
        String right4 = xorResult.substring(4);

        String s0Out = sbox(left4, S0);
        String s1Out = sbox(right4, S1);

        System.out.println("S0 Output       : " + s0Out);
        System.out.println("S1 Output       : " + s1Out);

        String combined = s0Out + s1Out;
        System.out.println("S0||S1          : " + combined);

        String p4 = permute(combined, P4);
        System.out.println("After P4        : " + p4);

        String newLeft = xor(left, p4);
        System.out.println("L" + round + " = L" + (round-1) + " XOR P4 : " + newLeft);

        System.out.println("---------------------------\n");

        return new String[]{newLeft, right};
    }

    // ---------- Encryption ----------

    static String encrypt(String plaintext, String key) {

        String[] keys = generateKeys(key);
        String k1 = keys[0];
        String k2 = keys[1];

        System.out.println("=========== ENCRYPTION ===========");

        String ip = permute(plaintext, IP);
        System.out.println("After IP        : " + ip);

        String left = ip.substring(0,4);
        String right = ip.substring(4);

        // Round 1
        String[] round1 = fk(left, right, k1, 1);
        String L1 = round1[0];
        String R1 = round1[1];

        // Swap
        System.out.println("After SWAP      : " + R1 + L1 + "\n");

        // Round 2
        String[] round2 = fk(R1, L1, k2, 2);
        String L2 = round2[0];
        String R2 = round2[1];

        String preOutput = L2 + R2;
        System.out.println("Before IP-1     : " + preOutput);

        String cipher = permute(preOutput, IP_INV);
        System.out.println("After IP-1      : " + cipher);

        return cipher;
    }

    // ---------- Decryption ----------

    static String decrypt(String ciphertext, String key) {

        String[] keys = generateKeys(key);
        String k1 = keys[0];
        String k2 = keys[1];

        System.out.println("=========== DECRYPTION ===========");

        String ip = permute(ciphertext, IP);
        System.out.println("After IP        : " + ip);

        String left = ip.substring(0,4);
        String right = ip.substring(4);

        // Round 1 (use K2 first)
        String[] round1 = fk(left, right, k2, 1);
        String L1 = round1[0];
        String R1 = round1[1];

        System.out.println("After SWAP      : " + R1 + L1 + "\n");

        // Round 2 (use K1)
        String[] round2 = fk(R1, L1, k1, 2);
        String L2 = round2[0];
        String R2 = round2[1];

        String preOutput = L2 + R2;
        System.out.println("Before IP-1     : " + preOutput);

        String plain = permute(preOutput, IP_INV);
        System.out.println("After IP-1      : " + plain);

        return plain;
    }

    // ---------- MAIN ----------

    public static void main(String[] args) {

        String mode = args[0];
        String key = args[1];
        String text = args[2];

        if (mode.equals("encrypt")) {
            System.out.println("Final Ciphertext: " + encrypt(text, key));
        } else {
            System.out.println("Final Plaintext: " + decrypt(text, key));
        }
    }
}
