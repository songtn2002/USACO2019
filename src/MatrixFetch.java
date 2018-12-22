import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class MatrixFetch {

    private static int N;
    private static int M;
    private static int[][] matrix;

    private static BigInteger max = BigInteger.ZERO;

    private static BigInteger calcMax(int[] line){
        BigInteger[][] dp = new BigInteger[M][M];
        for (int i = 0; i<M; i++){
            for (int j = 0; j<M; j++){
                dp[i][j] = BigInteger.ZERO;
            }
        }
        BigInteger coeff = BigInteger.ONE;
        for (int i = 1; i < M; i++){
            coeff = coeff.multiply(BigInteger.valueOf(2));
            for (int j = 0; j<=i; j++){
                int st = j;
                int nd = j+(M-1-i);
                BigInteger fstOp = BigInteger.ZERO;
                if (nd<M-1) fstOp= dp[i-1][j].add(coeff.multiply(BigInteger.valueOf(line[nd+1])));
                BigInteger sndOp = BigInteger.ZERO;
                if (j!=0) sndOp = dp[i-1][j-1].add(BigInteger.valueOf(line[st-1]).multiply(coeff));
                //System.out.println(fstOp.toString()+"+"+sndOp.toString());
                if (fstOp.compareTo(sndOp)<0){
                    dp[i][j] = sndOp;
                }else{
                    dp[i][j] = fstOp;
                }
            }
        }
        //System.out.println("IN: "+Arrays.toString(line));
        //for (BigInteger[] a: dp)System.out.println(Arrays.deepToString(a));
        //System.out.println("------------------------------");
        BigInteger ans = BigInteger.ZERO;
        coeff = coeff.multiply(BigInteger.valueOf(2));
        for (int i = 0; i<M; i++){
            BigInteger score = dp[M-1][i].add(coeff.multiply(BigInteger.valueOf(line[i])));
            if (score.compareTo(ans) > 0) ans = score;
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
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
            max = max.add(calcMax(line));
        }
        System.out.println(max.toString());
    }
}
