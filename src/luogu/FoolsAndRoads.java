package luogu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class FoolsAndRoads {

    private static int N;
    private static int K;

    private static LinkedList<Edge> edges = new LinkedList<>();
    private static LinkedList<Edge>[] al;
    private static LinkedList<Tour> tours = new LinkedList<>();
    private static Edge[] alTL;
    private static Edge[] alTR;
    private static int[][] mem;

    private static int root;
    private static int[] VS;
    private static int[] depth;
    private static int[] id;

    private static int[] book;
    private static int k = -1;
    private static void dfs (int c, int dp){
        k++;
        VS[k] = c;
        depth[k] = dp;
        if (book[c] == 0){
            book[c] = 1;
            id[c] = k;
        }
        for (Edge e : al[c]){
            if (book[e.v] == 0){
                dfs(e.v, dp+1);
            }
            k++;
            VS[k] = c;
            depth[k] = dp;
        }
    }

    private static int[][] rmq;

    private static void ST(){
        for (int j = 0; j < rmq[0].length; j++){
            for (int i = 0; i<rmq.length; i++){
                if (j == 0) {
                    rmq[i][j] = VS[i];
                }else{
                    int len = (int) Math.pow(2, j);
                    if (i+len>=rmq.length) continue;
                    int depth1 = depth[id[rmq[i][j-1]]];
                    int depth2 = depth[id[rmq[i+len][j-1]]];
                    if (depth1 < depth2){
                        rmq[i][j] = rmq[i][j-1];
                    }else{
                        rmq[i][j] = rmq[i+len][j-1];
                    }
                }
            }
        }
    }

    private static int query (int i , int j){
        int dist = j-i+1;
        int v = (int) (Math.log(dist)/Math.log(2));
        int offset = (int) (Math.pow(2, v));
        int depth1 = depth[id[rmq[i][v]]];
        int depth2 = depth[id[rmq[j-offset+1][v]]];
        if (depth1<depth2) {
            return rmq[i][v];
        }else{
            return rmq[j-offset+1][v];
        }
    }

    public static void main (String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(in.readLine());
        al = new LinkedList[N+1];
        id = new int[N+1];
        VS = new int[N*2-1];
        book = new int[N+1];
        depth = new int[N*2-1];
        mem = new int[N+1][N+1];
        alTR = new Edge[N+1];
        alTL = new Edge[N+1];
        for (int i = 1; i<=N; i++) {
            al[i] = new LinkedList<>();
        }
        for (int i = 1; i<N; i++){
            Edge[] es = Edge.parseEdges(in.readLine());
            al[es[0].u].add(es[0]);
            al[es[1].u].add(es[1]);
            edges.add(es[0]);
            if (i==1) root = es[0].u;
        }
        K = Integer.parseInt(in.readLine());
        for (int i = 0; i<K; i++) tours.add(Tour.parseTour(in.readLine()));
        dfs (root, 1);
        System.out.println("VS: "+ Arrays.toString(VS));
        System.out.println("DP: "+Arrays.toString(depth));
        int rmqL = (int)Math.log(depth.length)+1;
        rmq = new int[VS.length][rmqL];
        ST();
        for (Edge e: edges){
            int depthU = depth[id[e.u]];
            int depthV = depth[id[e.v]];
            Edge o = new Edge(e.v, e.u);
            if (depthU > depthV){
                alTR[e.u] = e;
            }else{
                alTL[e.v] = o;
            }
        }
        for (Tour t: tours){
            int id1 = id[t.beg];
            int id2 = id[t.end];
            int LCA = query(Math.min(id1, id2), Math.max(id1, id2));
            int cur = t.beg;
            while (cur!=LCA){
                Edge togo = alTR[cur];
                mem[togo.u][togo.v]++;
                cur = togo.v;
            }
            while (cur!=t.end){
                Edge togo = alTL[cur];
                mem[togo.u][togo.v]++;
                cur = togo.v;
            }
            for (Edge e : edges){
                System.out.println(mem[e.u][e.v]+mem[e.v][e.u]);
            }
        }
    }

    private static class Edge{

        int u;
        int v;

        private static Edge[] parseEdges(String line){
            Edge[] res = new Edge[2];
            StringTokenizer st = new StringTokenizer(line);
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            res[0] = new Edge(a, b);
            res[1] = new Edge(b, a);
            return res;
        }

        public Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }

        @Override
        public boolean equals(Object obj) {
            Edge o = (Edge) obj;
            return (this.u == o.u && this.v == o.v) || (this.u==o.v && this.v ==o.u);
        }
    }

    private static class Tour{

        int beg;
        int end;

        private static Tour parseTour (String line){
            StringTokenizer st= new StringTokenizer(line);
            int beg = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            return new Tour (beg, end);
        }

        public Tour(int beg, int end) {
            this.beg = beg;
            this.end = end;
        }

    }
}
