import java.io.*;
import java.util.Arrays;

public class mincross {

    private static int N;

    private static int[] dict;
    private static int[] cows;

    private static int[] filter(int[] a){
        int[] id = new int[N+1];
        int[] book = new int[N];
        int counter = 0;
        for (int i = 0; i<N; i++){
            int j = a[i];
            if (id[j] == 0){
                id[j] = i;
            }else{
                book[id[j]] = 1;
                book[i] = 1;
                counter+=2;
            }
        }
        int[] res = new int[N - counter];
        int index = 0;
        for (int i = 0; i < N; i++){
            if (book[i] == 0){
                res[index] = a[i];
                index++;
            }
        }
        return res;
    }

    private static int minInv;

    private static int calcInv (int[] array, int start, int end, int[] tmp){
        if (start == end) return 0;
        int middle = (start+end)/2;
        int res = calcInv(array, start, middle, tmp) + calcInv(array, middle+1, end, tmp);
        int fp = start;
        int sp = middle+1;
        int tmpP = start;
        while (fp<=middle && sp<=end){
            if (array[fp] <= array[sp]){
                tmp[tmpP] = array[fp];
                fp++;
            }else{
                tmp[tmpP] = array[sp];
                sp++;
                res+=middle-fp+1;
            }
            tmpP++;
        }
        while (fp<=middle) {
            tmp[tmpP] = array[fp];
            fp++;
            tmpP++;
        }
        while (sp<=end) {
            tmp[tmpP] = array[sp];
            sp++;
            tmpP++;
        }
        for (int i = start; i<=end; i++){
            array[i] = tmp[i];
        }
        return res;
    }

    public static void main (String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("mincross.in")));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("mincross.out")));
        N = Integer.parseInt(in.readLine());
        dict = new int[N+1];
        int[] rawLeft = new int[N];
        for (int i = 0; i< N; i++){
            rawLeft[i] = Integer.parseInt(in.readLine());
        }
        rawLeft = filter(rawLeft);
        for (int i = 1; i <= rawLeft.length; i++){
            dict[rawLeft[i-1]] = i;
        }
       // System.out.println("left: "+Arrays.toString(rawLeft));
        int[] rawRight = new int[N];
        for (int i = 0; i< N; i++){
            rawRight[i] = Integer.parseInt(in.readLine());
        }
        rawRight = filter(rawRight);
        cows = new int[rawRight.length];
        for (int i = 0; i<rawRight.length; i ++){
            int num = dict[rawRight[i]];
            cows[i] = num;
        }
        //System.out.println("right: "+Arrays.toString(rawRight));
        int[] orgCows = Arrays.copyOf(cows, cows.length);
        int[] temp = new int[orgCows.length];
        int inv = calcInv(orgCows, 0, cows.length-1, temp);
        minInv = inv;
        for (int i = cows.length-1; i>=1; i--){
            inv += 2*cows[i]-cows.length-1;
            if (inv < minInv) minInv = inv;
        }
        out.println(minInv);
        out.close();
    }

}
