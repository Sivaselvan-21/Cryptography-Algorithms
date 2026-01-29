// GCD.java
public class GCD {
    public static void main(String[] args) {
        if(args.length < 2){
            System.out.println("Usage: java GCD <num1> <num2>");
            return;
        }
        long a = Long.parseLong(args[0]);
        long b = Long.parseLong(args[1]);
        System.out.println(gcd(a, b));
    }

    static long gcd(long a, long b){
        if(b == 0) return a;
        return gcd(b, a % b);
    }
}
