package cn.xhu.edu.util;

public class EditDistanceUtil {

    public static int calculate(String s, String t) {
        return getEditDistance(s, t);
    }

    //返回三�?�最小�??
    private static int Minimum(int a, int b, int c) {
        int im = a < b ? a : b;
        return im < c ? im : c;
    }

    private static int getEditDistance(String s, String t) {
        int d[][]; // matrix
        int n; // length of s
        int m; // length of t
        int i; // iterates through s
        int j; // iterates through t
        char s_i; // ith character of s
        char t_j; // jth character of t
        int cost; // cost

        // Step 1
        n = s.length();
        m = t.length();
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];

        // Step 2
        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }
        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }

        // Step 3
        for (i = 1; i <= n; i++) {
            s_i = s.charAt(i - 1);
            // Step 4
            for (j = 1; j <= m; j++) {
                t_j = t.charAt(j - 1);
                // Step 5
                cost = (s_i == t_j) ? 0 : 1;
                // Step 6
                d[i][j] = Minimum(d[i - 1][j] + 1, d[i][j - 1] + 1,
                        d[i - 1][j - 1] + cost);
            }
        }
        // Step 7
        // print(d, m, n);
        return d[n][m];
    }
    /*
       public static void print(int d[][],int m, int n){
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= m; j++) {
                    System.out.print(d[i][j]+" ");
                }
                System.out.println();
            }
        }
    */
}