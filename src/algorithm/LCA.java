package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class LCA {

    private static int N;
    private static int Q;
    private static int S;
    private static Edge[] edges;

    private static LinkedList<Edge>[] al;

    private static int[] vs;
    private static int[] depth;

    private static int[] id;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(in.readLine());
        Q = Integer.parseInt(in.readLine());
        S = Integer.parseInt(in.readLine());
        edges = new Edge[N];
        al = new LinkedList[N+1];
        vs = new int[N*2-1];
        depth = new int[N*2-1];
        id = new int[N+1];
        for (int i = 0; i<N-1; i++){
            
        }
    }

    private static class Edge{

        int u;
        int v;
        int w;

        public Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }


    }
}
