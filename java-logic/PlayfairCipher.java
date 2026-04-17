import java.util.*;

public class PlayfairCipher {

    static char[][] matrix = new char[5][5];

    // Function to generate key matrix
    static void generateMatrix(String key) {
        boolean[] used = new boolean[26];
        key = key.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");

        StringBuilder sb = new StringBuilder();

        for (char c : key.toCharArray()) {
            if (!used[c - 'A']) {
                sb.append(c);
                used[c - 'A'] = true;
            }
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            if (c == 'J') continue;
            if (!used[c - 'A']) {
                sb.append(c);
                used[c - 'A'] = true;
            }
        }

        System.out.println("\nStep 1: Key Matrix Formation");
        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = sb.charAt(k++);
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Function to prepare plaintext
    static String prepareText(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char a = text.charAt(i);
            char b = (i + 1 < text.length()) ? text.charAt(i + 1) : 'X';

            if (a == b) {
                result.append(a).append('X');
            } else {
                result.append(a);
                if (i + 1 < text.length()) {
                    result.append(b);
                    i++;
                } else {
                    result.append('X');
                }
            }
        }

        System.out.println("\nStep 2: Prepared Text (Pairs): " + result);
        return result.toString();
    }

    static int[] findPosition(char c) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == c)
                    return new int[]{i, j};
            }
        }
        return null;
    }

 
    static String encrypt(String text) {
        StringBuilder cipher = new StringBuilder();

        System.out.println("\nStep 3: Encryption Process");

        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);

            int[] posA = findPosition(a);
            int[] posB = findPosition(b);

            System.out.println("\nPair: " + a + " " + b);
            System.out.println("Positions: (" + posA[0] + "," + posA[1] + ") and (" + posB[0] + "," + posB[1] + ")");

            if (posA[0] == posB[0]) { // Same row
                char c1 = matrix[posA[0]][(posA[1] + 1) % 5];
                char c2 = matrix[posB[0]][(posB[1] + 1) % 5];
                cipher.append(c1).append(c2);
                System.out.println("Same Row -> Replace with right letters: " + c1 + " " + c2);

            } else if (posA[1] == posB[1]) { // Same column
                char c1 = matrix[(posA[0] + 1) % 5][posA[1]];
                char c2 = matrix[(posB[0] + 1) % 5][posB[1]];
                cipher.append(c1).append(c2);
                System.out.println("Same Column -> Replace with below letters: " + c1 + " " + c2);

            } else { // Rectangle
                char c1 = matrix[posA[0]][posB[1]];
                char c2 = matrix[posB[0]][posA[1]];
                cipher.append(c1).append(c2);
                System.out.println("Rectangle Rule -> Replace with: " + c1 + " " + c2);
            }
        }

        return cipher.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Key: ");
        String key = sc.nextLine();

        System.out.print("Enter Plaintext: ");
        String text = sc.nextLine();

        generateMatrix(key);
        String prepared = prepareText(text);
        String cipher = encrypt(prepared);

        System.out.println("\nFinal Cipher Text: " + cipher);
    }
}
