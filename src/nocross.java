import java.io.*;
import java.util.Arrays;

public class nocross {

    private static int N;

    private static int[] dict;
    private static int[] dictBack;
    private static int[] cows;

    private static int[] dp;

    public static void main (String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("nocross.in")));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("nocross.out")));
        N = Integer.parseInt(in.readLine());
        dict = new int[N+1];
        dictBack = new int[N+1];
        cows = new int[N];
        dp = new int[N];
        Arrays.fill(dp, Integer.MAX_VALUE);
        for (int i = 1; i<=N; i++){
            int num = Integer.parseInt(in.readLine());
            dict[num] = i;
            dictBack[i] = num;
        }
        for (int i = 0; i< N; i++){
            cows[i] = dict[Integer.parseInt(in.readLine())];
        }
        int len = 0;
        for (int i: cows){
            int realNum = dictBack[i];
            int min = Math.max(1, realNum-4);
            int max = Math.min(N, realNum+4);
            int[] toInd = new int[max-min+1];
            for (int k = min; k<=max; k++){
                toInd[k-min] = dict[k];
            }
            Arrays.sort(toInd);
            for (int k = toInd.length-1; k>=0; k--){
                int j = toInd[k];
                int insertInd = Arrays.binarySearch(dp, j);
                if (insertInd>=0){
                    continue;
                }else {
                    insertInd = -(insertInd+1);
                }
                dp[insertInd] = j;
                len = Math.max(len, insertInd+1);
            }
        }
        out.println(len);
        out.close();
    }
}
