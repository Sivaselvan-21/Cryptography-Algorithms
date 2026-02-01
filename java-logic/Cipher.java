// Cipher.java

public class Cipher {
    public static void main(String[] args) {
        if(args.length < 3){
            System.out.println("Usage: java Cipher <encrypt/decrypt> <key> <text>");
            return;
        }

        String mode = args[0];
        int key = Integer.parseInt(args[1]);
        String text = args[2];
        
        if(mode.equalsIgnoreCase("decrypt")) key = -key;

        System.out.println(shiftCipher(text, key));
    }

    static String shiftCipher(String text, int key) {
        StringBuilder sb = new StringBuilder();
        for(char c : text.toCharArray()) {
            if(Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                sb.append((char)(((c - base + key + 26) % 26) + base));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}

