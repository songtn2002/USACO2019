import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class TreenetworkCore {

    private static int N;
    private static int S;

    private static LinkedList<Edge>[] al;
    private static DFSRec[][] dp;
    private static int diaLen = 0;
    private static LinkedList<DFSRec> diameters = new LinkedList<>();

    private static DFSRec dfs (LinkedList<Edge> record){
        Edge last = record.getLast();
        if (dp[last.u][last.v]!=null) return dp[last.u][last.v];
        LinkedList<Edge> expEdges = al[last.v];
        DFSRec res = new DFSRec(new LinkedList<>(), 0);
        for (Edge e: expEdges){
            if (e.equals(last)) continue;
            record.add(e);
            DFSRec nxt = dfs(record);
            if (nxt.len+e.w>res.len){
                res.len = nxt.len+e.w;
                nxt.record.add(e);
                res.record = nxt.record;
            }
            record.removeLast();
        }
        dp[last.u][last.v] = res;
        if (res.len>diaLen) {
            diaLen = res.len;
            diameters.clear();
            diameters.add(res);
        }else if (res.len == diaLen){
            diameters.add(res);
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        N = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());
        al = new LinkedList[N+1];
        for (int i = 1; i<=N; i++){
            al[i] = new LinkedList<>();
        }
        dp = new DFSRec[N+1][N+1];

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

        @Override
        public boolean equals(Object obj) {
            Edge e = (Edge) obj;
            if ( (this. u == e.u && this.v == e.v) || (this.v == e.u&&this.u==e.v) ){
                return true;
            }else{
                return false;
            }
        }
    }

    private static class DFSRec{

        LinkedList<Edge> record;
        int len;

        public DFSRec(LinkedList<Edge> record, int len) {
            this.record = record;
            this.len = len;
        }
    }

    private static class Diameter{

        Edge[] edges;
        int len;

        public Diameter(LinkedList<Edge> list) {
            this.edges = new Edge[list.size()];
            this.len = 0;
            int counter = 0;
            for (Edge e: list){
                edges[counter] = e;
                len+=e.w;
                counter++;
            }
        }

    }
}
