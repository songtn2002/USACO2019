import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MatrixFetch {

    private static int N;
    private static int M;
    private static int[][] matrix;

    private static int max;

    private static int calcMax(int[] line){
        int[][] dp = new int[M][M];
        int coeff = 1;
        for (int i = 1; i < M; i++){
            coeff *=2;
            for (int j = 0; j<=i; j++){
                int st = j;
                int nd = j+(M-1-i);
                int fstOp = 0;
                if (nd<M-1) fstOp = dp[i-1][j]+line[nd+1]*coeff;
                int sndOp = 0;
                if (j!=0) sndOp = dp[i-1][j-1]+line[st-1]*coeff;
                dp[i][j] = Math.max(fstOp, sndOp);
            }
        }
        //System.out.println("IN: "+Arrays.toString(line));
        //for (int[] a: dp)System.out.println(Arrays.toString(a));
        //System.out.println("------------------------------");
        int ans = 0;
        coeff *=2;
        for (int i = 0; i<M; i++){
            int score = dp[M-1][i]+line[i]*coeff;
            if (score>ans) ans = score;
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream ("MatrixFetch.in")));
        StringTokenizer st = new StringTokenizer(in.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        matrix = new int[N][M];
        for (int i = 0; i<N; i++){
            st = new StringTokenizer(in.readLine());
            for (int j = 0; j<M; j++){
                matrix[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        in.close();
        for (int[] line: matrix){
            max += calcMax(line);
        }
        System.out.println(max);
    }
}
