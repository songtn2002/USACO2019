package DS;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BIT {

    private int len;
    private int[] BIT;

    public static BIT of (int[] array){
        int len = array.length;
        int[] BIT = new int[len+1];
        BIT res = new BIT(len, BIT);
        for (int i = 1; i<=array.length; i++){
            res.update(i, array[i-1]);
        }
        return res;
    }

    private static int lowBit (int num){
        return num & (-num);
    }

    public BIT(int len, int[] BIT) {
        this.len = len;
        this.BIT = BIT;
    }

    public int sum (int begIndex, int endIndex){
        return sum(endIndex) - sum(begIndex-1);
    }

    public int sum(int endIndex){
        int result = 0;
        for (int i = endIndex; i >0; i-=lowBit(i)){
            result += BIT[i];
        }
        return  result;
    }

    private void update(int index, int number){
        for (int i = index; i<=len; i = i+lowBit(i)){
            this.BIT[i] += number;
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(BIT);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("BIT.in")));
        int N = Integer.parseInt(in.readLine());
        int[] a = new int[N];
        StringTokenizer st = new StringTokenizer(in.readLine());
        for (int i = 0; i < N; i++){
            a[i] = Integer.parseInt(st.nextToken());
        }
        BIT bit = DS.BIT.of(a);
        System.out.println(bit.toString());
    }
}
