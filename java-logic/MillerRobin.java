import java.util.Random;

public class MillerRobin {

    static long power(long a, long d, long n, StringBuilder steps) {
        long result = 1;
        a = a % n;
        steps.append("Compute ").append(a).append("^").append(d)
             .append(" mod ").append(n).append("\n");

        while (d > 0) {
            if ((d & 1) == 1) {
                result = (result * a) % n;
                steps.append("  result = ").append(result).append("\n");
            }
            a = (a * a) % n;
            d >>= 1;
        }
        return result;
    }

    static String isPrimeWithSteps(long n, int k) {
        StringBuilder steps = new StringBuilder();

        if (n <= 1 || n == 4) return "Composite";
        if (n <= 3) return "Prime";

        steps.append("n = ").append(n).append("\n");

        long d = n - 1;
        int r = 0;
        while (d % 2 == 0) {
            d /= 2;
            r++;
        }

        steps.append("n - 1 = 2^").append(r)
             .append(" * ").append(d).append("\n\n");

        Random rand = new Random();

        for (int i = 1; i <= k; i++) {
            long a = 2 + Math.abs(rand.nextLong()) % (n - 4);
            steps.append("Iteration ").append(i).append(": a = ").append(a).append("\n");

            long x = power(a, d, n, steps);
            steps.append("x = ").append(x).append("\n");

            if (x == 1 || x == n - 1) {
                steps.append("Pass this iteration\n\n");
                continue;
            }

            long temp = d;
            boolean composite = true;

            while (temp != n - 1) {
                x = (x * x) % n;
                temp *= 2;
                steps.append("Square x -> ").append(x).append("\n");

                if (x == n - 1) {
                    composite = false;
                    steps.append("Becomes n-1 ->Pass\n\n");
                    break;
                }
            }

            if (composite) {
                steps.append("Composite detected\n");
                return steps.toString();
            }
        }

        steps.append("Result: Prime\n");
        return steps.toString();
    }

    public static void main(String[] args) {
        long n = Long.parseLong(args[0]);
        System.out.print(isPrimeWithSteps(n, 1));
    }
}
