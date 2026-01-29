public class HillCipher {

    static int[][] key = {
        {3, 3},
        {2, 5}
    };

    static int[][] invKey = {
        {15, 17},
        {20, 9}
    }; // inverse of key mod 26

    
    private static char encryptChar(char c, int val) {
        if (Character.isUpperCase(c)) {
            return (char) ((val % 26 + 26) % 26 + 'A');
        } else if (Character.isLowerCase(c)) {
            return (char) ((val % 26 + 26) % 26 + 'a');
        } else {
            return c; 
        }
    }

    static String process(String text, boolean decrypt) {
        StringBuilder result = new StringBuilder();
        int[][] mat = decrypt ? invKey : key;

       
        if (text.length() % 2 != 0) {
            char lastChar = text.charAt(text.length() - 1);
            text += Character.isUpperCase(lastChar) ? 'X' : (Character.isLowerCase(lastChar) ? 'x' : 'X');
        }

        for (int i = 0; i < text.length(); i += 2) {
            char c1 = text.charAt(i);
            char c2 = text.charAt(i + 1);

            int a = Character.isUpperCase(c1) ? c1 - 'A' : (Character.isLowerCase(c1) ? c1 - 'a' : 0);
            int b = Character.isUpperCase(c2) ? c2 - 'A' : (Character.isLowerCase(c2) ? c2 - 'a' : 0);

            int x = mat[0][0] * a + mat[0][1] * b;
            int y = mat[1][0] * a + mat[1][1] * b;

            result.append(encryptChar(c1, x));
            result.append(encryptChar(c2, y));
        }

        return result.toString();
    }

    public static void main(String[] args) {
        boolean decrypt = args[0].equals("decrypt");
        String text = args[1];

        System.out.print(process(text, decrypt));
    }
}
