import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Angry {

    private static int N;
    private static int[] hb;
    private static int[][] dp;

    public static void main (String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(in.readLine());
        hb = new int[N];
        dp = new int[2][N];
        for (int i = 0; i<N; i++){
            hb[i] = Integer.parseInt(in.readLine());
        }
        Arrays.sort(hb);
        for (int i = 1; i<N; i++){
            dp[0][i] = Math.max(dp[0][i-1]+1, hb[i]-hb[i-1]);
        }
        for (int i = N-2; i>=0; i--){
            dp[1][i] = Math.max(dp[1][i+1]+1, hb[i+1]-hb[i]);
        }
        double powerMin = Integer.MAX_VALUE;
        for (int i = 0; i<N-1; i++){
            double dist = (hb[i+1]-hb[i])/2.0;
            double tempPower = Math.max(Math.max(dp[0][i]+1.0, dist), dp[1][i+1]+1.0);
            if (tempPower<powerMin) powerMin = tempPower;
        }
        System.out.printf("%.1f", powerMin);
        System.out.println();
    }
}
