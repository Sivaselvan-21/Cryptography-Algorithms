import java.util.Random;

public class MillerRobin {

    static long power(long a, long d, long n) {
        long result = 1;
        a = a % n;

        while (d > 0) {
            if ((d & 1) == 1)
                result = (result * a) % n;

            a = (a * a) % n;
            d >>= 1;
        }
        return result;
    }

    static boolean isPrime(long n, int k) {
        if (n <= 1 || n == 4) return false;
        if (n <= 3) return true;

        // n − 1 = d × 2^r
        long d = n - 1;
        while (d % 2 == 0)
            d /= 2;

        Random rand = new Random();

        for (int i = 0; i < k; i++) {
            long a = 2 + Math.abs(rand.nextLong()) % (n - 4);
            long x = power(a, d, n);

            if (x == 1 || x == n - 1)
                continue;

            long temp = d;
            boolean composite = true;

            while (temp != n - 1) {
                x = (x * x) % n;
                temp *= 2;

                if (x == 1) return false;
                if (x == n - 1) {
                    composite = false;
                    break;
                }
            }

            if (composite) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        long n = Long.parseLong(args[0]);
        int iterations = 5;

        System.out.print(isPrime(n, iterations)
                ? "Prime"
                : "Composite");
    }
}
