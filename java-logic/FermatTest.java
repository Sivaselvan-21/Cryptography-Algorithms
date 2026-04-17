import java.util.Random;

public class FermatTest {

    static long power(long a, long exp, long n, StringBuilder steps) {
        long result = 1;
        a = a % n;

        steps.append("Compute ").append(a).append("^")
             .append(exp).append(" mod ").append(n).append("\n");

        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * a) % n;
                steps.append("  result = ").append(result).append("\n");
            }
            a = (a * a) % n;
            exp >>= 1;
        }
        return result;
    }

    static String isPrimeWithSteps(long n, int k) {
        StringBuilder steps = new StringBuilder();

        if (n <= 1) return "Composite\n";
        if (n <= 3) return "Prime\n";

        steps.append("n = ").append(n).append("\n");
        steps.append("Using Fermat’s Little Theorem\n\n");

        Random rand = new Random();

        for (int i = 1; i <= k; i++) {
            long a = 2 + Math.abs(rand.nextLong()) % (n - 3);
            steps.append("Iteration ").append(i)
                 .append(": a = ").append(a).append("\n");

            long x = power(a, n - 1, n, steps);
            steps.append("x = ").append(x).append("\n");

            if (x != 1) {
                steps.append("x ≠ 1 → Composite detected\n");
                return steps.toString();
            }

            steps.append("x = 1 → Pass iteration\n\n");
        }

        steps.append("Result: Probably Prime\n");
        return steps.toString();
    }

    public static void main(String[] args) {
        long n = Long.parseLong(args[0]);
        System.out.print(isPrimeWithSteps(n, 1));
    }
}
